-- user
drop table if exists User;
create table User
(
   id                   bigint unsigned not null auto_increment comment '主键',
   createdTime          datetime not null comment '创建时间',
   modifiedTime         datetime not null comment '修改时间',
   email                varchar(64) comment '邮箱',
   nickname             varchar(16) comment '昵称',
   password             varchar(32) comment '密码',
   primary key (id),
   index idx_modifiedTime(modifiedTime),
   unique uk_email(email(16)),
   index idx_nickname(nickname(16))
) engine=InnoDB default charset=utf8mb4 comment='用户表';

-- access
drop table if exists Role;
create table Role
(
   id                   bigint unsigned not null auto_increment comment '主键',
   createdTime          datetime not null comment '创建时间',
   modifiedTime         datetime not null comment '修改时间',
   name                 varchar(32) not null comment '角色名称',
   primary key (id),
   index idx_modifiedTime(modifiedTime),
   unique uk_name(name)
) engine=InnoDB default charset=utf8mb4 comment='角色表';

drop table if exists Permission;
create table Permission
(
   id                   bigint unsigned not null auto_increment comment '主键',
   createdTime          datetime not null comment '创建时间',
   modifiedTime         datetime not null comment '修改时间',
   name                 varchar(32) not null comment '权限名称',
   path                 varchar(256) not null comment '权限路径',
   primary key (id),
   index idx_modifiedTime(modifiedTime),
   unique uk_name(name)
) engine=InnoDB default charset=utf8mb4 comment='权限表';

drop table if exists UserRoleLink;
create table UserRoleLink
(
   id                   bigint unsigned not null auto_increment comment '主键',
   createdTime          datetime not null comment '创建时间',
   modifiedTime         datetime not null comment '修改时间',
   userId               bigint unsigned not null comment '用户id',
   roleId               bigint unsigned not null comment '角色id',
   primary key (id),
   index idx_modifiedTime(modifiedTime),
   unique uk_userId_roleId(userId, roleId),
   unique uk_roleId_userId(roleId, userId)
) engine=InnoDB default charset=utf8mb4 comment='用户角色关联表';

drop table if exists RolePermissionLink;
create table RolePermissionLink
(
   id                   bigint unsigned not null auto_increment comment '主键',
   createdTime          datetime not null comment '创建时间',
   modifiedTime         datetime not null comment '修改时间',
   roleId               bigint unsigned not null comment '角色id',
   permissionId         bigint unsigned not null comment '权限id',
   primary key (id),
   index idx_modifiedTime(modifiedTime),
   unique uk_roleId_permissionId(roleId, permissionId),
   unique uk_permissionId_roleId(permissionId, roleId)
) engine=InnoDB default charset=utf8mb4 comment='角色权限关联表';

-- security
drop table if exists Random;
create table Random
(
   id                   bigint unsigned not null auto_increment comment '主键',
   createdTime          datetime not null comment '创建时间',
   modifiedTime         datetime not null comment '修改时间',
   way                  tinyint(1) unsigned not null comment '途径:1-email;2-sms;',
   type                 tinyint(1) unsigned not null comment '类型:1-注册;2-认证;3-重置;',
   target               varchar(64) not null comment '目标',
   value                varchar(64) not null comment '值',
   expiresIn            int unsigned not null comment '超时时间/秒',
   status               tinyint(1) unsigned not null comment '状态:1-未使用;2-静默;3-已使用;',
   primary key (id),
   index idx_modifiedTime(modifiedTime),
   unique uk_way_type_target(way, type, target)
) engine=InnoDB default charset=utf8mb4 comment='验证码表';

-- oauth2
drop table if exists Client;
create table Client
(
   id                   bigint unsigned not null auto_increment comment '主键',
   createdTime          datetime not null comment '创建时间',
   modifiedTime         datetime not null comment '修改时间',
   name                 varchar(32) not null comment '客户端名称',
   secret               varchar(32) not null comment '密钥',
   redirectRules        varchar(1024) not null comment '密钥',
   accessExpiresIn      int unsigned not null comment '访问超时时间/秒',
   refreshExpiresIn     int unsigned not null comment '刷新超时时间/秒',
   isExclusive          tinyint(1) unsigned not null comment '是否为排它模式',
   tokenCountLimit      int unsigned not null comment '令牌数量限制',
   isDisabled           tinyint(1) unsigned not null comment '是否禁用',
   primary key (id),
   index idx_modifiedTime(modifiedTime)
) engine=InnoDB default charset=utf8mb4 comment='客户端表';

drop table if exists AuthorizationCode;
create table AuthorizationCode
(
   id                   bigint unsigned not null auto_increment comment '主键',
   createdTime          datetime not null comment '创建时间',
   modifiedTime         datetime not null comment '修改时间',
   code                 varchar(32) not null comment '授权码',
   expiresIn            int unsigned not null comment '超时时间/秒',
   isUsed               tinyint(1) unsigned not null comment '是否已使用',
   responseType         varchar(32) not null comment '授权类型',
   clientId             bigint unsigned not null comment '客户端id',
   redirectUri          varchar(1024) comment '重定向地址',
   scope                varchar(128) comment '权限范围',
   state                varchar(1024) comment '客户端当前状态',
   userId               bigint unsigned not null comment '用户id',
   primary key (id),
   index idx_modifiedTime(modifiedTime),
   unique uk_code(code)
) engine=InnoDB default charset=utf8mb4 comment='授权码表';

drop table if exists Token;
create table Token
(
   id                   bigint unsigned not null auto_increment comment '主键',
   createdTime          datetime not null comment '创建时间',
   modifiedTime         datetime not null comment '修改时间',
   clientId             bigint unsigned not null comment '客户端id',
   userId               bigint unsigned not null comment '用户id',
   grantType            varchar(16) not null comment '授权类型',
   scope                varchar(128) not null comment '权限范围',
   tokenType            varchar(16) not null comment '权限范围',
   accessToken          varchar(32) not null comment '访问令牌',
   accessExpiresIn      int unsigned not null comment '访问超时时间/秒',
   accessCreatedTime    datetime not null comment '访问令牌创建时间',
   refreshToken         varchar(32) not null comment '刷新令牌',
   refreshExpiresIn     int unsigned not null comment '刷新超时时间/秒',
   refreshCreatedTime   datetime not null comment '刷新令牌创建时间',
   isInvalid            tinyint(1) unsigned not null comment '是否失效',
   primary key (id),
   index idx_modifiedTime(modifiedTime),
   index idx_userId_clientId_isInvalid(userId, clientId, isInvalid),
   unique uk_accessToken(accessToken),
   unique uk_refreshToken(refreshToken)
) engine=InnoDB default charset=utf8mb4 comment='令牌表';