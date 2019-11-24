comment on column CHAT_HISTORY.CHAT_HISTORY_ID is '主键 注意是字符串';

alter table CHAT_HISTORY alter column CHAT_HISTORY_ID varchar(200) not null;

