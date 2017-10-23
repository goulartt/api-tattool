package com.utfpr.tattool.api.apitattool.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="art_image")
public class ImageS3 {
	 	@Id
	    @GeneratedValue(strategy=GenerationType.AUTO)
	    private long id;
	 
	 
	    @Column(name = "s3_key", nullable = false, length=200)
	    private String key;
	 
	    
	    @Column(name = "url", nullable = false, length=1000)
	    private String url;
	    

		public ImageS3(String key, String url) {
			
			this.key = key;
			this.url = url;
		}


		public long getId() {
			return id;
		}


		public void setId(long id) {
			this.id = id;
		}


		public String getKey() {
			return key;
		}


		public void setKey(String key) {
			this.key = key;
		}


		public String getUrl() {
			return url;
		}


		public void setUrl(String url) {
			this.url = url;
		}
	    
	    
}
