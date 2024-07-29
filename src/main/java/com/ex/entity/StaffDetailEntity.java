package com.ex.entity;

import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity

@Table(name = "STAFFDETAIL")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StaffDetailEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STAFF_SEQ")								// 시퀀스 생성
	@SequenceGenerator(name = "STAFF_SEQ", sequenceName = "STAFF_SEQ", allocationSize=1, initialValue = 1)
	private Integer staffdetail_id ;
	
	@OneToOne
	private MembersEntity member;
	
	private String  position;
	
	private LocalDate hire_date;
	
	private Integer branch_id;
}
