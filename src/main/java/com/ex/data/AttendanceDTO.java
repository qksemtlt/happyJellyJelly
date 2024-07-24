package com.ex.data;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceDTO {
	private Integer attendance_id;		// ATTENDANCE 고유 식별 번호
	private Integer dog_id;				// DOGS 테이블 참조키
	private Integer daygroup_id;		// DAYCAREGROUPS 테이블 참조키
	private Integer monthgroup_id;		// MONTHCAREGROUPS 테이블 참조키
	private Date attendance_date;		// 출석일자
	private String status;				// 출석상태
	
	/*
	ATTENDANCE_ID	NUMBER	No
	DOG_ID			NUMBER	Yes
	DAYGROUP_ID		NUMBER	Yes
	MONTHGROUP_ID	NUMBER	Yes
	ATTENDANCE_DATE	DATE	Yes
	STATUS			VARCHAR2(20 BYTE)	Yes
	*/
	
	
}
