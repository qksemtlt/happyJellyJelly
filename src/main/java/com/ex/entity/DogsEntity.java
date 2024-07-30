package com.ex.entity;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
@Table(name = "dogs")
public class DogsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dogs_seq")								// 시퀀스 생성
	@SequenceGenerator(name = "dogs_seq", sequenceName = "dogs_seq", allocationSize=1, initialValue = 1)	// 시퀀스 설정
	private Integer dog_id;	// DOGS 테이블 식별번호
	
	
	@ManyToOne
	private MembersEntity member;		// MEMBERS 테이블 식별번호(견주)
	
	private String dogname;				// 강아지 이름
	private String breed;				// 강아지 품종
	private LocalDate birth_date;		// 강아지 생일
	private char gender;				// 강아지 성별
	private String dog_serialnum;		// 강아지 일련번호
	private String dog_profile;			// 강아지 프로필사진 파일명
	private Integer neutering;			// 중성화여부
	private List<String> vaccination;	// 예방접종여부
	
	@Column(precision=10, scale=1)
	private BigDecimal weight;				// 강아지 몸무게
	
}
