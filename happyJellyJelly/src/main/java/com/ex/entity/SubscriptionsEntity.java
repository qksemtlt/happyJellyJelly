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
import lombok.ToString;

@Entity
@Table(name = "SUBSCRIPTIONS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"admissions", "ticket", "dogs", "member"})
public class SubscriptionsEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subscription_seq")
    @SequenceGenerator(name = "subscription_seq", sequenceName = "subscription_seq", allocationSize = 1)
	@Column(name="SUBSCRIPTION_ID")
	private Integer subscriptionId;
	
	@Column(name="START_DATE")
	private LocalDate startDate;
	
	@Column(name="END_DATE")
	private LocalDate endDate;
	
	@Column(name="AUTO_RENEWAL")
	private String autoRenewal;
	private String status;
	
	@Column(name="USAGE_COUNT")
	private Integer usageCount;
	private Integer amount;
	
	@Column(name="PAYMENT_DATE")
	private LocalDate paymentDate;
	private String paymethod;
	private Integer refund;
	
	@OneToOne
	@JsonBackReference
	@JoinColumn(name="admission_id")
	private AdmissionsEntity admissions;
	
	@ManyToOne
	@JsonBackReference
	private TicketEntity ticket;
	
	@ManyToOne
	@JsonBackReference
	private DogsEntity dogs;
	
	@ManyToOne
	@JsonBackReference
	private MembersEntity member;
	
}
