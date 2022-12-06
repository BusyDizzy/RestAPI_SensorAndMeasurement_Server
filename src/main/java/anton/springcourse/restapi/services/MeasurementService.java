package anton.springcourse.restapi.services;

import anton.springcourse.restapi.dto.MeasurementDTO;
import anton.springcourse.restapi.models.Measurement;
import anton.springcourse.restapi.models.Sensor;
import anton.springcourse.restapi.repositories.MeasurementRepository;
import anton.springcourse.restapi.repositories.SensorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final SensorRepository sensorRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorRepository sensorRepository, ModelMapper modelMapper) {
        this.measurementRepository = measurementRepository;
        this.sensorRepository = sensorRepository;
        this.modelMapper = modelMapper;
    }

    public List<Measurement> findAll(){
       return measurementRepository.findAll();
    }

    @Transactional
    public void save(Measurement measurement){
        int sensor_id = 0;
        Sensor sensor = measurement.getSensor();
        Optional<Sensor> dbSensor = sensorRepository.findByName(sensor.getName());

        if (dbSensor.isPresent()){
            sensor_id = dbSensor.get().getId();
        }
        sensor.setId(sensor_id);

        measurement.setSensor(sensor);
        measurement.setMeasuredAT(LocalDateTime.now());
        measurementRepository.save(measurement);
    }

    public MeasurementDTO convertToMeasurementDTO(Measurement measurement){
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

    public Measurement convertToMeasurement(MeasurementDTO measurementDTO){
        return modelMapper.map(measurementDTO, Measurement.class);
    }

}
