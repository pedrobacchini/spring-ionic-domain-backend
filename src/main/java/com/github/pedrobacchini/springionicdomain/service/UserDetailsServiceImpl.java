package com.github.pedrobacchini.springionicdomain.service;

import com.github.pedrobacchini.springionicdomain.domain.Client;
import com.github.pedrobacchini.springionicdomain.repository.ClientRepository;
import com.github.pedrobacchini.springionicdomain.security.ClientUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Client> optionalClient = clientRepository.findByEmail(email);
        if(optionalClient.isPresent()) {
            Client client = optionalClient.get();
            return new ClientUserDetails(client.getId(), client.getEmail(), client.getPassword(), client.getRoles());
        } else {
            throw new UsernameNotFoundException(email);
        }
    }
}
