package tarasb.spotifysongfinder.services;

import tarasb.spotifysongfinder.model.ReccoBeatsAudioFeaturesResponse;

import java.util.List;
import java.util.Map;

public interface ReccoBeatsService {

    Map<String, ReccoBeatsAudioFeaturesResponse.AudioFeatures> getAudioFeaturesBatch(List<String> spotifyIds);
}
