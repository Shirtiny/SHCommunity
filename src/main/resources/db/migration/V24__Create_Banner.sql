create table banner
(
	banner_id bigint auto_increment comment '主键id',
	constraint banner_pk
		primary key (banner_id),
	banner_name varchar(256) default 'bannername' not null comment '条幅名、广告名',
	banner_url varchar(1024) default 'https://file.moetu.org/images/2019/10/24/bannermd_1e1decde1a1835eaa.jpg' not null comment '对应图片地址',
	banner_shape varchar(256) default 'lg' not null comment '图片形状：长条lg、中等md、小型sm、圆cr，默认lg为长条'
)comment '条幅、广告';

create unique index banner_banner_id_uindex
	on banner (banner_id);

