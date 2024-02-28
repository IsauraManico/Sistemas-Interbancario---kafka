package ucan.edu.sb24.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ucan.edu.sb24.repository.ClienteRepository;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    ClienteRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByNome(username);
    }
}
