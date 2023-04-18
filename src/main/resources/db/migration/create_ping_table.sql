CREATE TABLE ping (
                      id SERIAL PRIMARY KEY,
                      ip_address VARCHAR(255),
                      domain_name VARCHAR(255),
                      check_date TIMESTAMP,
                      status VARCHAR(255),
                      ping_result TEXT
);