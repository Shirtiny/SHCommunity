alter table INVITATION
	add candy_num bigint default 0 not null;

comment on column INVITATION.candy_num is '帖子的糖数';

