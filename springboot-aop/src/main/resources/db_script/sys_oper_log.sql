-- 存在同名数据表，将先删除已存在的数据表再创建。
drop table if exists sys_oper_log;

-- 建表语句：
create table sys_oper_log
(
    id            bigint auto_increment comment '日志主键' primary key,
    title         varchar(50) charset utf8   default '' null comment '模块标题',
    business_type int(2)                     default 0  null comment '业务类型（0其它 1新增 2修改 3删除）',
    method        varchar(255) charset utf8  default '' null comment '方法名称',
    spell_time    bigint(20)                 default 0  null comment '方法耗时',
    params        text                       default null comment '方法传入参数',
    response_data text                       default null comment '方法返回值',
    status        int(1)                     default 0  null comment '操作状态（0正常 1异常）',
    error_msg     varchar(2000) charset utf8 default '' null comment '错误消息',
    oper_time     datetime                              null comment '操作时间'
) comment '操作日志记录' charset = utf8mb4