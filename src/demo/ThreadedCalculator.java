package demo;

import java.util.function.DoubleUnaryOperator;

public class ThreadedCalculator implements Runnable {
    private IntegralCalculator calculator;
    private Main main;

    public ThreadedCalculator(Main main, double a, double b, int n, DoubleUnaryOperator f) {
        calculator = new IntegralCalculator(a, b, n, f);
        this.main = main;
    }

    @Override
    public void run() {
        double v = calculator.calculate();
        main.sendResult(v);
    }
}
