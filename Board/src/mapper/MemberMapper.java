package mapper;

import java.util.List;

import vo.Member;

public interface MemberMapper {
	
	public int insertMember(Member member); 
	public int updateMember(Member member);
	public int deleteMember(String email);
	public Member selectMember(String email);
	public List<Member> selectMemberList();
	public int selectMemberCount();
	
	
}
