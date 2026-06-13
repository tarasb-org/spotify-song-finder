package tarasb.spotifysongfinder.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tarasb.spotifysongfinder.model.Song;

public interface SongRepository extends JpaRepository<Song, Long> {
}
