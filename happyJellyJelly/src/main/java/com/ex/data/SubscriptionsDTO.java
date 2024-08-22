package com.ex.data;
import java.time.LocalDate;
import com.ex.entity.AdmissionsEntity;
import com.ex.entity.DogsEntity;
import com.ex.entity.MembersEntity;
import com.ex.entity.TicketEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class SubscriptionsDTO {
	private Integer subscriptionId;	
	private LocalDate	startDate;
	private LocalDate	endDate;
	private String	autoRenewal;
	private String	status;	
	private Integer	usageCount;
	private Integer amount;
	private LocalDate paymentDate;
	private String paymethod;
	private Integer refund;
	private AdmissionsEntity admissions;
	private TicketEntity ticket;
	private DogsEntity	dogs;
	private MembersEntity member;
}
