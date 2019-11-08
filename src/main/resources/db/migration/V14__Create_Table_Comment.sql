create table comment
(
	comment_id bigint auto_increment comment '主键id',
	constraint comment_pk
		primary key (comment_id),
	reviewer_id bigint not null comment '评论者id，对应一个用户id',
	target_id bigint not null comment '评论的对象id，对应一个帖子的id',
	comment_content varchar(256) not null comment '评论内容',
	created_time bigint not null comment '创建时间'
)comment '帖子的评论';


create unique index comment_comment_id_uindex
	on comment (comment_id);


