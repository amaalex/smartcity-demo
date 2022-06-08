package project.smartcity.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "parking_lot")
@Entity
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ParkingLot {
    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonProperty("latitude")
    @Column(name="latitude")
    private double latitude;

    @JsonProperty("longitude")
    @Column(name="longitude")
    private double longitude;

    @JsonProperty("street_name")
    @Column(name="streetName")
    private String streetName;

    public ParkingLot(){}
}
