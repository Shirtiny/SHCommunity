create table chat_message
(
	chat_message_id bigint auto_increment comment '主键',
	constraint chat_message_pk
		primary key (chat_message_id),
	chat_history_id bigint not null comment '记录此消息的 聊天记录的id',
	chat_message_content varchar(256) not null comment '消息内容',
	gmt_created bigint not null comment '创建时间',
	sender_id bigint comment '发送者id，可以为空',
	recipient_id bigint default 0 comment '接收者id，可以为空，默认0'
)comment '聊天消息';

create unique index chat_message_chat_message_id_uindex
	on chat_message (chat_message_id);


