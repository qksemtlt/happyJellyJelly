package com.ex.data;

import java.time.LocalDate;
import com.ex.entity.MembersEntity;
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
	private String dogname;				// 강아지 이름
	private String breed;				// 강아지 품종
	private LocalDate birth_date;		// 강아지 생일
	private String gender;				// 강아지 성별
	private String dog_serialnum;		// 강아지 일련번호
	private String dog_profile;			// 강아지 프로필사진 파일명
	
	// db에 없는 항목 계산필요
	private Integer approvedCount;		// 승인완료 마리수
	private Integer pendingCount;		// 승인대기 마리수
	
/*
	DOG_ID		DOGS 고유 식별 번호		NUMBER			PK
	MEMBER_ID	MEMBERS 테이블 참조키	NUMBER	
	NAME		강아지 이름				VARCHAR2(50)	NOT NULL
	BREED		강아지 품종				VARCHAR2(50)	
	BIRTH_DATE	강아지 생일				DATE	
	GENDER		강아지 성별				CHAR(1)			CHECK
	DOG_SERIALNUM	강아지 일련번호		NUMBER			UNIQUE
	DOG_PROFILE	강아지 프로필 사진 파일명	VARCHAR2(100)	
*/
}
