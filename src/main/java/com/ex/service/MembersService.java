package com.ex.service;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
	
	public String findMemberUsername(MembersDTO membersDTO) {
		Optional<MembersEntity> op = membersRepository.findByNameAndEmailAndPhone(membersDTO.getName(), membersDTO.getEmail(), membersDTO.getPhone());
		if(op.isPresent()) {
			MembersEntity me = op.get();
			membersDTO.setUsername(me.getUsername());
			return "찾으신 아이디는" + membersDTO.getUsername() + "입니다.";
		}else {
			return "찾으시는 아이디가 없습니다. 정보를 확인해주세요";
		}		
	}
	
	public int passwdLostCheck(MembersDTO membersDTO) {
		int count = membersRepository.countByUsernameAndNameAndPhone(membersDTO.getUsername(), membersDTO.getName(), membersDTO.getPhone());
		return count;
	}
	
	public void passwdChange(String username, String pw) {
		MembersEntity me = membersRepository.findByUsername(username).get();
		me.setPassword(passwordEncoder.encode(pw));
		membersRepository.save(me);
	}
	
	public boolean checkPassword(String username, String rawPassword) {
	    Optional<MembersEntity> op = membersRepository.findByUsername(username);
	    if (op.isPresent()) {
	        MembersEntity me = op.get();
	        return passwordEncoder.matches(rawPassword, me.getPassword());
	    }
	    return false;
	}
	
	public MembersDTO readMembersInfo(String username) {
		Optional<MembersEntity> op = membersRepository.findByUsername(username);
		if(op.isPresent()) {
			MembersEntity me = op.get();
			MembersDTO membersDTO = MembersDTO.builder().username(me.getUsername())
					.name(me.getName()).email(me.getEmail())
					.phone(me.getPhone()).join_date(me.getJoin_date())
					.user_type(me.getUser_type()).build();
			return membersDTO;
		}else {
			throw new RuntimeException("user not found");
		}		
	}
	
	public void updateInfo(MembersDTO membersDTO) {
		MembersEntity me = membersRepository.findByUsername(membersDTO.getUsername()).get();
		me.setEmail(membersDTO.getEmail());
		me.setPhone(membersDTO.getPhone());
		membersRepository.save(me);
	}
	
	public void deleteMember(String username) {
		MembersEntity me = membersRepository.findByUsername(username).get();
		membersRepository.delete(me);
	}
	// 추가
	 public MembersEntity findByUsername(String username) {
	        return membersRepository.findByUsername(username)
	            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
	    }
}
