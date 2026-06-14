package tarasb.spotifysongfinder.api.v1.model;

import lombok.Data;

@Data
public class RecommendationRequestDTO {
    private double tempoWeight       = 0.5;
    private double energyWeight      = 0.5;
    private double danceabilityWeight = 0.5;
    private double valenceWeight     = 0.5;
    private double acousticnessWeight = 0.5;
    private double loudnessWeight    = 0.3;
}
