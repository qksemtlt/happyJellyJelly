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

   private Integer id;							// TICKET 고유 식별 번호
   
   @NotEmpty(message="이용권 이름은 필수입력 사항입니다.")
   private String ticketname;  					// 이용권 이름
   
   @NotEmpty(message="금액은 필수입력 사항입니다.")
   private Integer price;						// 금액
   
   private String groupType;					// 이용권종류
   private Integer salesstatus;					// 판매상태
   private String dayofweek;					// 요일 ex)월수금=135
   private Integer ticketcount;					// 횟수권갯수(예약가능갯수)
}
