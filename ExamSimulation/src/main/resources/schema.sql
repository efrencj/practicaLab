
DROP TABLE IF EXISTS KINGDOM;

CREATE TABLE KINGDOMS (
                        id VARCHAR(255)  PRIMARY KEY,
                        gold INT NOT NULL,
                        citizens INT NOT NULL,
                        food INT NOT NULL,
                        created_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP()
);

