package project.smartcity.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.smartcity.location.Location;
import project.smartcity.location.Reservation;
import project.smartcity.location.ReservationBody;
import project.smartcity.mail.MailService;
import project.smartcity.model.ParkingSpot;
import project.smartcity.repository.DriverRepository;
import project.smartcity.repository.ParkingLotRepository;
import project.smartcity.repository.ParkingSpotRepository;
import project.smartcity.services.DistanceCalculator;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/parkingLots")
public class ParkingLotController {

    private ParkingLotRepository parkingLotRepository;
    private DistanceCalculator distanceCalculator;
    private ParkingSpotRepository parkingSpotRepository;
    private DriverRepository driverRepository;

    private List<Reservation> reservations = new ArrayList<>();

    @Autowired
    public ParkingLotController(ParkingLotRepository parkingLotRepository, DistanceCalculator distanceCalculator,
                                ParkingSpotRepository parkingSpotRepository, DriverRepository driverRepository) {
        this.parkingLotRepository = parkingLotRepository;
        this.distanceCalculator = distanceCalculator;
        this.parkingSpotRepository = parkingSpotRepository;
        this.driverRepository = driverRepository;
    }

    @PostMapping(value = "/closestSpots", consumes = "application/json")
    public ResponseEntity<String> getClosestParkingLot(@RequestBody Location location) {
        HashMap<Integer, Double> available = distanceCalculator.getClosestParkingLots(location);

        if (available.size() == 0) {
            return new ResponseEntity<>("Sorry, there are no available parking lots :(", HttpStatus.OK);
        }
        String response = "The closest parking lots are: \n";
        for (Integer i : available.keySet()) {
            response += " - " + available.get(i).toString() + " km away on the street ";
            response += parkingLotRepository.findParkingLotById(i).getStreetName() + " (id =  " + i + ") with the following available spots: ";
            List<ParkingSpot> parkingSpots = parkingSpotRepository.findParkingSpotsByIdParkingLotAndAvailable(i, true);
            for (ParkingSpot p : parkingSpots) {
                response += p.getId() + " ";
            }
            response += "\n";
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/reserve", consumes = "application/json")
    public ResponseEntity<String> occupySpot(@RequestBody ReservationBody reservationBody) {
        int idParkingSpot = (parkingSpotRepository.findParkingSpotsByIdParkingLotAndIdSpot(reservationBody.getIdParkingLot(),
                reservationBody.getIdParkingSpot())).getId();
        int idDriver = (driverRepository.findByUsername(reservationBody.getUsername())).getId();
        int duration = reservationBody.getDuration();
        reservations.add(new Reservation(idParkingSpot, idDriver, duration));
        ParkingSpot ps = new ParkingSpot(idParkingSpot, reservationBody.getIdParkingSpot(), reservationBody.getIdParkingLot());
        ps.setAvailable(false);
        parkingSpotRepository.save(ps);

        TimerTask task = new TimerTask() {
            public void run() {
                ParkingSpot ps = parkingSpotRepository.findParkingSpotsById(idParkingSpot);
                if(!ps.isAvailable()){
                    MailService mailService = new MailService();
                    try {
                        mailService.sendmail(driverRepository.findDriverById(idDriver).getEmail());
                    }
                    catch(MessagingException | IOException e){
                        e.printStackTrace();
                    }
                }
            }
        };
        Timer timer = new Timer("Timer");
        long delay = (reservationBody.getDuration()*10000L)*6;
        timer.schedule(task, delay);
        return new ResponseEntity<>("You successfully occupied this spot!", HttpStatus.OK);
    }


}