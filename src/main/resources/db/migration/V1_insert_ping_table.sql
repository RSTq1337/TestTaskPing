INSERT INTO ping (ip_address, domain_name, check_date, status, ping_result)
VALUES ('192.168.0.1', 'example.com', NOW(), 'Success', 'Ping successful');

INSERT INTO ping (ip_address, domain_name, check_date, status, ping_result)
VALUES ('10.0.0.1', 'google.com', NOW(), 'Failure', 'Ping fail');