package project.smartcity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import project.smartcity.model.Driver;
import project.smartcity.model.ParkingLot;

public interface DriverRepository extends JpaRepository<Driver, Integer> {
    Driver findDriverById(@Param("id")Integer id);
    Driver findByUsername(@Param("username") String username);
}
