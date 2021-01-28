package com.tabatatimer.misc;


import android.os.Handler;



public class LoadingAnimationThread implements Runnable {

    @SuppressWarnings("FieldCanBeLocal")
    private final int SLEEP_TIME = 1500;

    private Thread   mThread;
    private boolean  mStopThread;
    private Handler  mHandler;
    private Runnable mRunnable;


    public LoadingAnimationThread(Handler handler, Runnable runnable) {

        super();
        mThread     = new Thread(this, "LoadingAnimationThread");
        mHandler    = handler;
        mRunnable   = runnable;
        mStopThread = false;
    }


    @Override
    public void run() {

        do {
            mHandler.post(mRunnable);

            try {
                Thread.sleep(SLEEP_TIME);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while (!mStopThread);
    }


    public void start() {

        mThread.start();
    }


    public void stop() throws
                       InterruptedException {

        mStopThread = true;
        mThread.join();
    }

}
