package rob.masonchallenge.city;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityMatchingService {
    private CityRepository cityRepository;

    public CityMatchingService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<CityMatchResult> findMatchingCities(String q) {
        return cityRepository.findMatchingCities(q).stream()
                .map(city -> CityMatchResult.builder().city(city).build())
                .collect(Collectors.toList());
    }

    public List<CityMatchResult> findMatchingCitiesNearLocation(String q, Double latitude, Double longitude) {
        return cityRepository.findMatchingCities(q).stream()
                .map(city -> CityMatchResult.builder()
                        .city(city)
                        .distance(calculateDistanceInMeters(city, latitude, longitude))
                        .build())
                .sorted()
                .collect(Collectors.toList());
    }

    static double calculateDistanceInMeters(City city, Double latitude, Double longitude) {
        final int R = 6371; // Radius of the earth

        double lat1 = city.getLatitude();
        double long1 = city.getLongitude();
        double lat2 = latitude;
        double long2 = longitude;

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(long2 - long1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        distance = Math.pow(distance, 2);

        return Math.sqrt(distance);
    }
}
