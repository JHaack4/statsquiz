package com.jerhis.statsquiz;


public class PlatformHandeler {

    public PlatformInterface platformInterface;

    public PlatformHandeler(PlatformInterface platformInterface) {
        this.platformInterface = platformInterface;
    }


    public int lastWakeLockMessenger = 0;
    public final void wakeLock(int k) {
        if (k != lastWakeLockMessenger) {
            platformInterface.wakeLockMessenger(k);
            lastWakeLockMessenger = k;
        }
    }

}
