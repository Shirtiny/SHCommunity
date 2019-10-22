alter table INVITATION
	add section_id bigint default 1;

comment on column INVITATION.section_id is '帖子所属版块的id';

