package test;

import org.junit.Test;

public class WaitTest {

    @Test
    public void aa() throws InterruptedException {
        final Object o = new Object();
        new Thread(() -> {
            synchronized (o) {
                System.out.println("============== 3 ==============");
                try {
                    o.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("============== 4 ==============");
            }
        }).start();
        new Thread(() -> {
            synchronized (o) {
                try {
                    System.out.println("============== 1 ==============");
                    o.notify();
                    Thread.sleep(1000);
                    System.out.println("============== 2 ==============");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        while (!Thread.interrupted()) ;
    }

    @Test
    public synchronized void bb() throws InterruptedException {
        notify();
        new Thread(new MyRun()).start();
        new Thread(new MyRun()).start();
        while (!Thread.interrupted()) ;
    }

    class MyRun implements Runnable {
        int i = 0;

        @Override
        public synchronized void run() {
            try {
                System.out.println(String.format("=== %s ===", i++));
                new Thread(this).start();
                notify();
                Thread.sleep(500);
                wait();
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
