use spotifysongfinder;

CREATE TABLE songs (
    id BIGINT NOT NULL AUTO_INCREMENT,
    spotify_id VARCHAR(255) NOT NULL,
    title VARCHAR(255),
    artist VARCHAR(255),
    album VARCHAR(255),
    image_url VARCHAR(1024),
    spotify_url VARCHAR(1024),
    release_date VARCHAR(32),
    tempo DOUBLE,
    energy DOUBLE,
    danceability DOUBLE,
    valence DOUBLE,
    acousticness DOUBLE,
    instrumentalness DOUBLE,
    popularity INT,
    loudness DOUBLE,
    liveness DOUBLE,
    speechiness DOUBLE,
    PRIMARY KEY (id),
    CONSTRAINT uk_songs_spotify_id UNIQUE (spotify_id)
);
