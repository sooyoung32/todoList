package mapper;

import java.util.List;

import vo.File;

public interface FileMapper {
	
	public int insertFile(File file); 
	public int updateFile(File file);
	public int deleteFile(int fileNo);
	public List<File> selectFileByBoardNo(int boardNo);
	public int selectFileCountByBoardNo();
	
}
