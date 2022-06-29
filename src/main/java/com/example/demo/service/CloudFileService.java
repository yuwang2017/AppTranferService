package com.example.demo.service;

import org.springframework.stereotype.Component;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Component
public class CloudFileService {
	
	public static final String BUCKET_NAME = "rangebuddy-transfer-bucket";
	

	private static Bucket bucket = null;
	
	static {
		Storage storage = StorageOptions.getDefaultInstance().getService();
		// Creates the new bucket
		bucket = storage.get(BUCKET_NAME);
	}
	
	public void saveFile(String fileName, byte[] content) {
		bucket.create(fileName, content);
	}

	public byte[] retrieveFile(String fileName) {
		Blob data = bucket.get(fileName);
		if (data != null) {
			return data.getContent();
		}
		return null;
	}
}
