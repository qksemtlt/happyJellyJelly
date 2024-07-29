package com.ex.data;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketDTO {

	private Integer TICKET_ID;	// TICKET 고유 식별 번호
	
	@NotEmpty(message="이용권 이름은 필수입력 사항입니다.")
	private String TICKETNAME;	// 이용권 이름
	
	@NotEmpty(message="금액은 필수입력 사항입니다.")
	private Integer PRICE;		// 금액
	
	private String GROUP_TYPE;	// 이용권종류
}
