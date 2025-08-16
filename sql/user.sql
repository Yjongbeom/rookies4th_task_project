create table users(
    id int(10) not null auto_increment primary key,
    userid varchar(100) not null ,
    name varchar(100) not null ,
    gender varchar(10),
    city varchar(100)
);

alter table users add unique index users_userid_idx(userid);

show index from users;

insert into users(userid,name,gender,city) values ('gildong','홍길동','남','서울');
commit;

insert into users(userid,name,gender,city) values ('dooly','둘리','여','부산');
commit;
