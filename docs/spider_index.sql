use spider;

alter table t_order add index t_order_index1 (is_delete, order_status, order_type, pay_time);

alter table t_order add index t_order_index2 (out_order_no);

alter table t_order_item add index t_order_item_index1 (order_id);

alter table t_original_order add index t_original_order_index1 (processed);

alter table t_original_order add index t_original_order_index2 (out_order_no);

alter table t_original_order_item add index t_original_order_item_index1 (order_id);

alter table t_business_log add index t_business_log_index1 (create_time,execution_time);