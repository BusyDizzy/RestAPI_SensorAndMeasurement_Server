package anton.springcourse.restapi.controllers;


import anton.springcourse.restapi.dto.SensorDTO;
import anton.springcourse.restapi.services.SensorService;
import anton.springcourse.restapi.util.MeasurementErrorResponse;
import anton.springcourse.restapi.util.MeasuremenException;
import anton.springcourse.restapi.util.SensorValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

import static anton.springcourse.restapi.util.ErrorUtil.returnErrorsToClient;

@RestController
@RequestMapping("/sensors")
public class SensorController {

    private final SensorService sensorService;
    private final SensorValidator sensorValidator;

    public SensorController(SensorService sensorService, SensorValidator sensorValidator) {
        this.sensorService = sensorService;
        this.sensorValidator = sensorValidator;
    }


    @ResponseBody
    @RequestMapping(value = "/registration", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> addSensor(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult){

        sensorValidator.validate(sensorDTO, bindingResult);

        if(bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);

        sensorService.save(sensorService.convertToSensor(sensorDTO));

        return ResponseEntity.ok(HttpStatus.OK);
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
