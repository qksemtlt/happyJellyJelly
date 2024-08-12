package com.ex.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ex.entity.AttendanceEntity;
import com.ex.entity.BranchEntity;
import com.ex.entity.MonthcareGroupsEntity;
import java.time.LocalDate;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Integer>{

	List<AttendanceEntity> findByAttendancedate(LocalDate attendancedate);

	List<AttendanceEntity> findByAttendancedateAndBranch(LocalDate attendancedate, BranchEntity branch);
	List<AttendanceEntity> findByAttendancedateAndBranchAndMonthgroup(LocalDate attendancedate, BranchEntity branch, MonthcareGroupsEntity monthgroup);
//	List<AttendanceEntity> findByAttendancedateAndBranchEntity_BranchId(LocalDate attendancedate, Integer branch_id);
//	List<AttendanceEntity> findByAttendancedateAndMonthgroup(LocalDate currentDate, Integer month_id);
}
