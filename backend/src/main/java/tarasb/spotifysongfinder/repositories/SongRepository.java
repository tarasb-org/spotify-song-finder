package tarasb.spotifysongfinder.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tarasb.spotifysongfinder.model.Song;

import java.util.Optional;

public interface SongRepository extends JpaRepository<Song, Long> {
    Optional<Song> findBySpotifyId(String spotifyId);
    boolean existsBySpotifyId(String spotifyId);
}
