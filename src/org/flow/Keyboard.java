package org.flow;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import lombok.NonNull;

@NonNull
public class Keyboard implements NativeKeyListener {

    public void nativeKeyPressed(NativeKeyEvent e){
        system.lastPressedKey = e.getKeyCode();
    }

    public void nativeKeyReleased(NativeKeyEvent e){
        system.lastPressedKey = 0;
    }

    public static void start(){
        try {
            GlobalScreen.registerNativeHook();
        }catch (NativeHookException e){
            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new Keyboard());
    }
}
