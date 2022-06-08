package project.smartcity.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.smartcity.location.Location;
import project.smartcity.model.ParkingLot;
import project.smartcity.model.ParkingSpot;
import project.smartcity.repository.ParkingLotRepository;
import project.smartcity.repository.ParkingSpotRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class DistanceCalculator {

    private ParkingLotRepository parkingLotRepository;
    private ParkingSpotRepository parkingSpotRepository;


    @Autowired
    public DistanceCalculator(ParkingLotRepository parkingLotRepository, ParkingSpotRepository parkingSpotRepository) {
        this.parkingLotRepository = parkingLotRepository;
        this.parkingSpotRepository = parkingSpotRepository;
    }

    public HashMap<Integer, Double> getClosestParkingLots(Location location) {
        List<ParkingLot> parkingLots = parkingLotRepository.findAll();
        List<ParkingLot> parkingLotsAvailable = new ArrayList<>();
        HashMap<Integer, Double> distances = new HashMap<>();
        HashMap<Integer, Double> closestAvailable = new HashMap<>();
        for (ParkingLot p : parkingLots) {
            List<ParkingSpot> parkingSpots = parkingSpotRepository.
                    findParkingSpotsByIdParkingLotAndAvailable(p.getId(), true);
            if (parkingSpots.size() > 0) {
                parkingLotsAvailable.add(p);
            }
        }
        if (parkingLotsAvailable.size() != 0) {
            double min = computeDistance(location.getLatitude(), location.getLongitude(),
                    parkingLotsAvailable.get(0).getLatitude(), parkingLotsAvailable.get(0).getLongitude());

            for (ParkingLot p : parkingLotsAvailable) {
                double distance = computeDistance(location.getLatitude(), location.getLongitude(),
                        p.getLatitude(), p.getLongitude());
                if (distance <= min)
                    min = distance;
                distances.put(p.getId(), distance);
            }

            for (Integer i : distances.keySet()) {
                if (distances.get(i) <= min + 0.5)
                    closestAvailable.put(i,distances.get(i));
            }
        }
        return closestAvailable;
    }

    public double computeDistance(double lat1, double long1, double lat2, double long2) {
        long1 = Math.toRadians(long1);
        long2 = Math.toRadians(long2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double lon = long2 - long1;
        double lat = lat2 - lat1;
        double a = Math.pow(Math.sin(lat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(lon / 2), 2);

        double c = 2 * Math.asin(Math.sqrt(a));
        double radius = 6371;
        return (c * radius * 1.609344);
    }
}
