package com.ex.data;

import java.util.Date;

import com.ex.entity.DogsEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdmissionsDTO {
    private Integer admissionId;
    
    private Integer dog_id; 
    
    private String dogname;
    
    private Date applicationDate;
    private String status;
    private Date approvalDate;
    private String desiredSubsType;
    private Integer desiredUsageCount;
    private Integer desiredDaysPerWeek;
    
    
}