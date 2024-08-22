package com.ex.controller;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String list(TicketDTO ticketDTO, Principal principal, Model model
    		, @RequestParam(value = "page", defaultValue = "0") int page) {
        model.addAttribute("ticketsList", ticketService.getTicketsAll(page));
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
    
    @PostMapping("update")
    public String updateTicket(TicketDTO ticketDTO) {
    	ticketService.updateTicket(ticketDTO, ticketDTO.getId());
    	return "redirect:/tickets/list";
    }
}
