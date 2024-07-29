package com.ex.entity;

import java.time.LocalDate;

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
@Table(name = "SUBSCRIPTIONS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionsEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SUBSCRIPTION_SEQ")							
	@SequenceGenerator(name = "SUBSCRIPTION_SEQ", sequenceName = "SUBSCRIPTION_SEQ", allocationSize=1, initialValue = 1)
	private Integer subscription_id;
	
	@ManyToOne
	private MembersEntity member;
	
	private String	subscription_type;
	
	private LocalDate	start_date;
	
	private LocalDate	end_date;
	
	private String	auto_renewal;
	
	private String	status;
	
	private Integer	usage_count;
	
	private String	regular_type;
	@ManyToOne
	private DogsEntity	dogs;
}
