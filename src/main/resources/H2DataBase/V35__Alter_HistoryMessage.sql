alter table chat_history
    add sender_id bigint null comment '发送者id';

alter table chat_history
    add recipient_id bigint null comment '接收人id';

