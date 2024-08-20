package com.ex.data;
import java.time.LocalDate;
import com.ex.entity.MembersEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StaffAssignmentsDTO {
   private Integer assignment_id;
   
   private MembersEntity mamber;   
   private Integer   daygroup_id;
   private Integer   monthgroup_id;
   private LocalDate start_date;
   private LocalDate end_date;


   
}
