package com.ex.data;

import com.ex.entity.BranchEntity;
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

   private Integer id;               // CLASS 테이블 식별번호
   private BranchEntity branches;      // BRANCHES 테이블 참조키
   private String name;            // 수업 이름
   private String description;         // 수업 내용
   private Integer capacity;         // 수용 인원
   private Integer month_price;      // 금액
   private TicketEntity ticket;      // 이용권
}
