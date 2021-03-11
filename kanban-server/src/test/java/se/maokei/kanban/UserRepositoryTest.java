package se.maokei.kanban;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import se.maokei.kanban.domain.User;
import se.maokei.kanban.repositories.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Fail.fail;
import static org.junit.Assert.assertEquals;

@DataJpaTest
@RunWith(SpringRunner.class)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Before
    public void setup() {

    }

    @After
    public void afterAllTests() {
        userRepository.deleteAll();
    }

    @Test
    public void persistUserTest() {
        String email = "johndoe@email.com";
        User user = new User();
        user.setName("John Does");
        user.setEmail(email);
        user.setUsername("thunder");
        user.setPassword("secret");

        userRepository.save(user);
        Optional<User> userOpt = userRepository.findByEmail(email);
        userOpt.ifPresentOrElse((usr) -> {
            assertEquals("Users ID are not equal", user.getId(), usr.getId());
        }, () -> fail("Did not get a user back to test against"));
    }
}
