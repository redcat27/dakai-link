package www.dakai.link.common.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.Random;

public class PasswordUtil {

    /**
     * 生成6位字母+数字密码
     *
     * @return
     */
    public static String getRandomPassword() {

        Random random = new Random();

        int totalLength = 6;
        int intRandom = random.nextInt(4) + 1; // 至少1个数字，至多5个
        int charRandom = totalLength - intRandom;

        StringBuilder intSb = new StringBuilder();
        for (int i = 0; i < intRandom; i++) {
            intSb.append(random.nextInt(10));
        }
        for (int i = 0; i < charRandom; i++) {
            intSb.append((char) (97 + random.nextInt(26)));
        }
        int sort = random.nextInt(2) % 2;
        if (sort == 0) {
            intSb.reverse();
        }
        return intSb.toString();

    }

    public static String md5Password(String password) {
        return DigestUtils.md5DigestAsHex(password.getBytes()).toUpperCase();
    }

    /**
     * token生成
     *
     * @param username
     * @param password
     * @param permissions
     * @param others
     * @return
     */
    public static String md5Token(String username, String password, String permissions, String... others) {
        StringBuilder tokenKey = new StringBuilder(username + "//" + password + "//" + permissions);
        if (ArrayUtils.isNotEmpty(others)) {
            for (String other : others) {
                if (StringUtils.isNotBlank(other)) {
                    tokenKey.append("//").append(other);
                }
            }
        }
        return DigestUtils.md5DigestAsHex(tokenKey.toString().getBytes()).toUpperCase();
    }

}

