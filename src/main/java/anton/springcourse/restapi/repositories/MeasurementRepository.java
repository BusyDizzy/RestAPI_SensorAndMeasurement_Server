package anton.springcourse.restapi.repositories;

import anton.springcourse.restapi.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer>, JpaSpecificationExecutor<Measurement> {

}

