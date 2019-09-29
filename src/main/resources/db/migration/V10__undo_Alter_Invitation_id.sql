alter table INVITATION alter column ID bigint default '(NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_23017C19_ED1F_4ACE_A8CC_1975200BED20)' auto_increment;

alter table INVITATION alter column AUTHOR_ID bigint default '0' not null;

