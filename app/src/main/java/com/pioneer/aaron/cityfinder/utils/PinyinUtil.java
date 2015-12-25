package com.pioneer.aaron.cityfinder.utils;

import android.util.Log;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Aaron on 12/24/15.
 */
public class PinyinUtil {
    public static HanyuPinyinOutputFormat format;
    public static List<Map<String, Map<String, String>>> list;
    public static Map<String, Map<String, String>> map;
    public static Map<String, String> pMap;
    public static List<String> strList;

    public static void setData(List<String> strList) throws BadHanyuPinyinOutputFormatCombination {
        PinyinUtil.strList = strList;
        format = new HanyuPinyinOutputFormat();
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
        list = new ArrayList<>();
        for (int i = 0; i < strList.size(); ++i) {
            map = new HashMap<>();
            pMap = new HashMap<>();
            pMap.put("simple", getSimple(strList.get(i)));
            pMap.put("complex", getComplex(strList.get(i)));
            map.put(strList.get(i), pMap);
            list.add(map);
        }
    }

    /**
     * function: get the abbreviation of str in Pinyin
     * @param str to get
     * @return abbreviation
     * */
    public static String getSimple(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        String tempSimple = null;
        for (int i = 0; i < str.length(); ++i) {
            tempSimple = getCharacterSimple(str.charAt(i));
            if (null == tempSimple) {
                stringBuilder.append(str.charAt(i));
            } else {
                stringBuilder.append(tempSimple);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * function: get the first character in Pinyin
     * @param
     * */
    public static String getCharacterSimple(char c) {
        String[] strings = null;
        try {
            strings = PinyinHelper.toHanyuPinyinStringArray(c, format);
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        if (null == strings) {
            return null;
        } else {
            return strings[0].charAt(0) + "";
        }
    }

    public static String getComplex(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        String tempComplex = null;
        for (int i = 0; i < str.length(); ++i) {
            tempComplex = getCharacterComplex(str.charAt(i));
            if (null == tempComplex) {
                stringBuilder.append(str.charAt(i));
            } else {
                stringBuilder.append(tempComplex);
            }
        }
        return stringBuilder.toString();
    }

    public static String getCharacterComplex(char c) {
        String[] strings = null;
        try {
            strings = PinyinHelper.toHanyuPinyinStringArray(c, format);
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        if (null == strings) {
            return null;
        } else {
            return strings[0];
        }
    }

    public static List<String> search(String str) {
        List<String> temp = new ArrayList<>();

        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i).get(strList.get(i)).get("complex").contains(str) ||
                    list.get(i).get(strList.get(i)).get("simple").contains(str)) {
                temp.add(strList.get(i));
//                Log.d("search", strList.get(i) + "");
            }
        }
        return temp;
    }

}
