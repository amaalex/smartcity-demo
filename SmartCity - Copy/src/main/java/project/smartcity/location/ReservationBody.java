package project.smartcity.location;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationBody {
    private String username;
    private int idParkingLot;
    private int idParkingSpot;
    private int duration;

    public ReservationBody(String username, int idParkingLot, int idParkingSpot, int duration){
        this.username = username;
        this.idParkingLot = idParkingLot;
        this.idParkingSpot = idParkingSpot;
        this.duration = duration;
    }

    public ReservationBody(){}
}
