DROP SCHEMA IF EXISTS testdb;
CREATE SCHEMA IF NOT EXISTS testdb DEFAULT CHARACTER SET utf8;
USE testdb;
  
DROP TABLE IF EXISTS testdb.ADDRESS;
create table testdb.ADDRESS (
   id int(11) NOT NULL AUTO_INCREMENT,
   street VARCHAR(30) NOT NULL,
   city  VARCHAR(30) NOT NULL,
   country  VARCHAR(30) NOT NULL,  
   PRIMARY KEY (id)
)ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS testdb.CUSTOMER;
create table testdb.CUSTOMER (
   id int(11) NOT NULL AUTO_INCREMENT,
   name VARCHAR(30) NOT NULL UNIQUE,  
   telephone VARCHAR(30) NOT NULL UNIQUE,
   customer_address_id int(11) DEFAULT NULL,
   PRIMARY KEY (id),   
   KEY fk_customer_address (customer_address_id),
   CONSTRAINT fk_customer_address FOREIGN KEY (customer_address_id) REFERENCES CUSTOMER (id) ON DELETE CASCADE 
)ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
 
