CREATE TABLE IF NOT EXISTS users (
	user_id SERIAL PRIMARY KEY,
	username VARCHAR(100),
	email VARCHAR(100),
	password VARCHAR(100),
	UNIQUE (email)
);

