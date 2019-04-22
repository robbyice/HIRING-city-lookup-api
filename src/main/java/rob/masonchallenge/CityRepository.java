package rob.masonchallenge;

import org.springframework.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

    }

    public ArrayList<City> getCities() {
        return cities;
    }
}
