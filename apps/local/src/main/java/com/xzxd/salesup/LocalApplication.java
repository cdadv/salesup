package com.xzxd.salesup;

import com.xzxd.salesup.listeners.HidListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LocalApplication {
    public static void main(String[] args) {
        // Starting hid listener to monitor all the input from hid devices
        HidListener barcodeScannerListener = new HidListener(0x1d57, 0x001c);
        barcodeScannerListener.listen();

        SpringApplication.run(LocalApplication.class, args);
    }
}
