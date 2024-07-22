package com.ex.service;
import java.time.LocalDate;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.ex.data.MembersDTO;
import com.ex.entity.MembersEntity;
import com.ex.repository.MembersRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MembersService {
	private final MembersRepository membersRepository;
	private final PasswordEncoder passwordEncoder;
	
	public void createMember(MembersDTO membersDTO) {
		MembersEntity me = MembersEntity.builder().username(membersDTO.getUsername())
					.password(passwordEncoder.encode(membersDTO.getPassword())).email(membersDTO.getEmail())
					.name(membersDTO.getName()).phone(membersDTO.getPhone()).user_type(membersDTO.getUser_type())
					.join_date(LocalDate.now()).build();
		membersRepository.save(me);
	}
}
