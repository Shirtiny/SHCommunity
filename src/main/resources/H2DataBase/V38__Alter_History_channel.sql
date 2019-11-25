alter table CHAT_HISTORY
    add channel varchar(200);

comment on column CHAT_HISTORY.channel is '频道';

