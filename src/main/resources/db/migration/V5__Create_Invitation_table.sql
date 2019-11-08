create table invitation
(
	id bigint auto_increment comment '帖子主键id',
	constraint invitation_pk
		primary key (id),
	title varchar(200) not null comment '标题',
	content varchar(500) not null comment '内容',
	reply_num bigint default 0 not null comment '回复数',
	gmt_created bigint not null comment '创建时间',
	gmt_modified bigint not null comment '更新时间'
)
comment '帖子';

create unique index invitation_id_uindex
	on invitation (id);

