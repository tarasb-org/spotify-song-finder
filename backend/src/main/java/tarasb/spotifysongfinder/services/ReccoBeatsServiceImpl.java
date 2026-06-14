package tarasb.spotifysongfinder.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import tarasb.spotifysongfinder.model.ReccoBeatsAudioFeaturesResponse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReccoBeatsServiceImpl implements ReccoBeatsService {

    private final RestClient restClient;

    public ReccoBeatsServiceImpl() {
        this.restClient = RestClient.builder()
                .baseUrl("https://api.reccobeats.com")
                .build();
    }

    public Map<String, ReccoBeatsAudioFeaturesResponse.AudioFeatures> getAudioFeaturesBatch(List<String> spotifyIds) {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder
                    .fromPath("/v1/audio-features");
            spotifyIds.forEach(id -> builder.queryParam("ids", id));

            ReccoBeatsAudioFeaturesResponse response = restClient.get()
                    .uri(builder.build().toUriString())
                    .retrieve()
                    .body(ReccoBeatsAudioFeaturesResponse.class);

            if (response == null || response.getContent() == null) {
                return Map.of();
            }

            return response.getContent().stream()
                    .filter(f -> f.getSpotifyId() != null)
                    .collect(Collectors.toMap(
                            ReccoBeatsAudioFeaturesResponse.AudioFeatures::getSpotifyId,
                            f -> f,
                            (a, b) -> a
                    ));

        } catch (Exception e) {
            System.out.println("ReccoBeats batch error: " + e.getMessage());
            return Map.of();
        }
    }
}
