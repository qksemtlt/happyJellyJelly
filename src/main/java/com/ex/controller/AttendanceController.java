package com.ex.controller;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ex.data.AttendanceDTO;
import com.ex.data.BranchesDTO;
import com.ex.data.DogsDTO;
import com.ex.data.MonthcareGroupsDTO;
import com.ex.entity.BranchEntity;
import com.ex.entity.DogsEntity;
import com.ex.entity.MembersEntity;
import com.ex.entity.MonthcareGroupsEntity;
import com.ex.repository.TestMapper;
import com.ex.service.AttendanceService;
import com.ex.service.BranchesService;
import com.ex.service.DogService;
import com.ex.service.MembersService;
import com.ex.service.MonthcareGroupsService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/attendance*")
@RequiredArgsConstructor
public class AttendanceController {

	@Autowired
	private AttendanceService attendanceService;
	@Autowired
	private MembersService membersService;
	@Autowired
	private MonthcareGroupsService monthcareGroupsService;
	@Autowired
	private BranchesService branchesService;
	@Autowired
	private DogService dogService;
	
	@GetMapping("test")
	@ResponseBody
	public int test() {
		return 0;
	}

	
	@GetMapping("")
    public String getAttendance(@RequestParam(value = "date", required = false) String date,
    							@RequestParam(value = "selectMonthGroup", required = false) Integer selectMonthGroup,
                                Model model, Principal principal) {
		
		// 선택한 일자가 없을시 오늘 날짜로 세팅
		LocalDate currentDate = (date != null) ? LocalDate.parse(date) : LocalDate.now();
		
		// 사용자정보(지점id) 조회
		MembersEntity me = membersService.findByUsername(principal.getName());
		Integer branchId = me.getBranchId();
		
		// 소속지점정보 조회 (화면 지점이름을 조회하기 위함)
		BranchesDTO brancheDTO = branchesService.getBranchById(branchId);
		
		// 소속지점정규반 목록조회 (반선택 목록 조회하기 위함)
		List<MonthcareGroupsDTO> monthGroupList = monthcareGroupsService.getMonthcareGroupByBranch(branchId);
		
        // 모든반 출석부 목록조회	: 미선택시 	> 선생님이 근무하고 있는 지점의 해당일자 모든반 출석부
        // 특정반 출석부 목록조회 	: 반선택시 	> 선생님이 근무하고 있는 지점의 해당일자 해당반 출석부
        List<AttendanceDTO> attendances = attendanceService.getAttendanceByDateAndBranchOrMonthGroup(currentDate, branchId, selectMonthGroup);

        model.addAttribute("currentDate", currentDate);
        model.addAttribute("brancheDTO", brancheDTO);
        model.addAttribute("monthGroupList", monthGroupList);
        model.addAttribute("attendances", attendances);
        return "attendance/attendanceList";
    }
	
	
	@GetMapping("/createAttendance")
    public String createAttendanceForm(@RequestParam(value = "date", required = false) String date,
							            Model model, Principal principal) {
		MembersEntity me = membersService.findByUsername(principal.getName());
		Integer branchId = me.getBranchId();
		System.out.println("===============컨트롤러 createAttendance=================");
		System.out.println("branchId ::: " + branchId);
//		BranchesDTO brancheDTO = branchesService.getBranchById(branchId);
		BranchEntity be = new BranchEntity();
		be.setBranchId(branchId);
		List<MonthcareGroupsDTO> monthGroupList = monthcareGroupsService.getMonthcareGroupByBranch(branchId);
        List<DogsDTO> dogList = attendanceService.findByBranch(branchId);
        model.addAttribute("brancheEntity", be);
        model.addAttribute("monthGroupList", monthGroupList);
        model.addAttribute("dogList", dogList);
        return "attendance/createAttendance";
    }

    @PostMapping("/create")
    public String createAttendance(AttendanceDTO attendanceDTO) {
        attendanceService.createAttendance(attendanceDTO);
        return "redirect:/attendance";
    }
	
}
