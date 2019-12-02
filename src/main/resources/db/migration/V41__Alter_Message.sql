alter table chat_message
    add `readed` boolean default false not null comment '标识是否已读 默认false';

alter table chat_message
    add `systems` boolean default false null comment '标识是否是系统消息 可以为空';

