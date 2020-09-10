package spring;
import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired
	public void setMemberDao(MemberDao memberDao) {
		this.memDao = memberDao;
	}
	@Autowired
	public void serPrinter(MemberPrinter printer) {
		this.printer = printer;
	}
}
