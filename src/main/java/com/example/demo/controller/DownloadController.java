package com.example.demo.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.CloudFileService;

@RestController
public class DownloadController {
	
	@Autowired
	CloudFileService fservice;

	@GetMapping("/download/{code}")
	public void downalod(@PathVariable String code, HttpServletResponse response) {
		if(code == null || code.trim().length() == 0) {
			response.setStatus(404);
			return;
		}
		byte[] content = fservice.retrieveFile( code.trim().toUpperCase() + ".zip" );		
		
		if(content == null) {
			response.setStatus(404);
		} else {
			try {
				response.setStatus(200);
				response.setContentType("application/zip");
				response.setContentLength(content.length);
				response.getOutputStream().write(content);
				response.getOutputStream().close();
			}catch(Exception e) {
				response.setStatus(500);
			}
		}
	}
	
	
	@GetMapping("/")
	public String hello() {
		return "App Transfer Service";
	}

}
