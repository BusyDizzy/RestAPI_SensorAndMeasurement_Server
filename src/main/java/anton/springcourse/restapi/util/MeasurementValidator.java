package anton.springcourse.restapi.util;

import anton.springcourse.restapi.dto.MeasurementDTO;
import anton.springcourse.restapi.dto.SensorDTO;
import anton.springcourse.restapi.models.Sensor;
import anton.springcourse.restapi.repositories.SensorRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


import java.util.Optional;

@Component
public class MeasurementValidator implements Validator {
    private final SensorRepository sensorRepository;

    public MeasurementValidator(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Override
     public boolean supports(Class<?> aClass) {
        return MeasurementDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        MeasurementDTO measurementDTO = (MeasurementDTO) o;

        if (measurementDTO.getSensor() == null){
            return;
        }

        if (sensorRepository.findByName(measurementDTO.getSensor().getName()).isEmpty())
            errors.rejectValue("sensor","","Sensor does not exists");
    }
}
