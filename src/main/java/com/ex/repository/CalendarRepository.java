package com.ex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ex.entity.CalendarEntity;
import java.time.LocalDate;


public interface CalendarRepository extends JpaRepository<CalendarEntity, Long>{
//	CalendarEntity findByReport_date(LocalDate report_date);
}
