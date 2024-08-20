package com.ex.service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ex.data.AttendanceDTO;
import com.ex.data.DogsDTO;
import com.ex.entity.AttendanceEntity;
import com.ex.entity.BranchEntity;
import com.ex.entity.DogsEntity;
import com.ex.entity.MembersEntity;
import com.ex.entity.MonthcareGroupsEntity;
import com.ex.entity.SubscriptionsEntity;
import com.ex.repository.AttendanceRepository;
import com.ex.repository.DogsRepository;
import com.ex.repository.MembersRepository;
import com.ex.repository.MonthcareGroupsRepository;
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
	@Autowired
	MonthcareGroupsRepository monthcareGroupsRepository;
	@Autowired
	DogsRepository dogsRepository;
	
	private final TestMapper testMapper;
	
	public List<AttendanceDTO> getAttendanceAll(){
		List<AttendanceDTO> list = null;
		return list;
	}
	
	
	// 출석부 상세조회
	public AttendanceDTO getAttendanceById(Integer attendanceId) {
		System.out.println("===========서비스 getAttendanceById============");
		AttendanceEntity ae = attendanceRepository.findById(attendanceId).get();
		AttendanceDTO dto = AttendanceDTO.builder()
								.id(ae.getId())
								.dog(ae.getDog())
								.daygroup(ae.getDaygroup())
								.monthgroup(ae.getMonthgroup())
								.attendancedate(ae.getAttendancedate())
								.status(ae.getStatus())
								.dailyreport(ae.getDailyreport())
								.notes(ae.getNotes())
								.branch(ae.getBranch())
								.build();
		return dto;
	}
	
	
	// 해당일자출석부 목록조회
	public List<AttendanceDTO> getAttendanceByDate(LocalDate currentDate){
		return attendanceRepository.findByAttendancedate(currentDate).stream()
				.map(this::convertToDTO)
				.collect(Collectors.toList());
	}
	
	
	public List<AttendanceDTO> getAttendenceByDateAndBranch(LocalDate currentDate, Integer branchId){
		return testMapper.dateAndBranchAttendence(currentDate, branchId);
	}

	
	// 일자,지점1,반1 출석부 조회
	public List<AttendanceDTO> getAttendanceByDateAndBranchOrMonthGroup( String username
												, LocalDate attendancedate
												, Integer branch, Integer monthgroup) {
		
		// 선택된 반이 있다면 반id로 해당일자 출석부 조회
		if(monthgroup != null) {
			System.out.println("(monthgroup != null 선택된 반 있음");
			MonthcareGroupsEntity mge = new MonthcareGroupsEntity();
			mge.setId(monthgroup);
			return attendanceRepository.findByAttendancedateAndMonthgroup(attendancedate, mge)
					.stream()
					.map(this::convertToDTO)
					.collect(Collectors.toList());
			
		// 선택된 반이 없다면 지점id로 해당일자 출석부 조회
		}else {
			BranchEntity be = new BranchEntity();
			be.setBranchId(branch);
			
//			Optional<MembersEntity> me = membersRepository.findByUsername(username);
//			
//			Integer memberid = me.get().getMemberId();
//			System.out.println("memberid ::: " + memberid);
//			AttendanceEntity ae = attendanceRepository.findById(memberid).get();
			
			return attendanceRepository.findByAttendancedateAndBranch(attendancedate, be)
					.stream()
					.map(this::convertToDTO)
					.collect(Collectors.toList());
		}
    }
	
	
	// 지점별 강아지 출력
	public List<DogsDTO> findDogByBranch(Integer branchId) {
        List<DogsEntity> dogs = dogsRepository.findByBranch(branchId);

        List<DogsDTO> dogsDTOList = new ArrayList<>();

        for (DogsEntity dog : dogs) {
            DogsDTO dto = new DogsDTO();
            dto.setDogId(dog.getDogId());
            dto.setDogname(dog.getDogname());
            dogsDTOList.add(dto);
        }
        return dogsDTOList;
    }
	
	
	public void updateAttendance(AttendanceDTO attendanceDTO) {
		
		AttendanceEntity ae = AttendanceEntity.builder()
				.id(attendanceDTO.getId())
				.dog(attendanceDTO.getDog())
				.daygroup(attendanceDTO.getDaygroup())
				.monthgroup(attendanceDTO.getMonthgroup())
				.attendancedate(attendanceDTO.getAttendancedate())
				.status(attendanceDTO.getStatus())
				.dailyreport(attendanceDTO.getDailyreport())
				.notes(attendanceDTO.getNotes())
				.branch(attendanceDTO.getBranch())
				.build();
				
		attendanceRepository.save(ae);
		
		// USER_TYPE != REGULAR >>> 일반 수정
		// USER_TYPE == REGULAR >>> 특이사항만 수정
		
//		if(me.get().getUser_type().equals("REGULAR")) {
//			System.out.println("regular");
//		} else {
//			
//		}
	}
	
	
	// 출석부 등록
	public void createAttendance(Integer branchId, AttendanceDTO attendanceDTO) {
		BranchEntity be = new BranchEntity();
    	be.setBranchId(branchId);
		
        // DTO를 엔티티로 변환
        AttendanceEntity attendanceEntity = new AttendanceEntity();
        attendanceEntity.setAttendancedate(attendanceDTO.getAttendancedate());
        attendanceEntity.setDog(attendanceDTO.getDog());
        attendanceEntity.setMonthgroup(attendanceDTO.getMonthgroup());
        attendanceEntity.setStatus(attendanceDTO.getStatus());
        attendanceEntity.setNotes(attendanceDTO.getNotes());
        attendanceEntity.setBranch(be);

        // 데이터베이스에 저장
        attendanceRepository.save(attendanceEntity);
    }
	
	
	// 결제 > 입학완료 > 개배정 > 한달치 출석부 세팅 일~토 1~7
    public void setMonthAttendance(SubscriptionsEntity subs) {
    	// 사용자의 티켓 요일에 따른 출석부 세팅
    	// 246 > 월수금 71 > 토일
    	// 해당 값의 요일 일자에 출석부 넣어주기
    	
    	// 구독엔티티의 티켓엔티티의 dayofweek 컬럼 가져오기
    	subs.getTicket().getDayofweek();
    	LocalDate localDate;
//    	localDate.
    	
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
	
	
	// 지점id와 선생님id로 
//	public List<MonthcareGroupsDTO> getMonthGroupByTeacherId(Integer branchId, Integer teacherId){
//		List<MonthcareGroupsDTO> monthcareDtoList = new ArrayList<>();
//		List<MonthcareGroupsEntity> monthcareEntityList = monthcareGroupsRepository.findByBranchesAndTeachers(branchId, teacherId);
//		for(MonthcareGroupsEntity me : monthcareEntityList) {
//			MonthcareGroupsDTO dto = new MonthcareGroupsDTO();
//			dto.setId(me.getId());
//			dto.setName(me.getName());
//			dto.setDescription(me.getDescription());
//			dto.setCapacity(me.getCapacity());
//			dto.setBranches(me.getBranches());
//			dto.setTeacher(me.getTeachers());
//			monthcareDtoList.add(dto);
//		}
//		return monthcareDtoList;
//		
//	}
	
}
