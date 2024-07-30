package com.ex.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ex.data.DailyReportsDTO;
import com.ex.entity.DailyReportsEntity;
import com.ex.repository.DailyReportsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DailyReportsService {
	
	@Autowired
	private final DailyReportsRepository dailyReportsRepository;
	
	public List<DailyReportsEntity> dailyReportsAll(){
		return dailyReportsRepository.findAll();
	}
	
	public DailyReportsDTO getDailyReports(Long id){
		System.out.println("getDailyReports");
		DailyReportsEntity de = dailyReportsRepository.findById(id).get();
		DailyReportsDTO dailyReportsDTO = DailyReportsDTO.builder()
											.id(de.getId())
											.dogs(de.getDogs())
											.attendance(de.getAttendance())
											.report_date(de.getReport_date())
											.behavior(de.getBehavior())
											.activities(de.getActivities())
											.meals(de.getMeals())
											.health(de.getHealth())
											.bowel(de.getBowel())
											.title(de.getTitle())
											.contents(de.getContents())
											.members(de.getMembers())
											.build();
		return dailyReportsDTO;
	}
	
/*
	public List<DailyReportsDTO> getDiary(String username){
		System.out.println("야호");
//		MembersEntity ue = null;
		DailyReportsEntity ue = null;
		DailyReportsDTO di = null;
		List<DailyReportsDTO> list = null;
		Optional<MembersEntity> op = membersRepository.findByUsername(username);
		if(op.isPresent()) {
			Integer memberId = op.get().getMember_id();
//			Optional<DailyReportsEntity> op2 = calendarRepository.findByMembers(memberId);
//			ue = op2.get();
//			List<DailyReportsEntity> diary = ue.getDiaries();
			List<DailyReportsEntity> diary = null;
			list = new ArrayList<>(diary.size());
			for(DailyReportsEntity d: diary) {
				di = new DailyReportsDTO().builder()
					.report_id(d.getReport_id())
					.dogs(d.getDogs())
					.attendance(d.getAttendance())
					.report_date(d.getReport_date())
					.behavior(d.getBehavior())
					.activities(d.getActivities())
					.meals(d.getMeals())
					.health(d.getHealth())
					.bowel(d.getBowel())
					.contents(d.getContents())
					.build();
				list.add(di);
			}
		}
		return list;
	}
 */

}
