CREATE TABLE starship
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE movie_series (
                              id SERIAL PRIMARY KEY,
                              title VARCHAR(255)
);

CREATE TABLE starship_movie_series
(
    starship_id     INT,
    movie_series_id INT,
    FOREIGN KEY (starship_id) REFERENCES starship (id),
    FOREIGN KEY (movie_series_id) REFERENCES movie_series (id),
    PRIMARY KEY (starship_id, movie_series_id)
);

