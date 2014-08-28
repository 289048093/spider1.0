use spider;

-- ----------------------------
-- alter Table add Column for 't_original_order'
-- ----------------------------
alter table t_original_order add discount_fee bigint(20) comment '系统优惠金额（如打折，VIP，满就送等）' after total_fee;
alter table t_original_order add point_fee bigint(20) comment '买家使用积分,下单时生成，且一直不变' after discount_fee;
alter table t_original_order add has_post_fee tinyint(1) comment '是否包含邮费。与available_confirm_fee同时使用' after point_fee;
alter table t_original_order add available_confirm_fee bigint(20) comment '交易中剩余的确认收货金额（这个金额会随着子订单确认收货而不断减少，交易成功后会变为零）' after has_post_fee;
alter table t_original_order add real_point_fee bigint(20) comment '买家实际使用积分（扣除部分退款使用的积分），交易完成后生成（交易成功或关闭），交易未完成时该字段值为0' after available_confirm_fee;
alter table t_original_order add payment bigint(20) comment '实付金额' after real_point_fee;
alter table t_original_order add post_fee bigint(20) comment '邮费' after payment;
alter table t_original_order add received_payment bigint(20) comment '卖家实际收到的支付宝打款金额（由于子订单可以部分确认收货，这个金额会随着子订单的确认收货而不断增加，交易成功后等于买家实付款减去退款金额）' after post_fee;
alter table t_original_order add adjust_fee bigint(20) comment '手工调整金额' after received_payment;

-- ----------------------------
-- alter Table add Column for 't_original_order_item'
-- ----------------------------
alter table t_original_order_item add discount_fee bigint(20) comment '子订单级订单优惠金额' after actual_fee;
alter table t_original_order_item add adjust_fee bigint(20) comment '手工调整金额' after discount_fee;
alter table t_original_order_item add divide_order_fee bigint(20) comment '分摊之后的实付金额' after adjust_fee;
alter table t_original_order_item add part_mjz_discount bigint(20) comment '优惠分摊' after divide_order_fee;

-- ----------------------------
-- alter Table add Column for 't_order'
-- ----------------------------
alter table t_order add discount_fee bigint(20) comment '系统优惠金额（如打折，VIP，满就送等）' after total_fee;
alter table t_order add point_fee bigint(20) comment '买家使用积分,下单时生成，且一直不变' after discount_fee;
alter table t_order add has_post_fee tinyint(1) comment '是否包含邮费。与available_confirm_fee同时使用' after point_fee;
alter table t_order add available_confirm_fee bigint(20) comment '交易中剩余的确认收货金额（这个金额会随着子订单确认收货而不断减少，交易成功后会变为零）' after has_post_fee;
alter table t_order add real_point_fee bigint(20) comment '买家实际使用积分（扣除部分退款使用的积分），交易完成后生成（交易成功或关闭），交易未完成时该字段值为0' after available_confirm_fee;
alter table t_order add payment bigint(20) comment '实付金额' after real_point_fee;
alter table t_order add post_fee bigint(20) comment '邮费' after payment;
alter table t_order add received_payment bigint(20) comment '卖家实际收到的支付宝打款金额（由于子订单可以部分确认收货，这个金额会随着子订单的确认收货而不断增加，交易成功后等于买家实付款减去退款金额）' after post_fee;
alter table t_order add adjust_fee bigint(20) comment '手工调整金额' after received_payment;
alter table t_order add split_status varchar(20) comment '拆分类型';

-- ----------------------------
-- alter Table add Column for 't_order_item'
-- ----------------------------
alter table t_order_item add discount_fee bigint(20) comment '子订单级订单优惠金额' after actual_fee;
alter table t_order_item add adjust_fee bigint(20) comment '手工调整金额' after discount_fee;
alter table t_order_item add divide_order_fee bigint(20) comment '分摊之后的实付金额' after adjust_fee;
alter table t_order_item add part_mjz_discount bigint(20) comment '优惠分摊' after divide_order_fee;
alter table t_order_item add post_fee bigint(20) comment '邮费' after part_mjz_discount;
