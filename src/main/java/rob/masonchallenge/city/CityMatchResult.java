package rob.masonchallenge.city;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CityMatchResult implements Comparable<CityMatchResult> {
    @JsonUnwrapped private final City city;
    @JsonIgnore private final double distance;

    private final Double score;

    @Override
    public int compareTo(CityMatchResult o) {
        return Double.compare(this.distance, o.distance);
    }
}
