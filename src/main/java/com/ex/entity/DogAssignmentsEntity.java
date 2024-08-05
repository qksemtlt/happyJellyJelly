package com.ex.entity;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
	 private Integer dogassignment_id;
	 
	 private Date start_date;
	 private Date end_date;
	 
	 @ManyToOne
	 @JsonBackReference
	 @JoinColumn(name="dog_id")
	 private DogsEntity dogs;
	 
	 @ManyToOne
	 @JsonBackReference
	 private MonthcareGroupsEntity monthgroup;
	 
	 @OneToOne
	 @JoinColumn(name="admission_id")
	 private AdmissionsEntity admission;
}
