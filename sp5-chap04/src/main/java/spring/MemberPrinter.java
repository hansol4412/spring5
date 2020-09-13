package spring;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import org.springframework.lang.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
public class MemberPrinter {
	private DateTimeFormatter dateTimeFormatter;
	public MemberPrinter() {
		dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
	}
	public void Print(Member member) {
		if(dateTimeFormatter==null) {
			System.out.printf("회원정보: 아이디=%d, 이메일=%s, 이름=%s, 등록일=%tF\n",
					 member.getId(), member.getEmail(), member.getName(), member.getRegiserDateTime());
		}
		else {
			System.out.printf("회원정보: 아이디=%d, 이메일=%s, 이름=%s, 등록일=%s\n",
					 member.getId(), member.getEmail(), member.getName(), 
					 dateTimeFormatter.format(member.getRegiserDateTime()));
		}
	}
	
	/* @Autowired(required=false)
	 public void setDateTimeformatter(DateTimeFormatter dateTimeFormatter) {
		this.dateTimeFormatter = dateTimeFormatter;
	 } */
	
	
	/*@Autowired
	public void setDateTimeformatter(Optional<DateTimeFormatter> fomatterOpt) {
		if(fomatterOpt.isPresent()) {
		this.dateTimeFormatter = dateTimeFormatter;
		}
		else {
			this.dateTimeFormatter = null;
		}
	} */
	
	
	 @Autowired
	 public void setDateTimeformatter(@Nullable DateTimeFormatter dateTimeFormatter) {
		this.dateTimeFormatter = dateTimeFormatter;
	 }
	
}
