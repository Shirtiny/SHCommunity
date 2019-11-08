create table Comment
(
	comment_id bigint auto_increment,
	reviewer_id bigint not null,
	target_id bigint not null,
	comment_content varchar2(256) not null,
	created_time bigint not null
);

comment on table Comment is '帖子的评论';

comment on column Comment.comment_id is '主键id';

comment on column Comment.reviewer_id is '评论者id，对应一个用户id';

comment on column Comment.target_id is '评论的对象id，对应一个帖子的id';

comment on column Comment.comment_content is '评论内容';

comment on column Comment.created_time is '创建时间';

create unique index Comment_comment_id_uindex
	on Comment (comment_id);

alter table Comment
	add constraint Comment_pk
		primary key (comment_id);

