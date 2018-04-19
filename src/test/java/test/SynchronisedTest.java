package test;

import org.junit.Test;

public class SynchronisedTest {
    @Test
    public void aa() throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("== 1 ==");
                f();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("== 2 ==");
                f1();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("== 3 ==");
                f();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("== 4 ==");
                f1();
            }
        }).start();
        while (!Thread.interrupted()) ;
    }

    static int i = 0;

    static synchronized void f() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(String.format("============== %s ==============", i++));
    }

    synchronized void f1() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(String.format("============== %s ==============", i--));
    }
}
