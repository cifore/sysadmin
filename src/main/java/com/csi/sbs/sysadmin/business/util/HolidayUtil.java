package com.csi.sbs.sysadmin.business.util;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


import com.csi.sbs.sysadmin.business.clientmodel.HolidayModel;
import com.csi.sbs.sysadmin.business.constant.ExceptionConstant;
import com.csi.sbs.sysadmin.business.entity.HolidayEntity;
import com.csi.sbs.sysadmin.business.service.HolidayService;

public class HolidayUtil {

    //日期格式分隔符，格式如20190101或2019-01-01，""/"-"
    private  static String SEPARATOR = "";
    private static int YEAR = 2019;
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd") ;

    /**
     *         全年假期集合 = [查询全年的双休周末] + [得到所有的法定节假日] - [得到所有的调休日] - [查询数据库已经存在的假期集合]
     * @param args
     */
    @SuppressWarnings({ "null", "rawtypes" })
	public static ResultUtil getHoliday(HolidayService holidayService) {
        Set<String> allHolidays = new HashSet<>();
        //查询全年的双休周末
        Set<String> yearDoubleWeekend = getWeekDayList(YEAR);
        allHolidays.addAll(yearDoubleWeekend);
        // [+]
        Set<String> legalHolidays = getLegalHoliday(YEAR);
        allHolidays.addAll(legalHolidays);
        //得到所有的法定节假日
        // [-]
        //得到所有的调休为上班的日期
        Set<String> adjustRestWorkDays = getAdjustRestWorkDays(YEAR);
        allHolidays.stream().filter(x->adjustRestWorkDays.contains(x)).collect(Collectors.toSet());
        // [-]
        //查询数据库已经存在的假期集合，
        List<LinkedHashMap<String, Object>> existDBHolidaysMap = null;
        @SuppressWarnings("unchecked")
		List<HolidayEntity> res_allHoliday = (List<HolidayEntity>) holidayService.getAll();
        LinkedHashMap<String,Object> resultMap = new LinkedHashMap<String,Object>();
        if(res_allHoliday!=null && res_allHoliday.size()>0){
        	for(int i=0;i<res_allHoliday.size();i++){
            	resultMap.put("day", res_allHoliday.get(i).getDay());
            }
        	existDBHolidaysMap.add(resultMap);
        	Set<String> existDBHolidaysSet = new HashSet<>();
            existDBHolidaysMap.forEach(x->{
                existDBHolidaysSet.add(x.get("day").toString());
            });
            allHolidays = allHolidays.stream().filter(x->!existDBHolidaysSet.contains(x)).collect(Collectors.toSet());
        }
        //排序
        List<Integer> allHolidaysInt = allHolidays.stream().map(x -> Integer.valueOf(x)).collect(Collectors.toList());
        allHolidaysInt.sort(Comparator.naturalOrder());
        int allHolidaysSize = allHolidaysInt==null || allHolidaysInt.size()==0 ? 0 : allHolidaysInt.size();
        System.out.println("当前插入新的数据allHolidaysInt.size=" + allHolidaysSize +":"+allHolidaysInt);
        //插入所有假期数据到假期表中
        HolidayModel holidayModel = new HolidayModel();
        for(int k=0;k<allHolidaysInt.size();k++){
        	holidayModel.setCountrycode("CN");
        	holidayModel.setDay(allHolidaysInt.get(k).toString());
        	holidayService.save(holidayModel);
        }
        return ResponseUtil.responseS(ExceptionConstant.SUCCESS_CODE200, null);
    }

    /**
     * 手动维护2019年的法定节假日
     * @param year
     * @return
     */
    public static Set<String> getLegalHoliday(int year){
        Set<String> holidays = new HashSet<String>();
        //元旦 
        holidays.add("20190101");
        //春节 
        holidays.add("20190202");
        holidays.add("20190203");
        holidays.add("20190204");
        holidays.add("20190205");
        holidays.add("20190206");
        holidays.add("20190207");
        holidays.add("20190208");
        holidays.add("20190209");
        holidays.add("20190210");
        holidays.add("20190211");
        holidays.add("20190212");
        holidays.add("20190213");
        //清明节 
        holidays.add("20190405");
        holidays.add("20190406");
        holidays.add("20190407");
        //劳动节 
        holidays.add("20190501");
        //端午节 
        holidays.add("20190607");
        holidays.add("20190608");
        holidays.add("20190609");
        //中秋节 
        holidays.add("20190913");
        holidays.add("20190914");
        holidays.add("20190915");
        //国庆节 
        holidays.add("20191001");
        holidays.add("20191002"); 
        holidays.add("20191003"); 
        holidays.add("20191004"); 
        holidays.add("20191005"); 
        holidays.add("20191006"); 
        holidays.add("20191007");
        return holidays;
    }

