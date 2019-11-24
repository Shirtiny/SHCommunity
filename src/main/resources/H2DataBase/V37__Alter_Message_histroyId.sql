comment on column CHAT_MESSAGE.CHAT_HISTORY_ID is '记录此消息的 聊天记录的id 注意是字符串';

alter table CHAT_MESSAGE alter column CHAT_HISTORY_ID varchar(200) not null;

