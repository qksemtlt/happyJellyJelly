package com.ex.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
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
@Table(name = "attendance")
public class AttendanceEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attendance_seq")
	@SequenceGenerator(name = "attendance_seq", sequenceName = "attendance_seq", allocationSize=1, initialValue = 1)
	

	@Column(name = "ATTENDANCE_ID")
	private Integer id;							// ATTENDANCE 고유 식별 번호
	
	@ManyToOne
	@JsonBackReference
	private DogsEntity dog;						// DOGS 테이블 참조키
	
	@ManyToOne
	@JsonBackReference
	private DaycareGroupsEntity daygroup;		// DAYCAREGROUPS 테이블 참조키
	
	@ManyToOne
	@JsonBackReference
	private MonthcareGroupsEntity monthgroup;	// MONTHCAREGROUPS 테이블 참조키
	
	private LocalDate attendancedate;			// 출석일자
	private String status;						// 출석상태
	
	@OneToOne
	private DailyReportsEntity dailyreport;		// 알림장
	
	private String notes;						// 특이사항
}
