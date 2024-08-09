package com.ex.data;
import java.time.LocalDate;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MembersDTO {
	
	private Integer memberId;
	
	@NotEmpty(message="아이디는 필수입력 사항입니다.")
	private String username;
	
	@NotEmpty(message="이름은 필수입력 사항입니다.")
	private String name;
	
	@NotEmpty(message="비밀번호는 필수입력 사항입니다.", groups = {SignUp.class, PasswordChange.class})
	private String password;
	
	@NotEmpty(message="비밀번호 확인을 입력해주세요", groups = {SignUp.class, PasswordChange.class})
	private String password2;
	
	@NotEmpty(message="새로운 비밀번호는 필수입력 사항입니다.", groups = {PasswordChange.class})
	private String newpassword;
	
	@NotEmpty(message="이메일은 필수입력 사항입니다.")
	private String email;
	
	@NotEmpty(message="전화번호는 필수입력 사항입니다.")
	private String phone;
	private String user_type;
	private LocalDate join_date;
	private Integer branch_id;
	
	public interface SignUp {}
    public interface PasswordChange {}
}