    /**
     * 手动维护2019年的法定节假日的调休上班工作日
     * @param year
     * @return
     */
    public static Set<String> getAdjustRestWorkDays(int year) {
        Set<String> restDays = new HashSet<String>();
        //元旦
        //春节
        //清明节
        //劳动节
        //端午节
        //中秋节
        //国庆节
        restDays.add("20190929");
        restDays.add("20191012");

        return restDays;
    }

    /***
     * 得到一年内所有日期
     * @param year
     * @return
     */
    public static List<String> getAllDaysByYear(int year) {
        return getAllDays(year+"-01-01", year+"-12-31");
    }

    /***
     * 得到指定区间内的所有日期
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<String> getAllDays(String startTime, String endTime) {
        /**
         * 获取两个日期之间的所有日期
         *
         * @param startTime
         *            开始日期
         * @param endTime
         *            结束日期
         * @return
         */
            // 返回的日期集合
            List<String> days = new ArrayList<String>();

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date start = dateFormat.parse(startTime);
                Date end = dateFormat.parse(endTime);

                Calendar tempStart = Calendar.getInstance();
                tempStart.setTime(start);

                Calendar tempEnd = Calendar.getInstance();
                tempEnd.setTime(end);
                tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
                while (tempStart.before(tempEnd)) {
                    String formatLineDay = dateFormat.format(tempStart.getTime());
                    String formatDat = formatLineDay.replaceAll("-","");
                    days.add(formatDat);
                    tempStart.add(Calendar.DAY_OF_YEAR, 1);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

            return days;
    }

    /**
     * 获取一年内所有的双休日,这个方法有问题，[20190832, 20191131]竟然有这两个非法日期
     * @param year
     * @return
     */
    public static Set<String> getYearDoubleWeekend(int year){
        Set<String> listDates = new HashSet<String>();
        Calendar calendar=Calendar.getInstance();//当前日期
        calendar.set(year, 6, 1);
        Calendar nowyear=Calendar.getInstance();
        Calendar nexty=Calendar.getInstance();
        nowyear.set(year, 0, 1);//2010-1-1
        nexty.set(year+1, 0, 1);//2011-1-1
        calendar.add(Calendar.DAY_OF_MONTH, -calendar.get(Calendar.DAY_OF_WEEK));//周六
        Calendar c=(Calendar) calendar.clone();
        for(;calendar.before(nexty)&&calendar.after(nowyear);calendar.add(Calendar.DAY_OF_YEAR, -7)){
            listDates.add(calendar.get(Calendar.YEAR) + SEPARATOR + getDoubleMonthStr((1+calendar.get(Calendar.MONTH))) + SEPARATOR + getDoubleMonthStr(calendar.get(Calendar.DATE)));
            listDates.add(calendar.get(Calendar.YEAR) + SEPARATOR + getDoubleMonthStr(1+calendar.get(Calendar.MONTH)) + SEPARATOR + getDoubleMonthStr(1+calendar.get(Calendar.DATE)));
        }
        for(;c.before(nexty)&&c.after(nowyear);c.add(Calendar.DAY_OF_YEAR, 7)){
            listDates.add(c.get(Calendar.YEAR) + SEPARATOR + getDoubleMonthStr(1+c.get(Calendar.MONTH)) + SEPARATOR + getDoubleMonthStr(c.get(Calendar.DATE)));
            listDates.add(c.get(Calendar.YEAR) + SEPARATOR + getDoubleMonthStr(1+c.get(Calendar.MONTH)) + SEPARATOR + getDoubleMonthStr(1+c.get(Calendar.DATE)));
        }
        return listDates;
    }
    private static String getDoubleMonthStr(int monthInt){
        String monthStr = monthInt+"";
        String doublemonth = monthStr.length()==1? "0"+ monthStr : monthStr ;
        return doublemonth;
    }
    public static Set<String> getWeekDayList(int year) {
        Set<String> listDate = new HashSet<>();
        int i = 1;
        Calendar calendar = new GregorianCalendar(year, 0, 1);

        while (calendar.get(Calendar.YEAR) < year + 1) {
            calendar.set(Calendar.WEEK_OF_YEAR, i++);

            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            if (calendar.get(Calendar.YEAR) == year) {
                listDate.add(dateFormat.format(calendar.getTime()));
            }
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
            if (calendar.get(Calendar.YEAR) == year) {
                listDate.add(dateFormat.format(calendar.getTime()));
            }
        }
        return listDate;
    }
}
