package com.xzxd.salesup.listeners.archive;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.Queue;

public class KeyboardListener_jnativehook implements NativeKeyListener {
    /*
    TODO: JNativeHook library is not able to identify different input sources...
    https://github.com/kwhat/jnativehook/issues/118
     */
    private static final Logger logger = LoggerFactory.getLogger(KeyboardListener_jnativehook.class);
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


//    // HOW TO START WITH MAIN CLASS
//    public static void main(String[] args) {
//        try {
//            // Logger below should use java.util.logging package
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
//        GlobalScreen.addNativeKeyListener(new KeyboardListener_jnativehook());
//
//        SpringApplication.run(Application.class, args);
//    }
}
