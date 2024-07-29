package com.ex.data;

import com.ex.entity.TicketEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonthcareGroupsDTO {

	private Integer MONTHGROUP_ID;		// CLASS 테이블 식별번호
	private Integer BRANCH_ID;			// BRANCHES 테이블 참조키
	private String NAME;				// 수업 이름
	private String DESCRIPTION;			// 수업 내용
	private Integer CAPACITY;			// 수용 인원
	private Integer MONTH_PRICE;		// 금액
	private TicketEntity ticket;		// 이용권
}
