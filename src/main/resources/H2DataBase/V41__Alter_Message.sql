alter table CHAT_MESSAGE
    add readed boolean default false not null;

comment on column CHAT_MESSAGE.read is '标识是否已读 默认false';

alter table CHAT_MESSAGE
    add systems boolean default false;

comment on column CHAT_MESSAGE.system is '标识是否为系统消息 可以为空';

