package tarasb.spotifysongfinder.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "songs")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Song implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String spotifyId;

    private String title;
    private String artist;
    private String album;
    private String imageUrl;
    private String spotifyUrl;
    private String releaseDate;
    private Double tempo;
    private Double energy;
    private Double danceability;
    private Double valence;
    private Double acousticness;
    private Double instrumentalness;
    private Integer popularity;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return Objects.equals(spotifyId, song.spotifyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spotifyId);
    }
}
