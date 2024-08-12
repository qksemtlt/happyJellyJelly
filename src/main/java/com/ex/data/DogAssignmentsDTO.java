package com.ex.data;
import java.time.LocalDate;
import com.ex.entity.DogsEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DogAssignmentsDTO {
	private Integer assignment_id;
	private DogsEntity dogs;
	private Integer daygroup_id;
	private Integer monthgroup_id; 
	private LocalDate start_date;
    private LocalDate end_date;

}
