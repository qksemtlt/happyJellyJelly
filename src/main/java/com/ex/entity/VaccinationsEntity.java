package com.ex.entity;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "VACCINATIONS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VaccinationsEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vaccinations_seq")
    @SequenceGenerator(name = "vaccinations_seq", sequenceName = "vaccinations_seq", allocationSize = 1)
	private Integer vaccination_id;
	
	private String vaccine_type;
	private Date vaccination_date;
	private Date expiry_date;
	private String filename;
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name="admission_id")
	private AdmissionsEntity admissions;
	
}
