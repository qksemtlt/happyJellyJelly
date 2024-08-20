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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VACCINATIONS_SEQ")
    @SequenceGenerator(name = "VACCINATIONS_SEQ", sequenceName = "VACCINATIONS_SEQ", allocationSize = 1)
	@Column(name = "VACCINATION_ID")
    private Integer vaccinationId;
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name="dog_id")
	private DogsEntity dogs;
	
	@Column(name = "VACCINE_TYPE")
	private String vaccineType;
	
	
	private LocalDate vaccination_date;
	private LocalDate expiry_date;
	private String filename;
	

}
