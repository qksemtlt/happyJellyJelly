package com.ex.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Daycaregroups")
public class DaycareGroupsEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "daycare_seq")
	@SequenceGenerator(name = "daycare_seq", sequenceName = "daycare_seq", allocationSize=1, initialValue = 1)
	private Integer daygroup_id;	// DAYCAREGROUPS 테이블 식별번호
	
	@ManyToOne
	private BranchEntity branch;	// BRANCHES 테이블 (지점)
	
	private String name;			// 횟수권명
	private Integer capacity;		// 구매가능 횟수권 갯수? 이게 필요한가?
	private Integer day_price;		// 횟수권 가격
	
	@ManyToOne
	private TicketEntity ticket;	// 이용권
}
