create table banner
(
	banner_id bigint auto_increment,
	banner_name VARCHAR(256) default 'bannerName' not null,
	banner_url varchar2(1024) default 'https://file.moetu.org/images/2019/10/24/bannermd_1e1decde1a1835eaa.jpg' not null,
	banner_shape varchar(256) default 'lg' not null
);

comment on table banner is '条幅、广告';

comment on column banner.banner_id is '主键id';

comment on column banner.banner_name is '条幅名、广告名';

comment on column banner.banner_url is '对应图片地址';

comment on column banner.banner_shape is '图片形状：长条lg、中等md、小型sm、圆cr，默认lg为长条';

create unique index banner_banner_id_uindex
	on banner (banner_id);

alter table banner
	add constraint banner_pk
		primary key (banner_id);

