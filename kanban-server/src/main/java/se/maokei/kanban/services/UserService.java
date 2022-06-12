package se.maokei.kanban.services;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import se.maokei.kanban.domain.User;
import se.maokei.kanban.exceptions.UsernameAlreadyExistsException;
import se.maokei.kanban.repositories.UserRepository;

@AllArgsConstructor
@Service
public class UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser (User newUser){
        try{
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            //Username has to be unique (exception)
            newUser.setUsername(newUser.getUsername());
            // Make sure that password and confirmPassword match
            // We don't persist or show the confirmPassword
            newUser.setConfirmPassword("");
            return userRepository.save(newUser);

        }catch (Exception e) {
            throw new UsernameAlreadyExistsException("Username '"+newUser.getUsername()+"' already exists");
        }
    }
}
