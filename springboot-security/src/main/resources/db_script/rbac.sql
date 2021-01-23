-- 删除存在的数据表
drop table if exists sys_user;
-- 创建数据表 系统用户表 sys_user
create table sys_user
(
    id                      int(11) auto_increment primary key comment '用户ID',
    username                varchar(50) unique comment '用户名称',
    real_name               varchar(50) default null comment '真实姓名',
    password                text        default null comment '用户密码',
    create_by               varchar(50) default null comment '创建人',
    create_time             datetime    default null comment '创建时间',
    update_by               varchar(50) default null comment '更新人',
    update_time             datetime    default null comment '更新时间',
    last_login_time         datetime    default null comment '最近登录时间',
    enabled                 int(5)      default null comment '是否有效用户',
    account_non_expired     int(5)      default null comment '账户是否过期',
    account_non_locked      int(5)      default null comment '账户是否锁定',
    credentials_non_expired int(5)      default null comment '权限是否过期'
) engine = innodb,
  charset = utf8mb4;

-- 角色表 sys_role
drop table if exists sys_role;
create table sys_role
(
    id        int(11) auto_increment primary key comment '角色ID',
    role_name varchar(20) default null comment '角色名称',
    role_desc varchar(50) default null comment '角色描述'
) engine = innodb,
  charset = utf8mb4;


-- 权限表 sys_permission
drop table if exists sys_permission;
create table sys_permission
(
    id        int(11) auto_increment primary key comment '权限ID',
    perm_name varchar(50) default null comment '权限名称',
    perm_tag  varchar(50) default null comment '权限标识'
) engine = innodb,
  charset = utf8mb4;


-- 用户-角色表  sys_user_role
drop table if exists sys_user_role;
create table sys_user_role
(
    id  int(11) auto_increment primary key comment '主键ID',
    uid int(11) default null comment '用户ID',
    rid int(11) default null comment '角色ID'
) engine = innodb,
  charset = utf8mb4;

-- 角色-权限表 sys_role_permission
drop table if exists sys_role_permission;
create table sys_role_permission
(
    id  int(11) auto_increment primary key comment '主键ID',
    rid int(11) default null comment '角色ID',
    pid int(11) default null comment '权限ID'
) engine = innodb,
  charset = utf8mb4;
insert into sys_permission
values (1, '产品查询', 'ROLE_PRODUCT_LIST'),
       (2, '产品添加', 'ROLE_PRODUCT_ADD'),
       (3, '产品修改', 'ROLE_PRODUCT_UPDATE'),
       (4, '产品删除', 'ROLE_PRODUCT_DELETE');


-- 查询语句：
select *
from sys_permission;