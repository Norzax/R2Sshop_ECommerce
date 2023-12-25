package com.aclass.r2sshop_ecommerce.services.user_detail;

import com.aclass.r2sshop_ecommerce.models.dto.UserDetail.CustomUserDetails;
import com.aclass.r2sshop_ecommerce.models.entity.UserEntity;
import com.aclass.r2sshop_ecommerce.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User get username " + username + " does not exist!"));

        return new CustomUserDetails(user);
    }
}
