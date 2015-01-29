package kr.co.kware.boardpsy.member;

import java.util.List;


public interface MemberMapper {
	
	public int insertMember(Member member); 
	public int updateMember(Member member);
	public int deleteMember(String email);
	public Member selectMember(String email);
	public List<Member> selectMemberList();
	public int selectMemberCount();
	public Member selectFbMember(String fbUserId); 
	
	
}
