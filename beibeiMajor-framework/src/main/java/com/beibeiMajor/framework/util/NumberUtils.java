package com.beibeiMajor.framework.util;

import java.math.BigDecimal;
import java.text.NumberFormat;

public abstract class NumberUtils {

    public static boolean isNumber(String num) {
        try {
            @SuppressWarnings("unused")
            BigDecimal decimal=new BigDecimal(num);
            return true;
        } catch(NumberFormatException err) {
            return false;
        }
    }

    public static boolean isDigits(String content) {
        if(TextUtils.isEmpty(content)) {
            return false;
        }
        char[] chs=content.toCharArray();
        for(char c: chs) {
            if(!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isTelePhoneNum(String num) {
        if(TextUtils.isEmpty(num) || num.length() != 11) {
            return false;
        }
        if(!isDigits(num)) {
            return false;
        }
        if(num.startsWith("1")) {
            return true;
        }
        return false;
    }

    public static String format(Number number, int decimal) {
        NumberFormat nf=NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        nf.setMinimumFractionDigits(decimal);
        nf.setMaximumFractionDigits(decimal);
        return nf.format(number);
    }

    public byte[] intToBytes(int number) {
        int temp=number;
        byte[] b=new byte[4];
        for(int i=b.length - 1; i > -1; i--) {
            b[i]=new Integer(temp & 0xff).byteValue(); // 将最高位保存在最低位
            temp>>=8; // 向右移8位
        }
        return b;
    }

    public static int bytesToInt(byte[] b) {
        int s=0;
        for(int i=0; i < 3; i++) {
            if(b[i] >= 0) {
                s+=b[i];
            } else {
                s+=256 + b[i];
            }
            s*=256;
        }
        if(b[3] >= 0) { // 最后一个之所以不乘，是因为可能会溢出
            s+=b[3];
        } else {
            s+=256 + b[3];
        }
        return s;
    }

    public static byte[] charToBytes(char ch) {
        int temp=(int)ch;
        byte[] b=new byte[2];
        for(int i=b.length - 1; i > -1; i--) {
            b[i]=new Integer(temp & 0xff).byteValue(); // 将最高位保存在最低位
            temp>>=8; // 向右移8位
        }
        return b;
    }

    // 字节到字符转换
    public static char bytesToChar(byte[] b) {
        int s;
        if(b[0] > 0) {
            s=b[0];
        } else {
            s=256 + b[0];
        }
        s*=256;
        if(b[1] > 0) {
            s+=b[1];
        } else {
            s+=256 + b[1];
        }
        char ch=(char)s;
        return ch;
    }

    // 浮点到字节转换
    public static byte[] doubleToBytes(double d) {
        byte[] b=new byte[8];
        long l=Double.doubleToLongBits(d);
        for(int i=0; i < b.length; i++) {
            b[i]=new Long(l).byteValue();
            l>>=8;
        }
        return b;
    }

    // 字节到浮点转换
    public static double bytesToDouble(byte[] b) {
        long l;

        l=b[0];
        l&=0xff;
        l|=((long)b[1] << 8);
        l&=0xffff;
        l|=((long)b[2] << 16);
        l&=0xffffff;
        l|=((long)b[3] << 24);
        l&=0xffffffffl;
        l|=((long)b[4] << 32);
        l&=0xffffffffffl;

        l|=((long)b[5] << 40);
        l&=0xffffffffffffl;
        l|=((long)b[6] << 48);
        l&=0xffffffffffffffl;
        l|=((long)b[7] << 56);
        return Double.longBitsToDouble(l);
    }

    public static void main(String[] args) {
        System.out.println(format(1.2222D, 0));
    }
}
