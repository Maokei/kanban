package se.maokei.kanban.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.maokei.kanban.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
