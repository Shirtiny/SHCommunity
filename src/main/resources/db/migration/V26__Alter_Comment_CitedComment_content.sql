alter table COMMENT
	add CITED_COMMENT_CONTENT VARCHAR(256);

comment on column COMMENT.CITED_COMMENT_CONTENT is '被引用的评论的内容';

