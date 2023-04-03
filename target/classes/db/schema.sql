DROP TABLE IF EXISTS roadsign;
DROP TABLE IF EXISTS category;

CREATE TABLE category (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255),
  description VARCHAR(255)
);

CREATE TABLE roadsign (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  text VARCHAR(500),
  filename VARCHAR(255),
  image VARCHAR(255),
  category_id BIGINT NOT NULL,
  FOREIGN KEY (category_id) REFERENCES category(id)
);