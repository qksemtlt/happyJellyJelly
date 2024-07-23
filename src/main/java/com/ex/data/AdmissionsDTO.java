package com.ex.data;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdmissionsDTO {
    private Long admissionId;
    private Long dogId;
    private Date applicationDate;
    private String status;
    private Date approvalDate;
    private String desiredSubsType;
    private Integer desiredUsageCount;
    private Integer desiredDaysPerWeek;
}