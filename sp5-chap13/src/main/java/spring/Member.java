package spring;
import java.time.LocalDateTime;
public class Member {
	private long id;
	private String email;
	private String password;
	private String name;
	private LocalDateTime regiserDateTime;
	
	public Member(String email, String password, String name, LocalDateTime regiserDateTime) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.regiserDateTime =  regiserDateTime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public LocalDateTime getRegiserDateTime() {
		return regiserDateTime;
	}

	public void changePassword(String oldPassword, String newPassword) {
		if(!password.equals(oldPassword)) throw new WrongIdPasswordException();
		
		this.password = newPassword;
	}
	
	public boolean matchPassword(String password) {
		return this.password.equals(password);
	}
}
