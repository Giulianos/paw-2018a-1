CREATE TABLE IF NOT EXISTS users (
	user_id SERIAL PRIMARY KEY,
	username VARCHAR(15) NOT NULL,
	email VARCHAR(40) NOT NULL,
	password VARCHAR(100) NOT NULL,
	reg_date DATE DEFAULT CURRENT_DATE,
	transactions INT DEFAULT 0,
	UNIQUE (username),
	UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS publications (
	publication_id SERIAL PRIMARY KEY,
	supervisor VARCHAR(15),
	description VARCHAR(30),
	price REAL,
	quantity INT,
	image VARCHAR(50) DEFAULT '',
	is_confirmed BOOLEAN DEFAULT FALSE,
	FOREIGN KEY(supervisor) REFERENCES users (username) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS subscriptions (
	subscriber VARCHAR(15),
	publication_id INT,
	quantity INT,
	is_confirmed BOOLEAN,
	FOREIGN KEY(subscriber) REFERENCES users (username) ON DELETE CASCADE,
	FOREIGN KEY(publication_id) REFERENCES publications (publication_id) ON DELETE CASCADE,
	PRIMARY KEY (subscriber, publication_id)
);

CREATE TABLE IF NOT EXISTS buyers (
	buyer VARCHAR(15),
	publication_id INT,
	quantity INT,
	is_paid BOOLEAN,
	is_received BOOLEAN,
	FOREIGN KEY(buyer) REFERENCES users (username) ON DELETE CASCADE,
	FOREIGN KEY(publication_id) REFERENCES publications (publication_id) ON DELETE CASCADE,
	PRIMARY KEY (buyer, publication_id)
);
