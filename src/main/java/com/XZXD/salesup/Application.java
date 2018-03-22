package com.XZXD.salesup;

import com.XZXD.salesup.listeners.BarcodeScannerHidListener1;
import com.XZXD.salesup.listeners.BarcodeScannerHidListener2;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
public class Application {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
//        try {
//            Logger jNativeHookLogger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
//            jNativeHookLogger.setLevel(Level.OFF);
//            GlobalScreen.registerNativeHook();
//        }
//        catch (NativeHookException ex) {
//            logger.error("There was a problem registering the native hook.");
//            logger.error(ex.getMessage());
//
//            System.exit(1);
//        }
//        GlobalScreen.addNativeKeyListener(new BarcodeScannerListener());

//        BarcodeScannerHidListener1 barcodeScannerHidListener1 = new BarcodeScannerHidListener1();
//        barcodeScannerHidListener1.execute();

        BarcodeScannerHidListener2 barcodeScannerHidListener2 = new BarcodeScannerHidListener2();
        barcodeScannerHidListener2.execute();

        SpringApplication.run(Application.class, args);
    }
}
