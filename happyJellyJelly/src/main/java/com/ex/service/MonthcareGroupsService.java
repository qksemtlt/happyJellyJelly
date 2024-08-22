package com.ex.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.ex.data.MembersDTO;
import com.ex.data.MonthcareGroupsDTO;
import com.ex.entity.BranchEntity;
import com.ex.entity.MembersEntity;
import com.ex.entity.MonthcareGroupsEntity;
import com.ex.repository.BranchesRepository;
import com.ex.repository.MembersRepository;
import com.ex.repository.MonthcareGroupsRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MonthcareGroupsService {
	private final MonthcareGroupsRepository monthcareGroupsRepository;
	private final BranchesRepository branchRepository;
	private final MembersRepository membersRepository;
	
	// 해당 지점(branch_id)에 속해있는 반 리스트 조회
	public List<MonthcareGroupsDTO> getMonthcareGroupByBranch(Integer branchId){
		Optional<BranchEntity> op = branchRepository.findById(branchId);
		MonthcareGroupsDTO monthcareGroupsDTO = null;
		List<MonthcareGroupsDTO> monthcareDTOList = null;
		if(op.isPresent()) {
			BranchEntity branchEntity = op.get();
			List<MonthcareGroupsEntity> monthcareEntityList = branchEntity.getMonth();
			monthcareDTOList = new ArrayList<>(monthcareEntityList.size());
			for(MonthcareGroupsEntity month : monthcareEntityList) {
				monthcareGroupsDTO = new MonthcareGroupsDTO().builder().id(month.getId())
						.name(month.getName()).description(month.getDescription())
						.capacity(month.getCapacity())
						.teacher(month.getTeachers())
						.branches(month.getBranches()).build();
				monthcareDTOList.add(monthcareGroupsDTO);
			}
		}
		return monthcareDTOList;
	}
	
	// 해당 지점의 신규 반 생성
	public void createMonthcareGroup(Integer branch_id, MonthcareGroupsDTO monthDTO) {
		Optional<BranchEntity> op = branchRepository.findById(branch_id);
		if(op.isPresent()) {
			BranchEntity be = op.get();
			MonthcareGroupsEntity monthEntity = MonthcareGroupsEntity.builder().
					name(monthDTO.getName()).description(monthDTO.getDescription())
					.capacity(monthDTO.getCapacity()).teachers(monthDTO.getTeacher())
					.branches(be).build();
			monthcareGroupsRepository.save(monthEntity);
		}
	}
	
	// members 테이블에서 branch_id 조회 후 해당 지점의 선생님 리스트 조회
	public List<MembersDTO> getTeachers(Integer branch_id){		
		List<MembersDTO> membersDTOList = new ArrayList<>();
		List<MembersEntity> membersEntityList = membersRepository.findAll();
		for(MembersEntity me : membersEntityList) {
			if(me.getUserType().equals("TEACHER")&&me.getBranchId()==branch_id) {
				MembersDTO membersDTO = new MembersDTO();
				membersDTO.setMemberId(me.getMemberId());
				membersDTO.setUsername(me.getUsername());
				membersDTO.setName(me.getName());
				membersDTOList.add(membersDTO);
			}
		}
		return membersDTOList;
	}
	
	// 반 아이디 (monthcareGroups id)로 해당 반 정도 조회
	public MonthcareGroupsDTO getMonthGroup(Integer month_id) {
		Optional<MonthcareGroupsEntity> op = monthcareGroupsRepository.findById(month_id);
		if(op.isPresent()) {
			MonthcareGroupsEntity me = op.get();
			MonthcareGroupsDTO monthDTO = MonthcareGroupsDTO.builder().id(me.getId())
								.name(me.getName()).description(me.getDescription()).capacity(me.getCapacity())
								.teacher(me.getTeachers()).branches(me.getBranches()).build();
			return monthDTO;
		}else {
			throw new RuntimeException("class not found");
		}
	}
	
	// 반 아이디를 가지고 해당 반 정보 수정
	public void updateMonthgroup(Integer month_id, MonthcareGroupsDTO monthDTO) {
		Optional<MonthcareGroupsEntity> op = monthcareGroupsRepository.findById(month_id);
		if(op.isPresent()) {
			MonthcareGroupsEntity me = op.get();
			me.setName(monthDTO.getName());
			me.setDescription(monthDTO.getDescription());
			me.setCapacity(monthDTO.getCapacity());
			me.setTeachers(monthDTO.getTeacher());
			monthcareGroupsRepository.save(me);
		}
	}
}
