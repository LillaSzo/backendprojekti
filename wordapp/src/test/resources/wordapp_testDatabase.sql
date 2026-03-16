DROP TABLE IF EXISTS cards;
DROP TABLE IF EXISTS joined_decks;
DROP TABLE IF EXISTS decks;
DROP TABLE IF EXISTS app_users;
DROP TABLE IF EXISTS languages;

CREATE TABLE languages(
language_id BIGSERIAL PRIMARY KEY,
language VARCHAR(100) NOT NULL
);

INSERT INTO languages(language)
VALUES('Finnish'), ('English'), ('Hungarian'), ('Swedish'), ('French'), ('Turkish'), ('Spanish');

CREATE TABLE app_users(
user_id BIGSERIAL PRIMARY KEY,
username VARCHAR(100) NOT NULL,
password VARCHAR(100) NOT NULL,
role VARCHAR(100) NOT NULL
);

INSERT INTO app_users(username, password, role)
VALUES
('user', '$2a$12$wUMhJmQqiirdi/k3Y6Cwp.vLOwhxQvr1VTinrv/WzQ/sUjdGjrSp6', 'USER'),
('admin', '$2a$12$C5vcDo09ouvpQpi2aXfkyuxA6.NJD/0M7CQqzK73emeQ8rt1LGn5.', 'ADMIN');

CREATE TABLE decks(
deck_id BIGSERIAL PRIMARY KEY,
name VARCHAR(30) NOT NULL,
target_language_id BIGINT REFERENCES languages(language_id) NOT NULL,
translation_language_id BIGINT REFERENCES languages(language_id) NOT NULL,
user_id BIGSERIAL REFERENCES app_users(user_id) NOT NULL
);

INSERT INTO decks(name, target_language_id, translation_language_id, user_id)
VALUES
('Testin Test Deck', 1, 2, 2),
('Testin Test Deck 2', 1, 2, 2);

CREATE TABLE cards(
card_id BIGSERIAL PRIMARY KEY,
target_word VARCHAR(20),
translation VARCHAR(20),
sentence VARCHAR(100),
deck_id BIGSERIAL REFERENCES decks(deck_id) NOT NULL
);

INSERT INTO cards(target_word, translation, sentence, deck_id)
VALUES
('testi', 'test', 'Sanakortti testaamisen takia.', 1);

CREATE TABLE joined_decks(
user_id BIGSERIAL REFERENCES app_users(user_id) ON DELETE CASCADE,
deck_id BIGSERIAL REFERENCES decks(deck_id) ON DELETE CASCADE
);

INSERT INTO joined_decks(user_id, deck_id)
VALUES
(1, 1);

select * from decks


