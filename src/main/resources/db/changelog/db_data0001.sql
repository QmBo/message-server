create table if not exists users(
    id serial primary key,
    name varchar(255) not null unique ,
    password varchar(255) not null
);
create table if not exists messages(
    id serial primary key,
    message varchar(255),
    user_id int references users(id)
);
insert into users(name, password) VALUES ('admin', 'password');