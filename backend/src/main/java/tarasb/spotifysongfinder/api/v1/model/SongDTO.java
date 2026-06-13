package tarasb.spotifysongfinder.api.v1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tarasb.spotifysongfinder.model.Song;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongDTO {

    @JsonIgnore
    private Long id;
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
}
