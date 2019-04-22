package rob.masonchallenge.city;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class City {
    private final String name;
    private final String alternateNames;
    private final double latitude;
    private final double longitude;
}
