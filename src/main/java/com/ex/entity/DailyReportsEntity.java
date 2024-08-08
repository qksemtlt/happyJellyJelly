package com.ex.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "DAILYREPORTS")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyReportsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REPORT _SEQ")							
	@SequenceGenerator(name = "REPORT _SEQ", sequenceName = "REPORT _SEQ", allocationSize=1, initialValue = 1)
	private Integer id ;					// DAILYREPORTS테이블 식별번호
	
	@ManyToOne
	@JsonBackReference
	private DogsEntity dogs ;				// DOGS테이블 식별번호
	
	@ManyToOne
	@JsonBackReference
	private AttendanceEntity attendance ;	// ATTENDANCE테이블 식별번호(출석부)
	
	private LocalDate report_date;			// 작성일자
	private String behavior;				// 기분
	private String activities;				// 활동성
	private String meals;					// 식사
	private String health;					// 건강상태
	private String bowel;					// 배변활동
	private String contents;				// 알림장내용
	private String title;					// 알림장제목
	
	@ManyToOne
	@JsonBackReference
	private MembersEntity members;			//  테이블 id 컬럼

}
