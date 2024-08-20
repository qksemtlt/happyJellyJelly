package com.ex.repository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ex.entity.TicketEntity;

public interface TicketRepository extends JpaRepository<TicketEntity, Integer>{

	public List<TicketEntity> findBySalesstatus(Integer sts);
	
	Page<TicketEntity> findAll(Pageable pageable);
}
