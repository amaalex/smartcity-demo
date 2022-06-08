package project.smartcity.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Table(name = "parking_spot")
@Entity
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ParkingSpot {
    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonProperty("id_spot")
    @Column(name="id_spot")
    private int idSpot;

    @JsonProperty("id_parking_lot")
    @Column(name="id_parking_lot")
    private int idParkingLot;

    @JsonProperty("available")
    @Column(name="available")
    @ColumnDefault("true")
    private boolean available;

    public ParkingSpot(){}

    public ParkingSpot(int id, int idSpot, int idParkingLot){
        this.id = id;
        this.idSpot = idSpot;
        this.idParkingLot = idParkingLot;
    }
}
