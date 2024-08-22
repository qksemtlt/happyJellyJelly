package com.ex.data;
import java.time.LocalDate;
import com.ex.entity.AttendanceEntity;
import com.ex.entity.DogsEntity;
import com.ex.entity.MembersEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class DailyReportsDTO {

	private Integer id;						// DAILYREPORTS테이블 식별번호 시퀀스 REPORT_SEQ
	private DogsEntity dogs;				// 강아지 식별번호
	private AttendanceEntity attendance;	// 출석부 식별번호
	private LocalDate report_date;			// 작성일자
	private String behavior;				// 기분
	private String activities;				// 활동성
	private String meals;					// 식사
	private String health;					// 건강상태
	private String bowel;					// 배변활동
	private String contents;				// 알림장내용
	private String title;					// 알림장제목
	private MembersEntity members;			// 견주 식별번호
	

}
