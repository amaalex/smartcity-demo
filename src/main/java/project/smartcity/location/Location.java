package project.smartcity.location;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Location {

    @JsonProperty("latitude")
    private double latitude;

    @JsonProperty("longitude")
    private double longitude;

    public Location(){}

    public Location(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
