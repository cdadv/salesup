package com.XZXD.salesup;

import com.XZXD.salesup.listeners.BarcodeScannerListener;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
@EnableResourceServer
public class Application {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        try {
            Logger jNativeHookLogger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
            jNativeHookLogger.setLevel(Level.OFF);
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            logger.error("There was a problem registering the native hook.");
            logger.error(ex.getMessage());

            System.exit(1);
        }
        GlobalScreen.addNativeKeyListener(new BarcodeScannerListener());
        SpringApplication.run(Application.class, args);
    }
}
