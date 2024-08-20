<<<<<<< HEAD
package com.ex.entity;
import java.math.BigDecimal;
import java.util.List;
import org.hibernate.annotations.ColumnDefault;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BRANCHES")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SequenceGenerator(name="branches_seq", sequenceName="branches_seq", initialValue=1, allocationSize=0)
public class BranchEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="branches_seq")
    @Column(name = "BRANCH_ID")
    private Integer branchId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "POSTCODE")
    private String postCode;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "ADDRESS2")
    private String address2;

    @Column(name = "PHONE")
    private String phone;
    
    @Column(name = "ACTIVE")
    @ColumnDefault("true")
    private boolean active;    
    
    @Column(name = "LATITUDE", precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(name = "LONGITUDE", precision = 10, scale = 8)
    private BigDecimal longitude;
    
    @OneToMany(mappedBy="branches", cascade=CascadeType.REMOVE)
    @JsonManagedReference
    private List<MonthcareGroupsEntity> month;
=======
package com.ex.entity;
import java.math.BigDecimal;
import java.util.List;
import org.hibernate.annotations.ColumnDefault;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BRANCHES")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SequenceGenerator(name="branches_seq", sequenceName="branches_seq", initialValue=1, allocationSize=0)
public class BranchEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="branches_seq")
    @Column(name = "BRANCH_ID")
    private Integer branchId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "POSTCODE")
    private String postCode;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "ADDRESS2")
    private String address2;

    @Column(name = "PHONE")
    private String phone;
    
    @Column(name = "ACTIVE")
    @ColumnDefault("true")
    private boolean active;    
    
    @Column(name = "LATITUDE", precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(name = "LONGITUDE", precision = 10, scale = 8)
    private BigDecimal longitude;
    
    @OneToMany(mappedBy="branches", cascade=CascadeType.REMOVE)
    @JsonManagedReference
    private List<MonthcareGroupsEntity> month;
>>>>>>> branch 'woocheol' of https://github.com/gomting0/happyJelly.git
}