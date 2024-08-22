package com.ex.data;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffMgDTO {
    private Integer memberId;
    private String username;
    private String name;
    private String email;
    private String phone;
    private String usertype;
    private Integer branchId;
    private String branchName;
    private boolean active;
}