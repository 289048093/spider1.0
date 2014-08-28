/*
 * Created by king on 13-12-17
 */

Ext.define('Supplier.view.Search', {
    extend: 'Ext.form.Panel',
    id: 'search',
    alias: 'widget.orderSearch',
    region: 'north',
    border: 0,
    //bodyPadding: 10,
    layout: {
        type: 'hbox',
        align: 'left'
    },
    height: 'auto',
    bodyPadding: 10,
    defaultType: 'fieldcontainer',
    defaults: {
        margin: '0 10 0 0',
        defaults: {
            xtype: 'combo',
            labelWidth: 40,
            width: 100,
            queryMode: 'local',
            triggerAction: 'all',
            forceSelection: true,
            editable: false
        }
    },
    initComponent: function () {
        this.items = [
            {
                xtype: 'combo',
                width: 160,
                labelWidth: 60,
                fieldLabel: '物流公司',
                name: 'shippingComp',
                emptyText: '请选择',
                store: Espide.Common.expressStore(true)
            },
            {
                items: [
                    {
                        name: 'dateType',
                        labelWidth: 80,
                        width: 180,
                        fieldLabel: '日期类型',
                        itemId: 'dateType',
                        value: 'all',
                        store: [
                            ['all', '全部'],
                            ['payDate', '付款日期'],
                            ['printDate', '打印日期'],
                            ['deliveryDate', '发货日期'],
                            ['orderDate', '下单日期'],
                            ['receiptDate', '签收日期']
                        ]
                    }
                ]
            },
            {
                items: [
                    Ext.create('Go.form.field.DateTime', {
                        fieldLabel: '开始日期',
                        value: new Date(new Date().getTime() - 60 * 60 * 24 * 1000 * 7),
                        name: 'startDate',
                        itemId: 'startDate',
                        format: 'Y-m-d H:i:s',
                        margin: '0 0 5 0',
                        labelWidth: 60,
                        width: 220
                    }),
                    Ext.create('Go.form.field.DateTime', {
                        fieldLabel: '结束日期',
                        //value: new Date(),
                        name: 'endDate',
                        itemId: 'endDate',
                        format: 'Y-m-d H:i:s',
                        margin: '0 0 5 0',
                        labelWidth: 60,
                        width: 220
                    })
                ]
            },
            {
                items: [

                    {
                        xtype: 'fieldcontainer',
                        layout: 'hbox',
                        combineErrors: true,
                        msgTarget: 'side',
                        fieldLabel: '条件1',
                        labelWidth: 40,
                        width: 300,
                        defaults: {
                            xtype: 'combo',
                            queryMode: 'local',
                            triggerAction: 'all',
                            forceSelection: true,
                            editable: false,
                            hideLabel: true,
                            margin: '0 5 0 0'
                        },
                        items: [
                            {
                                width: 75,
                                name: 'conditionQuery',
                                itemId: 'querySelect',
                                value: 'buyerId',
                                valueField: 'id',
                                displayField: 'value',
                                store: Ext.create('Ext.data.Store', {
                                    fields: ['id', 'value', 'type'],
                                    data: [
                                        {id: 'buyerId', value: '买家id', type: 'string'},
                                        {id: 'receiverName', value: '收货人', type: 'string'},
                                        {id: 'buyerMessage', value: '买家留言', type: 'string'},
                                        {id: 'remark', value: '卖家备注', type: 'string'},
                                        {id: 'orderNo', value: '订单编号', type: 'string'},
                                        {id: 'outOrderNo', value: '原订单号', type: 'string'},
                                        {id: 'prodCode', value: '产品编码', type: 'string'},
                                        {id: 'shippingNo', value: '快递单号', type: 'string'},
                                        {id: 'totalFee', value: '成交金额', type: 'number'},
                                        {id: 'receiverMobile', value: '收货手机', type: 'string'},
                                        {id: 'prodName', value: '商品名称', type: 'string'}
                                    ]
                                })
                            },
                            {
                                name: 'conditionType',
                                value: 'has',
                                itemId: 'queryType',
                                valueField: 'id',
                                displayField: 'value',
                                width: 70,
                                store: Ext.create('Ext.data.Store', {
                                    fields: ['id', 'value', 'type'],
                                    data: [
                                        {id: 'has', value: '包含', type: 'string'},
                                        {id: '!', value: '不包含', type: 'string'},
                                        {id: '=', value: '等于', type: 'all'},
                                        {id: '!=', value: '不等于', type: 'number'},
                                        {id: '>=', value: '大于等于', type: 'number'},
                                        {id: '<=', value: '小于等于', type: 'number'}

                                    ]
                                })
                            },
                            {xtype: 'textfield', name: 'conditionValue', width: 80, margin: '0 10 0 0'}
                        ]
                    }

                ]
            },

            /*{
                xtype: 'fieldcontainer',
                hidden: true,
                layout: 'hbox',
                combineErrors: true,
                msgTarget: 'side',
                fieldLabel: '条件1',
                labelWidth: 40,
                width: 300,
                defaults: {
                    xtype: 'combo',
                    queryMode: 'local',
                    triggerAction: 'all',
                    forceSelection: true,
                    editable: false,
                    hideLabel: true,
                    margin: '0 5 0 0'
                },
                items: [

                    {
                        width: 90,
                        name: 'conditionQuery',
                        itemId: 'querySelect',
                        value: 'prodCode',
                        valueField: 'id',
                        hidden: true,
                        displayField: 'value',
                        store: Ext.create('Ext.data.Store', {
                            fields: ['id', 'value', 'type'],
                            data: [
                                {id: 'remark', value: '卖家备注', type: 'string'},
                                {id: 'prodCode', value: '产品编码', type: 'string'},
                                {id: 'prodName', value: '商品名称', type: 'string'}
                            ]
                        })
                    },
                    {
                        name: 'conditionType',
                        value: 'has',
                        itemId: 'queryType',
                        hidden: true,
                        valueField: 'id',
                        displayField: 'value',
                        width: 70,
                        store: Ext.create('Ext.data.Store', {
                            fields: ['id', 'value', 'type'],
                            data: [
                                {id: 'has', value: '包含', type: 'string'},
                                {id: '!', value: '不包含', type: 'string'}

                            ]
                        })
                    },
                    {xtype: 'textfield', name: 'conditionValue', width: 80, hidden: true, margin: '0 10 0 0'}
                ]
            },*/
            {
                fieldLabel: '订单状态', value: 'CONFIRMED', name: 'orderStatus', id: 'orderState', hidden: true,
                queryMode: 'local',
                triggerAction: 'all',
                forceSelection: true,
                xtype: 'combo',
                store: Espide.Common.orderState.getStore(true)
            },
            {
                xtype: 'button',
                text: '确定',
                itemId: 'confirmBtn',
                width: 55
            }
        ];
        this.tbar = {
            ui: 'footer',
            items: [
                { text: '待处理', itemId: 'CONFIRMED', belong: 'mainBtn', disabled: true},
                '--》',
                { text: '已打印', itemId: 'PRINTED', belong: 'mainBtn'},
                '--》',
                { text: '已验货', itemId: 'EXAMINED', belong: 'mainBtn'},
                '--》',
                { text: '处理完', itemId: 'INVOICED', belong: 'mainBtn'}
            ]
        };

        this.callParent(arguments);
    }
})