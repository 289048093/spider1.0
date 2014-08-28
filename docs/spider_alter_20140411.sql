use spider;

-- ----------------------------
-- alter Table add Column for 't_order_fetch'
-- ----------------------------
alter table t_order_fetch MODIFY shop_id varchar(30) not null;
alter table t_order modify column repo_id int(11) default null;
alter table t_order modify column repo_name varchar(50) default null;
