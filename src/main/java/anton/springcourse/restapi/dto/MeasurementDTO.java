package anton.springcourse.restapi.dto;
import org.hibernate.validator.constraints.Range;
import javax.validation.constraints.NotNull;


public class MeasurementDTO {

    @NotNull
    @Range(min = -100, max = 100, message = "Диапазон значенией от -100 до 100")
    private Double value;

    @NotNull
    private Boolean raining;
    @NotNull
    private SensorDTO sensor;

    public Double getValue() {
        return value;
    }

    public MeasurementDTO() {
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Boolean isRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }
    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }
}
