use spider;

insert into t_conf values(null, 'postage_product_sku', '', '邮费虚拟商品的sku', now(), now(), null, 0);

insert into t_conf values(null, 'order_fetch_interval', '60', '抓取订单的最小时间间隔,单位秒', now(), now(), null, 0);

insert into t_conf values(null, 'order_analyze_interval', '60', '分析订单的最小时间间隔,单位秒', now(), now(), null, 0);

insert into t_conf values(null, 'next_order_no', '111111', '', now(), now(), null, 0);

insert into t_conf values(null, 'next_meal_set_no', '111111', '', now(), now(), null, 0);

insert into t_conf(config_key, config_value, config_comment, create_time)
values('no_send_goods_trigger_warn_time', '16:00', '在指定时间已付款却还未发货的订单预警(精确到分, 请使用 24 小时制并使用英文状态下的冒号, 且不要使用 24 点), 若不设置默认是 16:00', now());

insert into t_conf(config_key, config_value, config_comment, create_time)
values('latest_time_distance_hour', '24', '已发货未签收的包裹最新的状态距离现在多长时间未更新, 单位: 小时. 若不设置默认是 24', now());

insert into t_conf(config_key, config_value, config_comment, create_time)
values('gd_address', '广东', '提醒广东省内的订单跟踪物流的地址(请设置得越简洁越好并保持在两个字左右, 可填广东省的, 只写广东即可), 若不设置默认是 广东', now());
insert into t_conf(config_key, config_value, config_comment, create_time)
values('gd_distance_hour', '48', '提醒广东省内的订单有物流信息后未签收的时间间隔, 单位: 小时. 若不设置默认是 48', now());

insert into t_conf(config_key, config_value, config_comment, create_time)
values('jzh_address', '江苏/浙江/上海', '提醒江浙沪跟踪物流的地址(请设置得越简洁越好并保持在两个字左右, 如上海市, 只写上海即可), / 隔开, 若不设置默认是 江苏/浙江/上海/', now());
insert into t_conf(config_key, config_value, config_comment, create_time)
values('jzh_distance_hour', '72', '提醒江浙沪地区的订单有物流信息后未签收的时间间隔, 单位: 小时. 若不设置默认是 72', now());

insert into t_conf(config_key, config_value, config_comment, create_time)
values('slow_address', '广西/福建/海南/江西/安徽/湖北/湖南/河南/北京/天津/河北/山东/山西/重庆',
       '提醒稍慢地区跟踪物流的地址(请设置得越简洁越好并保持在两个字左右, 如广西省, 只写广西即可), / 隔开, 若不设置默认是 广西/福建/海南/江西/安徽/湖北/湖南/河南/北京/天津/河北/山东/山西/重庆', now());
insert into t_conf(config_key, config_value, config_comment, create_time)
values('slow_distance_hour', '96', '提醒稍慢地区的订单有物流信息后未签收的时间间隔, 单位: 小时. 若不设置默认是 96', now());

insert into t_conf(config_key, config_value, config_comment, create_time)
values('py_address', '内蒙/辽宁/吉林/黑龙江/云南/贵州/四川/陕西/甘肃/青海/宁夏/西藏',
       '提醒偏远地区跟踪物流的地址(请设置得越简洁越好并保持在两个字左右, 如内蒙古自治区, 只写内蒙即可), / 隔开, 若不设置默认是 内蒙/辽宁/吉林/黑龙江/云南/贵州/四川/陕西/甘肃/青海/宁夏/西藏', now());
insert into t_conf(config_key, config_value, config_comment, create_time)
values('py_distance_hour', '144', '提醒偏远地区的订单有物流信息后未签收的时间间隔, 单位: 小时. 若不设置默认是 144', now());
