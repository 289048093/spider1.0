package com.ejushang.spider.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日期辅助函数
 * User: Baron.Zhang
 * Date: 13-12-23
 * Time: 下午2:46
*/
public class DateUtilHelper{
	public static final String yyyy_MM_dd = "yyyy-MM-dd";
    private static String defaultDatePattern = "yyyy-MM-dd";
    /**
     * 获取时间戳。
     * 当传到另一
     * @return
     */
    public static String getTimestamp(){
    	return getString(new Date(),"yyyyMMddHHmmss");
    }
    
    /**
     * 检查时间戳是否过期.
     * 原理是：把操作时间以及当前时间，都换算成秒，比较两数之间是否小于unit
     * @param operTime 操作时间
     * @param unit 偏移量，以秒为单位
     * @return
     */
    public static boolean isTheSameTimestamp(Date operTime,int unit){
    	//Date operTime1 = getDate(operTime,"yyyyMMddHHmmss");
    	long oper=operTime.getTime()/1000; //获得毫秒
    	long now = new Date().getTime()/1000;
    	if ((now - oper)<=unit){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    public static String fmtTextDate(String inputText,String dateFormat){
		SimpleDateFormat sdf = new SimpleDateFormat(getFormat(dateFormat));
		return sdf.format(getDate(inputText,dateFormat));
	}
    
    private static String getFormat(String dateFormat){
		if(dateFormat==null || "".equals(dateFormat)){
			dateFormat = "yyyy-MM-dd";
		}
		return dateFormat;
	}
    
    /**
     * 检查时间戳是否过期.
     * 原理是：把操作时间以及当前时间，都换算成秒，比较两数之间是否小于unit
     * @param operTime 操作时间
     * @param unit 偏移量，以秒为单位
     * @return
     */
    public static boolean isTheSameTimestamp(String operTime,int unit){
    	//Date operTime1 = getDate(operTime,"yyyyMMddHHmmss");
    	long oper=getDate(operTime,"yyyyMMddHHmmss").getTime()/1000; //获得毫秒
    	long now = new Date().getTime()/1000;
    	if ((now - oper)<=unit){
    		return true;
    	}else{
    		return false;
    	}
    }
    /**
     * 获得默认的 date pattern
     */
    public static String getDatePattern() {
        return defaultDatePattern;
    }

    /**
     * 把一个只含年月日的时间补充上现在的时分秒
     * @param date
     */
    public static Date compDate(Date date){
    	Calendar now = Calendar.getInstance();
    	Calendar cl = Calendar.getInstance();
    	cl.setTime(date);
    	cl.set(Calendar.HOUR_OF_DAY, now.get(Calendar.HOUR_OF_DAY));
    	cl.set(Calendar.MINUTE,now.get(Calendar.MINUTE) );
    	cl.set(Calendar.SECOND,now.get(Calendar.SECOND) );
    	
    	date.setTime(cl.getTimeInMillis());
    	return cl.getTime();
    }
    /**
     * 返回预设Format的当前日期字符串
     */
    public static String getToday() {
        Date today = new Date();
        return getString(today);
    }

    /**
     * 使用预设Format格式化Date成字符串
     */
    public static String getString(Date date) {
        return date == null ? "" : getString(date, getDatePattern());
    }

    /**
     * 使用参数Format格式化Date成字符串
     */
    public static String getString(Date date, String pattern) {
        return date == null ? "" : new SimpleDateFormat(pattern).format(date);
    }    
    
    /**
     * 将字符串转化为日期
     * @param dateText
     * @param format
     * @return
     */
	public static Date getDate(String dateText, String format)
	{

		if (dateText == null||dateText.equals("")){
			return null;
		}
		DateFormat df ;
		try
		{
			if (format == null||format.equals("")){
				df = new SimpleDateFormat(yyyy_MM_dd);
			}
			else{
				df = new SimpleDateFormat(format);
			}
			df.setLenient(false);
			return df.parse(dateText);
		}catch (ParseException e){
			System.out.println("错误："+e);
			return null;
		}
	}
	public static Date getDate(String dateText){
		return getDate(dateText,null);		
	}

	/**
	 * 取得当前日期的字符表示
	 * @return
	 */
	public static String getCurrentDateString(){
		return getString(new Date(), yyyy_MM_dd);
	}

//	public static String dateToString(Date date, String pattern)
//	{
//
//		if (date == null){
//			return null;
//		}
//
//		try{
//			SimpleDateFormat sfDate = new SimpleDateFormat(pattern);
//			sfDate.setLenient(false);
//			return sfDate.format(date);
//		}catch (Exception e){
//			return null;
//		}
//	}
	
//	public static String fmtDate(Date date){
//		if (date == null){
//			return "";
//		}
//
//		try{
//			SimpleDateFormat sfDate = new SimpleDateFormat(yyyy_MM_dd);
//			sfDate.setLenient(false);
//			return sfDate.format(date);
//		}catch (Exception e){
//			return null;
//		}		
//	}
	
	
//	/**
//	 * 取当前日期时间
//	 * @return
//	 */
//	public static Date getCurrentDateTime(){
//		java.util.Calendar calNow = java.util.Calendar.getInstance();
//		java.util.Date dtNow = calNow.getTime();	
//		return dtNow;
//	}
	/**
     * 把一个包含时分秒的时间的时分秒设为0
     * @param date
     */
    public static Date cutDate(Date date){
    	Calendar cl1 = Calendar.getInstance();
    	Calendar cl2 = Calendar.getInstance();
    	cl1.clear();
    	cl2.setTime(date);
    	cl1.set(Calendar.YEAR, cl2.get(Calendar.YEAR));
    	cl1.set(Calendar.MONTH,cl2.get(Calendar.MONTH) );
    	cl1.set(Calendar.DAY_OF_MONTH,cl2.get(Calendar.DAY_OF_MONTH));
    	//date.setTime(cl.getTimeInMillis());
    	return cl1.getTime();
    }
	/**
	 * 要考虑到不同年份 获取两个日期间相差多少天
	 */
    public static int daysOfTwo(Date fDate, Date oDate) {
    	
    	  Long fTime=cutDate(fDate).getTime();
    	
    	  Long oTime=cutDate(oDate).getTime();
    	  
    	  System.out.println(oTime-fTime);
    	  return (int) ((oTime-fTime)/(24*60*60*1000L));
    	
     }
	
	/**
	 * 本年
	 * @return
	 */
	public static String getThisYear(){
		 
		return getString(new Date(), "yyyy");
	}
		
	/**
	 * 本月
	 * @return
	 */
	public static String getThisMonth(){
		 
		return getString(new Date(), "M");
	}
	
	/**
	 * 本月，汉字
	 * @return
	 */
	public static String getThisMonthChar(){
		String str = getString(new Date(), "M");
		String charMonth="";
		if (str.equals("1")){
			charMonth="一月";
		}else if (str.equals("2")){
			charMonth="二月";
		}else if(str.equals("3")){
			charMonth="三月";
		}else if(str.equals("4")){
			charMonth="四月";
		}else if(str.equals("5")){
			charMonth="五月";
		}else if(str.equals("6")){
			charMonth="六月";
		}else if(str.equals("7")){
			charMonth="七月";
		}else if(str.equals("8")){
			charMonth="八月";
		}else if(str.equals("9")){
			charMonth="九月";
		}else if(str.equals("10")){
			charMonth="十月";
		}else if(str.equals("11")){
			charMonth="十一月";
		}else if(str.equals("12")){
			charMonth="十二月";
		}
		
		return charMonth;
	}
	
	//////////////////////////////////////////////////////////////////
//    static {
//        //尝试试从messages_zh_Cn.properties中获取defaultDatePattner.
//        try {
//            Locale locale = LocaleContextHolder.getLocale();
//            defaultDatePattern = ResourceBundle.getBundle(Constants.MESSAGE_BUNDLE_KEY, locale).getString("date.default_format");
//        }
//        catch (MissingResourceException mse) {
//            //do nothing
//        }
//    }

//    /**
//     * 使用预设格式将字符串转为Date
//     */
//    public static Date parse(String strDate) throws ParseException {
//        return StringUtils.isBlank(strDate) ? null : parse(strDate, getDatePattern());
//    }
//
//    /**
//     * 使用参数Format将字符串转为Date
//     */
//    public static Date parse(String strDate, String pattern) throws ParseException {
//        return StringUtils.isBlank(strDate) ? null : new SimpleDateFormat(pattern).parse(strDate);
//    }

    /**
     * 在日期上增加数个整月
     */
    public static Date addMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return cal.getTime();
    }
    /**
     * 在日期上增加数个年
     */
    public static Date addYear(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, n);
        return cal.getTime();
    }
	
