package spring;

public class MemberPrinter {
	public void Print(Member member) {
		System.out.printf("회원정보: 아이디=%d, 이메일=%s, 이름=%s, 등록읽=%tF\n",
						 member.getId(), member.getEmail(), member.getName(), member.getRegiserDateTime());
	}
}
