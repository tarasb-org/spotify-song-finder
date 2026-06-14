package tarasb.spotifysongfinder.services;

import tarasb.spotifysongfinder.api.v1.model.SongDTO;

import java.util.List;

public interface SongService {

    List<SongDTO> getAllSongs();

    SongDTO getSongById(Long id);

    SongDTO getSongBySpotifyId(String spotifyId);

    SongDTO saveSong(SongDTO songDTO);

    List<SongDTO> searchSongs(String query);

}
