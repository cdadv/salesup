package com.XZXD.salesup.listeners;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class BarcodeScannerListener implements NativeKeyListener{
    private static final Logger logger = LoggerFactory.getLogger(BarcodeScannerListener.class);

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
        logger.info("Key Typed: " + NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()));
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        logger.info("Key Pressed: " + NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()));
        logger.info(nativeKeyEvent.paramString());
        if (nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
        logger.info("Key Released: " + NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()));
    }
}
