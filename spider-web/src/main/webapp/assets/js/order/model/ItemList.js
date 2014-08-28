Ext.define('Supplier.model.ItemList',
    {
        //不要忘了继承
        extend:'Ext.data.Model',
        fields:['id','prodNo', 'prodName', 'skuCode', 'itemType','prodCateName',
            {name: 'prodPrice', type: 'float'},
            {name: 'totalFee', type: 'float'},
            {name: 'postFee', type: 'float'},
            {name: 'actualFee', type: 'float'},
            'prodCount', 'priceDescription', 'actuallyNumber', 'brandName', 'outOrderNo'],
        idProperty: 'id'
    }
)