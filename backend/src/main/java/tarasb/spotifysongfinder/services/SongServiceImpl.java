package tarasb.spotifysongfinder.services;

import org.springframework.stereotype.Service;
import tarasb.spotifysongfinder.api.v1.mapper.SongMapper;
import tarasb.spotifysongfinder.api.v1.model.SongDTO;
import tarasb.spotifysongfinder.model.ReccoBeatsAudioFeaturesResponse;
import tarasb.spotifysongfinder.model.Song;
import tarasb.spotifysongfinder.repositories.SongRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;
    private final SongMapper songMapper;
    private final SpotifyService spotifyService;
    private final ReccoBeatsService reccoBeatsService;

    public SongServiceImpl(SongRepository songRepository, SongMapper songMapper, SpotifyService spotifyService, ReccoBeatsService reccoBeatsService) {
        this.songRepository = songRepository;
        this.songMapper = songMapper;
        this.spotifyService = spotifyService;
        this.reccoBeatsService = reccoBeatsService;
    }


    @Override
    public List<SongDTO> getAllSongs() {
        return songRepository.findAll()
                .stream()
                .map(songMapper::songToSongDTO)
                .toList();
    }

    @Override
    public SongDTO getSongById(Long id) {
        Song song = songRepository.findById(id)
                .orElseThrow(RuntimeException::new); // todo handle exception
        return songMapper.songToSongDTO(song);
    }

    @Override
    public SongDTO getSongBySpotifyId(String spotifyId) {
        Song song = songRepository.findBySpotifyId(spotifyId)
                .orElseThrow(RuntimeException::new); // todo handle exception
        return songMapper.songToSongDTO(song);
    }

    @Override
    public SongDTO saveSong(SongDTO songDTO) {
        Optional<Song> existing = songRepository.findBySpotifyId(songDTO.getSpotifyId());

        if (existing.isPresent()) {
            Song song = existing.get();
            // update audio features if they were missing before
            if (song.getTempo() == null && songDTO.getTempo() != null) {
                song.setTempo(songDTO.getTempo());
                song.setEnergy(songDTO.getEnergy());
                song.setDanceability(songDTO.getDanceability());
                song.setValence(songDTO.getValence());
                song.setAcousticness(songDTO.getAcousticness());
                song.setInstrumentalness(songDTO.getInstrumentalness());
                song.setLoudness(songDTO.getLoudness());
                song.setLiveness(songDTO.getLiveness());
                song.setSpeechiness(songDTO.getSpeechiness());
                songRepository.save(song);
            }
            return songMapper.songToSongDTO(song);
        }

        Song song = songMapper.songDTOToSong(songDTO);
        return songMapper.songToSongDTO(songRepository.save(song));
    }

    @Override
    public List<SongDTO> searchSongs(String query) {
        List<SongDTO> results = spotifyService.searchSongs(query);

        List<String> spotifyIds = results.stream()
                .map(SongDTO::getSpotifyId)
                .toList();

        Map<String, ReccoBeatsAudioFeaturesResponse.AudioFeatures> featuresMap =
                reccoBeatsService.getAudioFeaturesBatch(spotifyIds);

        results.forEach(dto -> {
            var features = featuresMap.get(dto.getSpotifyId());
            if (features != null) {
                dto.setTempo(features.getTempo());
                dto.setEnergy(features.getEnergy());
                dto.setDanceability(features.getDanceability());
                dto.setValence(features.getValence());
                dto.setAcousticness(features.getAcousticness());
                dto.setInstrumentalness(features.getInstrumentalness());
                dto.setLoudness(features.getLoudness());
                dto.setLiveness(features.getLiveness());
                dto.setSpeechiness(features.getSpeechiness());
            }
        });

        results.forEach(this::saveSong);
        return results;
    }
}
