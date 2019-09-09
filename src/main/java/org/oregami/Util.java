package org.oregami;

public class Util {

    public static void waitSomeTime() {
        int seconds = 3;
        try {
            System.out.println("waiting for " + seconds + " seconds...");
            for (int i = 0; i < seconds; i++) {
                Thread.sleep( 1000);
                System.out.println((i+1));
            }
            System.out.println("finished waiting!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
