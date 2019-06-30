package util.mask;

import java.security.MessageDigest;

/**
 * @author hum
 */
public class Mask {
    private static final int ID_LEN15 = 15;
    private static final int ID_LEN18 = 18;
    private static final int ID_START = 6;
    private static final int ID_LEN15_END = 12;
    private static final int ID_LEN18_END = 14;
    private static final String PHONE_MASK = "****";
    private static final int PHONE_LEN = 11;
    private static final int PHONE_START = 4;
    private static final int PHONE_END = 8;
    private static final String EMAIL_MASK = "*";

    /**
     * mask yyyymmdd of Id with MD5Self
     *
     * @param str id str
     * @return mask id str
     */
    public static String getIdMask(String str) {
        StringBuilder stringBuilder = new StringBuilder(str.trim());
        if (stringBuilder.length() == ID_LEN18) {
            stringBuilder.replace(ID_START, ID_LEN18_END, getMd5(str.substring(ID_START, ID_LEN18_END)));
        } else if (stringBuilder.length() == ID_LEN15) {
            stringBuilder.replace(ID_START, ID_LEN15_END, getMd5(str.substring(ID_START, ID_LEN15_END)));
        }
        return stringBuilder.toString();
    }

    /**
     * mask mid four num of Phone with ****
     *
     * @param str phone str
     * @return mask phone str
     */
    public static String getPhoneMask(String str) {
        StringBuilder stringBuilder = new StringBuilder(str.trim());
        if (stringBuilder.length() == PHONE_LEN) {
            stringBuilder.replace(PHONE_START, PHONE_END, PHONE_MASK);
        }
        return stringBuilder.toString();
    }

    /**
     * mask all str before character '@' of email with *
     *
     * @param str email str
     * @return mask email str
     */
    public static String getEmailMask(String str) {
        StringBuilder stringBuilder = new StringBuilder(str.trim());
        int index = stringBuilder.lastIndexOf("@");
        if (index != -1) {
            stringBuilder.replace(0, index, EMAIL_MASK);
        }
        return stringBuilder.toString();
    }

    /**
     * get MD5 str
     * change two index of the str to avoid decode
     *
     * @param str str
     * @return MD5 str
     */
    private static String getMd5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] hash = md.digest();
            StringBuilder secPwd = new StringBuilder();
            for (byte aHash : hash) {
                int v = aHash & 0xFF;
                if (v < 16) {
                    secPwd.append(0);
                }
                secPwd.append(Integer.toString(v, 16));
            }
            char tmp = secPwd.charAt(ID_LEN15_END);
            secPwd.setCharAt(ID_LEN15_END, secPwd.charAt(ID_START));
            secPwd.setCharAt(ID_START, tmp);
            return secPwd.toString();
        } catch (Exception e) {
            return str;
        }
    }
}
