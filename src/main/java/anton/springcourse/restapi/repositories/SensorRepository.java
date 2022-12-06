package anton.springcourse.restapi.repositories;

import anton.springcourse.restapi.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer>, JpaSpecificationExecutor<Sensor> {
    Optional<Sensor> findByName(String name);
}
