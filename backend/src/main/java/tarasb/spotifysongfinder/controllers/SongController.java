package tarasb.spotifysongfinder.controllers;

import org.springframework.web.bind.annotation.*;
import tarasb.spotifysongfinder.api.v1.model.SongDTO;
import tarasb.spotifysongfinder.services.SongService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/songs")
public class SongController {

    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping("/search")
    public List<SongDTO> searchSongs(@RequestParam String query) {
        return songService.searchSongs(query);
    }

    @GetMapping("/{spotifyId}")
    public SongDTO getSongBySpotifyId(@PathVariable String spotifyId) {
        return songService.getSongBySpotifyId(spotifyId);
    }
}
