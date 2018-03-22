package com.XZXD.salesup.listeners;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Queue;


@Service
public class BarcodeScannerHidListener0 implements NativeKeyListener{
    /*
    TODO: JNativeHook library is not able to identify different input sources...
    https://github.com/kwhat/jnativehook/issues/118
     */
    private static final Logger logger = LoggerFactory.getLogger(BarcodeScannerHidListener0.class);
    // Empty the queue when detect enter
    private static Queue<String> characterQueue = new LinkedList<>();
    private static Queue<String> stringQueue = new LinkedList<>();

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
//        logger.info("Key Typed: " + NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()));
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
//        logger.info("Key Pressed: " + NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()));
//        logger.info(nativeKeyEvent.paramString());
        String currentCharacter = NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode());
        if (!currentCharacter.equals("Enter")) {
            characterQueue.add(currentCharacter);
        } else {
            StringBuffer sb = new StringBuffer();
            while (!characterQueue.isEmpty()) {
                sb.append(characterQueue.remove());
            }
            stringQueue.add(sb.toString());
            logger.info(sb.toString());
        }
        // Handle error
        if (nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException e1) {
                logger.error(e1.getMessage());
            }
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
//        logger.info("Key Released: " + NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()));
    }
}
