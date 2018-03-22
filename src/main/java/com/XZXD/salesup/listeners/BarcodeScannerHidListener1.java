package com.XZXD.salesup.listeners;

import org.hid4java.*;
import org.hid4java.event.HidServicesEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

@Component
public class BarcodeScannerHidListener1 implements HidServicesListener {
    /*
    TODO: hid4java library is not working very well... and it's hard to convert the HID byte code to key names.
    https://github.com/gary-rowe/hid4java
     */
    private static final Logger logger = LoggerFactory.getLogger(BarcodeScannerHidListener1.class);

    private static final int PACKET_LENGTH = 64;

    public class ScanTask extends TimerTask {
        HidDevice barcodeScanner;

        ScanTask(HidDevice barcodeScanner) {
            this.barcodeScanner = barcodeScanner;
        }

        @Override
        public void run() {
            byte inBuffer[] = new byte[PACKET_LENGTH];
            int val = barcodeScanner.read(inBuffer);
            switch (val) {
                case -1:
                    //System.err.println(currentDevice.getLastErrorMessage()); always throw null
                    break;
                case 0:
                    break;
                default:
                    if (inBuffer != null) {
                        logger.info(Arrays.toString(inBuffer));
                    }
                    break;
            }
        }
    }

    public void execute() {
        // Configure to use custom specification
        HidServicesSpecification hidServicesSpecification = new HidServicesSpecification();
        hidServicesSpecification.setAutoShutdown(true);
        hidServicesSpecification.setScanInterval(500);
        hidServicesSpecification.setPauseInterval(5000);
        hidServicesSpecification.setScanMode(ScanMode.SCAN_AT_FIXED_INTERVAL_WITH_PAUSE_AFTER_WRITE);

        // Get HID services using custom specification
        HidServices hidServices = HidManager.getHidServices(hidServicesSpecification);
        hidServices.addHidServicesListener(this);

        // Start the services
        logger.info("Starting HID services.");
        hidServices.start();

        logger.info("Enumerating attached devices...");

        // Provide a list of attached devices
        for (HidDevice hidDevice : hidServices.getAttachedHidDevices()) {
            if (hidDevice.getVendorId() == Short.valueOf("7511")) {
                HidDevice barcodeScanner = hidServices.getHidDevice(hidDevice.getVendorId(), hidDevice.getProductId(), hidDevice.getSerialNumber());
                if (barcodeScanner != null) {
                    Timer timer = new Timer();
                    ScanTask scanTask = new ScanTask(barcodeScanner);
                    timer.schedule(scanTask, 0, 100);
                }
            }
        }

        logger.info("Sleep starts");
        sleepUninterruptibly(30, TimeUnit.SECONDS);
//        hidServices.shutdown();
    }
    @Override
    public void hidDeviceAttached(HidServicesEvent event) {

    }

    @Override
    public void hidDeviceDetached(HidServicesEvent event) {

    }

    @Override
    public void hidFailure(HidServicesEvent event) {

    }

    /**
     * Invokes {@code unit.}{@link java.util.concurrent.TimeUnit#sleep(long) sleep(sleepFor)}
     * uninterruptibly.
     */
    public static void sleepUninterruptibly(long sleepFor, TimeUnit unit) {
        boolean interrupted = false;
        try {
            long remainingNanos = unit.toNanos(sleepFor);
            long end = System.nanoTime() + remainingNanos;
            while (true) {
                try {
                    // TimeUnit.sleep() treats negative timeouts just like zero.
                    NANOSECONDS.sleep(remainingNanos);
                    return;
                } catch (InterruptedException e) {
                    interrupted = true;
                    remainingNanos = end - System.nanoTime();
                }
            }
        } finally {
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
