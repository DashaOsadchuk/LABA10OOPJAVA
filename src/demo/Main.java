package demo;

public class Main {

    double totalResult;
    int finished;

    public static void main(String[] args) {
	    new Main().run();
    }

    private void run() {
        double a = 0.0;
        double b = Math.PI;
        // Math.sin(x)
        int n = 100_000_000;
//        IntegralCalculator calculator = new IntegralCalculator(a, b, n, this::f);
//        long start = System.currentTimeMillis();
//        double result = calculator.calculate();
//        long finish = System.currentTimeMillis();
//        System.out.println("result = " + result);
//        System.out.println(finish-start);
        int nThreads = 100;
        totalResult = 0;
        // 0 (0,Pi/10)
        // (Pi/10, 2*Pi/10)
        // (2*Pi/10, 3*Pi/10)
        // ...
        long start = System.currentTimeMillis();
        double delta = (b-a)/nThreads;
        int ni = n / nThreads;
        for (int i = 0; i<nThreads; i++) {
            double ai = a + i*delta;
            double bi = a + (i+1)*delta;
            ThreadedCalculator calculator = new ThreadedCalculator(this, ai, bi, ni, this::f);
            new Thread(calculator).start();
        }
        finished = 0;
        try {
            synchronized (this) {
                while (finished < nThreads) {
                    wait();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long finish = System.currentTimeMillis();
        System.out.println("totalResult = " + totalResult);
        System.out.println(finish-start);
    }

    public double f(double x) {
        return Math.sin(x);
    }

    public synchronized void sendResult(double res) {
        finished++;
        totalResult += res;
        notify();
    }
}

