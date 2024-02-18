ALTER TABLE starship
    ADD CONSTRAINT unique_starship_name UNIQUE (name);

ALTER TABLE movie_series
    ADD CONSTRAINT unique_movie_series_title UNIQUE (title);



