drop table if exists file;
create table file
(
   id                   bigint unsigned not null auto_increment comment '主键',
   created_time         datetime not null comment '创建时间',
   modified_time        datetime not null comment '修改时间',
   access_id            varchar(32) not null comment '文件访问标识',
   file_system_id       varchar(64) not null comment '文件系统标识',
   local_path           varchar(1024) not null comment '本地路径',
   original_name        varchar(1024) comment '文件原始名称',
   type                 varchar(32) comment '文件类型',
   digest               varchar(32) comment '文件摘要',
   size                 bigint unsigned not null comment '文件大小/b',
   primary key (id),
   index idx_modifiedTime(modified_time),
   unique uk_accessId(access_id),
   unique uk_digest(digest)
) engine=InnoDB default charset=utf8 comment='文件表';