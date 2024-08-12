package com.ex.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ex.entity.DailyReportsEntity;
import com.ex.entity.MembersEntity;

public interface DailyReportsRepository extends JpaRepository<DailyReportsEntity, Integer> {

	public List<DailyReportsEntity> findByMembers(MembersEntity members);

}
