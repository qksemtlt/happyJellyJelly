package com.ex.data;
import java.math.BigDecimal;
import java.time.LocalDate;
import com.ex.entity.MembersEntity;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DogsDTO {
	
	private Integer dogId;				// DOGS 테이블 식별번호
	private MembersEntity member;		// MEMBERS 테이블 식별번호(견주)
	@NotEmpty(message="이름은 필수입력 사항입니다.")
	private String dogname;				// 강아지 이름
	@NotEmpty(message="견종은 필수입력 사항입니다.")
	private String breed;				// 강아지 품종
	private LocalDate birthDate;		// 강아지 생일
	private char gender;				// 강아지 성별
	@NotEmpty(message="동물 등록번호는 필수입력 사항입니다.")
	private String dogSerialnum;		// 강아지 일련번호
	
	private String dogProfile;			// 강아지 프로필사진 파일명
	private Integer neutering;			// 중성화여부
	private BigDecimal weight;				// 강아지 몸무게
	
	// db에 없는 항목 계산필요
	private Integer approvedCount;		// 승인완료 마리수
	private Integer pendingCount;		// 승인대기 마리수
	
}
