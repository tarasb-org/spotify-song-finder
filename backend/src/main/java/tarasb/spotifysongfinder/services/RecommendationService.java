package tarasb.spotifysongfinder.services;

import tarasb.spotifysongfinder.api.v1.model.RecommendationRequestDTO;
import tarasb.spotifysongfinder.api.v1.model.RecommendationResponseDTO;

import java.util.List;

public interface RecommendationService {

    List<RecommendationResponseDTO> getRecommendations(String spotifyId, RecommendationRequestDTO weights);
}
