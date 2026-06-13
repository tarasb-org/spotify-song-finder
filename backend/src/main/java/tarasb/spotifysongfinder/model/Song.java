package tarasb.spotifysongfinder.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Song implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long spotifyId;
    private String title;
    private String artist;
    private String album;
    private Date releaseDate;
    private String spotifyUrl;
    private Integer releaseYear;

    private List<Song> similarSongs;

    /*
     tempo
     energy
     danceability
     valence
     acousticness
     instrumentalness
     popularity
    */

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return Objects.equals(id, song.id)
                && Objects.equals(spotifyId, song.spotifyId)
                && Objects.equals(title, song.title)
                && Objects.equals(artist, song.artist)
                && Objects.equals(album, song.album)
                && Objects.equals(releaseDate, song.releaseDate)
                && Objects.equals(spotifyUrl, song.spotifyUrl)
                && Objects.equals(releaseYear, song.releaseYear)
                && Objects.equals(similarSongs, song.similarSongs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, spotifyId, title, artist, album, releaseDate, spotifyUrl, releaseYear, similarSongs);
    }
}
