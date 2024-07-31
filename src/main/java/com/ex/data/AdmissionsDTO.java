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
    private Integer admissionId; //아이디
    private DogsEntity dogs; // 도그 ㅏ이디
    private Date applicationDate; // 입학신청날짜
    private String status;		//신청상태
    private Date approvalDate;	// 승인날짜 
    private String desiredSubsType; // 구독 타입
    private Integer desiredUsageCount;  // 희망날짜
    private Integer desiredDaysPerWeek; // 
    private String  pottytraining; // 배변훈련
    private String  marking; //마킹
    private String  ration; // 급여 형태
    private String  appetite; // 식욕
    private Integer  walk; //산책횟수    
    private Integer numberofweeks; // 주 산책횟수
    private String  significant; // 특이사항
}