package tarasb.spotifysongfinder.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReccoBeatsAudioFeaturesResponse {
    private List<AudioFeatures> content;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AudioFeatures {
        private String id;
        private String href;
        private Double tempo;
        private Double energy;
        private Double danceability;
        private Double valence;
        private Double acousticness;
        private Double instrumentalness;
        private Double loudness;
        private Double liveness;
        private Double speechiness;
        private Integer key;
        private Integer mode;

        public String getSpotifyId() {
            if (href == null) return null;
            return href.substring(href.lastIndexOf("/") + 1);
        }
    }
}
