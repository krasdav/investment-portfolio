package org.dav.equitylookup.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FinancialAnalysis {

    // Suppress default constructor for noninstantiability
    private FinancialAnalysis() {
        throw new AssertionError();
    }

    public static BigDecimal getPercentageChange(BigDecimal a, BigDecimal b) {
        BigDecimal valueChange = b.subtract(a);
        BigDecimal percentageChange = valueChange.divide(a, 4, RoundingMode.HALF_EVEN).multiply(new BigDecimal("100"));
        return percentageChange.setScale(2, RoundingMode.HALF_EVEN);
    }

    public static BigDecimal getValueChange(BigDecimal a, BigDecimal b) {
        return b.subtract(a).setScale(2, RoundingMode.HALF_EVEN);
    }

    public static String getPercentageChange(String a, String b){
        return String.valueOf(getPercentageChange(new BigDecimal(a),new BigDecimal(b)));
    }

    public static String getValueChange(String a, String b) {
        return String.valueOf(getValueChange(new BigDecimal(a),new BigDecimal(b)));
    }

}
