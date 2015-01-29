package kr.co.kware.boardpsy.file;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class FileServiceImpl implements FileService{

	@Autowired
	private FileMapper fileMapepr;
	
	@Override
	public int insertFile(File fileVO, int boardNo) {
		fileVO.setBoardNo(boardNo);
		fileVO.setFlag(1);
		System.err.println("파일서비스구현체 : insert");
		return fileMapepr.insertFile(fileVO);
	}

	@Override
	public int modifyFile(File fileVO) {
		return fileMapepr.updateFile(fileVO);
	}

	@Override
	public int deleteFile(int fileNo) {
		File file = fileMapepr.selectFileByFileNo(fileNo);
		// 파일이 없으면 0 있으면 1
		file.setFlag(0);
		return fileMapepr.deleteFile(file);
	}

	@Override
	public List<File> getFileByBoardNo(int boardNo) {
		return fileMapepr.selectFileByBoardNo(boardNo);
	}

	@Override
	public File getFileByFileNo(int fileNo) {
		System.out.println("파일서피스 파일번호 //" + fileNo);
		return fileMapepr.selectFileByFileNo(fileNo);
	}
}
