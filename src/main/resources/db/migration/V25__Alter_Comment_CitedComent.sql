alter table COMMENT
	add CITED_COMMENT_ID bigint;

comment on column COMMENT.CITED_COMMENT_ID is '被引用的评论id';

