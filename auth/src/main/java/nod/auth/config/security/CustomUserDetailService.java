package nod.auth.config.security;

import lombok.RequiredArgsConstructor;
import nod.auth.domain.user.RoleType;
import nod.auth.domain.user.entity.User;
import nod.auth.domain.user.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User findUser = userRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(RoleType.ROLE_USER.name()));

        return new CustomUser(findUser.getUserId(), findUser.getPassword(), authorities, findUser.getId());
    }
}
