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
        List<CityMatchResult> sorted = cityRepository.findMatchingCities(q).stream()
                .map(city -> CityMatchResult.builder()
                        .city(city)
                        .distance(calculateDistanceInMeters(city, latitude, longitude))
                        .build())
                .sorted()
                .collect(Collectors.toList());

        if (sorted.isEmpty()) {
            return sorted;
        }

        double closest = sorted.get(0).getDistance();
        double furthest = sorted.get(sorted.size() - 1).getDistance();

        return sorted
                .stream()
                .map(cityMatchResult -> cityMatchResult.toBuilder().score(calculateScore(cityMatchResult.getDistance(), closest, furthest)).build())
                .collect(Collectors.toList());
    }

    private double calculateScore(double distance, double closest, double furthest) {
        return ((furthest - distance) / (furthest - closest)) * 1.0;
    }

    static double calculateDistanceInMeters(City city, Double latitude, Double longitude) {
        final int R = 6371;

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
