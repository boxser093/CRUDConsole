CREATE TABLE Label
(
    id     int not null primary key generated always as IDENTITY (INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 99999 CACHE 1),
    name   varchar(255),
    status text
);

CREATE TABLE Post
(
    id        int not null primary key generated always as IDENTITY (INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 99999 CACHE 1),
    content   text,
    created   timestamp,
    update    timestamp,
    status    text,
    writer_id int
);

CREATE TABLE Writer
(
    id        int not null primary key generated always as IDENTITY (INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 99999 CACHE 1),
    firstName varchar(64),
    lastName  varchar(128),
    status    text,
    post_id   int,
    foreign key (post_id) references Post (id)
);

ALTER TABLE Post ADD CONSTRAINT writer_Id foreign key (writer_id) references Writer(id);
