create table section
(
	section_id bigint auto_increment,
	section_title varchar(255) default '版块标题' not null,
	section_description varchar(255) default '版块描述' not null,
	section_avatar_image varchar(500) not null,
	invitation_total_num bigint default 0,
	invitation_star_num bigint default 0,
	section_owner_id bigint default 0 not null
);

comment on table section is '版块分区';

comment on column section.section_id is '版块分区id';

comment on column section.section_title is '版块标题';

comment on column section.section_description is '版块描述';

comment on column section.section_avatar_image is '版块头像';

comment on column section.invitation_total_num is '帖子总数';

comment on column section.invitation_star_num is '加精帖子数';

comment on column section.section_owner_id is '版主id';

create unique index section_section_id_uindex
	on section (section_id);

alter table section
	add constraint section_pk
		primary key (section_id);

