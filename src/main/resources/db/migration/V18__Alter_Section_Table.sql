alter table SECTION
	add section_rate int default 0;

comment on column SECTION.section_rate is '版块评分';

