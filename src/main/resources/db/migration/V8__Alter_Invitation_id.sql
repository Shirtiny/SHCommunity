alter table INVITATION alter column ID varchar(16) default (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_99C1E205_B32A_4D26_92F9_80DE50455257) auto_increment;

alter table INVITATION alter column AUTHOR_ID varchar(6) default 0;

