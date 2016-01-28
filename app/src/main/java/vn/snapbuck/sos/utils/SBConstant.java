package vn.snapbuck.sos.utils;

/**
 * Created by sb04 on 1/28/16.
 */
public class SBConstant {

    public static final String SERVER_BASE_URL = "http://www.yourvoice.vn/";
    public static final int DATABASE_VERSION = 1;

    //SECURITY
    public static final int MAX_LENGTH = 320;
    public static final byte[] KEY = {0x7b, 0x44, 0x64, 0x6a, 0x3b, 0x6a,
            0x42, 0x7e, 0x73, 0x2a, 0x69, 0x05, 0x34, 0x1e, 0x58, 0x03};

    //NOTIFICATION
    public static final String PROPERTY_REG_ID = "registration_id";
    public static final String PROPERTY_APP_VERSION = "appVersion";
    public static final String SENDER_ID = "445446113575";
    public static final int MESSAGE_NOTIFICATION_ID = 150190;

    public static final int TIME_OUT = 30000;
    public static final int TIME_OUT_10 = 10000;

    public static final int BUFFER_SIZE = 1024;

    public static final String ANDROID_PLATFORM = "Android";
    public static final String AMAZON_PLATFORM = "amazon-fireos";
    public static final String AMAZON_DEVICE = "Amazon";

}
