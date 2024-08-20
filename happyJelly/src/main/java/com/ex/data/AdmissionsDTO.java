package com.ex.data;
import java.time.LocalDate;
import java.util.Date;
import com.ex.entity.BranchEntity;
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
public class AdmissionsDTO {
    private Integer admissionId; //아이디
    private DogsEntity dogs; // 도그 ㅏ이디
    private Date applicationDate; // 입학신청날짜
    private LocalDate desiredDate; // 희망날짜 
    private String status;      //신청상태
    private Date approvalDate;   // 승인날짜 
    private String  pottytraining; // 배변훈련
    private String  marking; //마킹
    private String  ration; // 급여 형태
    private String  appetite; // 식욕
    private Integer  walk; //산책횟수    
    private Integer numberofweeks; // 주 산책횟수
    private String  significant; // 특이사항
    private String reason; // 반려사유
    private BranchEntity branch;
    private MonthcareGroupsEntity monthcaregroups;
}