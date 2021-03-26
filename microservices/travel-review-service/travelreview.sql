-- When setting up database in MySQL, use uft8mb4 encoding to support emojis

-- User-trip table
DROP TABLE IF EXISTS `user_trip_history`
CREATE TABLE `user_trip_history`(
    `user_id`, varchar(32) not null,
    `trip_id`, varchar(32) not null,
    `create_time`, timestamp not null default current_timestamp,
    primary key (`user_id`)
);

-- Trip details table
DROP TABLE IF EXISTS `trip_details`
CREATE TABLE `user_trip_history`(
    `trip_id`, varchar(32) not null,
    `group_size`, int not null,
    `destination_id`, varchar(32),
    `start_date`, timestamp not null,
    `number_of_days`, int not null,
    `remarks`, varchar(512),
    `chat_record_id`, varchar(32),
    `create_time`, timestamp not null default current_timestamp,
    `update_time`, timestamp not null default current_timestamp on update current_timestamp,
    primary key (`trip_id`)
);

-- destination information table
-- This table uses Google location code to annotate a point of interests.
DROP TABLE IF EXISTS `destination`
CREATE TABLE `destination`(
    `destination_id`, varchar(32) not null,
    `google_location_code`, varchar(10) not null,
    `create_time`, timestamp not null default current_timestamp,
    `update_time`, timestamp not null default current_timestamp on update current_timestamp,
    primary key (`destination_id`)
);