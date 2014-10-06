package ru.ifmo.md.lesson4;

import android.os.Handler;

/**
 * Created by eugene on 10/6/14.
 */
public class LongClickOnBackspaceButton implements Runnable {
    private Thread t;
    private Handler h;
    public boolean f;

    LongClickOnBackspaceButton(Handler h2) {
       t = new Thread(this);
       h = h2;
       t.start();
    }

    @Override
    public void run() {
        while (!f) {
            h.sendEmptyMessage(1);
            try {
                t.sleep(200);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }
}
