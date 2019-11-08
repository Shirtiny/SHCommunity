create table invitation
(
	id bigint auto_increment,
	title varchar2(200) not null,
	content varchar2(500) not null,
	reply_num bigint default 0 not null,
	gmt_created bigint not null,
	gmt_modified bigint not null
);

comment on table invitation is '帖子';

comment on column invitation.id is '帖子主键id';

comment on column invitation.title is '标题';

comment on column invitation.content is '内容';

comment on column invitation.reply_num is '回复数';

comment on column invitation.gmt_created is '创建时间';

comment on column invitation.gmt_modified is '更新时间';

create unique index invitation_id_uindex
	on invitation (id);

alter table invitation
	add constraint invitation_pk
		primary key (id);

