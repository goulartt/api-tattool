package com.utfpr.tattool.api.apitattool.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.utfpr.tattool.api.apitattool.model.ImageS3;

@Service
public class FileArchiveService {
	
	private AmazonS3Client s3Client = new AmazonS3Client(new AWSCredentials() {
		@Override
		public String getAWSSecretKey() {
			// TODO Auto-generated method stub
			return "AKIAJKMB7ZN4NOORD6EQ";
		}
		
		@Override
		public String getAWSAccessKeyId() {
			// TODO Auto-generated method stub
			return "K6xPdIpdqzjU9dEV64SRFFddtS8zyOvACfpWgTc";
		}
	});
	
		
	

	private static final String S3_BUCKET_NAME = "api-tattool-tatuagens";

	/**
	 * Save image to S3 and return CustomerImage containing key and public URL
	 * 
	 * @param multipartFile
	 * @return
	 * @throws IOException
	 */
	public ImageS3 saveFileToS3(MultipartFile multipartFile) {
		try {
			File fileToUpload = convertFromMultiPart(multipartFile);
			String key = Instant.now().getEpochSecond() + "_" + fileToUpload.getName();

			/* save file */
			s3Client.putObject(new PutObjectRequest(S3_BUCKET_NAME, key, fileToUpload));

			/* get signed URL (valid for one year) */
			GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(S3_BUCKET_NAME, key);
			generatePresignedUrlRequest.setMethod(HttpMethod.GET);
			generatePresignedUrlRequest.setExpiration(DateTime.now().plusYears(1).toDate());

			URL signedUrl = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

			return new ImageS3(key, signedUrl.toString());
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		

	}
	public void deleteImageFromS3(ImageS3 customerImage){
		s3Client.deleteObject(new DeleteObjectRequest(S3_BUCKET_NAME, customerImage.getKey()));	
	}

	/**
	 * Convert MultiPartFile to ordinary File
	 * 
	 * @param multipartFile
	 * @return
	 * @throws IOException
	 */
	private File convertFromMultiPart(MultipartFile multipartFile) throws IOException {

		File file = new File(multipartFile.getOriginalFilename());
		file.createNewFile(); 
		FileOutputStream fos = new FileOutputStream(file); 
		fos.write(multipartFile.getBytes());
		fos.close(); 

		return file;
	}
}
