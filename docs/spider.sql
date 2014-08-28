/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2014-4-11 19:04:43                           */
/*==============================================================*/


drop table if exists t_brand;

drop table if exists t_business_log;

drop table if exists t_conf;

drop table if exists t_customer;

drop table if exists t_gift_brand;

drop table if exists t_gift_brand_item;

drop table if exists t_gift_prod;

drop table if exists t_invoice_printinfo;

drop table if exists t_logistics_info;

drop table if exists t_logistics_printinfo;

drop table if exists t_mealset;

drop table if exists t_mealset_item;

drop table if exists t_operate_log;

drop table if exists t_operation;

drop table if exists t_order;

drop table if exists t_order_fetch;

drop table if exists t_order_item;

drop table if exists t_original_order;

drop table if exists t_original_order_item;

drop table if exists t_permission;

drop table if exists t_prod_category;

drop table if exists t_prod_sales;

drop table if exists t_product;

drop table if exists t_repository;

drop table if exists t_resource;

drop table if exists t_role;

drop table if exists t_role_permission;

drop table if exists t_shop;

drop table if exists t_shop_auth;

drop table if exists t_sql_log;

drop table if exists t_storage;

drop table if exists t_user;

drop table if exists t_user_role;

/*==============================================================*/
/* Table: t_brand                                               */
/*==============================================================*/
create table t_brand
(
   id                   int(11) not null auto_increment,
   name                 varchar(50) not null comment '品牌名',
   description          text comment '品牌描述',
   create_time          datetime default '0000-00-00 00:00:00',
   update_time          datetime default '0000-00-00 00:00:00',
   delete_time          datetime default '0000-00-00 00:00:00',
   is_delete            tinyint(1) default 0,
   primary key (id)
);

/*==============================================================*/
/* Table: t_business_log                                        */
/*==============================================================*/
create table t_business_log
(
   id                   int(11) not null auto_increment,
   user_id              int(11) default NULL comment '操作用户ID',
   username             varchar(64) default NULL comment '操作用户名称',
   operation_id         int(11) default NULL comment '操作ID',
   operation_name       varchar(32) default NULL comment '操作名称',
   resource_name        varchar(32) default NULL comment '模块名称',
   ip                   varchar(32) default NULL comment '操作用户的IP',
   params               text comment '请求传入的参数',
   request_url          varchar(512) default NULL comment '请求的url',
   create_time          datetime default '0000-00-00 00:00:00',
   execution_time       int(11) default NULL comment '请求耗时',
   primary key (id)
);

/*==============================================================*/
/* Table: t_conf                                                */
/*==============================================================*/
create table t_conf
(
   id                   int(11) not null auto_increment,
   config_key           varchar(64) not null comment 'key',
   config_value         varchar(128) not null comment 'value',
   config_comment       varchar(512) default NULL comment '说明',
   create_time          datetime default '0000-00-00 00:00:00',
   update_time          datetime default '0000-00-00 00:00:00',
   delete_time          datetime default '0000-00-00 00:00:00',
   is_delete            tinyint(1) default 0,
   primary key (id),
   unique key config_key (config_key, is_delete)
);

/*==============================================================*/
/* Table: t_customer                                            */
/*==============================================================*/
create table t_customer
(
   id                   int(11) not null auto_increment,
   buyer_id             varchar(50) not null comment '用户账号(支付宝id 或 京东账号)',
   name                 varchar(20) not null comment '会员名',
   phone                varchar(20) default NULL comment '固定电话',
   mobile               varchar(20) default NULL comment '移动电话',
   address              varchar(256) default NULL comment '住址',
   integral             int(11) not null comment '积分数',
   primary key (id)
);

