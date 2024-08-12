package com.ex.entity;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
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

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Monthcaregroups")
public class MonthcareGroupsEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "class_seq")
   @SequenceGenerator(name = "class_seq", sequenceName = "class_seq", allocationSize=1, initialValue = 1)
   private Integer id;            // CLASS 테이블 식별번호
   private String name;            // 수업 이름
   private String description;      // 수업 내용
   private Integer capacity;      // 수용 인원
   
   @ManyToOne
   @JsonBackReference
   private MembersEntity teachers;
   
   @ManyToOne
   @JsonBackReference
   private BranchEntity branches;   // BRANCHES 테이블 참조키
   
   @OneToMany(mappedBy="monthgroup", cascade=CascadeType.REMOVE)
   @JsonManagedReference
   private List<DogAssignmentsEntity> dogAssign;
   
   @ManyToOne
   @JsonBackReference
   private TicketEntity ticket;
   
 
}


