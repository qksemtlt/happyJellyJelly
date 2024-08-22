package com.ex.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ex.entity.MonthcareGroupsEntity;

@Repository
public interface MonthcareGroupsRepository extends JpaRepository<MonthcareGroupsEntity, Integer>{

}
