drop table if exists File;
create table File
(
   id                   bigint unsigned not null auto_increment comment '主键',
   accessId             varchar(32) not null comment '文件访问标识',
   createdTime          datetime not null comment '创建时间',
   modifiedTime         datetime not null comment '修改时间',
   fileSystemId         varchar(64) not null comment '文件系统标识',
   localPath            varchar(1024) not null comment '本地路径',
   originalName         varchar(1024) comment '文件原始名称',
   type                 varchar(32) comment '文件类型',
   digest               varchar(32) comment '文件摘要',
   size                 bigint unsigned not null comment '文件大小/b',
   primary key (id),
   index idx_modifiedTime(modifiedTime),
   unique uk_accessId(accessId(32)),
   unique uk_digest(digest(32))
) default charset=utf8mb4 comment='文件表';