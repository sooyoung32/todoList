package kr.co.kware.boardpsy.file;

import java.util.List;

public interface FileService {
	public int insertFile(File fileVO, int boardNo);
	public int modifyFile(File fileVO);
	public int deleteFile(int fileNo);
	public List<File> getFileByBoardNo(int boardNo);
	public File getFileByFileNo(int fileNo);
}
