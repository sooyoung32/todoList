package kr.co.kware.member.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import kr.co.kware.member.mapper.MemberMapper;
import kr.co.kware.member.vo.Member;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{
	@Autowired
	private MemberMapper memberMapper;
	
	private Logger logger =Logger.getLogger(MemberServiceImpl.class);
	

	@Override
	public int joinMember(Member member) {
		member.setWritingDate(new Date());
		return memberMapper.insertMember(member);
	}

	@Override
	public int modifyMember(Member member, String password) {
		member.setPassword(password);
		member.setModifyDate(new Date());
		logger.debug("멤버 서비스 페북 업데이트 : "+member);
		return memberMapper.updateMember(member);
	}

	@Override
	public int deleteMember(String email, String pw) {
		Member member = new Member();
		if (memberMapper.selectMember(member.getEmail()).getPassword().equals(pw)) {
			return memberMapper.deleteMember(email);
		} else {
			return 0;
		}
	}

	@Override
	public Member getMemberbyEmail(String email) {
		return memberMapper.selectMember(email);
	}

	@Override
	public int countTotalMember() {
		return memberMapper.selectMemberCount();
	}

	@Override
	public List<Member> getMemberList() {
		return memberMapper.selectMemberList();
	}

	@Override
	public String loginCheck(String email, String password) {
		Member member = memberMapper.selectMember(email);
		if (member == null) {
			return "noID";
		} else if (!member.getPassword().equals(password)) {
			return "noPW";
		} else {
			member.setLoginDate(new Date());
			modifyMember(member, password);
			return "success";
		}
	}

	@Override
	public Member getFbMemberbyFbuserId(String fbUserId) {
		return memberMapper.selectFbMember(fbUserId);
	}
	
	

}
