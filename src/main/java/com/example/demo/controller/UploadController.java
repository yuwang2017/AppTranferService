package com.example.demo.controller;

import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.CloudFileService;

@RestController
public class UploadController {

	@Autowired
	CloudFileService fservice;

	@PostMapping("/upload")
	public String uploadImageSpringWay(@RequestParam("uploaded_file") MultipartFile file,
			HttpServletResponse response) {

		String results = "";
		response.setStatus(405);
		String fileCode = getCode();
		boolean success = false;
		try {
			String fileName = file.getOriginalFilename();
			if (fileName.indexOf(".zip") < 0)
				return "Invalid File";
			byte[] bb = file.getBytes();
			results = fileName + " - " + bb.length;

			
			String newFileName = fileCode + ".zip";

			fservice.saveFile(newFileName, bb);
			success = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(501);
			results = ex.toString();
		}
		if(success) {
			response.setStatus(200);
			results = fileCode;
		}
		return results;
	}

	/*
	 * Generate a random code in the format of ABC1234
	 */
	private String getCode() {
		String code = "";

		Random random = new Random();

		String setOfCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		for (int i = 0; i < 3; i++) {
			int randomInt = random.nextInt(setOfCharacters.length());
			char randomChar = setOfCharacters.charAt(randomInt);
			code = code + randomChar;
		}
		for (int i = 0; i < 4; i++) {
			int randomInt = random.nextInt(10);
			code = code + Integer.toString(randomInt);
		}
		return code;
	}
}
