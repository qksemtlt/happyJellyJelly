package com.ex.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.ex.data.CalendarDTO;
import com.ex.entity.CalendarEntity;
import com.ex.entity.MembersEntity;
import com.ex.repository.MembersRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CalendarService {
	
	private final MembersRepository membersRepository;

	public List<CalendarDTO> getDiary(String username){
		MembersEntity ue = null;
		CalendarDTO di = null;
		List<CalendarDTO> list = null;
		Optional<MembersEntity> op =  membersRepository.findByUsername(username);
		if(op.isPresent()) {
			ue = op.get();
//			List<CalendarEntity> diary = ue.getDiaries();
			List<CalendarEntity> diary = null;
			list = new ArrayList<>(diary.size());
			for(CalendarEntity d: diary) {
				di = new CalendarDTO().builder()
					.report_id(d.getReport_id())
					.dogs(d.getDogs())
					.attendance_id(d.getAttendance_id())
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
}
