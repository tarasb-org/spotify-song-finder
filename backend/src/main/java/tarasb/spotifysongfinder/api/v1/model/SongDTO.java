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
    private Long spotifyId;
    private String title;
    private String artist;
    private String album;
    private Date releaseDate;
    private String spotifyUrl;
    private Integer releaseYear;

    private List<Song> similarSongs;
}
