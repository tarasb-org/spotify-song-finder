package tarasb.spotifysongfinder.services;

import org.springframework.stereotype.Service;
import tarasb.spotifysongfinder.api.v1.model.SongDTO;

import java.util.List;

@Service
public class SongServiceImpl implements SongService {

    @Override
    public List<SongDTO> getAllSongs() {
        return List.of();
    }

    @Override
    public SongDTO getSongById(Long id) {
        return null;
    }
}
