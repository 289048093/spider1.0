/**
 * 仓库列表
 * Created by HuaLei.Du on 13-12-18.
 */

Ext.define('EBDesktop.WarehouseListModel', {
    extend: 'Ext.data.Model',
    fields: ['id', 'name', 'repoCode', 'address', 'chargePerson', 'shippingComp', 'chargeMobile', 'chargePhone'],
    idProperty: 'id'
});

Ext.define('EBDesktop.warehouse.List', {
    extend: 'Ext.container.Container',
    alias: 'widget.warehouseList',
    id: 'warehouseList',
    fixed: true,
    layout: 'border',
    initComponent: function () {

        var warehouseStore,
            searchForm,
            warehouseGrid;

        // 显示添加窗口
        function showAddWin() {
            var saveWin = Ext.getCmp('warehouseSave') || Ext.widget('warehouseSave', { title: '添加仓库'}),
                saveForm = Ext.getCmp('warehouseSaveForm');

            saveForm.getForm().reset();
            saveWin.down('[itemId=action]').setValue('/repository/add');
            saveWin.show();
        }

        // 显示修改窗口
        function showEditWin(t, record) {
            var saveWin = Ext.getCmp('warehouseSave') || Ext.widget('warehouseSave', {
                    title: '修改仓库'
                }),
                saveForm = Ext.getCmp('warehouseSaveForm');

            saveForm.getForm().loadRecord(record);
            saveForm.down('[itemId=action]').setValue('/repository/update');
            saveWin.show();
        }

        // 移除仓库
        function removeWarehouse() {
            var url = '/repository/delete',
                ids = Espide.Common.getGridSels('warehouseGrid', 'id');

            if (ids.length < 1) {
                Espide.Common.showGridSelErr('请先选择要删除的仓库');
                return;
            }

            Ext.MessageBox.confirm('提醒', '您确定要删除吗？', function (optional) {
                Espide.Common.doAction({
                    url: url,
                    params: {
                        id: ids.join()
                    },
                    successCall: function () {
                        Ext.getCmp('warehouseGrid').getStore().loadPage(1);
                    },
                    successTipMsg: '删除成功'
                })(optional);
            });
        }

        // 仓库搜索
        function doWarehouseSearch() {
            Espide.Common.doSearch('warehouseGrid', 'warehouseSearch', true);
        }

        warehouseStore = Ext.create('Ext.data.Store', {
            extend: 'Ext.data.Store',
            model: 'EBDesktop.WarehouseListModel',
            proxy: {
                type: 'ajax',
                api: {
                    read: '/repository/list'
                },
                reader: {
                    type: 'json',
                    successProperty: 'success',
                    root: 'data.obj.result',
                    totalProperty: 'data.obj.totalCount',
                    messageProperty: 'msg'
                }
            },
            autoLoad: true,
            pageSize: 15
        });

        searchForm = Ext.create('Ext.form.Panel', {
            region: 'north',
            layout: 'hbox',
            border: 0,
            bodyStyle: {
                padding: '6px 0 6px 8px'
            },
            id: 'warehouseSearch',
            defaults: {
                xtype: 'combo',
                labelWidth: 60,
                width: 220,
                margin: '0 10 0 0'
            },
            items: [
                {
                    xtype: 'textfield',
                    name: 'name',
                    fieldLabel: '仓库名称',
                    listeners: {
                        specialkey: function (field, e) {
                            if (e.getKey() == Ext.EventObject.ENTER) {
                                doWarehouseSearch();
                            }
                        }
                    }
                },
                {
                    xtype: 'button',
                    text: '查询',
                    width: 60,
                    itemId: 'searchBtn',
                    handler: function () {
                        doWarehouseSearch();
                    }
                },
                {
                    xtype: 'button',
                    text: '添加仓库',
                    width: 70,
                    itemId: 'addBtn',
                    handler: showAddWin
                },
                {
                    xtype: 'button',
                    text: '删除已选',
                    width: 70,
                    itemId: 'deleteBtn',
                    handler: removeWarehouse
                }
            ]
        });

        warehouseGrid = Ext.create('Ext.grid.Panel', {
            region: 'center',
            store: warehouseStore,
            forceFit: true,
            id: 'warehouseGrid',
            selType: 'checkboxmodel',
            viewConfig: {
                emptyText: '<div style="text-align:center; padding:10px;color:#F00">没有数据</div>'
            },
            columns: [
                {
                    text: '仓库名',
                    dataIndex: 'name',
                    width: 100
                },
                {
                    text: '编码',
                    dataIndex: 'repoCode',
                    width: 80
                },
                {
                    text: '地址',
                    dataIndex: 'address',
                    width: 200
                },
                {
                    text: '负责人',
                    dataIndex: 'chargePerson',
                    width: 80
                },
                {
                    text: '物流公司',
                    dataIndex: 'shippingComp',
                    width: 100,
                    renderer: Espide.Common.getExpress
                },
                {
                    text: '电话',
                    dataIndex: 'chargePhone',
                    width: 120
                },
                {
                    text: '手机',
                    dataIndex: 'chargeMobile',
                    width: 120
                }
            ],
            listeners: {
                'itemdblclick': showEditWin
            },
            bbar: Ext.create('Ext.PagingToolbar', {
                store: warehouseStore,
                displayInfo: true
            })
        });

        this.items = [searchForm, warehouseGrid];

        this.callParent(arguments);
    }
});