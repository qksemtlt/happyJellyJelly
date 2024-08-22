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
	
	// 회원가입
	public void createMember(MembersDTO membersDTO) {
		MembersEntity me = MembersEntity.builder().username(membersDTO.getUsername())
					.password(passwordEncoder.encode(membersDTO.getPassword())).email(membersDTO.getEmail())
					.name(membersDTO.getName()).phone(membersDTO.getPhone()).userType(membersDTO.getUserType())
					.joinDate(LocalDate.now()).build();
		membersRepository.save(me);
	}
	
	// 아이디 찾기
	public String findMemberUsername(MembersDTO membersDTO) {
		Optional<MembersEntity> op = membersRepository.findByNameAndEmailAndPhone(membersDTO.getName(), membersDTO.getEmail(), membersDTO.getPhone());
		if(op.isPresent()) {
			MembersEntity me = op.get();
			membersDTO.setUsername(me.getUsername());
			return "찾으신 아이디는 [" + membersDTO.getUsername() + "] 입니다.";
		}else {
			return "찾으시는 아이디가 없습니다. 정보를 확인해주세요";
		}		
	}
	
	// 비밀번호 찾기 (개인 정보 입력 후 일치하는 레코드 개수 리턴
	public int passwdLostCheck(MembersDTO membersDTO) {
		int count = membersRepository.countByUsernameAndNameAndPhone(membersDTO.getUsername(), membersDTO.getName(), membersDTO.getPhone());
		return count;
	}
	
	// 비밀번호 변경
	public void passwdChange(String username, String pw) {
		MembersEntity me = membersRepository.findByUsername(username).get();
		me.setPassword(passwordEncoder.encode(pw));
		membersRepository.save(me);
	}
	
	// 비밀번호 변경 시 유효성 검사 진행하는 메소드
	// 기존 비밀번호와 새로 변경하려는 비밀번호가 일치하면 false 리턴
	public boolean checkPassword(String username, String rawPassword) {
	    Optional<MembersEntity> op = membersRepository.findByUsername(username);
	    if (op.isPresent()) {
	        MembersEntity me = op.get();
	        return passwordEncoder.matches(rawPassword, me.getPassword());
	    }
	    return false;
	}
	
	// username 으로 검색하여 레코드 한 줄 출력
	public MembersDTO readMembersInfo(String username) {
		Optional<MembersEntity> op = membersRepository.findByUsername(username);
		if(op.isPresent()) {
			MembersEntity me = op.get();
			MembersDTO membersDTO = MembersDTO.builder().username(me.getUsername())
					.name(me.getName()).email(me.getEmail())
					.phone(me.getPhone()).joinDate(me.getJoinDate())
					.userType(me.getUserType()).branchId(me.getBranchId()).build();
			return membersDTO;
		}else {
			throw new RuntimeException("user not found");
		}		
	}
	
	// 개인정보 변경
	public void updateInfo(MembersDTO membersDTO) {
		MembersEntity me = membersRepository.findByUsername(membersDTO.getUsername()).get();
		me.setEmail(membersDTO.getEmail());
		me.setPhone(membersDTO.getPhone());
		membersRepository.save(me);
	}
	
	// 회원 탈퇴
	public void deleteMember(String username) {
		MembersEntity me = membersRepository.findByUsername(username).get();
		membersRepository.delete(me);
	}
	
	public MembersEntity findByUsername(String username) {
        return membersRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
	
	// 카카오, 네이버 로그인 시 이전 로그인 기록이 있는지 확인 후 없으면 회원 가입 해주는 메서드
	public void socialCheck(String username, String name) {
		Optional<MembersEntity> op = membersRepository.findByUsername(username);
		if(op.isEmpty()) {
			MembersEntity me = MembersEntity.builder().username(username).
					name(name).password(passwordEncoder.encode("12")).
					joinDate(LocalDate.now()).userType("REGULAR")
					.build();
			membersRepository.save(me);
		}
	}
}
