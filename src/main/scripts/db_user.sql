
-- Create userids
GRANT ALL PRIVILEGES ON *.* TO 'bbs'@'localhost' IDENTIFIED BY 'bbs123';
GRANT ALL PRIVILEGES ON *.* TO 'bbs'@'*' IDENTIFIED BY 'bbs123';

-- Create Database
CREATE DATABASE BBS;


-- create table
CREATE TABLE USERS (
  ID,
  EMAIL,
  NAME,
  CREATED_BY,
  CREATED_TIME,
  UPDATED_TIME
);
