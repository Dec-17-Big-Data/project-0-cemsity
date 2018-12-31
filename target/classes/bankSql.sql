/*******************************************************************************
   Chinook Database - Version 1.4
   Script: bankSql
   Description: Creates and populates the JDBC bank app.
   DB Server: Oracle
   Author: Stephen Hilson
   License:
********************************************************************************/

/*******************************************************************************
   Drop database if it exists
********************************************************************************/
DROP USER bankAdmin CASCADE;


/*******************************************************************************
   Create database
********************************************************************************/
CREATE USER bankAdmin
IDENTIFIED BY
DEFAULT TABLESPACE users
TEMPORARY TABLESPACE temp
QUOTA 10M ON users;

GRANT connect to bankAdmin;
GRANT resource to bankAdmin;
GRANT create session TO bankAdmin;
GRANT create table TO bankAdmin;
GRANT create view TO bankAdmin;



conn bankAdmin/


-- ADD CONTRANTS AND OTHER
create table users (
    user_id number(10) NOT NULL,
    user_name varchar(255) NOT NULL UNIQUE,
    user_first varchar(255) NOT NULL,
    user_last varchar(255) NOT NULL,
    user_password varchar(255) NOT NULL,
    CONSTRAINT PK_user PRIMARY KEY (user_id),

);

create table accounts (
    account_id number(10) NOT NULL,
    user_id number(10),
    account_type varchar(255),
    account_ammount number(20) ,
    CONSTRAINT PK_account PRIMARY KEY (account_id),
    CONSTRAINT FK_UserAccount
      FOREIGN KEY (user_id)
      References users(user_id)
      ON DELETE CASCADE,
    CHECK (account_ammount >= 0),

);

create table transactions (
    transaction_id number(15) not null,
    account1_id number(10) not null,
    account2_id number(10),
    transaction_ammount number(10) not null,
    transaction_type varchar(255) not null,
    transaction_date date DEFAULT GETDATE(),
    constraint PK_transaction
      PRIMARY KEY (transaction_id),
    constraint FK_accountTransaction1
      FOREIGN KEY (account1_id)
      References accounts(account_id)
      ON DELETE CASCADE,
    CONSTRAINT FK_accountTransaction2
      FOREIGN KEY (account2_id)
      REFERENCES accounts(account_id),
      on DELETE CASCADE,
    CHECK (transaction_ammount > 0)
);

create table super_users (
    super_user_ID number(10) NOT NULL PRIMARY KEY,
    super_user_name varchar(255) not null unique,
    super_user_password varchat(255) not null,
);

/***************************************
* SEQUENCES
***************************************/
CREATE SEQUENCE seq_user
  MINVALUE 1
  START WITH 1
  INCREMENT 1
  cache 10;

CREATE SEQUENCE seq_acc
  MINVALUE 1
  start with 1
  INCREMENT 1
  CASHE 10;

CREATE SEQUENCE seq_trans
  MINVALUE 1
  Start with 1
  INCREMENT 1
  cashe 10;

/*************************
* PROCEDURES
* ************************/

CREATE OR REPLACE PROCEDURE withdraw (
  account,
  amount,
)


