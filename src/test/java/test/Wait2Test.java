package test;

import org.junit.Test;

public class Wait2Test {

    @Test
    public void aa() throws InterruptedException {
        new Thread(new LaRun()).start();
        new Thread(new LaRun()).start();
        new Thread(new LaRun()).start();
        new Thread(new LaRun()).start();
        new Thread(new LaRun()).start();
        new Thread(new LaRun()).start();
        new Thread(new LaRun()).start();
        new Thread(new LaRun()).start();
        new Thread(new LaRun()).start();
        while (!Thread.interrupted()) ;
    }
}
