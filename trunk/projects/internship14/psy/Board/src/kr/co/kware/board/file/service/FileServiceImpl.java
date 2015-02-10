package kr.co.kware.board.file.service;

import java.util.List;

import kr.co.kware.board.file.mapper.FileMapper;
import kr.co.kware.board.file.vo.File;
import kr.co.kware.common.dbcommon.DeletionStatus;

import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class FileServiceImpl implements FileService{

	@Autowired
	private FileMapper fileMapepr;
	
	@Override
	public int insertFile(File fileVO, int articleNo) {
		fileVO.setArticleNo(articleNo);
		fileVO.setDeletionStatus(DeletionStatus.PRESENT);
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
		file.setDeletionStatus(DeletionStatus.DELETED);
		return fileMapepr.deleteFile(file);
	}

	@Override
	public List<File> getFileByArticleNo(int articleNo) {
		return fileMapepr.selectFileByArticleNo(articleNo);
	}

	@Override
	public File getFileByFileNo(int fileNo) {
		System.out.println("파일서피스 파일번호 //" + fileNo);
		return fileMapepr.selectFileByFileNo(fileNo);
	}
}
