package tarasb.spotifysongfinder.config;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import se.michaelthelin.spotify.SpotifyApi;

@Configuration
@ConfigurationProperties(prefix = "spotify")
@Validated
@Getter @Setter
public class SpotifyConfig {
    @NotBlank
    private String clientId;

    @NotBlank
    private String clientSecret;

    @Bean
    public SpotifyApi spotifyApi() {
        return SpotifyApi.builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .build();
    }
}
