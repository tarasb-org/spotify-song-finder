package tarasb.spotifysongfinder.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import tarasb.spotifysongfinder.api.v1.model.SongDTO;
import tarasb.spotifysongfinder.model.Song;

@Mapper
public interface SongMapper {
    SongMapper INSTANCE = Mappers.getMapper(SongMapper.class);

    SongDTO songToSongDTO(Song song);
}
