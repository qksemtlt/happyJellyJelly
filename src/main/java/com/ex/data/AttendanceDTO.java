package com.ex.data;

import java.time.LocalDate;
import java.util.Date;
import com.ex.entity.DaycareGroupsEntity;
import com.ex.entity.DogsEntity;
import com.ex.entity.MonthcareGroupsEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceDTO {
	
	private Integer id;							// ATTENDANCE 고유 식별 번호
	private DogsEntity dog;						// DOGS 테이블 참조키
	private DaycareGroupsEntity daygroup;		// DAYCAREGROUPS 테이블 참조키
	private MonthcareGroupsEntity monthgroup;	// MONTHCAREGROUPS 테이블 참조키
	private LocalDate attendancedate;			// 출석일자
	private String status;						// 출석상태
	
}
