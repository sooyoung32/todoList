package service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import mapper.BoardMapper;
import mapper.MemberMapper;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vo.Member;

@Component
public class MemberService {
	@Autowired
	private MemberMapper memberMapper;
	private Logger logger =Logger.getLogger(MemberService.class);
	
	// 가입
	public int joinMember(Member member) {
		try {
			member.setWritingDate(new Date());
			member.setWritingIP(InetAddress.getLocalHost().toString());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return memberMapper.insertMember(member);
	}

	// 비번수정
	public int updateMember(Member member, String password) throws UnknownHostException {

//		boolean pwCheck = false;
//		if (memberMapper.selectMember(member.getEmail()).getPassword().equals(member.getPassword())) {
//			pwCheck = true;
//		} else {
//			pwCheck = false;
//		}
//
//		if (pwCheck) {
//			member.setPassword(newPW);
//			member.setModifyDate(new Date());
//			member.setModifyIP(InetAddress.getLocalHost().toString());
//			return memberMapper.updateMember(member);
//		}
//		return 0;
		member.setPassword(password);
		member.setModifyDate(new Date());
		member.setModifyIP(InetAddress.getLocalHost().toString());
		logger.debug("멤버 서비스 페북 업데이트 : "+member);
		return memberMapper.updateMember(member);
		
	}

	// 회원탈퇴
	public int deleteMember(String email, String pw) {
		Member member = new Member();
		if (memberMapper.selectMember(member.getEmail()).getPassword().equals(pw)) {
			return memberMapper.deleteMember(email);
		} else {
			return 0;
		}
	}

	// 멤버선택
	public Member selectMember(String email) {
		return memberMapper.selectMember(email);
	}

	// 멤버 카운트
	public int selectMemberCount() {
		return memberMapper.selectMemberCount();
	}

	// 멤버 리스트
	public List<Member> selectMemberList() {
		return memberMapper.selectMemberList();
	}

	// 로그인 체크
	public String loginCheck(String email, String password) throws UnknownHostException {
		Member member = memberMapper.selectMember(email);
		if (member == null) {
			return "noID";
		} else if (!member.getPassword().equals(password)) {
			return "noPW";
		} else {
			member.setLoginDate(new Date());
			updateMember(member, password);
			System.out.println(updateMember(member, password));
			return "success";
		}
	}
	
	public Member selectFbMember(String fbUserId){
		return memberMapper.selectFbMember(fbUserId);
	}
	
	

}
