package test;

public class LaRun implements Runnable {
    static Object o = new Object();
    static int i;

    @Override
    public void run() {
        synchronized (o) {
            try {
                System.out.println(String.format("== %s ==", i));
                int i1 = i;
                i++;
                if (i1 < 3) {
                    o.wait();
                } else {
                    o.notify();
                }
                System.out.println(String.format("== i=%s, i1=%s ==", i, i1));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
