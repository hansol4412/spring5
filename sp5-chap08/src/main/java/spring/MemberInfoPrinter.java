package spring;

public class MemberInfoPrinter {
	private MemberDao memDao;
	private MemberPrinter printer;
	
	public void printMemberInFo(String email) {
		Member member = memDao.selectByEmail(email);
		if(member == null) {
			System.out.println("데이터 없음");
			return;
		}
		printer.Print(member);
		System.out.println();
	}
	public void setMemberDao(MemberDao memberDao) {
		this.memDao = memberDao;
	}
	public void serPrinter(MemberPrinter printer) {
		this.printer = printer;
	}
}
