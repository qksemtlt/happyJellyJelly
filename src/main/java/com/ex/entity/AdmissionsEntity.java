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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long admission_id;
    private Long	dog_id;
    private Date	application_date;
    private String	status;
    private Date	approval_date;
    private String	desired_subs_type;
    private Long	desired_usage_count;
    private Long	desired_days_per_week;
;
}