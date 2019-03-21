package sample.repository;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;
import sample.data.Module;

/**
 * Created by buchgeher on 05.09.2016.
 */
@Repository
public interface ModuleRepository extends GraphRepository<Module> {
}
