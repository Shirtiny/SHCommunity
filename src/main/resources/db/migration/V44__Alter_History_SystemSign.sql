alter table chat_history
    add system_sign boolean default false not null comment '标识此消息记录为 用户与系统的通信历史';

