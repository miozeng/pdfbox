package com.zm.pdfbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PdfboxApplication {

	public static void main(String[] args) {
		System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");
		SpringApplication.run(PdfboxApplication.class, args);
	}
}
