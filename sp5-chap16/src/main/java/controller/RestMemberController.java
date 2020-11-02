package controller;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;
import java.io.IOException;
import java.net.URI;

import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import spring.Member;
import spring.MemberDao;
import spring.MemberNotFoundException;
import spring.MemberRegisterService;
import spring.RegisterRequest;
import spring.DuplicateMemberException;

@RestController
public class RestMemberController {
	private MemberDao memberDao;
	private MemberRegisterService memberRegisterService;
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	public void setMemberRegisterService(MemberRegisterService memberRegisterService) {
		this.memberRegisterService = memberRegisterService;
	}
	
	@GetMapping("/api/members")
	public List<Member> members(){
		return memberDao.selectAll();
	}
	
	/*
	 *  HttpServletResponse이용 : 404오류 응답이 되면 JSON 형식이 아닌 HTML을 응답결과로 제공
	    => API 호출하는 프로그램 입장에서 JSON과 HTML응답을 모두 처리하는 것은 부담이 됨
	@GetMapping("/api/members/{id}")
	public Member member(@PathVariable Long id, HttpServletResponse response) throws IOException {
		Member member = memberDao.selectById(id);
				if(member==null) {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
					return null;
				}
		return member;
	}
	*/
	
	/*
	@GetMapping("/api/members/{id}")
	public ResponseEntity<Object> member(@PathVariable Long id){
		Member member = memberDao.selectById(id);
				if(member==null) {
					//ResponseEntity로 생성하면 코드 중복 => @ExceptionHandler 사용
				     return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("no member"));
				}
		return //ResponseEntity.status(HttpStatus.OK).body(member);
				ResponseEntity.ok(member);
	}
	*/
	
	@GetMapping("/api/members/{id}")
	public Member member(@PathVariable Long id){
		Member member = memberDao.selectById(id);
				if(member==null) {
					throw new MemberNotFoundException();	
				}
		return member;
	}
	
	@ExceptionHandler(MemberNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNoData(){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("no member"));
			   // return ResponseEntity.notFound().build();
	}
	
	/*
	@PostMapping("/api/members")
	public void newMember(
			@RequestBody @Valid RegisterRequest regReq, HttpServletResponse response) throws IOException  {
		try {
			Long newMemberId = memberRegisterService.regist(regReq);
			response.setHeader("Location","/api/members/" + newMemberId);
			response.setStatus(HttpServletResponse.SC_CREATED);
		} catch (DuplicateMemberException dupEx) {
			response.sendError(HttpServletResponse.SC_CONFLICT);
		}
	}
	*/
	
	@PostMapping("/api/members")
	public ResponseEntity<Object> newMember(
			@RequestBody @Valid RegisterRequest regReq ,
			Errors errors) {
		
		if (errors.hasErrors()) {
			String errorCodes = errors.getAllErrors()
					.stream()
					.map(error -> error.getCodes()[0])
					.collect(Collectors.joining(","));
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorResponse("errorCodes = " + errorCodes));
		}
		try {
			Long newMemberId = memberRegisterService.regist(regReq);
			URI uri = URI.create("/api/members/" + newMemberId);
			return ResponseEntity.created(uri).build();
		} catch (DuplicateMemberException dupEx) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
}
