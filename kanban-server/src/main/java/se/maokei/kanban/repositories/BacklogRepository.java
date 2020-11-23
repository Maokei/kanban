package se.maokei.kanban.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.maokei.kanban.domain.Backlog;

@Repository
public interface BacklogRepository extends CrudRepository<Backlog, Long> {
}
