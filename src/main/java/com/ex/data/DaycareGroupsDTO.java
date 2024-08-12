package com.ex.data;
import com.ex.entity.BranchEntity;
import com.ex.entity.TicketEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DaycareGroupsDTO {

	private Integer daygroup_id;	// DAYCAREGROUPS 테이블 식별번호
	private BranchEntity branch;	// BRANCHES 테이블 식별변호(지점번호)
	private String name;			// 횟수권명
	private Integer capacity;		// 수용인원
	private Integer day_price;		// 횟수권가격
	private TicketEntity ticket;	// 이용권
}
