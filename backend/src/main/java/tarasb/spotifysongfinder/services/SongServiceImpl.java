package tarasb.spotifysongfinder.services;

import org.springframework.stereotype.Service;
import tarasb.spotifysongfinder.api.v1.mapper.SongMapper;
import tarasb.spotifysongfinder.api.v1.model.SongDTO;
import tarasb.spotifysongfinder.model.Song;
import tarasb.spotifysongfinder.repositories.SongRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;
    private final SongMapper songMapper;
    private final SpotifyServiceImpl spotifyService;

    public SongServiceImpl(SongRepository songRepository, SongMapper songMapper, SpotifyServiceImpl spotifyService) {
        this.songRepository = songRepository;
        this.songMapper = songMapper;
        this.spotifyService = spotifyService;
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
        if (songRepository.existsBySpotifyId(songDTO.getSpotifyId())) {
            return getSongBySpotifyId(songDTO.getSpotifyId());
        }
        Song song = songMapper.songDTOToSong(songDTO);
        Song savedSong = songRepository.save(song);
        return songMapper.songToSongDTO(savedSong);
    }

    @Override
    public List<SongDTO> searchSongs(String query) {
        List<SongDTO> results = spotifyService.searchSongs(query);
        results.forEach(this::saveSong);
        return results;
    }
}
