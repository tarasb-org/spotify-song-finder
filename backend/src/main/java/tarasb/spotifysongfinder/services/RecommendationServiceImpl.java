package tarasb.spotifysongfinder.services;

import org.springframework.stereotype.Service;
import tarasb.spotifysongfinder.api.v1.mapper.SongMapper;
import tarasb.spotifysongfinder.api.v1.model.RecommendationRequestDTO;
import tarasb.spotifysongfinder.api.v1.model.RecommendationResponseDTO;
import tarasb.spotifysongfinder.api.v1.model.SimilarityCalculator;
import tarasb.spotifysongfinder.api.v1.model.SongDTO;
import tarasb.spotifysongfinder.repositories.SongRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendationServiceImpl implements RecommendationService {
    private final SpotifyServiceImpl spotifyService;
    private final ReccoBeatsService reccoBeatsService;
    private final SimilarityCalculator similarityCalculator;
    private final SongService songService;

    public RecommendationServiceImpl(SpotifyServiceImpl spotifyService,
                                     ReccoBeatsService reccoBeatsService,
                                     SimilarityCalculator similarityCalculator,
                                     SongService songService) {
        this.spotifyService = spotifyService;
        this.reccoBeatsService = reccoBeatsService;
        this.similarityCalculator = similarityCalculator;
        this.songService = songService;
    }

    @Override
    public List<RecommendationResponseDTO> getRecommendations(String spotifyId,
                                                              RecommendationRequestDTO weights) {
        // 1. load source song
        SongDTO source = songService.getSongBySpotifyId(spotifyId);

        // 2. fetch candidates from Spotify
        List<SongDTO> candidates = fetchCandidates(source);

        // 3. score each candidate
        return candidates.stream()
                .filter(c -> !c.getSpotifyId().equals(spotifyId)) // exclude source itself
                .map(c -> similarityCalculator.calculate(source, c, weights))
                .sorted(Comparator.comparingDouble(RecommendationResponseDTO::getSimilarityScore).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    private List<SongDTO> fetchCandidates(SongDTO source) {
        String releaseYear = source.getReleaseDate() != null
                ? source.getReleaseDate().substring(0, 4)
                : "2020";

        List<String> queries = List.of(
                source.getArtist(),
                source.getArtist() + " similar artists",
                "pop " + releaseYear,
                "synth pop",
                source.getTitle() + " cover"
        );

        // use songService not spotifyService — this includes ReccoBeats enrichment
        return queries.stream()
                .flatMap(q -> songService.searchSongs(q).stream())
                .collect(Collectors.toMap(
                        SongDTO::getSpotifyId,
                        s -> s,
                        (a, b) -> a  // deduplicate by spotifyId
                ))
                .values()
                .stream()
                .collect(Collectors.toList());
    }

}
