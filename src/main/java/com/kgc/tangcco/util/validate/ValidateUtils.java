package com.kgc.tangcco.util.validate;


public class ValidateUtils {
    /**
     * 判断当前内容是否为空
     * @param str 要判断的内容
     * @return 不为空返回true,为空返回false
     */
    public static boolean validateEmpty(String str){
        if(str== null || "".equals(str)){
            return false;
        }
        return true;
    }

    /**
     * 正则验证
     * @param str 要执行验证的数据
     * @param regx 正则表达式
     * @return 验证通过返回true,验证失败返回false
     */
    public static boolean validateRegx(String str, String regx){
        if(validateEmpty(str)){
            return str.matches(regx);
        }
        return false;
    }

    /**
     * 判断两个参数的内容是否相同
     * @param stra 参数a
     * @param strb 参数b
     * @return 如果参数相同返回true,如果不同返回false
     */
    public static boolean validateSame(String stra,String strb){
        if(validateEmpty(stra) && validateEmpty(strb)){
            return stra.equalsIgnoreCase(strb);
        }
        return false;
    }
}
