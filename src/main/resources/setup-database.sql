CREATE DATABASE costdata_application CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;
/* CREATE USER 'repuser'@'localhost' IDENTIFIED BY 'reppass'; */
GRANT ALL PRIVILEGES ON costdata_application.* to 'repuser'@'localhost';