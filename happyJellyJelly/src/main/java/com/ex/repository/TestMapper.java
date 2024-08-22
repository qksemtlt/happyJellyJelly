package com.ex.repository;
import java.time.LocalDate;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.ex.data.AttendanceDTO;

@Repository
@Mapper
public interface TestMapper {

	// xml과 연동 호출
	List<AttendanceDTO> dateAndBranchAttendence(@Param("currentDate") LocalDate currentDate,@Param("branchid") Integer branchid);
}
