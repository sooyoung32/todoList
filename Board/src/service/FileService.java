package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vo.File;
import mapper.FileMapper;

@Component
public class FileService {
	
	@Autowired
	FileMapper fileMapepr;
	
	public int insertFile(File fileVO, int boardNo){
		fileVO.setBoardNo(boardNo);
		return fileMapepr.insertFile(fileVO);
	}
	
	public int updateFile(File fileVO){
		return fileMapepr.updateFile(fileVO);
	}
	
	public int deleteFile(int fileNo){
		return fileMapepr.deleteFile(fileNo);
	}
	
	public List<File> selectFileByBoardNo(int boardNo){
		return fileMapepr.selectFileByBoardNo(boardNo);
	}
	
}
