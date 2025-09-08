inCREATE TABLE tasks (
  id BIGSERIAL PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  description TEXT,
  due_date DATE,
  priority INT,
  status VARCHAR(20),
  created_at TIMESTAMP DEFAULT now()
);