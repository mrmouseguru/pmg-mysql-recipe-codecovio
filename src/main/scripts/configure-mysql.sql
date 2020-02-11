## Use to run mysql db docker image, optional if you're not using a local mysqldb
# docker run --name mysqldb -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -d mysql

# connect to mysql and run as root user
#Create Databases
CREATE DATABASE pmg_dev;
CREATE DATABASE pmg_prod;

#Create database service accounts
CREATE USER 'pmg_dev_user'@'localhost' IDENTIFIED BY 'pmouseguru';
CREATE USER 'pmg_prod_user'@'localhost' IDENTIFIED BY 'pmouseguru';

#Database grants
GRANT SELECT ON pmg_dev.* to 'pmg_dev_user'@'localhost';
GRANT INSERT ON pmg_dev.* to 'pmg_dev_user'@'localhost';
GRANT DELETE ON pmg_dev.* to 'pmg_dev_user'@'localhost';
GRANT UPDATE ON pmg_dev.* to 'pmg_dev_user'@'localhost';
GRANT SELECT ON pmg_prod.* to 'pmg_prod_user'@'localhost';
GRANT INSERT ON pmg_prod.* to 'pmg_prod_user'@'localhost';
GRANT DELETE ON pmg_prod.* to 'pmg_prod_user'@'localhost';
GRANT UPDATE ON pmg_prod.* to 'pmg_prod_user'@'localhost';