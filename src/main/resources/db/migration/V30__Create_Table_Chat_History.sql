create table chat_history
(
	chat_history_id bigint auto_increment,
	chat_history_name varchar(256) not null,
	gmt_created bigint not null,
	gmt_modified bigint not null,
	message_num bigint default 0 not null
);

comment on table chat_history is '聊天记录、频道';

comment on column chat_history.chat_history_id is '主键';

comment on column chat_history.chat_history_name is '聊天记录的名称';

comment on column chat_history.gmt_created is '聊天创建时间';

comment on column chat_history.gmt_modified is '更新时间';

comment on column chat_history.message_num is '消息条数';

create unique index chat_history_chat_history_id_uindex
	on chat_history (chat_history_id);

alter table chat_history
	add constraint chat_history_pk
		primary key (chat_history_id);

