package com.ex.data;
import java.time.LocalDate;
import java.util.Date;
import com.ex.entity.DogsEntity;
import com.ex.entity.MembersEntity;
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
	private MembersEntity member;
	private String	subscriptionType;
	private LocalDate	startDate;
	private LocalDate	endDate;
	private String	autoRenewal;
	private String	status;
	private Integer	usageCount;
	private String	regularType;
	private DogsEntity	dogs;
	private Integer amount;
	private Date paymentDate;
	private String paymethod;
	private Integer refund;
}
