package anton.springcourse.restapi.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class SensorDTO {

    @NotEmpty(message ="Name should not be empty")
    @Size(min = 3, max =30, message = "Name should be greater than 3 and less than 30")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
