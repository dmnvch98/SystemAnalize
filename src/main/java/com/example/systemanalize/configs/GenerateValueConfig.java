package com.example.systemanalize.configs;

import java.math.BigDecimal;

public class GenerateValueConfig {
    private final BigDecimal R;
    private final BigDecimal a;
    private final BigDecimal m;

    private final int loopSize;

    public GenerateValueConfig(String R, String a, String m, int loopSize) {
        this.R = new BigDecimal(R);
        this.a = new BigDecimal(a);
        this.m = new BigDecimal(m);
        this.loopSize = loopSize;
    }

    public BigDecimal getR() {
        return R;
    }

    public BigDecimal getA() {
        return a;
    }

    public BigDecimal getM() {
        return m;
    }

    public int getLoopSize() {
        return loopSize;
    }
}
