package com.ex.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ex.entity.MembersEntity;

@Repository
public interface MembersRepository extends JpaRepository<MembersEntity, Integer> {
	public Optional<MembersEntity> findByUsername(String username);
	public Optional<MembersEntity> findByNameAndEmailAndPhone(String name, String email, String phone);
	public int countByUsernameAndNameAndPhone(String username, String name, String phone);
}
