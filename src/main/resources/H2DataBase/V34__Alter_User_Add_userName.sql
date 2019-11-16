alter table USER
    add user_name varchar(100);

comment on column USER.user_name is '用户名';

create unique index USER_user_name_uindex
    on USER (user_name);

