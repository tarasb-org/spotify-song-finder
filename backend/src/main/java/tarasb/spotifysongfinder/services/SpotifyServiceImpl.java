package tarasb.spotifysongfinder.services;

import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import tarasb.spotifysongfinder.api.v1.model.SongDTO;
import tarasb.spotifysongfinder.config.SpotifyConfig;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class SpotifyServiceImpl implements SpotifyService {

    private final SpotifyApi spotifyApi;

    public SpotifyServiceImpl(SpotifyConfig config) {
        this.spotifyApi = SpotifyApi.builder()
                .setClientId(config.getClientId())
                .setClientSecret(config.getClientSecret())
                .build();
    }

    @Override
    public List<SongDTO> searchSongs(String query) {
        refreshToken();
        try {
            Paging<Track> tracks = spotifyApi.searchTracks(query)
                    .limit(10)
                    .build()
                    .execute();

            return Arrays.stream(tracks.getItems())
                    .map(this::trackToSongDTO)
                    .toList();

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new RuntimeException("Spotify search failed", e);
        }
    }

    private void refreshToken() {
        try {
            ClientCredentialsRequest request = spotifyApi.clientCredentials().build();
            var credentials = request.execute();
            spotifyApi.setAccessToken(credentials.getAccessToken());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new RuntimeException("Failed to fetch Spotify token", e);
        }
    }

    private SongDTO trackToSongDTO(Track track) {
        SongDTO dto = new SongDTO();
        dto.setSpotifyId(track.getId());
        dto.setTitle(track.getName());
        dto.setArtist(track.getArtists()[0].getName());
        dto.setAlbum(track.getAlbum().getName());
        dto.setPopularity(track.getPopularity());
        dto.setSpotifyUrl("https://open.spotify.com/track/" + track.getId());

        if (track.getAlbum().getImages().length > 0) {
            dto.setImageUrl(track.getAlbum().getImages()[0].getUrl());
        }

        if (track.getAlbum().getReleaseDate() != null) {
            dto.setReleaseDate(track.getAlbum().getReleaseDate());
        }

        return dto;
    }
}
