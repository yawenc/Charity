package com.bertazoli.charity.shared.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;

import com.bertazoli.charity.shared.Constants;
import com.bertazoli.charity.shared.beans.Charity;

public class Util {
    public static boolean isNullOrEmpty(String string) {
        return (string == null || string.length() == 0);
    }

    public static String getEncryptedPassword(String password, String salt) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(Constants.ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update((password + salt).getBytes());
        byte[] mdbytes = md.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mdbytes.length; i++) {
            sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public static boolean isEmpty(Collection<?> list) {
        return list == null || list.isEmpty();
    }
}
