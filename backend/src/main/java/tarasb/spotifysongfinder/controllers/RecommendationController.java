package tarasb.spotifysongfinder.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tarasb.spotifysongfinder.api.v1.model.RecommendationRequestDTO;
import tarasb.spotifysongfinder.api.v1.model.RecommendationResponseDTO;
import tarasb.spotifysongfinder.services.RecommendationService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @PostMapping("/{spotifyId}")
    public ResponseEntity<List<RecommendationResponseDTO>> getRecommendations(
            @PathVariable String spotifyId,
            @RequestBody RecommendationRequestDTO weights) {
        return ResponseEntity.ok(recommendationService.getRecommendations(spotifyId, weights));
    }
}
