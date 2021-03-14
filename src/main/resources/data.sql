drop table if exists compass_decision_table;

create table compass_decision_table(
id varchar(120) not null primary key,
name varchar(120),
description varchar(512),
input_variables_json varchar(2048),
output_variables_json varchar(2048),
rules_json varchar(2048),
status varchar(24),
version int default 0,
creator varchar(100),
create_time varchar(20),
updater  varchar(12),
update_time varchar(12)
);

drop table if exists compass_account;

create table compass_account(
id varchar(50) not null primary key,
username varchar(12),
email varchar(50),
pwd_salt varchar(30),
hashed_pwd varchar(100),
create_time varchar(20)
);

insert into compass_account(
id,
username,
email,
pwd_salt,
hashed_pwd,
) values('01','admin','admin@test.com','$2a$10$mZVYtfglKBhtN5rmRgy2mu','$2a$10$mZVYtfglKBhtN5rmRgy2muQK1fYQFUyyzWNXLWkUTDROBz8tjr1RK');