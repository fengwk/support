-- user
drop table if exists user;
create table user
(
   id                   bigint unsigned not null auto_increment comment '主键',
   created_time         datetime not null comment '创建时间',
   modified_time        datetime not null comment '修改时间',
   email                varchar(64) comment '邮箱',
   nickname             varchar(64) comment '昵称',
   password             varchar(32) comment '密码',
   primary key (id),
   index idx_modifiedTime(modified_time),
   unique uk_email(email),
   index idx_nickname(nickname(16))
) engine=InnoDB default charset=utf8 comment='用户表';

-- access
drop table if exists role;
create table role
(
   id                   bigint unsigned not null auto_increment comment '主键',
   created_time         datetime not null comment '创建时间',
   modified_time        datetime not null comment '修改时间',
   name                 varchar(32) not null comment '角色名称',
   primary key (id),
   index idx_modifiedTime(modified_time),
   unique uk_name(name)
) engine=InnoDB default charset=utf8 comment='角色表';

drop table if exists permission;
create table permission
(
   id                   bigint unsigned not null auto_increment comment '主键',
   created_time         datetime not null comment '创建时间',
   modified_time        datetime not null comment '修改时间',
   name                 varchar(32) not null comment '权限名称',
   path                 varchar(256) not null comment '权限路径',
   primary key (id),
   index idx_modifiedTime(modified_time),
   unique uk_name(name)
) engine=InnoDB default charset=utf8 comment='权限表';

drop table if exists user_role_link;
create table user_role_link
(
   id                   bigint unsigned not null auto_increment comment '主键',
   created_time         datetime not null comment '创建时间',
   modified_time        datetime not null comment '修改时间',
   user_id              bigint unsigned not null comment '用户id',
   role_id              bigint unsigned not null comment '角色id',
   primary key (id),
   index idx_modifiedTime(modified_time),
   unique uk_userId_roleId(user_id, role_id),
   unique uk_roleId_userId(role_id, user_id)
) engine=InnoDB default charset=utf8 comment='用户角色关联表';

drop table if exists role_permission_link;
create table role_permission_link
(
   id                   bigint unsigned not null auto_increment comment '主键',
   created_time         datetime not null comment '创建时间',
   modified_time        datetime not null comment '修改时间',
   role_id              bigint unsigned not null comment '角色id',
   permission_id        bigint unsigned not null comment '权限id',
   primary key (id),
   index idx_modifiedTime(modified_time),
   unique uk_roleId_permissionId(role_id, permission_id),
   unique uk_permissionId_roleId(permission_id, role_id)
) engine=InnoDB default charset=utf8 comment='角色权限关联表';

-- security
drop table if exists random;
create table random
(
   id                   bigint unsigned not null auto_increment comment '主键',
   created_time         datetime not null comment '创建时间',
   modified_time        datetime not null comment '修改时间',
   way                  tinyint(1) unsigned not null comment '途径:1-email;2-sms;',
   type                 tinyint(1) unsigned not null comment '类型:1-注册;2-认证;3-重置;',
   target               varchar(64) not null comment '目标',
   value                varchar(64) not null comment '值',
   expires_in           int unsigned not null comment '超时时间/秒',
   status               tinyint(1) unsigned not null comment '状态:1-未使用;2-静默;3-已使用;',
   primary key (id),
   index idx_modifiedTime(modified_time),
   unique uk_way_type_target(way, type, target)
) engine=InnoDB default charset=utf8 comment='验证码表';

-- oauth2
drop table if exists client;
create table client
(
   id                   bigint unsigned not null auto_increment comment '主键',
   created_time         datetime not null comment '创建时间',
   modified_time        datetime not null comment '修改时间',
   name                 varchar(32) not null comment '客户端名称',
   secret               varchar(32) not null comment '密钥',
   redirect_rules       varchar(1024) not null comment '密钥',
   access_expires_in    int unsigned not null comment '访问超时时间/秒',
   refresh_expires_in   int unsigned not null comment '刷新超时时间/秒',
   is_exclusive         tinyint(1) unsigned not null comment '是否为排它模式',
   token_count_limit    int unsigned not null comment '令牌数量限制',
   is_disabled          tinyint(1) unsigned not null comment '是否禁用',
   primary key (id),
   index idx_modifiedTime(modified_time)
) engine=InnoDB default charset=utf8 comment='客户端表';

drop table if exists authorization_code;
create table authorization_code
(
   id                   bigint unsigned not null auto_increment comment '主键',
   created_time         datetime not null comment '创建时间',
   modified_time        datetime not null comment '修改时间',
   code                 varchar(32) not null comment '授权码',
   expires_in           int unsigned not null comment '超时时间/秒',
   is_used              tinyint(1) unsigned not null comment '是否已使用',
   bound_response_type  varchar(32) not null comment '授权类型',
   bound_client_id      bigint unsigned not null comment '客户端id',
   bound_redirect_uri   varchar(1024) comment '重定向地址',
   bound_scope          varchar(128) comment '权限范围',
   bound_state          varchar(1024) comment '客户端当前状态',
   bound_user_id        bigint unsigned not null comment '用户id',
   primary key (id),
   index idx_modifiedTime(modified_time),
   unique uk_code(code)
) engine=InnoDB default charset=utf8 comment='授权码表';

drop table if exists token;
create table token
(
   id                   bigint unsigned not null auto_increment comment '主键',
   created_time         datetime not null comment '创建时间',
   modified_time        datetime not null comment '修改时间',
   client_id            bigint unsigned not null comment '客户端id',
   user_id              bigint unsigned not null comment '用户id',
   grant_type           varchar(32) not null comment '授权类型',
   scope                varchar(128) not null comment '权限范围',
   token_type           varchar(16) not null comment '权限范围',
   access_token         varchar(32) not null comment '访问令牌',
   access_expires_in    int unsigned not null comment '访问超时时间/秒',
   access_created_time  datetime not null comment '访问令牌创建时间',
   refresh_token        varchar(32) comment '刷新令牌',
   refresh_expires_in   int unsigned comment '刷新超时时间/秒',
   refresh_created_time datetime comment '刷新令牌创建时间',
   is_invalid           tinyint(1) unsigned not null comment '是否失效',
   primary key (id),
   index idx_modifiedTime(modified_time),
   index idx_userId_clientId_isInvalid(user_id, client_id, is_invalid),
   unique uk_accessToken(access_token),
   unique uk_refreshToken(refresh_token)
) engine=InnoDB default charset=utf8 comment='令牌表';