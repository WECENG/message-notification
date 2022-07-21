DROP TABLE IF EXISTS `message_log`;
CREATE TABLE `message_log`
(
    id          VARCHAR(32)   NOT NULL  COMMENT '主键',
    message_id  VARCHAR(32)   NOT NULL COMMENT '消息唯一码',
    broadcast   INT           NOT NULL COMMENT '广播消息 0：单播，1：广播',
    topic       VARCHAR(500) COMMENT '消息主题',
    payload     VARCHAR(4000) NOT NULL COMMENT '消息内容',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET=utf8 COMMENT = '消息历史记录表';
ALTER TABLE `message_log` ADD UNIQUE unique_idx_message_id(message_id);
ALTER TABLE `message_log` COMMENT '消息历史记录表';

DROP TABLE IF EXISTS `message_log_read`;
CREATE TABLE `message_log_read`
(
    id          VARCHAR(32) NOT NULL COMMENT '主键',
    message_id  VARCHAR(32) NOT NULL COMMENT '消息唯一码',
    user_id     VARCHAR(32) NOT NULL COMMENT '用户id',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET=utf8 COMMENT = '消息阅览历史记录';
ALTER TABLE `message_log_read` ADD UNIQUE unique_idx_user_id_message_id(message_id,user_id);
ALTER TABLE `message_log_read` COMMENT '消息阅览历史记录';

DROP TABLE IF EXISTS `message_module_tag`;
CREATE TABLE `message_module_tag` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `module_tag` varchar(32) NOT NULL COMMENT '模块类型',
  `description` varchar(32) NOT NULL COMMENT '描述',
  `creator_id` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` varchar(32) DEFAULT NULL COMMENT '最近一次修改人',
  `update_time` datetime DEFAULT NULL COMMENT '最后一次修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `unique_message_module_tag` (`module_tag`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='消息订阅模块类型表';

DROP TABLE IF EXISTS `message_subscribe_rule`;
CREATE TABLE `message_subscribe_rule` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `module_tag` varchar(64) NOT NULL COMMENT '模块类型',
  `rule` varchar(4000) NOT NULL COMMENT '规则',
  `strong_match` varchar(16) DEFAULT NULL COMMENT '是否强匹，1是，0否',
  `creator_id` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` varchar(32) DEFAULT NULL COMMENT '最近一次修改人',
  `update_time` datetime DEFAULT NULL COMMENT '最后一次修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='消息订阅规则';

DROP TABLE IF EXISTS `message_subscribe_rule_user`;
CREATE TABLE `message_subscribe_rule_user` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `rule_id` varchar(32) NOT NULL COMMENT '模块类型',
  `user_id` varchar(32) NOT NULL COMMENT '规则',
  `creator_id` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` varchar(32) DEFAULT NULL COMMENT '最近一次修改人',
  `update_time` datetime DEFAULT NULL COMMENT '最后一次修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='消息订阅规则用户配置表';
