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
    
    private DogsEntity dogs;
    
   
    
    private Date applicationDate;
    
    private String status;
    private Date approvalDate;
    private String desiredSubsType;
    private Integer desiredUsageCount;
    private Integer desiredDaysPerWeek;
    
    private String  pottytraining; // 배변훈련
    private String  marking; //마킹
    private String  ration; // 급여 형태
    private String  appetite; // 식욕
    private Integer  walk; //산책횟수    
    private Integer numberofweeks; // 주 산책횟수
    private String  significant; // 특이사항

    
    
}