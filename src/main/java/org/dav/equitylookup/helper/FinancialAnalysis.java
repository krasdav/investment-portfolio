package org.dav.equitylookup.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class FinancialAnalysis {

    private static final DecimalFormat df = new DecimalFormat("#.##");

    // Suppress default constructor for noninstantiability
    private FinancialAnalysis() {
        throw new AssertionError();
    }

    public static BigDecimal getPercentageChange(BigDecimal a, BigDecimal b ){
        BigDecimal valueChange = b.subtract(a);
        BigDecimal percentageChange = valueChange.divide(a,4, RoundingMode.HALF_EVEN).multiply(new BigDecimal("100"));
        return percentageChange.setScale(2,RoundingMode.HALF_EVEN);
    }

    public static BigDecimal getValueChange(BigDecimal a, BigDecimal b ){
        return b.subtract(a).setScale(2,RoundingMode.HALF_EVEN);
    }

}
