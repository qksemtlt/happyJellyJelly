package com.ex.service;
import java.util.List;
import org.springframework.stereotype.Service;
import com.ex.data.SubscriptionsDTO;
import com.ex.entity.SubscriptionsEntity;
import com.ex.repository.SubscriptionsRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubscriptionsService {

	private final SubscriptionsRepository subscriptonsRepository;
	
	public List<SubscriptionsDTO> mysubsInfo(){
		List<SubscriptionsEntity> subList = subscriptonsRepository.findAll();
		SubscriptionsDTO subsDTO = null;
		List<SubscriptionsDTO> subsDTOList = null;
		for(SubscriptionsEntity subs : subList) {
			subsDTO = new SubscriptionsDTO().builder().subscriptionId(subs.getSubscriptionId())
					.status(subs.getStatus()).build();
			subsDTOList.add(subsDTO);
		}		
		return subsDTOList;
	} 
}
