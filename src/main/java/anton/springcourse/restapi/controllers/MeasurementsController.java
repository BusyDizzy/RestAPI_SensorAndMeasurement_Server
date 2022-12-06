package anton.springcourse.restapi.controllers;

import anton.springcourse.restapi.dto.MeasurementDTO;
import anton.springcourse.restapi.dto.MeasurementResponse;
import anton.springcourse.restapi.models.Measurement;
import anton.springcourse.restapi.services.MeasurementService;
import anton.springcourse.restapi.services.SensorService;
import anton.springcourse.restapi.util.MeasuremenException;
import anton.springcourse.restapi.util.MeasurementErrorResponse;
import anton.springcourse.restapi.util.MeasurementValidator;
import anton.springcourse.restapi.util.ErrorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

    private final MeasurementService measurementService;

    private final SensorService sensorService;

    private final ModelMapper modelMapper;
    private final MeasurementValidator measurementValidator;

    public MeasurementsController(MeasurementService measurementService, SensorService sensorService, ModelMapper modelMapper, MeasurementValidator measurementValidator) {
        this.measurementService = measurementService;
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
        this.measurementValidator = measurementValidator;
    }

    @ResponseBody
    @RequestMapping( method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MeasurementDTO[]> getMeasurements(){
        List<MeasurementDTO> measurements = measurementService.findAll().stream().map(measurementService::convertToMeasurementDTO)
                .collect(Collectors.toList());
        MeasurementDTO[] m = new MeasurementDTO[measurements.size()];
        measurements.toArray(m);

        return new ResponseEntity<MeasurementDTO[]>(m, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult){

        measurementValidator.validate(measurementDTO, bindingResult);

        if(bindingResult.hasErrors())
            ErrorUtil.returnErrorsToClient(bindingResult);

        measurementService.save(measurementService.convertToMeasurement(measurementDTO));

        return  ResponseEntity.ok(HttpStatus.OK);
    }

    @RequestMapping(value ="/rainyDaysCount", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Long getRainyDaysCount(){
        return measurementService.findAll().stream().filter(Measurement::isRaining).count();
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasuremenException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
