create table user
(
    id           bigint auto_increment,
    nickname     varchar(100) not null,
    password     varchar(100),
    email        varchar(200) not null,
    avatarimage  varchar(500),
    github_id    varchar(500),
    gmt_create   bigint       not null comment '创建时间戳',
    gmt_modified bigint       not null comment '变更时间戳',
    constraint user_pk
        primary key (id)
) comment '论坛的用户表';

create unique index user_id_uindex
    on user (id);

create unique index user_email_uindex
    on user (email);

create unique index user_github_id_uindex
    on user (github_id);