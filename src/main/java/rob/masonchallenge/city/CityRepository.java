package rob.masonchallenge.city;

import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CityRepository {
    private ArrayList<City> cities;

    public CityRepository(File citiesData) {

        System.out.println("Reading in City Data");

        this.cities = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(citiesData))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\t");

                City newCity = new City(data[1], data[3], Double.valueOf(data[4]), Double.valueOf(data[5]));
                this.cities.add(newCity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Done reading in City Data");
    }

    public List<City> getCities() {
        return cities;
    }

    public List<City> findMatchingCities(String searchTerm) {
        System.out.println("Beginning city search");

        List<City> matchingCities = this.cities.stream()
                .filter((City city) -> StringUtils.startsWithIgnoreCase(city.getName(), searchTerm))
                .collect(Collectors.toList());

        System.out.println("Ending city search");
        return matchingCities;
    }
}
