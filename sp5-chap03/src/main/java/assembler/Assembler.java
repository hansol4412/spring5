package assembler;
import spring.ChangePasswordService;
import spring.MemberRegisterService;
import spring.MemberDao;
public class Assembler {
	private MemberRegisterService reqSvc;
	private ChangePasswordService pswSvc;
	private MemberDao memberDao;
	
	public Assembler() {
		memberDao = new MemberDao();
		reqSvc = new MemberRegisterService(memberDao);
		pswSvc = new ChangePasswordService();
		pswSvc.setMemberDao(memberDao);
	}
	
	public MemberDao getMemberDao() {
		return memberDao;
	}
	
	public MemberRegisterService getMemberRegisterService() {
		return reqSvc;
	}
	
	public ChangePasswordService getChangePasswordService() {
		return pswSvc;
	}

}
