
-- ADD CONTRANTS AND OTHER
create table users (
    user_id number(10),
    user_name varchar(255),
    user_first varchar(255),
    user_last varchar(255),
    user_super number(1),
    user_password varchar(255)

);

create table accounts (
    account_id number(10),
    user_id number(10),
    account_type varchar(255),
    account_ammount number(20)

);

create table transactions (
    transaction_id number(15),
    user1_id number(10),
    user2_id number(10),
    account1_id number(10),
    account2_id number(10),
    transaction_ammount number(10),
    transaction_type varchar(255)
);

