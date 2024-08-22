package com.ex.entity;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
@Table(name="DOGASSIGNMENTS")
public class DogAssignmentsEntity {
	 @Id
	 @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dogassign_seq")
	 @SequenceGenerator(name = "dogassign_seq", sequenceName = "dogassign_seq", allocationSize=1, initialValue = 1)
	 @Column(name="DOGASSIGNMENT_ID")
	 private Integer dogassignmentId;
	 
	 @Column(name="START_DATE")
	 private LocalDate startDate;
		
	 @Column(name="END_DATE")
	 private LocalDate endDate;
	 
	 @ManyToOne
	 @JsonBackReference
	 @JoinColumn(name="dog_id")
	 private DogsEntity dogs;
	 
	 @ManyToOne
	 @JsonBackReference
	 @JoinColumn(name = "MONTHGROUP_ID")
	 private MonthcareGroupsEntity monthgroup;
	 
	  @OneToOne
	  @JsonBackReference
	  @JoinColumn(name = "ADMISSION_ID")
	  private AdmissionsEntity admission;
}
