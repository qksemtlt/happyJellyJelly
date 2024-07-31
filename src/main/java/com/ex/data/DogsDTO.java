package com.ex.data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
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
	
	private Integer dog_id;				// DOGS 테이블 식별번호
	private MembersEntity member;		// MEMBERS 테이블 식별번호(견주)
	@NotEmpty(message="이름은 필수입력 사항입니다.")
	private String dogname;				// 강아지 이름
	@NotEmpty(message="견종은 필수입력 사항입니다.")
	private String breed;				// 강아지 품종
	private LocalDate birth_date;		// 강아지 생일
	private char gender;				// 강아지 성별
	@NotEmpty(message="동물 등록번호는 필수입력 사항입니다.")
	private String dog_serialnum;		// 강아지 일련번호
	private String dog_profile;			// 강아지 프로필사진 파일명
	private Integer neutering;			// 중성화여부
//	private List<String> vaccination;	// 예방접종여부
	private BigDecimal weight;				// 강아지 몸무게
	
	
	
}
