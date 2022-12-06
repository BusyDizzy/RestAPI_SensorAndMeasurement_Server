package anton.springcourse.restapi.services;

import anton.springcourse.restapi.dto.SensorDTO;
import anton.springcourse.restapi.models.Sensor;
import anton.springcourse.restapi.repositories.SensorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorService {

    private final SensorRepository sensorRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SensorService(SensorRepository sensorRepository, ModelMapper modelMapper) {
        this.sensorRepository = sensorRepository;
        this.modelMapper = modelMapper;
    }
   public Optional<Sensor> findByName(String name){
        return sensorRepository.findByName(name);
    }
   @Transactional
   public void save(Sensor sensor){
       sensorRepository.save(sensor);
   }

   public Sensor convertToSensor(SensorDTO sensorDTO){
       return modelMapper.map(sensorDTO, Sensor.class);
   }

}
