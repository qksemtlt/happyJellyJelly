package com.ex.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ex.data.DailyReportsDTO;
import com.ex.data.TicketDTO;
import com.ex.service.TicketService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/tickets/*")
@RequiredArgsConstructor
@Controller
public class TicketController {
	
	@Autowired
	private TicketService ticketService;

	@GetMapping("list")
    public String list(TicketDTO ticketDTO, Principal principal, Model model) {
        // 여기서 실제로 DB에서 데이터를 가져오는 서비스 메서드를 호출해야 합니다.
        List<TicketDTO> ticketsList = ticketService.getTicketsList();
        model.addAttribute("ticketsList", ticketsList);
        return "tickets/ticketList";
    }
	
	@GetMapping("/tickets/add")
    public String addTicketForm(Model model) {
        model.addAttribute("ticket", new TicketDTO());
        return "tickets/createTicketForm";
    }

    @PostMapping("/tickets/add")
    public String addTicket(@ModelAttribute("ticket") TicketDTO ticketDTO) {
        ticketService.addTicket(ticketDTO);
        return "redirect:/tickets/list";
    }
    
    @GetMapping("detail/{id}")
    public String getTicketDetail(@PathVariable("id") Integer id, Model model) {
    	TicketDTO ticketDTO = ticketService.getTicketById(id);
    	model.addAttribute("ticketDTO", ticketDTO);
    	return "tickets/ticketDetail";
    }
}
