package com.ex.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "ADMISSIONS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdmissionsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admissions_seq")
    @SequenceGenerator(name = "admissions_seq", sequenceName = "admissions_SEQ", allocationSize = 1)
      @Column(name = "ADMISSION_ID")
      private Integer admissionId;
      
     @ManyToOne
     @JoinColumn(name = "dog_id") //
     private DogsEntity dogs;
     
     
      @Column(name = "APPLICATION_DATE")
      private Date applicationDate;

      @Column(name = "STATUS")
      private String status;

      @Column(name = "APPROVAL_DATE")
      private Date approvalDate;

      @Column(name = "DESIRED_SUBS_TYPE")
      private String desiredSubsType;

      @Column(name = "DESIRED_USAGE_COUNT")
      private Integer desiredUsageCount;

      @Column(name = "DESIRED_DAYS_PER_WEEK")
      private Integer desiredDaysPerWeek;

      private String  pottytraining; // 배변훈련
      private String  marking; //마킹
      private String  ration; // 급여 형태
      private String  appetite; // 식욕
      private Integer  walk; //산책횟수    
      private Integer numberofweeks; // 주 산책횟수
      private String  significant; // 특이사항
      private String reason; // 반려사유
    }
