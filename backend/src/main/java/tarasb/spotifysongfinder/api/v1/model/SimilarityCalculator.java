package tarasb.spotifysongfinder.api.v1.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SimilarityCalculator {

    public RecommendationResponseDTO calculate(SongDTO source, SongDTO candidate,
                                               RecommendationRequestDTO weights) {
        List<String> reasons = new ArrayList<>();
        double totalWeight = 0.0;
        double weightedScore = 0.0;

        // tempo: normalize to 0-1 range (max diff ~250 BPM)
        if (hasValues(source.getTempo(), candidate.getTempo())) {
            double diff = Math.abs(source.getTempo() - candidate.getTempo()) / 250.0;
            double score = 1.0 - Math.min(diff, 1.0);
            weightedScore += score * weights.getTempoWeight();
            totalWeight += weights.getTempoWeight();
            if (score > 0.85) reasons.add("Very similar tempo");
            else if (score > 0.65) reasons.add("Similar tempo");
        }

        // energy: already 0-1
        if (hasValues(source.getEnergy(), candidate.getEnergy())) {
            double diff = Math.abs(source.getEnergy() - candidate.getEnergy());
            double score = 1.0 - diff;
            weightedScore += score * weights.getEnergyWeight();
            totalWeight += weights.getEnergyWeight();
            if (score > 0.85) reasons.add("Very similar energy");
            else if (score > 0.65) reasons.add("Similar energy");
        }

        // danceability: already 0-1
        if (hasValues(source.getDanceability(), candidate.getDanceability())) {
            double diff = Math.abs(source.getDanceability() - candidate.getDanceability());
            double score = 1.0 - diff;
            weightedScore += score * weights.getDanceabilityWeight();
            totalWeight += weights.getDanceabilityWeight();
            if (score > 0.85) reasons.add("Very similar danceability");
            else if (score > 0.65) reasons.add("Similar danceability");
        }

        // valence (mood): already 0-1
        if (hasValues(source.getValence(), candidate.getValence())) {
            double diff = Math.abs(source.getValence() - candidate.getValence());
            double score = 1.0 - diff;
            weightedScore += score * weights.getValenceWeight();
            totalWeight += weights.getValenceWeight();
            if (score > 0.85) reasons.add("Very similar mood");
            else if (score > 0.65) reasons.add("Similar mood");
        }

        // acousticness: already 0-1
        if (hasValues(source.getAcousticness(), candidate.getAcousticness())) {
            double diff = Math.abs(source.getAcousticness() - candidate.getAcousticness());
            double score = 1.0 - diff;
            weightedScore += score * weights.getAcousticnessWeight();
            totalWeight += weights.getAcousticnessWeight();
            if (score > 0.85) reasons.add("Very similar acousticness");
            else if (score > 0.65) reasons.add("Similar acousticness");
        }

        // loudness: normalize to 0-1 range (range is roughly -60 to 0 dB)
        if (hasValues(source.getLoudness(), candidate.getLoudness())) {
            double diff = Math.abs(source.getLoudness() - candidate.getLoudness()) / 60.0;
            double score = 1.0 - Math.min(diff, 1.0);
            weightedScore += score * weights.getLoudnessWeight();
            totalWeight += weights.getLoudnessWeight();
            if (score > 0.85) reasons.add("Very similar loudness");
            else if (score > 0.65) reasons.add("Similar loudness");
        }

        double finalScore = totalWeight > 0 ? weightedScore / totalWeight : 0.0;

        if (reasons.isEmpty()) reasons.add("Somewhat similar overall sound");

        return new RecommendationResponseDTO(candidate, finalScore, reasons);
    }

    private boolean hasValues(Double a, Double b) {
        return a != null && b != null;
    }
}
