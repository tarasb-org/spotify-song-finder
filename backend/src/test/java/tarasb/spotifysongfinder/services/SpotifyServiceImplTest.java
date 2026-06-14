package tarasb.spotifysongfinder.services;

import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.model_objects.specification.*;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;
import tarasb.spotifysongfinder.api.v1.model.SongDTO;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpotifyServiceImplTest {

    @Mock
    private SpotifyApi spotifyApi;

    @Mock
    private ClientCredentialsRequest clientCredentialsRequest;

    @Mock
    private ClientCredentials clientCredentials;

    @Mock
    private SearchTracksRequest searchTracksRequest;

    private SpotifyServiceImpl spotifyService;

    @BeforeEach
    void setUp() {
        spotifyService = new SpotifyServiceImpl(spotifyApi);
    }

    @Test
    void searchSongs() throws IOException, ParseException, SpotifyWebApiException {
        Track track = buildTrack("1abc", "Blinding Lights", "The Weekend", "After Hours", 90);

        mockTokenRefresh();
        mockSearchTracks(track);

        List<SongDTO> results = spotifyService.searchSongs("blinding lights");

        assertEquals(1, results.size());
        assertEquals("1abc", results.get(0).getSpotifyId());
        assertEquals("Blinding Lights", results.get(0).getTitle());
        assertEquals("The Weekend", results.get(0).getArtist());
        assertEquals("After Hours", results.get(0).getAlbum());
        assertEquals(90, results.get(0).getPopularity());
        assertEquals("https://open.spotify.com/track/1abc", results.get(0).getSpotifyUrl());
    }

    @Test
    void searchSongs_emptyResults_returnsEmptyList() throws IOException, SpotifyWebApiException, ParseException {
        mockTokenRefresh();
        mockSearchTracks();

        List<SongDTO> results = spotifyService.searchSongs("xyznonexistentsong");

        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    void searchSongs_setsSpotifyUrlCorrectly() throws IOException, SpotifyWebApiException, ParseException {
        Track track = buildTrack("abc123", "Song", "Artist", "Album", 50);

        mockTokenRefresh();
        mockSearchTracks(track);

        List<SongDTO> results = spotifyService.searchSongs("song");

        assertEquals("https://open.spotify.com/track/abc123", results.get(0).getSpotifyUrl());
    }



    private void mockTokenRefresh() throws IOException, SpotifyWebApiException, ParseException {
        ClientCredentialsRequest.Builder credBuilder = mock(ClientCredentialsRequest.Builder.class);
        when(spotifyApi.clientCredentials()).thenReturn(credBuilder);
        when(credBuilder.build()).thenReturn(clientCredentialsRequest);
        when(clientCredentialsRequest.execute()).thenReturn(clientCredentials);
        when(clientCredentials.getAccessToken()).thenReturn("fake-token");
    }

    private void mockSearchTracks(Track... tracks) throws IOException, SpotifyWebApiException, ParseException {
        SearchTracksRequest.Builder builder = mock(SearchTracksRequest.Builder.class);
        when(spotifyApi.searchTracks(anyString())).thenReturn(builder);
        when(builder.limit(anyInt())).thenReturn(builder);
        when(builder.build()).thenReturn(searchTracksRequest);

        Paging<Track> paging = mock(Paging.class);
        when(paging.getItems()).thenReturn(tracks);
        when(searchTracksRequest.execute()).thenReturn(paging);
    }

    private Track buildTrack(String id, String name, String artistName, String albumName, int popularity) {
        ArtistSimplified artist = mock(ArtistSimplified.class);
        when(artist.getName()).thenReturn(artistName);

        AlbumSimplified album = mock(AlbumSimplified.class);
        when(album.getName()).thenReturn(albumName);
        when(album.getImages()).thenReturn(new Image[0]);
        when(album.getReleaseDate()).thenReturn("2020-01-01");

        Track track = mock(Track.class);
        when(track.getId()).thenReturn(id);
        when(track.getName()).thenReturn(name);
        when(track.getArtists()).thenReturn(new ArtistSimplified[]{artist});
        when(track.getAlbum()).thenReturn(album);
        when(track.getPopularity()).thenReturn(popularity);

        return track;
    }
}