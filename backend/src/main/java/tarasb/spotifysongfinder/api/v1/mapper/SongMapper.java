package tarasb.spotifysongfinder.api.v1.mapper;

import org.mapstruct.Mapper;
import tarasb.spotifysongfinder.api.v1.model.SongDTO;
import tarasb.spotifysongfinder.model.Song;

@Mapper
public interface SongMapper {

    SongDTO songToSongDTO(Song song);

    Song songDTOToSong(SongDTO songDTO);
}
