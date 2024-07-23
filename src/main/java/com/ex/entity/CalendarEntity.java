package com.ex.entity;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "DAILYREPORTS")
public class CalendarEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer report_id;				// DAILYREPORTS테이블 식별번호 시퀀스 REPORT_SEQ
	
	@ManyToOne
	private DogsEntity dogs;				// 강아지 식별번호
	
//	@ManyToOne
	private Integer attendance_id;			// 출석부 식별번호

	private LocalDate report_date;			// 작성일자
	private String behavior;				// 기분
	private String activities;				// 활동성
	private String meals;					// 식사
	private String health;					// 건강상태
	private String bowel;					// 배변활동
	
	@Column(columnDefinition = "TEXT")
	private String contents;				// 알림장내용
	
	@ManyToOne
	private MembersEntity members;			// 강아지 테이블 id 컬럼
}
