package com.ex.entity;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Ticket")
@ToString(exclude = {"subscription", "month"})
public class TicketEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticket_seq")
   @SequenceGenerator(name = "ticket_seq", sequenceName = "ticket_seq", allocationSize=1, initialValue = 1)
   private Integer id;            // TICKET 고유 식별 번호
   private String ticketname;      // 이용권 이름
   private Integer price;         // 금액
   private String groupType;      // 이용권종류
   private Integer salesstatus;   // 판매상태
   private String dayofweek;      // 요일 ex)월수금=135
   private Integer ticketcount;   // 판매상태
   
   @OneToMany(mappedBy="ticket", cascade=CascadeType.REMOVE)
   @JsonManagedReference
   private List<SubscriptionsEntity> subscription;
   
   @OneToMany(mappedBy="ticket", cascade=CascadeType.REMOVE)
   @JsonManagedReference
   private List<MonthcareGroupsEntity> month;
}
