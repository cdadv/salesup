package com.xzxd.salesup.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import purejavahidapi.HidDevice;
import purejavahidapi.HidDeviceInfo;
import purejavahidapi.InputReportListener;
import purejavahidapi.PureJavaHidApi;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HidListener {
    /*
    TODO: purejavahidapi library works the best by now. Need to hardcode the mapping between HID byte code and keyboard usage names.
    Chapter10 http://www.usb.org/developers/hidpage/Hut1_12v2.pdf
     */
    private static final Logger logger = LoggerFactory.getLogger(HidListener.class);

    private int vendorIdHex;
    private int productIdHex;
    // mapping decimal usage id to usage name
    private Map<Integer, String> usageIdUsageNameMap;
    private StringBuffer sb;

    public HidListener(int vendorIdHex, int productIdHex) {
        this.vendorIdHex = vendorIdHex;
        this.productIdHex = productIdHex;
        this.usageIdUsageNameMap = new HashMap<>();
        // a-z characters
        usageIdUsageNameMap.put(4, "a");
        usageIdUsageNameMap.put(5, "b");
        usageIdUsageNameMap.put(6, "c");
        usageIdUsageNameMap.put(7, "d");
        usageIdUsageNameMap.put(8, "e");
        usageIdUsageNameMap.put(9, "f");
        usageIdUsageNameMap.put(10, "g");
        usageIdUsageNameMap.put(11, "h");
        usageIdUsageNameMap.put(12, "i");
        usageIdUsageNameMap.put(13, "j");
        usageIdUsageNameMap.put(14, "k");
        usageIdUsageNameMap.put(15, "l");
        usageIdUsageNameMap.put(16, "m");
        usageIdUsageNameMap.put(17, "n");
        usageIdUsageNameMap.put(18, "o");
        usageIdUsageNameMap.put(19, "p");
        usageIdUsageNameMap.put(20, "q");
        usageIdUsageNameMap.put(21, "r");
        usageIdUsageNameMap.put(22, "s");
        usageIdUsageNameMap.put(23, "t");
        usageIdUsageNameMap.put(24, "u");
        usageIdUsageNameMap.put(25, "v");
        usageIdUsageNameMap.put(26, "w");
        usageIdUsageNameMap.put(27, "x");
        usageIdUsageNameMap.put(28, "y");
        usageIdUsageNameMap.put(29, "z");

        // numerical numbers
        usageIdUsageNameMap.put(30, "1");
        usageIdUsageNameMap.put(31, "2");
        usageIdUsageNameMap.put(32, "3");
        usageIdUsageNameMap.put(33, "4");
        usageIdUsageNameMap.put(34, "5");
        usageIdUsageNameMap.put(35, "6");
        usageIdUsageNameMap.put(36, "7");
        usageIdUsageNameMap.put(37, "8");
        usageIdUsageNameMap.put(38, "9");
        usageIdUsageNameMap.put(39, "0");
        
        // function keys
        usageIdUsageNameMap.put(40, "RETURN");
//        usageIdUsageNameMap.put(41, "ESCAPE");
//        usageIdUsageNameMap.put(42, "DELETE");
//        usageIdUsageNameMap.put(43, "TAB");
//        usageIdUsageNameMap.put(44, "SPACE");
//        usageIdUsageNameMap.put(45, "-");
//        usageIdUsageNameMap.put(46, "=");
//        usageIdUsageNameMap.put(47, "[");
//        usageIdUsageNameMap.put(48, "]");
//        usageIdUsageNameMap.put(49, "\\");
//        usageIdUsageNameMap.put(50, "#");
//        usageIdUsageNameMap.put(51, ";");
//        usageIdUsageNameMap.put(52, "'");
//        usageIdUsageNameMap.put(53, "`");
//        usageIdUsageNameMap.put(54, ",");
//        usageIdUsageNameMap.put(55, ".");
//        usageIdUsageNameMap.put(56, "/");

       this.sb = new StringBuffer();
    }

    public void listen() {
        List<HidDeviceInfo> devList = PureJavaHidApi.enumerateDevices();
        HidDeviceInfo devInfo = null;
        for (HidDeviceInfo info : devList) {
            if (info.getVendorId() == (short)vendorIdHex && info.getProductId() == (short)productIdHex) {
                devInfo = info;
                break;
            }
        }

        if (devInfo != null) {
            HidDevice dev= null;
            try {
                dev = PureJavaHidApi.openDevice(devInfo);
                logger.info("hid device is connected successfully");
            } catch (IOException e) {
                logger.error(String.format("failed to connect hid device: %s", e.getMessage()));
            }
            dev.setInputReportListener(new InputReportListener() {
                @Override
                public void onInputReport(HidDevice source, byte Id, byte[] data, int len) {
                    if (len != 8) {
                        logger.error(String.format("expect input report length 8, get %d", len));
                        throw new RuntimeException("Does not support hid devices that report data length other than 8");
                    }
                    for (int i = 0; i < len; i++) {
                        int decimalDigit = data[i] & 0xff;
                        // TODO: First digit can express pressed shift or not
                        if (i != 2) {
                            if (decimalDigit != 0) {
                                logger.error(String.format("expecting digits in position (0, 1, 3, 4, 5, 6, 7) of data are 0, get %d", data[i]));
                                throw new RuntimeException(String.format("expecting digits in position (0, 1, 3, 4, 5, 6, 7) of data are 0, get %d", data[i]));
                            }
                        } else {
                            if (usageIdUsageNameMap.containsKey(decimalDigit)) {
                                if (usageIdUsageNameMap.get(decimalDigit).equals("RETURN")) {
                                    // TODO: Anne's ticket
                                    logger.info(String.format("hid device input report: %s", sb.toString()));
                                    // clear string buffer for reusing
                                    sb.setLength(0);
                                } else {
                                    sb.append(usageIdUsageNameMap.get(decimalDigit));
                                }
                            } else if (decimalDigit != 0) {
                                logger.error("assuming scanner cannot provide digits other than a-z, 1-9, RETURN");
                                // TODO: Should wrap up RuntimeException as customized exception...
                                throw new RuntimeException("assuming scanner cannot provide digits other than a-z, 1-9, RETURN");
                            }
                        }
                    }
                }
            });
        } else {
            logger.warn(String.format("Cannot find hid device with vendorId %s and productId %s", this.vendorIdHex, this.productIdHex));
        }

    }
}