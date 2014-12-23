package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import service.FileService;
@Controller
public class FileController {
	@Autowired
	FileService fileServie;
	
	
	
	
}
