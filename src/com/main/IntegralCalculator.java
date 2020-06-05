package com.main;

import java.util.function.DoubleUnaryOperator;

public class IntegralCalculator {
    private double a;
    private double b;
    private int n;
    private DoubleUnaryOperator f;

    public IntegralCalculator(double a, double b, int n, DoubleUnaryOperator f) {
        this.a = a;
        this.b = b;
        this.n = n;
        this.f = f;
    }

    public double calculate() {
        double h = (b-a)/n;
        double sum = 0.5*f.applyAsDouble(a)+0.5*f.applyAsDouble(a+n*h);
        for (int i = 1; i < n-1; i++) {
            double x = a+i*h;
            sum+=f.applyAsDouble(x);
        }
        for (int i = 1; i < n; i++) {
            double x = a + i * h;
            double x_ = a+(i-1)*h;
            sum += (f.applyAsDouble(x_)+f.applyAsDouble(x));
        }
        return sum*h/3;
    }
}
