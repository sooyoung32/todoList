package kr.co.kware.board.file.vo;

import java.util.List;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

@Alias("MultipartFileList")
public class MultipartFileList {
	
	List<MultipartFile> fileList;

	public List<MultipartFile> getFileList() {
		return fileList;
	}

	public void setFileList(List<MultipartFile> fileList) {
		this.fileList = fileList;
	}
	
	
	
	
	
}
