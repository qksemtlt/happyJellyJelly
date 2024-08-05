package com.ex.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ex.data.TicketDTO;
import com.ex.entity.TicketEntity;
import com.ex.repository.TicketRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketService {
	
	@Autowired
	private final TicketRepository ticketRepository;
	
	// 판매중인 이용권 티켓만 목록 조회 (판매상태=1)
	public List<TicketDTO> getTicketsList(){
		System.out.println("TicketService 진입 getTicketsList()");
		List<TicketDTO> list = null;
		TicketDTO tDto = null;
		List<TicketEntity> dbTicketList = ticketRepository.findBySalesstatus(1);
		list = new ArrayList<>(dbTicketList.size());
		for(TicketEntity t: dbTicketList) {
			tDto = new TicketDTO().builder()
					.id(t.getId())
					.ticketname(t.getTicketname())
					.price(t.getPrice())
					.groupType(t.getGroupType())
					.salesstatus(t.getSalesstatus())
					.dayofweek(t.getDayofweek())
					.build();
			
			list.add(tDto);
		}
		return list;
	}
	
	// 이용권 상세 조회
	public TicketDTO getTicketById(Integer id) {
		TicketEntity te = ticketRepository.findById(id).get();
		TicketDTO ticketDto = TicketDTO.builder()
							.id(te.getId())
							.ticketname(te.getTicketname())
							.price(te.getPrice())
							.groupType(te.getGroupType())
							.salesstatus(te.getSalesstatus())
							.dayofweek(te.getDayofweek())
							.build();
		return ticketDto;
		
	}
	
	// 이용권 추가
	public void addTicket(TicketDTO ticketDTO) {
		
		String day;
		if (ticketDTO.getGroupType().equals("DAYCARE")) {
		    day = null;
		} else {
		    day = ticketDTO.getDayofweek();
		}
        TicketEntity ticket = TicketEntity.builder()
        						.ticketname(ticketDTO.getTicketname())
						        .price(ticketDTO.getPrice())
						        .groupType(ticketDTO.getGroupType())
						        .salesstatus(1)
						        .dayofweek(day)
						        .build();
        
        ticketRepository.save(ticket);
    }

}
