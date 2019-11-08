create table chat_history
(
	chat_history_id bigint auto_increment comment '主键',
	constraint chat_history_pk
		primary key (chat_history_id),
	chat_history_name varchar(256) not null comment '聊天记录的名称',
	gmt_created bigint not null comment '聊天创建时间',
	gmt_modified bigint not null comment '更新时间',
	message_num bigint default 0 not null comment '消息条数'
)comment '聊天记录、频道';

create unique index chat_history_chat_history_id_uindex
	on chat_history (chat_history_id);


