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
@Table(name="Monthcaregroups")
public class MonthcareGroupsEntity {
   
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "class_seq")
   @SequenceGenerator(name = "class_seq", sequenceName = "class_seq", allocationSize=1, initialValue = 1)
   
   private Integer id;               // CLASS 테이블 식별번호
   
   @ManyToOne
   private BranchEntity branches;      // BRANCHES 테이블 참조키
   private String name;            // 수업 이름
   private String description;         // 수업 내용
   private Integer capacity;         // 수용 인원
   private Integer month_price;      // 금액
   
   @ManyToOne
   private TicketEntity ticket;      // 이용권
}





