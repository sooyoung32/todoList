package kr.co.kware.boardpsy.file;

import java.util.List;


public interface FileMapper {
	
	public int insertFile(File file); 
	public int updateFile(File file);
	public int deleteFile(File file);
	public List<File> selectFileByBoardNo(int boardNo);
	public int selectFileCountByBoardNo();
	public File selectFileByFileNo(int fileNo);
	
}
