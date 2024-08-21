//package com.ex.repository;
//
//import java.util.List;
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Param;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import com.ex.data.BranchesDTO;
//import com.ex.entity.BranchEntity;
//
//@Repository
//@Mapper
//public interface BranchesMapRepository extends JpaRepository<BranchEntity, Integer> {
//    
//	List<BranchEntity> findByActiveTrue();
//	
//	@Query("UPDATE BranchEntity b SET b.latitude = :latitude, b.longitude = :longitude WHERE b.branchId = :branchId")
//    void updateCoordinates(@Param("branchId") Integer branchId, 
//                           @Param("latitude") Double latitude, 
//                           @Param("longitude") Double longitude);
//	
//	@Query("SELECT b FROM BranchEntity b WHERE b.active = true")
//    List<BranchEntity> findAllActiveBranches();
//    List<BranchesDTO> getAllBranches();
//    List<BranchesDTO> searchBranches(@Param("term") String term);
//    
//}
//
