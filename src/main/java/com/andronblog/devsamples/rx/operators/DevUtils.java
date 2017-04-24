package com.andronblog.devsamples.rx.operators;


public class DevUtils {

    private static String getTid() {
        return String.valueOf(Thread.currentThread().getId());
    }

    public static void println(String out) {
        System.out.println(getTid() + "|" + out);
    }
}
