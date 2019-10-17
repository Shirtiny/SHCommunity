alter table INVITATION
	add views bigint default 0;

comment on column INVITATION.views is '浏览量';