/*==============================================================*/
/* Table: t_gift_brand                                          */
/*==============================================================*/
create table t_gift_brand
(
   id                   int(11) not null auto_increment,
   brand_id             int(11) default NULL comment '品牌id',
   price_begin          bigint(20) default NULL comment '起始价格',
   price_end            bigint(20) default NULL comment '结束价格',
   in_use               tinyint(1) default 1 comment '是否启用(0是表示未启用, 1表示已启用)默认是已启用1',
   create_time          datetime default '0000-00-00 00:00:00',
   update_time          datetime default '0000-00-00 00:00:00',
   delete_time          datetime default '0000-00-00 00:00:00',
   is_delete            tinyint(1) default 0,
   primary key (id)
);

/*==============================================================*/
/* Table: t_gift_brand_item                                     */
/*==============================================================*/
create table t_gift_brand_item
(
   id                   int(11) not null auto_increment,
   gift_brand_id        int(11) not null comment '优惠活动-品牌id',
   gift_prod_id         int(11) not null comment '赠送商品id',
   gift_prod_count      int(11) comment '赠送数量',
   create_time          datetime default '0000-00-00 00:00:00',
   update_time          datetime default '0000-00-00 00:00:00',
   delete_time          datetime default '0000-00-00 00:00:00',
   is_delete            tinyint(4) default 0,
   primary key (id)
);

/*==============================================================*/
/* Table: t_gift_prod                                           */
/*==============================================================*/
create table t_gift_prod
(
   id                   int(11) not null auto_increment,
   sell_prod_id         int(11) default NULL comment '购买的商品id',
   gift_prod_id         int(11) not null comment '赠送的商品id',
   gift_prod_count      int(11) default NULL comment '赠送数量',
   in_use               tinyint(1) default 1 comment '是否启用(0是表示未启用, 1表示已启用)默认是已启用1',
   create_time          datetime default '0000-00-00 00:00:00',
   update_time          datetime default '0000-00-00 00:00:00',
   delete_time          datetime default '0000-00-00 00:00:00',
   is_delete            tinyint(1) default 0,
   primary key (id)
);

