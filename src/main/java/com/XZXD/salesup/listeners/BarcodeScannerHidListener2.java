package com.XZXD.salesup.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import purejavahidapi.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public class BarcodeScannerHidListener2 {
    /*
    TODO: purejavahidapi library works the best by now. Need to hardcode the mapping between HID byte code and keyboard usage names.
    Chapter10 http://www.usb.org/developers/hidpage/Hut1_12v2.pdf
     */
    private static final Logger logger = LoggerFactory.getLogger(BarcodeScannerHidListener2.class);

    private final Charset UTF8_CHARSET = Charset.forName("UTF-8");

    private int count = 0;

    public void execute() {
        List<HidDeviceInfo> devList = PureJavaHidApi.enumerateDevices();
        HidDeviceInfo devInfo = null;
        for (HidDeviceInfo info : devList) {
            if (info.getVendorId() == (short)0x1d57 && info.getProductId() == (short)0x001c) {
                devInfo = info;
                break;
            }
        }

        HidDevice dev= null;
        try {
            dev = PureJavaHidApi.openDevice(devInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        dev.setInputReportListener((source, Id, data, len) -> {
69352            logger.info(String.format("onInputReport: id %d len %d data ", Id, len));
            for (int i = 0; i < len; i++)
                logger.info(String.format("%02X ", data[i]
                ));
            logger.info("");
            count++;
        });
    }
}
