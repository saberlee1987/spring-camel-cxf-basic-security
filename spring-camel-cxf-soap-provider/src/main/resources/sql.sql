create table persons
(
    id           int auto_increment
        primary key,
    firstname    varchar(70) not null,
    lastname     varchar(80) not null,
    nationalCode varchar(10) not null,
    age          int         not null,
    email        varchar(50) not null,
    mobile       varchar(15) not null,
    constraint nationalCode
        unique (nationalCode)
);
alter table customer change customercity customer_city VARCHAR(225);

ALTER TABLE table
    ADD [COLUMN] column_name_1 column_1_definition [FIRST|AFTER existing_column],
ADD [COLUMN] column_name_2 column_2_definition [FIRST|AFTER existing_column],
...;


create table authorities (
                             user_id bigint not null,
                             authority varchar(30)
) engine=InnoDB;

create table users (
                       id bigint not null auto_increment,
                       accountNonExpired bit not null,
                       accountNonLocked bit not null,
                       credentialsNonExpired bit not null,
                       enabled bit not null,
                       password varchar(80),
                       username varchar(70),
                       primary key (id)
) engine=InnoDB;

alter table authorities
    add constraint FKk91upmbueyim93v469wj7b2qh
        foreign key (user_id)
            references users (id)