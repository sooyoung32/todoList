package kr.co.kware.member.service;

import java.util.List;

import kr.co.kware.member.vo.Member;


public interface MemberService {
	
	public int joinMember(Member member);
	public int modifyMember(Member member, String password);
	public int deleteMember(String email, String pw);
	public Member getMemberbyEmail(String email);
	public int countTotalMember();
	public List<Member> getMemberList();
	public String loginCheck(String email, String password);
	public Member getFbMemberbyFbuserId(String fbUserId);
	
}
