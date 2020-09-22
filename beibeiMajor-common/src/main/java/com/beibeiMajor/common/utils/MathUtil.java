package com.beibeiMajor.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * 数学计算工具类
 */
public class MathUtil {
    protected static Logger logger = LoggerFactory.getLogger(MathUtil.class);
    private static final int DEFAULT_SCALE = 2; // 默认保留小数位

    /**
     * 相加
     * @param args
     * @return
     */
    public static Double add(Object... args){
        BigDecimal b1 = addOpt(args);
        if (null == b1) {
            return null;
        }
        return b1.doubleValue();

    }

    /**
     * 相加
     * @param args
     * @return
     */
    public static BigDecimal addOpt(Object... args){
        BigDecimal b1 = null;
        for (Object d : args) {
            if (null == d) {
                continue;
            }
            BigDecimal bd = new BigDecimal(d.toString());
            if (null == b1) {
                b1 = bd;
            } else {
                b1 = b1.add(bd);
            }
        }
        return b1;

    }

    /**
     * 相减
     * @param d1
     * @param doubleArgs
     * @return
     */
    public static BigDecimal subOpt(Object d1, Object... doubleArgs){
        if (null == d1) {
            return null;
        }
        BigDecimal b1 = new BigDecimal(d1.toString());
        for (Object d : doubleArgs) {
            if (null == d) {
                continue;
            }
            BigDecimal bd = new BigDecimal(d.toString());
            b1 = b1.subtract(bd);
        }
        return b1;
    }

    /**
     * 相减
     * @param d1
     * @param doubleArgs
     * @return
     */
    public static Double sub(Object d1, Object... doubleArgs){
        BigDecimal b1 = subOpt(d1, doubleArgs);
        if (b1 == null) {
            return null;
        }
        return b1.doubleValue();
    }

    /**
     * 相乘
     * @param d1
     * @param doubleArgs
     * @return
     */
    public static BigDecimal mulOpt(Object d1, Object... doubleArgs){
        if (null == d1) {
            return null;
        }
        BigDecimal b1 = new BigDecimal(d1.toString());
        for (Object d : doubleArgs) {
            if (null == d) {
                continue;
            }
            BigDecimal bd = new BigDecimal(d.toString());
            b1 = b1.multiply(bd);
        }
        return b1;
    }

    /**
     * 相乘
     * @param d1
     * @param doubleArgs
     * @return
     */
    public static Double mul(Object d1, Object... doubleArgs){
        BigDecimal b1 = mulOpt(d1, doubleArgs);
        if (b1 == null) {
            return null;
        }
        return b1.doubleValue();
    }

    /**
     * 相除
     * @param d1
     * @param doubleArgs
     * @return
     */
    public static Double div(Object d1, Object... doubleArgs){
        return divAndSetScale(DEFAULT_SCALE, d1, doubleArgs);
    }

    /**
     * 相除
     * @param d1
     * @param doubleArgs
     * @return
     */
    public static BigDecimal divOpt(Object d1, Object... doubleArgs){
        return divAndSetScaleOpt(DEFAULT_SCALE, d1, doubleArgs);
    }

    /**
     * 相除，保留scale位小数
     * @param scale
     * @param d1
     * @param doubleArgs
     * @return
     */
    public static BigDecimal divAndSetScaleOpt(int scale, Object d1, Object... doubleArgs){
        if(scale < 0 || doubleArgs == null || doubleArgs.length == 0){
            throw new IllegalArgumentException("The scale or doubleArgs error");
        }
        if (null == d1) {
            return null;
        }
        BigDecimal b1 = new BigDecimal(d1.toString());
        for (Object d : doubleArgs) {
            if (null == d) {
                continue;
            }
            BigDecimal bd = new BigDecimal(d.toString());
            b1 = b1.divide(bd, scale, BigDecimal.ROUND_HALF_UP);
        }
        return b1;
    }

    /**
     * 相除，保留scale位小数
     * @param scale
     * @param d1
     * @param doubleArgs
     * @return
     */
    public static Double divAndSetScale(int scale, Object d1, Object... doubleArgs){
        BigDecimal b1 = divAndSetScaleOpt(scale, d1, doubleArgs);
        if (b1 == null) {
            return null;
        }
        return b1.doubleValue();
    }

    /**
     * 四舍五入保留r位小数
     * @param num 需要调整的数据
     * @param s 保留位数
     * @return
     */
    public static Double roundDouble(Object num, Integer s) {
        if(null == num) {
            return null;
        }
        BigDecimal bd = roundNumber(num, s);
        if (bd != null) {
            return bd.doubleValue();
        }
        return null;
    }

    /**
     * 四舍五入保留r位小数
     * @param num 需要调整的数据
     * @param s 保留位数
     * @return
     */
    public static BigDecimal roundNumber(Object num, Integer s) {
        if(null == num) {
            return null;
        }
        try {
            BigDecimal bd = new BigDecimal(num.toString());
            return bd.setScale(s, BigDecimal.ROUND_HALF_UP);
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 默认格式化
     * @param num
     * @return
     */
    public static BigDecimal format(Object num) {
        return roundNumber(num, DEFAULT_SCALE);
    }

    /**
     * 默认格式化
     * @param num
     * @return
     */
    public static Double defaultFormat(Object num) {
        return roundDouble(num, DEFAULT_SCALE);
    }

    /**
     * 四舍五入，默认保留2位小数
     * @param num 需要调整的数据
     * @return 该数字的字符串值
     */
    public static String roundDouble(Object num) {
        Double d = roundDouble(num, DEFAULT_SCALE);
        if(null == d) {
            return "";
        }
        return d.toString();
    }

    public static void main(String[] args) {
        System.out.println(add(1, 2, 3));
        System.out.println(sub(3, 2, 1));
        System.out.println(mul(3, 2, 1));
        System.out.println(div(3, 3, 2));
        System.out.println(divOpt(3, 2));
        System.out.println(divAndSetScale(5,1, 3));
    }


    /**
     * 判断字符串是否是数字
     */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
}
