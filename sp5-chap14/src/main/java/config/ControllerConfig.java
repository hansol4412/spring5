package config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import controller.RegisterController;
import controller.RegisterControllerWithGlobalValidator;
import controller.RegisterControllerWithLocalValidator;
import controller.LoginController;
import controller.LogoutController;
import controller.ChangePwdController;
import controller.MemberListController;
import spring.MemberRegisterService;
import spring.AuthService;
import spring.ChangePasswordService;
import spring.MemberDao;
import survey.SurveyController;
@Configuration
public class ControllerConfig {
	@Autowired
	private MemberRegisterService memberRegSvc;
	@Autowired
	private AuthService authService;
	@Autowired
	private ChangePasswordService changePasswordService;
	@Autowired
	private MemberDao memberDao;
	@Bean
	public RegisterControllerWithLocalValidator registerControllerLocalValidator() {
		RegisterControllerWithLocalValidator controller = new RegisterControllerWithLocalValidator();
		controller.setMemberRegisterService(memberRegSvc);
		return controller;
	}
	
	@Bean
	public SurveyController surveyController() {
		return new SurveyController();
	}
	
	@Bean 
	public LoginController loginController() {
		LoginController controller = new LoginController();
		controller.setAuthService(authService);
		return controller;
	}
	
	@Bean 
	public LogoutController logoutController() {
		return new LogoutController();
	}
	
	@Bean 
	public ChangePwdController changePwdController() {
		ChangePwdController controller = new ChangePwdController();
		controller.setChangePasswordService(changePasswordService);
		return controller;
	}
	
	@Bean
	public MemberListController memberListController() {
		MemberListController controller = new MemberListController();
		controller.setMemberDao(memberDao);
		return controller;
	}
	
}
