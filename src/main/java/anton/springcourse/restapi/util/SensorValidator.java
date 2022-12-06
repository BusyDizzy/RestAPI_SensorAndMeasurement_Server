package anton.springcourse.restapi.util;

import anton.springcourse.restapi.dto.SensorDTO;
import anton.springcourse.restapi.repositories.SensorRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class SensorValidator implements Validator {

    private final SensorRepository sensorRepository;

    public SensorValidator(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return SensorDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        SensorDTO sensorDTO = (SensorDTO) o;
        if(sensorRepository.findByName(sensorDTO.getName()).isPresent())
            errors.rejectValue("name","","Sensor already exists");
    }


}
