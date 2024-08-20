package com.ex.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ex.entity.VaccinationsEntity;

@Repository
public interface VaccinationsRepository  extends JpaRepository<VaccinationsEntity, Integer>{

}
