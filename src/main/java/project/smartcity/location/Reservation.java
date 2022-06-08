package project.smartcity.location;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Reservation {

    private int idParkingSpot;
    private int idDriver;
    private int duration;

    public Reservation(int idParkingSpot, int idDriver, int duration){
        this.idParkingSpot = idParkingSpot;
        this.idDriver = idDriver;
        this.duration = duration;
    }

    public Reservation(){}
}
