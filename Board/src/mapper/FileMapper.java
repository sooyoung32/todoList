package mapper;

import java.util.List;

import vo.File;

public interface FileMapper {
	
	public int insertFile(File file); 
	public int updateFile(File file);
	public int deleteFile(File file);
	public List<File> selectFileByBoardNo(int boardNo);
	public int selectFileCountByBoardNo();
	public File selectFileByFileNo(int fileNo);
	
}
