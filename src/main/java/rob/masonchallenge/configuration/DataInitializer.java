package rob.masonchallenge.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rob.masonchallenge.CityRepository;

@Configuration
public class DataInitializer {

    @Bean
    public CityRepository cityRepository() {
        return new CityRepository();
    }

}
