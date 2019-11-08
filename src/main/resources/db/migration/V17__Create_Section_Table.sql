create table section
(
	section_id bigint auto_increment comment '版块分区id',
	constraint section_pk
		primary key (section_id),
	section_title varchar(255) default '版块标题' not null comment '版块标题',
	section_description varchar(255) default '版块描述' not null comment '版块描述',
	section_avatar_image varchar(500) not null comment '版块头像',
	invitation_total_num bigint default 0 comment '帖子总数',
	invitation_star_num bigint default 0 comment '加精帖子数',
	section_owner_id bigint default 0 not null comment '版主id'
) comment '版块分区';


create unique index section_section_id_uindex
	on section (section_id);