/*==============================================================*/
/* Table: t_invoice_printinfo                                   */
/*==============================================================*/
create table t_invoice_printinfo
(
   id                   int(10) unsigned not null auto_increment,
   print_html           text comment '发票信息打印的lodop代码',
   logistics_picture_path varchar(500) default NULL comment '发票图片路径',
   create_time          datetime default '0000-00-00 00:00:00',
   update_time          datetime default '0000-00-00 00:00:00',
   delete_time          datetime default '0000-00-00 00:00:00',
   is_delete            tinyint(1) default 0,
   primary key (id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='发票打印信息';

/*==============================================================*/
/* Table: t_logistics_info                                      */
/*==============================================================*/
create table t_logistics_info
(
   id                   int(11) not null auto_increment,
   order_no             varchar(64) not null comment '订单号(一个订单在业务上只允许有一个快递单号)',
   express_no           varchar(64) not null comment '物流单号(每一笔物流单号的数据都会向 kuaidi100 发送请求. 请求一次就需要付费! 所以, 请务必保证只在线上可以请求及只发送一次)',
   express_company      varchar(255) not null comment '物流公司名(shunfeng, yunda, ems, tiantian等)',
   send_to              varchar(32) default NULL comment '收货地址(到市一级即可. 如广东省深圳市, 北京市)',
   express_info         text comment '物流信息(由快递 100 提供)',
   express_status       tinyint(1) default 0 comment '物流状态(1表示配送完成, 0表示未完成).',
   first_time           datetime default '0000-00-00 00:00:00' comment '第一条物流状态时间',
   latest_time          datetime default '0000-00-00 00:00:00' comment '物流状态最新的时间',
   was_request          tinyint(1) default 0 comment '是否已请求第三方物流(1已请求, 0未请求), 默认是 0',
   create_time          datetime default '0000-00-00 00:00:00',
   update_time          datetime default '0000-00-00 00:00:00',
   primary key (id),
   unique key express_no (express_no)
);

/*==============================================================*/
/* Table: t_logistics_printinfo                                 */
/*==============================================================*/
create table t_logistics_printinfo
(
   id                   int(10) unsigned not null auto_increment,
   name                 varchar(150) not null comment '物流名称',
   law                  int(11) not null comment '物流单号递增规律',
   print_html           text comment '物流信息打印的lodop代码',
   logistics_picture_path varchar(500) default NULL comment '物流图片路径',
   create_time          datetime default '0000-00-00 00:00:00',
   update_time          datetime default '0000-00-00 00:00:00',
   delete_time          datetime default '0000-00-00 00:00:00',
   is_delete            tinyint(1) default 0,
   primary key (id)
);

/*==============================================================*/
/* Table: t_mealset                                             */
/*==============================================================*/
create table t_mealset
(
   id                   int(11) not null auto_increment,
   name                 varchar(20) not null comment '套餐名',
   code                 varchar(20) not null comment '套餐条形码',
   sell_description     varchar(512) default NULL comment '卖点描述',
   create_time          datetime default '0000-00-00 00:00:00',
   update_time          datetime default '0000-00-00 00:00:00',
   delete_time          datetime default '0000-00-00 00:00:00',
   is_delete            tinyint(1) default 0,
   primary key (id)
);

/*==============================================================*/
/* Table: t_mealset_item                                        */
/*==============================================================*/
create table t_mealset_item
(
   id                   int(11) not null auto_increment,
   meal_id              int(20) not null comment '套餐id',
   prod_id              int(11) not null comment '商品id',
   meal_price           bigint(20) not null comment '套餐价',
   meal_count           int(20) not null comment '套餐中的数量',
   create_time          datetime default '0000-00-00 00:00:00',
   update_time          datetime default '0000-00-00 00:00:00',
   delete_time          datetime default '0000-00-00 00:00:00',
   is_delete            tinyint(1) default 0,
   primary key (id)
);

/*==============================================================*/
/* Table: t_operate_log                                         */
/*==============================================================*/
create table t_operate_log
(
   id                   int(11) not null auto_increment,
   log_type             varchar(255) not null comment '日志类型(商品, 套餐, 订单, 仓库, 分类, 品牌, 库存... 等)',
   type_id              int(11) not null comment '类型id(商品id, 套餐id, 订单id, 仓库id, 分类id, 品牌id, 库存id... 等)',
   operate_id           int(11) not null comment '操作人id',
   operate_name         varchar(32) not null comment '操作人名字',
   description          varchar(256) default NULL comment '具体做了什么(修改了库存, 删除了订单里面的项等等)',
   primary key (id)
);

/*==============================================================*/
/* Table: t_operation                                           */
/*==============================================================*/
create table t_operation
(
   id                   int(11) not null auto_increment,
   resource_id          int(11) default NULL comment '资源id',
   name                 varchar(20) not null comment '操作名',
   url                  varchar(512) not null comment '操作的 url',
   configable           tinyint(1) not null default 0 comment '页面是否可配置(0不可配, 1可配), 默认是 0',
   required             varchar(256) default NULL comment '此操作前置的操作名称,多个以 逗号 分隔',
   primary key (id),
   unique key name (name)
);

/*==============================================================*/
/* Table: t_order                                               */
/*==============================================================*/
create table t_order
(
   id                   int(11) not null auto_increment,
   order_no             varchar(20) not null comment '订单编号',
   order_type           varchar(15) not null comment '订单类型',
   order_status         varchar(20) not null comment '订单状态',
   total_fee            bigint(20) not null comment '商品金额（商品价格乘以数量的总金额',
   discount_fee         bigint(20) comment '系统优惠金额',
   point_fee            bigint(20) comment '买家使用积分,下单时生成，且一直不变',
   has_post_fee         tinyint(1) comment '是否包含邮费。与available_confirm_fee同时使用',
   available_confirm_fee bigint(20) comment '交易中剩余的确认收货金额（这个金额会随着子订单确认收货而不断减少，交易成功后会变为零）',
   real_point_fee       bigint(20) comment '买家实际使用积分（扣除部分退款使用的积分），交易完成后生成（交易成功或关闭），交易未完成时该字段值为0',
   payment              bigint(20) comment '实付金额',
   post_fee             bigint(20) comment '邮费',
   received_payment     bigint(20) comment '卖家实际收到的支付宝打款金额（由于子订单可以部分确认收货，这个金额会随着子订单的确认收货而不断增加，交易成功后等于买家实付款减去退款金额',
   adjust_fee           bigint(20) comment '手工调整金额',
   buyer_id             varchar(30) not null comment '买家Id(淘宝号)',
   buyer_message        text comment '买家留言',
   remark               text comment '客服备注',
   receiver_name        varchar(128) not null comment '收货人姓名',
   receiver_phone       varchar(128) default NULL comment '收货人手机号(与收货人电话在业务上必须存在一个)',
   receiver_mobile      varchar(128) default NULL comment '收货人电话(与收货人手机号在业务上必须存在一个)',
   receiver_zip         varchar(128) default NULL comment '收货人邮编',
   receiver_state       varchar(64) not null comment '收货人省份',
   receiver_city        varchar(64) not null comment '收货人城市',
   receiver_district    varchar(64) default NULL comment '收货人地区',
   receiver_address     varchar(256) not null comment '不包含省市区的详细地址',
   shipping_no          varchar(128) default NULL comment '物流编号',
   shipping_comp        varchar(64) default NULL comment '物流公司',
   repo_id              int(11) comment '库房id',
   repo_name            varchar(50) comment '库房名',
   buy_time             datetime default '0000-00-00 00:00:00' comment '下单时间',
   pay_time             datetime default '0000-00-00 00:00:00' comment '支付时间',
   confirm_time         datetime default '0000-00-00 00:00:00' comment '确认时间',
   confirm_user_id      int(11) default NULL comment '确认人id',
   confirm_user         varchar(20) default NULL comment '确认人名字',
   print_time           datetime default '0000-00-00 00:00:00' comment '打印时间',
   print_user_id        int(11) default NULL comment '打印人id',
   print_user           varchar(20) default NULL comment '打印人名字',
   inspection_time      datetime default NULL comment '验货时间',
   inspection_user_id   int(11) default NULL comment '验货人ID',
   delivery_time        datetime default NULL comment '发货时间',
   delivery_user_id     int(11) default NULL comment '发货人ID',
   receipt_time         datetime default NULL comment '签收时间',
   out_platform_type    varchar(10) not null comment '外部平台类型(天猫还是京东)',
   out_order_no         varchar(100) not null comment '外部订单号',
   shop_id              int(11) not null comment '店铺id',
   shop_name            varchar(50) not null comment '店铺名',
   need_invoice         tinyint(1) default NULL comment '是否需要发票',
   invoice_name         varchar(100) default NULL comment '发票抬头',
   invoice_content      varchar(100) default NULL comment '发票内容',
   create_time          datetime default '0000-00-00 00:00:00' comment '创建时间',
   update_time          datetime default '0000-00-00 00:00:00' comment '更新时间',
   is_delete            tinyint(1) default 0 comment '是否删除(1 表示已删除, 0 表示未删除), 默认是 0',
   original_order_id    int(11) default NULL comment '原始订单ID',
   split_status         varchar(20),
   primary key (id),
   unique key order_no (order_no)
);

/*==============================================================*/
/* Table: t_order_fetch                                         */
/*==============================================================*/
create table t_order_fetch
(
   id                   int(11) not null auto_increment,
   fetch_time           datetime not null comment '抓取时间',
   out_platform         varchar(20) not null comment '抓取平台',
   shop_id              varchar(32) not null comment '店铺id',
   create_time          datetime default '0000-00-00 00:00:00' comment '创建时间',
   update_time          datetime default '0000-00-00 00:00:00' comment '更新时间',
   is_delete            tinyint(1) default 0 comment '是否删除(1 表示已删除, 0 表示未删除), 默认是 0',
   primary key (id)
);

/*==============================================================*/
/* Table: t_order_item                                          */
/*==============================================================*/
create table t_order_item
(
   id                   int(11) not null auto_increment,
   order_no             varchar(20) not null,
   prod_id              int(11) not null comment '商品id',
   prod_code            varchar(32) not null comment '商品编码',
   sku_code             varchar(32) not null comment '商品条形码',
   prod_name            varchar(32) not null comment '商品名称',
   prod_price           bigint(20) not null comment '商品单价',
   post_fee             bigint(20) comment '邮费',
   prod_count           int(11) not null comment '购买数量',
   total_fee            bigint(20) not null comment '应付金额（商品价格 * 商品数量 + 手工调整金额 - 子订单级订单优惠金额）',
   actual_fee           bigint(20) not null comment '子订单实付金额。计算公式如下：payment = price * num + adjust_fee - discount_fee',
   discount_fee         bigint(20) comment '子订单级订单优惠金额',
   adjust_fee           bigint(20) comment '手工调整金额',
   divide_order_fee     bigint(20) comment '分摊之后的实付金额',
   part_mjz_discount    bigint(20) comment '优惠分摊',
   out_order_no         varchar(20) not null comment '外部平台的订单编号',
   item_type            varchar(10) not null comment '订单项类型(商品, 套餐, 赠品...)',
   price_description    varchar(256) default NULL comment '订单价格描述',
   create_time          datetime default '0000-00-00 00:00:00' comment '创建时间',
   update_time          datetime default '0000-00-00 00:00:00' comment '更新时间',
   is_delete            tinyint(1) default 0 comment '是否删除(1 表示已删除, 0 表示未删除), 默认是 0',
   order_id             int(11) not null comment '订单ID',
   sub_out_order_no     varchar(32) comment '外部平台子订单ID',
   primary key (id)
);

/*==============================================================*/
/* Table: t_original_order                                      */
/*==============================================================*/
create table t_original_order
(
   id                   int(11) not null auto_increment,
   status               varchar(50) not null comment '订单状态',
   total_fee            bigint(20) not null comment '商品金额（商品价格乘以数量的总金额）',
   discount_fee         bigint(20) comment '系统优惠金额（如打折，VIP，满就送等）',
   point_fee            bigint(20) comment '买家使用积分,下单时生成，且一直不变',
   has_post_fee         tinyint(1) comment '是否包含邮费。与available_confirm_fee同时使用',
   available_confirm_fee bigint(20) comment '交易中剩余的确认收货金额（这个金额会随着子订单确认收货而不断减少，交易成功后会变为零）',
   real_point_fee       bigint(20) comment '买家实际使用积分（扣除部分退款使用的积分），交易完成后生成（交易成功或关闭），交易未完成时该字段值为0',
   payment              bigint(20) comment '实付金额',
   post_fee             bigint(20) comment '邮费',
   received_payment     bigint(20) comment '卖家实际收到的支付宝打款金额（由于子订单可以部分确认收货，这个金额会随着子订单的确认收货而不断增加，交易成功后等于买家实付款减去退款金额）',
   adjust_fee           bigint(20) comment '手工调整金额',
   buyer_message        text comment '买家留言',
   remark               text comment '客服备注',
   buyer_id             varchar(30) not null comment '买家Id(淘宝号)',
   receiver_name        varchar(128) not null comment '收货人姓名',
   receiver_phone       varchar(128) default NULL comment '收货人手机号(与收货人电话在业务上必须存在一个)',
   receiver_mobile      varchar(128) default NULL comment '收货人电话(与收货人手机号在业务上必须存在一个)',
   receiver_zip         varchar(128) default NULL comment '收货人邮编',
   receiver_state       varchar(64) not null comment '收货人省份',
   receiver_city        varchar(64) not null comment '收货人城市',
   receiver_district    varchar(64) default NULL comment '收货人地区',
   receiver_address     varchar(256) not null comment '不包含省市区的详细地址',
   buy_time             datetime default '0000-00-00 00:00:00' comment '下单时间',
   pay_time             datetime default '0000-00-00 00:00:00' comment '支付时间',
   out_platform_type    varchar(10) not null comment '外部平台类型(天猫还是京东)',
   out_order_no         varchar(100) not null comment '外部订单号',
   shop_id              bigint(11) not null comment '店铺id',
   shop_name            varchar(50) not null comment '店铺名',
   need_invoice         tinyint(1) default NULL comment '是否需要发票',
   invoice_name         varchar(100) default NULL comment '发票抬头',
   invoice_content      varchar(100) default NULL comment '发票内容',
   create_time          datetime default '0000-00-00 00:00:00',
   update_time          datetime default '0000-00-00 00:00:00',
   is_delete            tinyint(1) default 0 comment '是否删除(1 表示已删除, 0 表示未删除), 默认是 0',
   processed            tinyint(1) default NULL comment '是否已经被订单处理程序处理过',
   primary key (id)
);

/*==============================================================*/
/* Table: t_original_order_item                                 */
/*==============================================================*/
create table t_original_order_item
(
   id                   int(11) not null auto_increment,
   order_no             varchar(20) default NULL comment '原始订单编号',
   sku_code             varchar(20) default NULL comment '商品条形码',
   price                bigint(20) default NULL comment '商品单价(宝贝的吊牌价/一口价)',
   buy_count            bigint(11) default NULL comment '购买数量',
   total_fee            bigint(20) default NULL comment '应付金额（商品价格 * 商品数量 + 手工调整金额 - 子订单级订单优惠金额）',
   actual_fee           bigint(20) default NULL comment '子订单实付金额',
   discount_fee         bigint(20) comment '子订单级订单优惠金额',
   adjust_fee           bigint(20) comment '手工调整金额',
   divide_order_fee     bigint(20) comment '分摊之后的实付金额',
   part_mjz_discount    bigint(20) comment '优惠分摊',
   create_time          datetime default '0000-00-00 00:00:00' comment '创建时间',
   update_time          datetime default '0000-00-00 00:00:00' comment '更新时间',
   is_delete            tinyint(1) default 0 comment '是否删除(1 表示已删除, 0 表示未删除), 默认是 0',
   order_id             int(11) not null comment '原始订单ID',
   sub_order_no         varchar(32) comment '原始订单子编号',
   primary key (id)
);

/*==============================================================*/
/* Table: t_permission                                          */
/*==============================================================*/
create table t_permission
(
   id                   int(11) not null auto_increment,
   operation_id         int(20) not null comment '操作id',
   primary key (id)
);

/*==============================================================*/
/* Table: t_prod_category                                       */
/*==============================================================*/
create table t_prod_category
(
   id                   int(11) not null auto_increment,
   name                 varchar(50) not null comment '分类名',
   create_time          datetime default '0000-00-00 00:00:00',
   update_time          datetime default '0000-00-00 00:00:00',
   delete_time          datetime default '0000-00-00 00:00:00',
   is_delete            tinyint(1) default 0,
   primary key (id)
);

/*==============================================================*/
/* Table: t_prod_sales                                          */
/*==============================================================*/
create table t_prod_sales
(
   id                   int(11) not null auto_increment,
   prod_id              int(11) not null comment '商品id',
   sale_count           int(11) default 0 comment '销售数量',
   send_count           int(11) default 0 comment '发货数量',
   back_count           int(11) default 0 comment '退货数量',
   create_time          datetime default '0000-00-00 00:00:00',
   update_time          datetime default '0000-00-00 00:00:00',
   delete_time          datetime default '0000-00-00 00:00:00',
   is_delete            tinyint(1) default 0,
   primary key (id)
);

/*==============================================================*/
/* Table: t_product                                             */
/*==============================================================*/
create table t_product
(
   id                   int(11) not null auto_increment,
   brand_id             int(11) not null comment '品牌id',
   prod_name            varchar(128) not null comment '商品名',
   prod_no              varchar(32) not null comment '商品编码',
   prod_code            varchar(32) not null comment '商品条形码',
   cid                  int(11) not null comment '商品分类id',
   description          text comment '产品描述',
   pic_url              varchar(512) not null comment '图片地址',
   buy_price            bigint(20) default NULL comment '进货价',
   shop_price           bigint(20) not null comment '销售价',
   standard_price       bigint(20) not null comment '市场价',
   color                varchar(10) default NULL comment '颜色',
   weight               varchar(20) default NULL comment '重量',
   box_size             varchar(20) default NULL comment '包装尺寸',
   speci                text comment '规格',
   type                 varchar(10) default 'prod' comment '产品类型：prod-商品,gift-赠品',
   create_time          datetime default '0000-00-00 00:00:00',
   update_time          datetime default '0000-00-00 00:00:00',
   delete_time          datetime default '0000-00-00 00:00:00',
   is_delete            tinyint(1) default 0,
   primary key (id)
);

/*==============================================================*/
/* Table: t_repository                                          */
/*==============================================================*/
create table t_repository
(
   id                   int(11) not null auto_increment,
   name                 varchar(50) default NULL comment '仓库名',
   repo_code            varchar(16) not null comment '仓库编码',
   address              varchar(200) not null comment '仓库地址',
   charge_person        varchar(20) not null comment '仓库负责人',
   charge_person_id     int(11) default NULL comment '责任人id',
   shipping_comp        varchar(20) default NULL comment '物流公司',
   charge_mobile        varchar(20) default NULL comment '责任人手机号',
   charge_phone         varchar(20) default NULL comment '负责人电话',
   create_time          datetime default '0000-00-00 00:00:00',
   update_time          datetime default '0000-00-00 00:00:00',
   delete_time          datetime default '0000-00-00 00:00:00',
   is_delete            tinyint(1) default 0,
   primary key (id)
);

/*==============================================================*/
/* Table: t_resource                                            */
/*==============================================================*/
create table t_resource
(
   id                   int(11) not null auto_increment,
   name                 varchar(20) not null comment '资源名',
   url                  varchar(512) not null comment '资源url',
   unique_key           varchar(50) not null comment '模块标识',
   icon_cls             varchar(32) default NULL,
   module               varchar(32) default NULL,
   entry_operation      varchar(32) not null,
   primary key (id)
);

/*==============================================================*/
/* Table: t_role                                                */
/*==============================================================*/
create table t_role
(
   id                   int(11) not null auto_increment,
   name                 varchar(20) not null comment '角色名',
   primary key (id)
);

/*==============================================================*/
/* Table: t_role_permission                                     */
/*==============================================================*/
create table t_role_permission
(
   id                   int(11) not null auto_increment,
   role_id              int(11) not null comment '角色id',
   permission_id        int(11) not null comment '权限id',
   primary key (id)
);

/*==============================================================*/
/* Table: t_shop                                                */
/*==============================================================*/
create table t_shop
(
   id                   int(11) not null auto_increment comment '主键',
   shop_id              varchar(20) not null comment '外部平台店铺id',
   cat_id               varchar(20) default NULL comment '店铺所属类目id',
   nick                 varchar(100) not null comment '卖家昵称',
   title                varchar(100) default NULL comment '店铺标题',
   description          text comment '店铺描述',
   bulletin             text comment '店铺公告',
   pic_path             varchar(200) default NULL comment '店标地址',
   item_score           varchar(10) default NULL comment '商品描述评分',
   service_score        varchar(10) default NULL comment '服务态度评分',
   delivery_score       varchar(10) default NULL comment '发货速度评分',
   de_express           varchar(50) default NULL comment '默认快递',
   enable_msg           tinyint(1) default NULL comment '是否启用发货短信，0：禁用；1：启用',
   msg_temp             text comment '发货短信模板',
   msg_sign             varchar(200) default NULL comment '短信签名',
   out_platform_type    varchar(10) default NULL comment '店铺来自哪个平台（如天猫，京东）',
   create_time          date default NULL comment '开店时间',
   update_time          date default NULL comment '最后修改时间',
   is_delete            tinyint(1) default 0 comment '是否删除标识(0：未删除；1：删除）',
   primary key (id)
);

/*==============================================================*/
/* Table: t_shop_auth                                           */
/*==============================================================*/
create table t_shop_auth
(
   id                   int(11) not null auto_increment comment '表主键',
   shop_id              varchar(20) default NULL comment '外部平台店铺id',
   session_key          varchar(100) not null comment '店铺对应的session key(即Access Token)',
   token_type           varchar(20) default NULL comment 'Access token的类型目前只支持bearer',
   expires_in           bigint(20) default NULL comment 'Access token过期时间',
   refresh_token        varchar(100) not null comment 'Refresh token',
   re_expires_in        bigint(20) default NULL comment 'Refresh token过期时间',
   r1_expires_in        bigint(20) default NULL comment 'r1级别API或字段的访问过期时间',
   r2_expires_in        bigint(20) default NULL comment 'r2级别API或字段的访问过期时间',
   w1_expires_in        bigint(20) default NULL comment 'w1级别API或字段的访问过期时间',
   w2_expires_in        bigint(20) default NULL comment 'w2级别API或字段的访问过期时间',
   user_nick            varchar(100) default NULL comment '外部平台账号昵称',
   user_id              varchar(100) not null comment '外部平台帐号对应id',
   out_platform_type    varchar(10) default NULL comment '当前授权用户来自哪个平台（如天猫，京东）',
   create_time          date default NULL comment 'Session key第一次授权时间',
   update_time          date default NULL comment 'Session key最后修改时间',
   is_delete            tinyint(1) default 0 comment '是否删除标识(0：未删除；1：删除）',
   primary key (id)
);

/*==============================================================*/
/* Table: t_sql_log                                             */
/*==============================================================*/
create table t_sql_log
(
   id                   int(11) not null auto_increment,
   business_log_id      int(11) default NULL comment '业务日志ID',
   content              text,
   sql_mapper           varchar(256) default NULL comment 'sql mapper ID',
   operation_type       varchar(20) default NULL comment '操作类型',
   create_time          datetime default '0000-00-00 00:00:00',
   execution_time       int(11) default NULL comment '请求耗时',
   primary key (id)
);

/*==============================================================*/
/* Table: t_storage                                             */
/*==============================================================*/
create table t_storage
(
   id                   int(11) not null auto_increment,
   prod_id              int(11) not null comment '商品id',
   repo_id              int(11) default NULL comment '仓库id',
   actually_number      int(11) not null comment '实际库存',
   modify_time          datetime default '0000-00-00 00:00:00' comment '库存最后更新时间',
   primary key (id)
);

/*==============================================================*/
/* Table: t_user                                                */
/*==============================================================*/
create table t_user
(
   id                   int(11) not null auto_increment,
   username             varchar(64) not null,
   password             varchar(256) not null,
   salt                 varchar(256) not null comment '加密用到的盐',
   root_user            tinyint(1) not null default 0 comment '是否是 root 用户(0 表示不是, 1 表示是), 默认是 0',
   role_id              int(11) not null comment '角色id',
   create_time          datetime default '0000-00-00 00:00:00',
   update_time          datetime default '0000-00-00 00:00:00',
   delete_time          datetime default '0000-00-00 00:00:00',
   is_delete            tinyint(1) default 0,
   email                varchar(256) default NULL,
   telephone            varchar(20) default NULL,
   repo_id              int(11) default NULL,
   real_name            varchar(64) default NULL,
   primary key (id)
);

/*==============================================================*/
/* Table: t_user_role                                           */
/*==============================================================*/
create table t_user_role
(
   id                   int(11) not null auto_increment,
   user_id              int(11) not null comment '用户id',
   role_id              int(11) not null comment '角色id',
   primary key (id)
);

