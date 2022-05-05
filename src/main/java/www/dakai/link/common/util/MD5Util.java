package www.dakai.link.common.util;

import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.io.InputStream;

public class MD5Util {
    public static String md5(String str) {
        return DigestUtils.md5DigestAsHex(str.getBytes()).toUpperCase();
    }

    public static String md5(InputStream inputStream) throws IOException {
        return DigestUtils.md5DigestAsHex(inputStream).toUpperCase();
    }

    public static String md5(byte[] bytes) {
        return DigestUtils.md5DigestAsHex(bytes).toUpperCase();
    }
}
