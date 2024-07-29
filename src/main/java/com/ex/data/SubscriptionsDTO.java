package com.ex.data;

import java.time.LocalDate;

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
	private Integer subscription_id;
	
	private MembersEntity member;
	private String	subscription_type;
	private LocalDate	start_date;
	private LocalDate	end_date;
	private String	auto_renewal;
	private String	status;
	private Integer	usage_count;
	private String	regular_type;
	private DogsEntity	dogs;
}
