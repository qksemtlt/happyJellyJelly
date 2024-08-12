package com.ex.service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ex.data.AttendanceDTO;
import com.ex.entity.AttendanceEntity;
import com.ex.entity.MembersEntity;
import com.ex.repository.AttendanceRepository;
import com.ex.repository.MembersRepository;
import com.ex.repository.TestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttendanceService {
	
	@Autowired
	private AttendanceRepository attendanceRepository;
	@Autowired
	private MembersRepository membersRepository;
	
	private final TestMapper testMapper;
	
	public List<AttendanceDTO> getAttendanceAll(){
		List<AttendanceDTO> list = null;
		return list;
	}
	
	// 일자,지점1,전체반 출석부 조회
	public List<AttendanceDTO> getAttendanceDate(LocalDate currentDate){
		
		return attendanceRepository.findByAttendancedate(currentDate).stream()
				.map(this::convertToDTO)
				.collect(Collectors.toList());
	}
	
	public List<AttendanceDTO> dateAndBranchAttendence(LocalDate currentDate, Integer branchId){
		return testMapper.dateAndBranchAttendence(currentDate, branchId);
	}

	// 일자,지점1,반1 출석부 조회
//	public List<AttendanceDTO> getAttendanceMonthgroup(LocalDate currentDate, Integer month_id){
//		// 반이름getMonthGroup
//		return attendanceRepository.findByAttendancedateAndMonthgroup(currentDate, month_id).stream()
//				.map(this::convertToDTO)
//				.collect(Collectors.toList());
//	}
	
	// USER_TYPE != REGULAR >>> 일반 수정
	// USER_TYPE == REGULAR >>> 특이사항만 수정
	public void updateAttendance(Integer username, AttendanceDTO attendanceDTO) {
		Optional<MembersEntity> me = membersRepository.findById(username);
		AttendanceEntity ae = AttendanceEntity.builder()
				.dog(attendanceDTO.getDog())
				.daygroup(attendanceDTO.getDaygroup())
				.monthgroup(attendanceDTO.getMonthgroup())
				.attendancedate(attendanceDTO.getAttendancedate())
				.status(attendanceDTO.getStatus())
				.dailyreport(attendanceDTO.getDailyreport())
				.notes(attendanceDTO.getNotes())
				.build();
				
		attendanceRepository.save(ae);
//		if(me.get().getUser_type().equals("REGULAR")) {
//			System.out.println("regular");
//		} else {
//			
//		}
	}
	
	
	
	
	private AttendanceDTO convertToDTO(AttendanceEntity entity) {
		AttendanceDTO dto = new AttendanceDTO();
		dto.setId(entity.getId());
		dto.setDog(entity.getDog());
		dto.setDaygroup(entity.getDaygroup());
		dto.setMonthgroup(entity.getMonthgroup());
		dto.setAttendancedate(entity.getAttendancedate());
		dto.setStatus(entity.getStatus());
		dto.setNotes(entity.getNotes());
		return dto;
	}

}
