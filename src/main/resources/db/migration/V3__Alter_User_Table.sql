alter table USER
	add token varchar2(500);

create unique index USER_token_uindex
	on USER (token);
