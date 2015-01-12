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

	public int insertFile(File fileVO, int boardNo) {
		fileVO.setBoardNo(boardNo);
		fileVO.setFlag(1);
		return fileMapepr.insertFile(fileVO);
	}

	public int updateFile(File fileVO) {
		return fileMapepr.updateFile(fileVO);
	}

	public int deleteFile(int fileNo) {
		File file = fileMapepr.selectFileByFileNo(fileNo);
		// ������ ������ 0 ������ 1
		file.setFlag(0);
		return fileMapepr.deleteFile(file);
	}

	public List<File> selectFileByBoardNo(int boardNo) {
		return fileMapepr.selectFileByBoardNo(boardNo);
	}

	public File selectFileByFileNo(int fileNo) {
		System.out.println("���ϼ��ǽ� ���Ϲ�ȣ //" + fileNo);
		return fileMapepr.selectFileByFileNo(fileNo);
	}
}
