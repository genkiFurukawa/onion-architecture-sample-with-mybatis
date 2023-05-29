CREATE TABLE user (
  id VARCHAR(36) NOT NULL,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE user_mail_address (
  id VARCHAR(36) NOT NULL,
  mail_address VARCHAR(255) NOT NULL,
  PRIMARY KEY (id, mail_address),
  FOREIGN KEY (id) REFERENCES user(id)
);
