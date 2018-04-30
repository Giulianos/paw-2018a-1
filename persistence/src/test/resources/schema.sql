CREATE TABLE IF NOT EXISTS _user (
	user_id INTEGER IDENTITY PRIMARY KEY
	username TEXT,
	email TEXT,
	password TEXT,
	UNIQUE (email)
);
