CREATE TABLE courses (
  id INT AUTO_INCREMENT PRIMARY KEY,
  modality VARCHAR(100) NOT NULL,
  name VARCHAR(255) NOT NULL,
  start_date DATE NOT NULL,
  end_date DATE NOT NULL,
  user_id INT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users(id)
);
