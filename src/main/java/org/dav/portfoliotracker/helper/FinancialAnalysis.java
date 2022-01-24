package org.dav.portfoliotracker.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FinancialAnalysis {

    // Suppress default construct or for noninstantiability
    private FinancialAnalysis() {
        throw new AssertionError();
    }

    public static BigDecimal getPercentageChange(BigDecimal totalCostPrice, BigDecimal totalProceedsPrice, BigDecimal marketValue, BigDecimal currentPrice) {
        return marketValue.add(totalProceedsPrice).subtract(totalCostPrice).divide(currentPrice, 2, RoundingMode.HALF_EVEN).multiply(new BigDecimal(100));
    }

    public static BigDecimal getValueChange(BigDecimal totalCostPrice, BigDecimal totalProceedsPrice, BigDecimal marketValue) {
        return marketValue.add(totalProceedsPrice).subtract(totalCostPrice).setScale(2, RoundingMode.HALF_EVEN);
    }


}
