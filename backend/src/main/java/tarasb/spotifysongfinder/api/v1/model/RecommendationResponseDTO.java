package tarasb.spotifysongfinder.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RecommendationResponseDTO {
    private SongDTO song;
    private double similarityScore;
    private List<String> reasons;
}
