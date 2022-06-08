package project.smartcity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import project.smartcity.model.ParkingLot;
import project.smartcity.model.ParkingSpot;

import java.util.List;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Integer> {

    List<ParkingSpot> findParkingSpotsByIdParkingLot(@Param("id_parking_lot")Integer id);

    List<ParkingSpot> findParkingSpotsByIdParkingLotAndAvailable(@Param("id_parking_lot")Integer id,
                                                                 @Param("available")Boolean available);

    ParkingSpot findParkingSpotsByIdParkingLotAndIdSpot(@Param("id_parking_lot")Integer id,
                                                                 @Param("id_spot")Integer idSpot);

    ParkingSpot findParkingSpotsById(@Param("id")Integer id);

}
