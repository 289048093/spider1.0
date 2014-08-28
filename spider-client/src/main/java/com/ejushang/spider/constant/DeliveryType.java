package com.ejushang.spider.constant;

/**
 * User: amos.zhou
 * Date: 13-12-23
 * Time: 上午11:59
 * 配送类型（物流公司）
 */
public enum DeliveryType {

    shunfeng("顺丰","505","SF","^[0-9]{12}$"),
    zhongtong("中通","500","ZTO","^((618|680|688|618|828|988|118|888|571|518|010|628|205|880|717|718|728|761|762|701|757)[0-9]{9})$|^((2008|2010|8050|7518)[0-9]{8})$"),
    yunda("韵达","102","YUNDA","^[0-9]{13}$"),
    zhaijisong("宅急送","103","ZJS","^[0-9]{10}$"),

    ems("ems","2","EMS","^[A-Z]{2}[0-9]{9}[A-Z]{2}$|[0-9]{13}"),
    yuantong("圆通","101","YTO","^(0|1|2|3|5|6|8|E|D|F|G|V|W|e|d|f|g|v|w)[0-9]{9}$"),
    shentong("申通","100","STO","^(268|888|588|688|368|468|568|668|768|868|968)[0-9]{9}$|^(268|888|588|688|368|468|568|668|768|868|968)[0-9]{10}$|^(STO)[0-9]{10}$"),
    quanritongkuaidi("全日通"),

    kuaijiesudi("快捷"),
    huitongkuaidi("汇通","502","HTKY","^(A|B|C|D|E|H|0)(D|X|[0-9])(A|[0-9])[0-9]{10}$|^(21|22|23|24|25|26|27|28|29|30|31|32|33|34|35|36|37|38|39)[0-9]{10}$"),
    guotongkuaidi("国通","1000001163","GTO","^(1|2)[0-9]{9}$"),
    lianbangkuaidi("联邦"),

    quanfengkuaidi("全峰","1216","QFKD","^[0-9]{12}$"),
    suer("速尔"),
    tiantian("天天","504","TTKDEX","^[0-9]{12}$"),
    youshuwuliu("优速"),

    youzhengguonei("邮政国内小包","2000002165","POSTB","^[GA]{2}[0-9]{9}([2-5][0-9]|[1][1-9]|[6][0-5])$|^[99]{2}[0-9]{11}$"),
    unknown("未知");



    public String name;
    public String  tmallId;
    public String  tmallCode;
    public String  tmallExp;

    DeliveryType(String name){
        this.name = name;
    }

    DeliveryType(String name ,String tmallId , String tmallCode, String  tmallExp) {
        this.name = name;
        this.tmallId = tmallId;
        this.tmallCode = tmallCode;
        this.tmallExp = tmallExp;
    }

    /**
     * 获取淘宝上物流公司id
     * @return
     */
    public String getTmallId(){
        return  tmallId;
    }

    /**
     * 获取淘宝上物流公司代码
     * @return
     */
    public String getTmallCode(){
        return tmallCode;
    }

    /**
     * 获取淘宝上验证物流单号正则表达式
     * @return
     */
    public String getTmallExp(){
        return tmallExp;
    }

    /**
     * 得到中文名称
     * @return
     */
    public String toDesc() {
        return name;
    }

    public static void main(String[] args) {
        System.out.println(DeliveryType.valueOf("quanfengkuaidi").toDesc());

    }

}
