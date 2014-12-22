package vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class UploadFile {
	
	List<MultipartFile> multipartFile;

	public List<MultipartFile> getMultipartFile() {
		return multipartFile;
	}

	public void setMultipartFile(List<MultipartFile> multipartFile) {
		this.multipartFile = multipartFile;
	} 
	
	
	
}
