package com.ex.repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ex.entity.AdmissionsEntity;
import com.ex.entity.SubscriptionsEntity;

@Repository
public interface SubscriptionsRepository extends JpaRepository<SubscriptionsEntity, Integer> {
	Optional<SubscriptionsEntity> findByAdmissions(AdmissionsEntity ae);	
	List<SubscriptionsEntity> findByStartDate(LocalDate startDate);
}
