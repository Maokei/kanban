package se.maokei.kanban;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.maokei.kanban.domain.User;
import se.maokei.kanban.repositories.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest extends AbstractContainerBaseTest {
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {

    }

    @AfterEach
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
        //noinspection ResultOfMethodCallIgnored
        userOpt.ifPresentOrElse(
                (usr) -> assertEquals(email, usr.getEmail(), "Users email are not equal"),
                () -> fail("Did not get a user back to test against")
        );
    }
}
