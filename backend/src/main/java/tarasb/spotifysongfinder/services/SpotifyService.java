package tarasb.spotifysongfinder.services;

import tarasb.spotifysongfinder.api.v1.model.SongDTO;

import java.util.List;

public interface SpotifyService {

    List<SongDTO> searchSongs(String query);

}
