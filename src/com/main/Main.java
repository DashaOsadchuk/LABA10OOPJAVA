package com.main;

public class Main {

    double totalResult;
    int finished;

    public static void main(String[] args) {
	    new Main().run();
    }

    private void run() {
        double a = 1.0;
        double b = 2.0;
        int n = 100_000_000;
        int nThreads = 100;
        finished = 0;
        totalResult = 0;
        long start = System.currentTimeMillis();
        double delta = (b-a)/nThreads;
        int ni = n / nThreads;
        for (int i = 0; i<nThreads; i++) {
            double ai = a + i*delta;
            double bi = a + (i+1)*delta;
            ThreadedCalculator calculator = new ThreadedCalculator(this, ai, bi, ni, this::f);
            new Thread(calculator).start();
        }
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
        System.out.println("Итог = " + totalResult);
        System.out.println(finish-start);
    }

    public double f(double x) {
        return (Math.pow (Math.E,x)-1)/(Math.pow (Math.E,x)+1);
    }

    public synchronized void sendResult(double res) {
        finished++;
        totalResult += res;
        notify();
    }
}

