package se.maokei.kanban.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.maokei.kanban.domain.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
    Project findByName(String name);
    Project findByProjectIdentifier(String projectIdentifier);

    @Override
    Iterable<Project> findAll();
}
