package com.ex.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ex.entity.SubscriptionsEntity;

@Repository
public interface SubscriptionsRepository extends JpaRepository<SubscriptionsEntity, Integer> {

}
