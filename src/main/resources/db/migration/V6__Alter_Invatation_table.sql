alter table INVITATION
	add author_id bigint default 0;

comment on column INVITATION.author_id is '发帖用户，默认为0';

