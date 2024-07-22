package com.ex.security;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.ex.entity.MembersEntity;
import com.ex.repository.MembersRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {
	private final MembersRepository membersRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		Optional<MembersEntity> op = this.membersRepository.findByUsername(username);
		if(op.isEmpty()) {
			throw new RuntimeException("사용자를 찾을 수 없습니다.");
		}
		MembersEntity me = op.get();
		List<GrantedAuthority> grantedList = new ArrayList<>();
		if("admin".equals(username)) {
			grantedList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}else {
			grantedList.add(new SimpleGrantedAuthority("ROLE_USER"));
		}
		return new User(me.getUsername(), me.getPassword(), grantedList);
	}
}
