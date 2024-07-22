package com.ex.entity;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Members")
@SequenceGenerator(name="members_seq", sequenceName="members_seq", initialValue=1, allocationSize=0)
public class MembersEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="members_seq")
	private Integer member_id;
	
	@Column(unique=true, nullable=false)
	private String username;
	
	@Column(nullable=false)
	private String name;
	private String email;
	private String phone;
	private LocalDate join_date;
	private String user_type;
	
	@Column(nullable=false)
	private String password;
}
