package com.qorvia.accountservice.config;

import com.qorvia.accountservice.model.user.UserInfo;
import com.qorvia.accountservice.model.organizer.Organizer;
import com.qorvia.accountservice.repository.OrganizerRepository;
import com.qorvia.accountservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrganizerRepository organizerRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserInfo user = userRepository.findByEmail(email).orElse(null);

        if (user != null) {
            return new CustomUserDetails(user);
        }

        Organizer organizer = organizerRepository.findByEmail(email).orElse(null);
        log.info("getting the organizer in CustomUserDetailService : {}", organizer.getEmail());

        if (organizer != null) {
            return new CustomOrganizerDetails(organizer);
        }

        throw new UsernameNotFoundException("Username not found");
    }
}
