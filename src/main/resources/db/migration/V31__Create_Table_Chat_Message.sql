create table chat_message
(
	chat_message_id bigint auto_increment,
	chat_history_id bigint not null,
	chat_message_content varchar(256) not null,
	gmt_created bigint not null,
	sender_id bigint,
	recipient_id bigint default 0
);

comment on table chat_message is '聊天消息';

comment on column chat_message.chat_message_id is '主键';

comment on column chat_message.chat_history_id is '记录此消息的 聊天记录的id';

comment on column chat_message.chat_message_content is '消息内容';

comment on column chat_message.gmt_created is '创建时间';

comment on column chat_message.sender_id is '发送者id，可以为空';

comment on column chat_message.recipient_id is '接收者id，可以为空，默认0';

create unique index chat_message_chat_message_id_uindex
	on chat_message (chat_message_id);

alter table chat_message
	add constraint chat_message_pk
		primary key (chat_message_id);

