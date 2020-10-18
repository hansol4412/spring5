package config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import controller.RegisterController;
import controller.RegisterControllerWithGlobalValidator;
import controller.RegisterControllerWithLocalValidator;
import spring.MemberRegisterService;
import survey.SurveyController;
@Configuration
public class ControllerConfig {
	@Autowired
	private MemberRegisterService memberRegSvc;
	
	/*
	@Bean
	public RegisterController registerController() {
	RegisterController controller = new RegisterController();
	controller.setMemberRegisterService(memberRegSvc);
	return controller;
	}
	*/
	
	
	/*
	 //Grobal Validator 설정
	@Bean
	public RegisterControllerWithGlobalValidator registerControllerGlobalValidator() {
		RegisterControllerWithGlobalValidator controller = new RegisterControllerWithGlobalValidator();
		controller.setMemberRegisterService(memberRegSvc);
		return controller;
	}
	*/
	
	//@InitBinder 애노테이션을 이용한 Local Validator 설정
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
}
