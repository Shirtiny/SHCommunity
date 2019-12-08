alter table CHAT_HISTORY
    add system_sign boolean default false not null;

comment on column CHAT_HISTORY.system_sign is '标识此消息记录为 用户与系统通信的历史';