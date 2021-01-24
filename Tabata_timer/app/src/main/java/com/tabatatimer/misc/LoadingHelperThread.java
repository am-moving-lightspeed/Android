package com.tabatatimer.misc;


import android.os.Handler;



public class LoadingHelperThread implements Runnable {

    @SuppressWarnings("FieldCanBeLocal")
    private final int SLEEP_TIME = 1500;

    private Thread   mThread;
    private boolean  mStopThread;
    private Handler  mHandler;
    private Runnable mRunnable;


    public LoadingHelperThread(Handler handler, Runnable runnable) {

        super();
        mThread     = new Thread(this, "LoadingHelperThread");
        mHandler    = handler;
        mRunnable   = runnable;
        mStopThread = false;
    }


    @Override
    public void run() {

        do {
            try {
                Thread.sleep(SLEEP_TIME);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

            mHandler.post(mRunnable);
        }
        while (!mStopThread);
    }


    public void start() {

        mThread.start();
    }


    public void stop() {

        mStopThread = true;
    }

}
