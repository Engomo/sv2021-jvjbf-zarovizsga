CREATE TABLE players(
                        id BIGINT NOT NULL AUTO_INCREMENT,
                        player_name VARCHAR(255) NOT NULL,
                        player_birthdate DATE,
                        player_position VARCHAR(255),
                        team_id BIGINT,
                        PRIMARY KEY (id),
                        FOREIGN KEY (team_id) REFERENCES teams(id));