package com.ex.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ex.data.DailyReportsDTO;
import com.ex.entity.DailyReportsEntity;
import com.ex.entity.MembersEntity;
import com.ex.repository.DailyReportsRepository;
import com.ex.repository.MembersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DailyReportsService {
	
	@Autowired
	private final DailyReportsRepository dailyReportsRepository;
	@Autowired
	private final MembersRepository membersRepository;
	@Autowired
	private final MembersService membersService;
	
	public List<DailyReportsDTO> getDailyReportsList(String username){
		List<DailyReportsDTO> list = null;
		DailyReportsDTO di = null;
		Optional<MembersEntity> op = membersRepository.findByUsername(username);
		if(op.isPresent()) {
			List<DailyReportsEntity> delist = dailyReportsRepository.findByMembers(op.get());
			list = new ArrayList<>(delist.size());
			for(DailyReportsEntity d: delist) {
				di = new DailyReportsDTO().builder()
						.id(d.getId())
						.dogs(d.getDogs())
						.attendance(d.getAttendance())
						.report_date(d.getReport_date())
						.behavior(d.getBehavior())
						.activities(d.getActivities())
						.meals(d.getMeals())
						.health(d.getHealth())
						.bowel(d.getBowel())
						.contents(d.getContents())
						.title(d.getTitle())
						.build();
//						.members(d.getMembers())
				list.add(di);
			}
			
		}
//		return dailyReportsRepository.findAll();
		return list;
	}
	
	public DailyReportsDTO getDailyReports(Integer id){
		DailyReportsEntity de = dailyReportsRepository.findById(id).get();
//		System.out.println("de.getTitle() ::: " + de.getTitle());
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
	
	// 알림장등록
	public void create(DailyReportsDTO dailyReportsDTO, String username, String selectDate) {
//		LocalDate diarydate = LocalDate.parse(selectDate);
		
		DailyReportsEntity de = DailyReportsEntity.builder()
								.dogs(dailyReportsDTO.getDogs())
								.attendance(dailyReportsDTO.getAttendance())
								.report_date(dailyReportsDTO.getReport_date())
								.behavior(dailyReportsDTO.getBehavior())
								.activities(dailyReportsDTO.getActivities())
								.meals(dailyReportsDTO.getMeals())
								.health(dailyReportsDTO.getHealth())
								.bowel(dailyReportsDTO.getBowel())
								.contents(dailyReportsDTO.getContents())
								.title(dailyReportsDTO.getTitle())
								.members(dailyReportsDTO.getMembers())
								.build();
		
		dailyReportsRepository.save(de);
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
