package rob.masonchallenge.city;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CityMatchResult implements Comparable<CityMatchResult> {
    @JsonUnwrapped
    private final City city;
    private final double score;
    private final double distance;


    @Override
    public int compareTo(CityMatchResult o) {
        return Double.compare(o.distance, this.distance);
    }
}
