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
--********************************************************************************/
--DROP USER bankAdmin CASCADE;
--
--
--/*******************************************************************************
--   Create database
--********************************************************************************/
--CREATE USER bankAdmin
--IDENTIFIED BY
--DEFAULT TABLESPACE users
--TEMPORARY TABLESPACE temp
--QUOTA 10M ON users;
--
--GRANT connect to bankAdmin;
--GRANT resource to bankAdmin;
--GRANT create session TO bankAdmin;
--GRANT create table TO bankAdmin;
--GRANT create view TO bankAdmin;
--
--
--
--conn bankAdmin/


-- ADD CONTRANTS AND OTHER
create table users (
    user_id number(10) NOT NULL,
    user_name varchar(255) NOT NULL UNIQUE,
    user_first varchar(255) NOT NULL,
    user_last varchar(255) NOT NULL,
    user_password varchar(255) NOT NULL,
    CONSTRAINT PK_user PRIMARY KEY (user_id)

);

create table accounts (
    account_id number(10) NOT NULL,
    user_id number(10) not null,
    account_type varchar(255) not null,
    account_ammount number(20) ,
    CONSTRAINT PK_account PRIMARY KEY (account_id),
    CONSTRAINT FK_UserAccount
      FOREIGN KEY (user_id)
      References users(user_id)
      ON DELETE CASCADE,
    CHECK (account_ammount >= 0)

);

create table transactions (
    transaction_id number(15) not null,
    account1_id number(10) not null,
    account2_id number(10),
    transaction_ammount number(10) not null,
    transaction_type varchar(255) not null,
    transaction_date timestamp DEFAULT CURRENT_TIMESTAMP,
    constraint PK_transaction
      PRIMARY KEY (transaction_id),
    constraint FK_accountTransaction1
      FOREIGN KEY (account1_id)
      References accounts(account_id)
      ON DELETE CASCADE,
    CONSTRAINT FK_accountTransaction2
      FOREIGN KEY (account2_id)
      REFERENCES accounts(account_id)
      on DELETE CASCADE,
    CHECK (transaction_ammount > 0)
);



/***************************************
* SEQUENCES
***************************************/
CREATE SEQUENCE seq_user
  MINVALUE 1
  START WITH 1
  INCREMENT BY 1
  cache 10;

CREATE SEQUENCE seq_acc
  MINVALUE 1
  start with 1
  INCREMENT by 1
  CACHE 10;

CREATE SEQUENCE seq_trans
  MINVALUE 1
  Start with 1
  INCREMENT BY 1
  CACHE 10;

/*************************
* PROCEDURES
* ************************/

CREATE OR REPLACE PROCEDURE new_user(
    name IN varchar,
    first IN varchar,
    last IN varchar,
    password in varchar
)
    IS 
    BEGIN 
        insert into users(user_id, user_name, user_first, user_last, user_password)
        values (seq_user.nextval, name, first, last, password);
        commit;
    END;
/

CREATE OR REPLACE PROCEDURE update_user (userId IN number, name IN varchar, first IN varchar, last IN varchar, password in varchar)
IS 
BEGIN
    update users
    set user_name = name, user_first = first, user_last = last, user_password = password
    where user_id = userId;
END;
/
CREATE OR REPLACE PROCEDURE delete_user (userId in number)
is 
begin
    DELETE FROM users where user_id = userId;
end;
/

CREATE OR REPLACE PROCEDURE new_account (user_ID IN NUMBER, acc_Type in varchar)
IS
BEGIN
    INSERT into accounts (account_id, user_id, account_type, account_ammount)
    values (seq_acc.nextval, user_ID, acc_Type, 0);
    commit;
END;
/

CREATE OR REPLACE PROCEDURE new_transaction (acc1 in number, acc2 in number, ammount in number, tran_type in varchar)
IS
BEGIN 
    insert into transactions(transaction_id, account1_id, account2_id, transaction_ammount, transaction_type)
    values (seq_trans.nextval, acc1, acc2, ammount, tran_type);
    commit;
END;
/

    

CREATE OR REPLACE PROCEDURE deposit (accId IN number, ammount in number)
IS
    acc_ammount number(10);
    cursor c1 is 
        select account_ammount
        from accounts 
        where account_id = accId;
    neg_number EXCEPTION;
BEGIN
    open c1;
    fetch c1 into acc_ammount;
    close c1;
    
    if ammount > 0 THEN 
        update accounts
        set account_ammount = (acc_ammount + ammount)
        where account_id = accId; 
        new_transaction (accId, null, ammount, 'deposit'); 
        commit;
    else
        raise neg_number;
    end if;
    
    EXCEPTION
        WHEN neg_number THEN 
            raise_application_error (-20001, 'Can not accept a negative number');
        when OTHERS then 
            raise_application_error (-20002, 'An error has hapened while depositing');
END;
/

CREATE OR REPLACE PROCEDURE withdraw (accId IN number, ammount in number)
IS
    acc_ammount number(10);
    cursor c1 is 
        select account_ammount
        from accounts 
        where account_id = accId;
    neg_number EXCEPTION;
BEGIN
    open c1;
    fetch c1 into acc_ammount;
    close c1;
    
    if ammount > 0 THEN 
        update accounts
        set account_ammount = (acc_ammount - ammount)
        where account_id = accId; 
        new_transaction(accId, null, ammount, 'withdraw');
        commit;
    else
        raise neg_number;
    end if;
    
    EXCEPTION
        WHEN neg_number THEN 
            raise_application_error (-20003, 'Can not accept a negative number');
        when OTHERS then 
            raise_application_error (-20004, 'An error has hapened while withdrawing');
END;
/


CREATE OR REPLACE PROCEDURE transfer (accId1 IN number, accId2 IN number, ammount in number)
IS
    acc1_ammount number(10);
    acc2_ammount number(10);
    
    cursor c1 is 
        select account_ammount
        from accounts 
        where account_id = accId1;
        
    cursor c2 is 
        select account_ammount
        from accounts 
        where account_id = accId2;
        
    neg_number EXCEPTION;
BEGIN
    open c1;
    fetch c1 into acc1_ammount;
    close c1;
    open c2;
    fetch c2 into acc2_ammount;
    close c2;
    
    if ammount > 0 THEN 
        update accounts
        set account_ammount = (acc1_ammount - ammount)
        where account_id = accId1; 
        
        update accounts
        set account_ammount = (acc2_ammount + ammount)
        where account_id = accId2; 
        
        new_transaction(accId1, accId2, ammount, 'transfer');
        commit;
    else
        raise neg_number;
    end if;
    
    EXCEPTION
        WHEN neg_number THEN 
            raise_application_error (-20005, 'Can not accept a negative number');
        when OTHERS then 
            raise_application_error (-20006, 'An error has hapened while transfering');
END;
/





