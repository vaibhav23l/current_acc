CREATE TABLE SEQUENCE (
  table_name VARCHAR(30),
  sequence_num INTEGER
);

CREATE TABLE CUSTOMER (
  customer_id VARCHAR(10) NOT NULL ,
  first_name VARCHAR(30),
  last_name  VARCHAR(50),
  PRIMARY KEY (customer_id)
);

CREATE TABLE  ACCOUNT (
  account_id  VARCHAR(10) NOT NULL ,
  customer_id VARCHAR(10),
  account_type VARCHAR(10) NOT NULL,
  create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  
  PRIMARY KEY (account_id),
  FOREIGN KEY (customer_id) REFERENCES Customer(customer_id)
);

CREATE TABLE  ACC_TRANSACTION (
  transaction_id  VARCHAR(10) NOT NULL ,
  account_id VARCHAR(10) NOT NULL,
  debit DECIMAL( 10, 3 ) NOT NULL DEFAULT  0,
  credit DECIMAL( 10, 3 ) NOT NULL DEFAULT  0,
  create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (transaction_id),
  FOREIGN KEY (account_id) REFERENCES Account(account_id)
);