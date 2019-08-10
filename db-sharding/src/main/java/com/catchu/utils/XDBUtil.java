package com.catchu.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 分库分表工具类
 *
 */
public class XDBUtil {

    public static Long getDataSourceModRouterKey(Long resourceId, Integer resourceType) {
        if (null == resourceId && null == resourceType) {
            return -1L;
        }
        String routerKey = "" + resourceId + resourceType;
        String s = DigestUtils.md5Hex(routerKey);
        int i = s.hashCode();
        int abs = Math.abs(i);
        return (long) abs;
    }

    public static int getTableSourceModRouterKey(Long resourceId, Integer resourceType) {
        if (null == resourceId && null == resourceType) {
            return -1;
        }
        String routerKey = "" + resourceId + resourceType;
        String s = DigestUtils.md5Hex(routerKey);
        int a;
        return (a = Math.abs(s.hashCode())) ^ a >>> 16;
    }

    public static void main(String[] args) {
        System.out.println(getTableSourceModRouterKey(299L, 1) % 64);
    }
}
