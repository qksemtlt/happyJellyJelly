package com.ex.repository;
import com.ex.entity.MembersEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MembersMgRepository extends JpaRepository<MembersEntity, Integer> {

   Optional<MembersEntity> findById(Integer id);
    
   @Query("SELECT m FROM MembersEntity m WHERE " +
              "(:keyword IS NULL OR " +
              "LOWER(m.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
              "LOWER(m.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
              "LOWER(m.userType) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
              "m.userType IN ('TEACHER', 'DIRECTOR', 'ADMIN')")
       Page<MembersEntity> searchStaffByKeyword(@Param("keyword") String keyword, Pageable pageable);
   Page<MembersEntity> findByUserTypeNotIn(List<String> userTypes, Pageable pageable);
    @Query("SELECT m FROM MembersEntity m WHERE m.name LIKE %:keyword% OR m.email LIKE %:keyword% OR m.userType LIKE %:keyword%")
    List<MembersEntity> searchByKeyword(@Param("keyword") String keyword);
    
    List<MembersEntity> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrUserTypeContainingIgnoreCase(
        String nameKeyword, String emailKeyword, String userTypeKeyword);
    
    List<MembersEntity> findByUserType(String userType);
    List<MembersEntity> findByUserTypeIn(List<String> userTypes);
    List<MembersEntity> findByUserTypeNotIn(List<String> userTypes);
    Optional<MembersEntity> findByMemberId(Integer memberId);

}