    /**
     * 在日期上增加数天
     */
    public static Date addDay(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, n);
        return cal.getTime();
    }
    
    /**
     * 在日期上增加数天
     */
    public static Date addHour(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, n);
        return cal.getTime();
    }
    /**
     * 在日期上增加数分
     */
    public static Date addMinute(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, n);
        return cal.getTime();
    }

    /**
     * 在日期上增加数秒
     */
    public static Date addSecond(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, n);
        return cal.getTime();
    }
	
//    /**
//     * 把日期填充上当前的时分秒
//     * @param date
//     */
//	public static Date getFullDate(Date date){
//		 return getFullDate(date,null);
//	}
//	
//    /**
//     * 把日期填充上时分秒
//     * @param date
//     */
//	public static Date getFullDate(Date date,Date cDate){
//		Date now = new Date();
//		if (cDate!=null){now = cDate;}
//		
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(now);
//		int h = cal.get(Calendar.HOUR_OF_DAY);
//		int m = cal.get(Calendar.MINUTE);
//		int s = cal.get(Calendar.SECOND);
//		cal.setTime(date);
//		cal.set(Calendar.HOUR_OF_DAY,h);
//		cal.set(Calendar.MINUTE,m);
//		cal.set(Calendar.SECOND,s);
//		return cal.getTime();
//	}
	
		
    public static void main(String[] args){
    	/*Date date1 = new Date();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Date date2 = new Date();
//		System.out.println(date1);
//		System.out.println(date2);
//		System.out.println(date1.equals(date2));
//		System.out.println(DateUtils.isSameDay(date1,date2));
		String timestamp = getTimestamp();
		System.out.println(timestamp);
		
		System.out.println(isTheSameTimestamp(getDate(timestamp,"yyyyMMddHHmmss"),10));*/
    	
    	System.out.println(addHour(new Date(),1));
    	
    }
    /**
     * 得到时间列表，从星期日到星期六
     * flag: Calendar.DAY_OF_WEEK,Calendar.DAY_OF_MONTH
     */
    public static List getDateList(Date date,int flag){
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	int todaynum = cal.get(flag);
		int weeknum = cal.getActualMaximum(flag);
		List list = new ArrayList();
		for(int i = 1; i <= weeknum ; i ++ ){
			int j = i - todaynum;			
			list.add(addDay(date,j));
		}		
		return list;
    }
    /**
     * 日期相同，年月日相同放回true
     * @param date1
     * @param date2
     * @return
     */
    public static boolean equal(Date date1,Date date2){
    	Calendar calendar1 = Calendar.getInstance();
    	Calendar calendar2 = Calendar.getInstance();
//    	if(){;
//    		
//    	}
    	
    	return false;
    }
    
    /**
     * 分解date
     * @param date
     * @return
     */
    public static Map<String,Integer> getDetails(String str,String format){
    	
    	Map<String,Integer> m = new HashMap<String,Integer>();
    	if(str==null || "".equals(str)) return m;
    	Date date = getDate(str, format);
    	Calendar cd= Calendar.getInstance();
    	cd.setTime(date);
    	m.put("yyyy",cd.get(Calendar.YEAR));
    	m.put("MM",cd.get(Calendar.MONTH)+1);
    	m.put("dd",cd.get(Calendar.DAY_OF_MONTH));
    	m.put("hh",cd.get(Calendar.HOUR));
    	m.put("mm",cd.get(Calendar.MINUTE));
    	m.put("ss",cd.get(Calendar.SECOND));
    	return m;
    }
    /**
     * 这里只写了month,后续需要的时候在添加
     * @param year
     * @param unit
     * @return
     */
    public static List getSerialName(String year,Integer unit){
    	List list = new ArrayList();
    	if(unit==Calendar.MONTH){ //求月份
    		if(year!=null&&getThisYear().compareTo(year)==0){ //year=thisYear
    			int m = Integer.valueOf(getThisMonth());
    			for(int i=1;i<=m;i++){
    				list.add(String.valueOf(i));
    			}
    		}else{ //不是本年，默认12个月份
    			for(int i=1;i<=12;i++){
    				list.add(String.valueOf(i));
    			}
    		}
    		
    	}
    
    	return list;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
 
}
