package com.ex.entity;

import java.time.LocalDate;

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
@Table(name = "dogs")
public class DogsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dog_id;				// DOGS 테이블 식별번호
	
	@ManyToOne
	private MembersEntity member;		// MEMBERS 테이블 식별번호(견주)
	
	private String name;				// 강아지 이름
	private String breed;				// 강아지 품종
	private LocalDate birth_date;		// 강아지 생일
	private char gender;				// 강아지 성별
	private Integer dog_serialnum;		// 강아지 일련번호
	private String dog_profile;			// 강아지 프로필사진 파일명
	
}
