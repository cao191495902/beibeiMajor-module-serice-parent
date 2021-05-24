package com.beibeiMajor.framework.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

public abstract class TextUtils {

    private static final Pattern EMAIL_REGEX_PATTERN=Pattern.compile(
        "(\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*)", Pattern.CASE_INSENSITIVE);
    
    private static StringBuilder buf = new StringBuilder();  
    private static int seq = 0;  
    private static final int ROTATION = 24; 
    private static int[] nums=new int[]{2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89,97};

    public static boolean isEmpty(String content) {
        return(content == null || content.length() == 0);
    }

    public static boolean isNotEmpty(String content) {
        return !isEmpty(content);
    }

    public static boolean isEmail(String content) {
        if(isEmpty(content)) {
            return false;
        }
        if(content.indexOf('@')<0 || content.indexOf('.')<0){
            return false;
        }
        return EMAIL_REGEX_PATTERN.matcher(content).matches();
    }

    public static String join(Collection<String> col, String sep) {
        if(col==null){
            return null;
        }
        StringBuilder sb=new StringBuilder();
        int index=0;
        for(String value: col) {
            if(index == 0) {
                sb.append(value);
            } else {
                sb.append(sep).append(value);
            }
            index++;
        }
        return sb.toString();
    }

    public static String[] split(String content, String separator) {
        List<String> list=new ArrayList<String>();
        int length=separator.length();
        int form=0;
        int start=0;
        while(content.indexOf(separator, form) >= form) {
            start=content.indexOf(separator, form);
            if(start == form) {
                list.add("");
            } else {
                list.add(content.substring(form, start));
            }
            form=start + length;
        }

        if(form < content.length()) {
            list.add(content.substring(form));
        } else {
            list.add("");
        }
        String[] array=new String[list.size()];
        return list.toArray(array);
    }

    public static String replace(String content, String find, String replaceWith) {
        String[] array=split(content, find);
        List<String> list=Arrays.asList(array);
        return join(list, replaceWith);
    }

    public static String byte2hex(byte[] b) {
        StringBuffer hs=new StringBuffer();
        String stmp;
        for(int n=0; n < b.length; n++) {
            stmp=(Integer.toHexString(b[n] & 0XFF));
            if(stmp.length() == 1) {
                hs.append("0").append(stmp);
            } else {
                hs.append(stmp);
            }
            if(n < b.length - 1) {
                hs.append("");
            }
        }
        return hs.toString().toUpperCase();
    }
    
 

    public static synchronized String genUmid(){  
      if (seq > ROTATION) seq = 0;  
      buf.delete(0, buf.length());  
        String time=System.currentTimeMillis()+"";
        String subTime=time.substring(2,time.length());
        int ls=seq++;
        long seqTime=Long.parseLong(subTime)*nums[ls];
      return seqTime+"";
    } 
    


  
}
