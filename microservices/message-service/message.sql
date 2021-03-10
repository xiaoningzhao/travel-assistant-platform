-- When setting up database in MySQL, use uft8mb4 encoding to support emojis

-- message records table
DROP TABLE IF EXISTS `message`
CREATE TABLE `message`(
    `message_id` bigint unsigned not null auto_increment,
    `chat_id` bigint unsigned not null,
    `user_id` bigint unsigned not null,
    `message_text` text not null,
    `create_at` timestamp not null default current_timestamp,
    primary key (`message_id`)
);

-- chat table
DROP TABLE IF EXISTS `chat`
CREATE TABLE `chat`(
    `chat_id` bigint unsigned not null auto_increment,
    `create_at` timestamp not null default current_timestamp,
    primary key (`chat_id`)
);