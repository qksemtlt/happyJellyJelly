package com.ex.data;
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
	
	@NotEmpty(message="아이디는 필수입력 항목입니다.")
	private String username;
	
	@NotEmpty(message="이름은 필수입력 항목입니다.")
	private String name;
	
	@NotEmpty(message="비밀번호는 필수입력 항목입니다.")
	private String password;
	private String email;
	private String phone;
	private String user_type;
}
