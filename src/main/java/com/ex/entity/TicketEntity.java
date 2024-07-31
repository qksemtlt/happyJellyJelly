package com.ex.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name="Ticket")
public class TicketEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticket_seq")
   @SequenceGenerator(name = "ticket_seq", sequenceName = "ticket_seq", allocationSize=1, initialValue = 1)
   private Integer id;         // TICKET 고유 식별 번호
   private String ticketname;   // 이용권 이름
   private Integer price;      // 금액
   private String groupType;   // 이용권종류
}
