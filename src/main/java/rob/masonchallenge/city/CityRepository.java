package rob.masonchallenge.city;

import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CityRepository {
    private ArrayList<City> cities;

    public CityRepository(InputStream fileStream) {

        long start = System.currentTimeMillis();

        this.cities = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(fileStream, StandardCharsets.UTF_8))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\t");

                City newCity = new City(data[1], data[3], Double.valueOf(data[4]), Double.valueOf(data[5]));
                this.cities.add(newCity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.println("Read city data in " +  (end - start) + " milliseconds");
    }

    public List<City> getCities() {
        return cities;
    }

    public List<City> findMatchingCities(String searchTerm) {
        long start = System.currentTimeMillis();

        List<City> matchingCities = this.cities.stream()
                .filter((City city) -> StringUtils.startsWithIgnoreCase(city.getName(), searchTerm))
                .collect(Collectors.toList());

        long end = System.currentTimeMillis();
        System.out.println("Found matching cities in " +  (end - start) + " milliseconds");

        return matchingCities;
    }
}
