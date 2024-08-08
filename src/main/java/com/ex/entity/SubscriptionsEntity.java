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
@Table(name = "SUBSCRIPTIONS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionsEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subscription_seq")
    @SequenceGenerator(name = "subscription_seq", sequenceName = "subscription_seq", allocationSize = 1)
	private Integer subscription_id;

	private Date start_date;
	private Date end_date;
	private String auto_renewal;
	private String status;
	private Integer usage_count;
	private Integer amount;
	private Date payment_date;
	private String paymethod;
	private Integer refund;
	
	@ManyToOne
	@JsonBackReference
	private TicketEntity ticket;
	
	@OneToOne
	@JsonBackReference
	@JoinColumn(name="admission_id")
	private AdmissionsEntity admissions;
}
