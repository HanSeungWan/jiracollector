package com.daou.jiracollector.jiradatamaker;

//java class

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 이 클래스는 프로젝트 진행시 자주 사용 하는 함수들을 모아 놓은 클래스 입니다. 필요한 기능을 가진 함수를 사용 할 수 있다.
 *
 * @author 한승완
 * @version 1.0.0 2016-02-18
 * @see jiraDataMakerUtil
 * @since JDK1.8
 */
public class jiraDataMakerUtil {
    /**
     * @param data
     * @return
     */
    public String filterNumber1to2(int data) {

        if (data < 10) {
            String tempMonth = "0" + data;
            return tempMonth;
        } else if (data >= 10) {
            String tempMonth = data + "";
            return tempMonth;
        }

        return null;
    }

    /**
     * @param arrList
     * @return
     */
    public HashMap<String, Integer> wordCount(ArrayList<String> arrList) {

        HashMap<String, Integer> wordCountResult = new HashMap<>();

        String compareData = null;

        Iterator<String> oldIe = arrList.iterator();

        while (oldIe.hasNext()) {
            compareData = oldIe.next();
            Integer count = wordCountResult.get(compareData);
            if (count == null) {
                wordCountResult.put(compareData, 1);
            } else {
                wordCountResult.put(compareData, count + 1);
            }
        }

        return wordCountResult;
    }

    /**
     * @param data
     * @throws IOException
     */
    public void printHashMap(HashMap<String, Integer> data) throws IOException {

        Iterator iterator = data.keySet().iterator();

        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            int value = data.get(key);
            System.out.println("ISSUE : " + key + " VALUE : " + value + "\n");
        }
    }

    /**
     * @param array
     * @return
     */
    public int[] initArray(int[] array) {

        for (int i = 0; i < array.length; i++) {
            array[i] = 0;
        }

        return array;

    }

    /**
     * @param arrList
     * @return
     */
    public ArrayList<String> getRidOfDuplicateData(ArrayList<String> arrList) {

        HashSet hs = new HashSet(arrList);

        ArrayList<String> newArrList = new ArrayList<String>(hs);

        return newArrList;
    }

    /**
     * @return
     */
    public String getCurrentDate() {

        String currentDate = null;

        long time = System.currentTimeMillis();
        SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd");
        currentDate = dayTime.format(new Date(time));

        return currentDate;
    }

    public String getCurrentDateTime() {

        String currentTime = null;

        long time = System.currentTimeMillis();
        SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        currentTime = dayTime.format(new Date(time));

        return currentTime;
    }
    /**
     * @param jsonResult
     * @return
     */
    public HashMap<String, int[]> ascSortHahMapByKey(HashMap<String, int[]> jsonResult) {

        HashMap<String, int[]> newHashMap = new HashMap<String, int[]>();

        TreeMap<String, int[]> treeMap = new TreeMap<String, int[]>(jsonResult);

        Iterator<String> iteratorKey = treeMap.keySet().iterator();

        while (iteratorKey.hasNext()) {
            String key = iteratorKey.next();
            int[] value = treeMap.get(key);

            newHashMap.put(key, value);

            System.out.println(key + "," + value);
        }

        return newHashMap;
    }

    /**
     * yyyy-mm-dd 형식을 yyyymmdd으로 바꾸어 int로 사용 가능 하도록 하는 함수
     *
     * @param day yyyy-mm-dd 형식 데이터
     * @return yyyymmdd 형식 데이터
     */
    public int dayString2Int(String day) {

        int newDay = 0;

        newDay = Integer.parseInt(day.replace("-", ""));

        return newDay;
    }

    /**
     * yyyymmdd 형식을 yyyy-mm-dd 으로 바꾸어 String으로 사용 가능 하도록 하는 함수
     *
     * @param date yyyymmdd 형식 데이터
     * @return yyyy-mm-dd 형식 데이터
     */
    public String dayInt2STring(int date) {

        String newDay = null;
        String tempDay = Integer.toString(date);

        String year = tempDay.substring(0, 4);
        String month = tempDay.substring(4, 6);
        String day = tempDay.substring(6, 8);

        newDay = year + filterNumber1to2(Integer.parseInt(month)) + filterNumber1to2(Integer.parseInt(day));

        return newDay;
    }

    /**
     * 검색할 날짜를 날짜 증가 법칙에 맞게 변경해 주는 함수
     *
     * @param date 날짜 데이터
     * @return 날짜 규칙에 맞게 변경된 날짜 데이터
     */
    public String checkDay(String date) {

        int tempMonth = 0;
        int tempYear = 0;
        String day = date.substring(6, 8);
        String month = date.substring(4, 6);
        String year = date.substring(0, 4);

        int endOfMonth = getEndMonth(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day)) + 1;

        String checkEndOfMonth = Integer.toString(endOfMonth);

        if (day.equals(checkEndOfMonth)) {
            tempMonth = Integer.parseInt(month) + 1;
            month = tempMonth + "";
            day = "01";
            if (month.equals("13")) {
                tempYear = Integer.parseInt(year) + 1;
                year = tempYear + "";
                month = "01";
            }
        }

        return year + "-" + filterNumber1to2(Integer.parseInt(month)) + "-"
                + filterNumber1to2(Integer.parseInt(day));
    }

    /**
     * 월 마지막일을 얻는 함수
     *
     * @param year  년도
     * @param month 월
     * @param day   날짜
     * @return 해당 월을 마지막 날짜
     */
    public int getEndMonth(int year, int month, int day) {

        int endDay = 0;

        Calendar today = Calendar.getInstance();

        today.set(year, month, day);

        endDay = today.getMaximum(Calendar.DAY_OF_MONTH);

        return endDay;

    }

    /**
     *
     * @param issuetype
     * @return
     */
    public String issuetypeFormatChange(String issuetype){

        String newIssuetypeFormat =  "";

        if("결함/장애".equals(issuetype)){
            newIssuetypeFormat = issuetype.replace("/", "");

            return newIssuetypeFormat;
        }

        return issuetype;
    }

    /**
     * 날짜 증가 메서드
     * @param oldDate
     * @return
     */
    public String increaseDate(String oldDate){

        int temp = 0;

        temp = dayString2Int(oldDate) + 1;
        oldDate = dayInt2STring(temp);
        String newDate = checkDay(oldDate);

        return newDate;
    }

    public String filterSpecialChar(String oldString){

        String newString  = "";

        newString = oldString.replace("/","\\u002f");

        return newString;
    }

    public String encodeUTF8(String oldString) throws UnsupportedEncodingException {

        String newString = "";

        newString = URLEncoder.encode(oldString, "UTF-8");

        return newString;
    }

    public boolean checkHasJsonData(String jsonData){

        if("errors".matches(jsonData)){
            return false;
        }

        return true;
    }
}
