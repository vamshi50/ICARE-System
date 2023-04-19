package com.icare.db;

import com.icare.db.entities.UserPassword;
import com.icare.db.repositories.UserPasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService implements UserDetailsService {
    @Autowired
    private UserPasswordRepository userPasswordRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        UserPassword userPassword = userPasswordRepository.findByUserName(username);

        if (userPassword == null) {
            throw new UsernameNotFoundException("User  not found.");
        }
        System.out.println("userObj");
        System.out.println(userPassword);

        String role = userPassword.getUser().getRole();
        System.out.println("role"+role);
        UserDetails userDetails = User.withUsername(userPassword.getUserName()).password(userPassword.getEncryptedPassword()).roles(role)
                .authorities(role).build();

        return userDetails;
    }
}