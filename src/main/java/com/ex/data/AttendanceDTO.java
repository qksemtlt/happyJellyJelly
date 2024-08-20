package com.ex.data;
import java.time.LocalDate;
import com.ex.entity.AttendanceEntity;
import com.ex.entity.BranchEntity;
import com.ex.entity.DailyReportsEntity;
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

   private Integer id;                   // ATTENDANCE 고유 식별 번호
   private DogsEntity dog;                // DOGS 테이블 참조키
   private DaycareGroupsEntity daygroup;       // DAYCAREGROUPS 테이블 참조키
   private MonthcareGroupsEntity monthgroup;    // MONTHCAREGROUPS 테이블 참조키
   private LocalDate attendancedate;          // 출석일자
   private String status;                   // 출석상태
   private DailyReportsEntity dailyreport;    // 알림장
   private String notes;                   // 특이사항
   private BranchEntity branch;            // BRANCHES 테이블 참조키
   
   // mybatis 전용
//   private Integer attendance_id;
//   private Integer daygroup_id;
//   private Integer monthgroup_id;
//   private Integer dog_dog_id;
//   private Integer daygroup_daygroup_id;
//   private Integer monthgroup_monthgroup_id;
//   private Integer dailyreport_id;
//   private Integer branch_branch_id;

   public void addEntity(AttendanceEntity ae) {
      this.id = ae.getId();
      this.dog = ae.getDog();
      this.daygroup = ae.getDaygroup();
      this.monthgroup = ae.getMonthgroup();
      this.attendancedate = ae.getAttendancedate();
      this.status = ae.getStatus();
      this.dailyreport = ae.getDailyreport();
      this.notes = ae.getNotes();
   }

}
