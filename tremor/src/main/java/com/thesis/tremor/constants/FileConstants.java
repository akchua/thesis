package com.thesis.tremor.constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   8 Sep 2017
 */
@Component
public class FileConstants {

	private final String fileHome;
	
	private final String testImageHome;
	
	private final String imageDefaultFileName;
	
	@Autowired
	public FileConstants(@Value("${file.home}") String fileHome,
						@Value("${file.image.defaultFileName}") String imageDefaultFileName) {
		this.fileHome = fileHome;
		this.testImageHome = fileHome + "program_data/test_image/";
		this.imageDefaultFileName = imageDefaultFileName;
	}

	public String getFileHome() {
		return fileHome;
	}

	public String getTestImageHome() {
		return testImageHome;
	}

	public String getImageDefaultFileName() {
		return imageDefaultFileName;
	}
}
