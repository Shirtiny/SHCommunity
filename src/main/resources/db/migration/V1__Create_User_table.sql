create table USER
(
    ID           BIGINT auto_increment,
    NICKNAME     VARCHAR(100) not null,
    PASSWORD     VARCHAR(100),
    EMAIL        VARCHAR(200) not null
        constraint USER_EMAIL_UINDEX
            unique,
    AVATARIMAGE  VARCHAR(500),
    GITHUB_ID    VARCHAR(500)
        constraint USER_GITHUB_ID_UINDEX
            unique,
    GMT_CREATE   BIGINT       not null,
    GMT_MODIFIED BIGINT       not null,
    constraint USER_PK
        primary key (ID)
);

comment on table USER is '论坛的用户表';

comment on column USER.GMT_CREATE is '创建时间戳';

comment on column USER.GMT_MODIFIED is '变更时间戳';

create unique index USER_ID_UINDEX
    on USER (ID);