package se.maokei.kanban.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.maokei.kanban.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
}
