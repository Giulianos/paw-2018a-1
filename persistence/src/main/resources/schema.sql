CREATE TABLE IF NOT EXISTS _user (
	user_id SERIAL PRIMARY KEY
	username TEXT,
	email TEXT,
	password TEXT,
	UNIQUE (email)
);
