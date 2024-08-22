package com.ex.entity;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
	@Column(name="DOG_ID")
	private Integer dogId;				// DOGS 테이블 식별번호
	private String dogname;				// 강아지 이름
	private String breed;				// 강아지 품종
	
	@Column(name="BIRTH_DATE")
	private LocalDate birthDate;		// 강아지 생일
	private char gender;				// 강아지 성별
	@Column(name="DOG_SERIALNUM")
	private String dogSerialnum;		// 강아지 일련번호
	
	@Column(name="DOG_PROFILE")
	private String dogProfile;			// 강아지 프로필사진 파일명
	private Integer neutering;			// 중성화여부
	
	@Column(precision=10, scale=1)
	private BigDecimal weight;				// 강아지 몸무게
	
	@ManyToOne
	@JsonBackReference
	private MembersEntity member;		// MEMBERS 테이블 식별번호(견주)
		
	@OneToMany(mappedBy="dogs", cascade=CascadeType.REMOVE)
	@JsonManagedReference
	private List<AdmissionsEntity> admissions;
	
	@OneToMany(mappedBy="dogs", cascade=CascadeType.REMOVE)
	@JsonManagedReference
	private List<DogAssignmentsEntity> dogAssign;
	
	@OneToMany(mappedBy="dogs", cascade=CascadeType.REMOVE)
	@JsonManagedReference
	private List<SubscriptionsEntity> subscriptions;
}
