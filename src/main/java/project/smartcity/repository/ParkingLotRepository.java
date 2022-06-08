package project.smartcity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import project.smartcity.model.ParkingLot;

import java.util.List;

public interface ParkingLotRepository extends JpaRepository<ParkingLot, Integer> {

    ParkingLot findParkingLotById(@Param("id")Integer id);

    List<ParkingLot> findParkingLotByStreetName(@Param("street_name") String name);

    List<ParkingLot> findParkingLotByLatitudeAndLongitude(@Param("latitude") double latitude, @Param("latitude") double longitude);
}
