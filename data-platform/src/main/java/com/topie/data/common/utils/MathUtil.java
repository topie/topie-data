package com.topie.data.common.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;

/**
 * Created by cgj on 2015/12/31.
 */
public class MathUtil {
    public static String getPercent(double num) {
        NumberFormat format = NumberFormat.getPercentInstance();
        format.setMinimumFractionDigits(2);
        return format.format(num);
    }

    public static BigDecimal getBigDecimal(Object value) {
        BigDecimal ret = null;
        if (value != null) {
            if (value instanceof BigDecimal) {
                ret = (BigDecimal) value;
            } else if (value instanceof String) {
                ret = new BigDecimal((String) value);
            } else if (value instanceof BigInteger) {
                ret = new BigDecimal((BigInteger) value);
            } else if (value instanceof Number) {
                ret = new BigDecimal(((Number) value).doubleValue());
            } else {
                throw new ClassCastException(
                        "Not possible to coerce [" + value + "] from class " + value.getClass()
                                + " into a BigDecimal.");
            }
        }
        return ret;
    }

    public static double getDivideValue(double a, double b, int scale) {
        BigDecimal bd = MathUtil.getBigDecimal(a / b);
        double perUvView = bd.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        return perUvView;
    }

    public static double getDivideValue(int a, int b, int scale) {
        BigDecimal bd = MathUtil.getBigDecimal((double) a / b);
        double perUvView = bd.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        return perUvView;
    }
}
