CREATE TABLE singleton_value (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 car_count INT NOT NULL CHECK (car_count >= 0 AND car_count <= 100)
);
