package kr.co.kware.board.file.service;

import java.util.List;

import kr.co.kware.board.file.vo.File;

public interface FileService {
	public int insertFile(File fileVO, int articleNo);
	public int modifyFile(File fileVO);
	public int deleteFile(int fileNo);
	public List<File> getFileByArticleNo(int articleNo);
	public File getFileByFileNo(int fileNo);
}
