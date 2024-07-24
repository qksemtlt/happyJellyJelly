package com.ex.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "branches")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SequenceGenerator(name="branches_seq", sequenceName="branches_seq", initialValue=1, allocationSize=0)
public class BranchEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="branches_seq")
    @Column(name = "BRANCH_ID")
    private Integer branch_id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String address;
    
    @Column(nullable = false)
    private String phone;
    
    @Column(nullable = false)
    private Integer manager_id;
    
    @Column(nullable = false)
    private boolean active = true;
}