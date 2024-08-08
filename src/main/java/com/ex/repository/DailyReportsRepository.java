package com.ex.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ex.data.DailyReportsDTO;
import com.ex.entity.DailyReportsEntity;
import com.ex.entity.MembersEntity;

public interface DailyReportsRepository extends JpaRepository<DailyReportsEntity, Integer> {

	public List<DailyReportsEntity> findByMembers(MembersEntity members);

}
