package com.ex.data;
import java.time.LocalDate;
import com.ex.entity.DogsEntity;
import com.ex.entity.MembersEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalendarDTO {

	private Integer report_id;		// DAILYREPORTS테이블 식별번호 시퀀스 REPORT_SEQ
	private DogsEntity dogs;		// 강아지 식별번호
	private Integer attendance_id;	// 출석부 식별번호
	private LocalDate report_date;	// 작성일자
	private String behavior;		// 기분
	private String activities;		// 활동성
	private String meals;			// 식사
	private String health;			// 건강상태
	private String bowel;			// 배변활동
	private String contents;		// 알림장내용
	private MembersEntity members;	// 견주 식별번호
	
}
/*
REPORT_ID 		DAILYREPORTS  고유 식별 번호	NUMBER	PK	REPORT _SEQ
DOG_ID 			DOGS 테이블 참조키				NUMBER		FK :  DOG_ID
ATTENDANCE_ID 	ATTENDANCE 테이블 참조키		NUMBER		FK :  ATTENDANCE_ID 
REPORT_DATE		작성날짜		DATE		
BEHAVIOR		기분 	 		VARCHAR2(200) 		
ACTIVITIES		활동성	 	VARCHAR2(200) 		
MEALS			식사	 		VARCHAR2(200) 		
HEALTH			건강상태	 	VARCHAR2(200) 		
BOWEL			배변활동	 	VARCHAR2(200) 		
CONTENTS		알림장 내용	 	VARCHAR2(500) 		
*/
/*   
private Long id;               	// 식별번호
private String title;			// 제목
private String content;			// 내용
private LocalDate diarydate;		// 일자

private Integer writingYn;		// 작성여부
private List<String> food;		// 식사
private Integer madicationcount;	// 투약횟수
private String poop;				// 응가상태
private Integer poopcount;		// 응가횟수
private String   feeling;		// 기분
*/