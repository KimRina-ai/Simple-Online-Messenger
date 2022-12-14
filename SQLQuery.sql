CREATE TABLE member (
  id varchar(20) NOT NULL,
  pw varchar(20) NOT NULL,
  nickname varchar(20) NOT NULL,
  email varchar(50) DEFAULT NULL,
  name varchar(20) NOT NULL,
  birth varchar(20) NOT NULL,
  pnum varchar(20) DEFAULT NULL,
  home varchar(50) DEFAULT NULL,
  member_code bigint DEFAULT NULL,
  on_off varchar(20) not null,
  comments varchar(100) ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

create table friend (
id varchar (20) NOT NULL,
friend_id varchar(20) NOT NULL,
primary key (id, friend_id),
foreign key(id) references member (id)
);
