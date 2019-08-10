package com.catchu.routers;


import com.catchu.constants.XDBConstant;
import com.catchu.enums.XDBEnvEnums;

/**
 * 分库路由
 */
public class XDBDataSourceModRouter extends XDBRouter<Long, Integer> {
    @Override
    public Integer getRouterIndexW(Long params) {
        Integer dbCount = Integer.valueOf(XDBConstant.KOALA_DISDB_PROPERTIES.getProperty(XDBConstant.KOALA_DISDB_DBCOUNT.replace("{}", XDBEnvEnums.WRITE.getEnviromentType().toLowerCase())));
        Integer index = (int) (params % dbCount);
        return index;
    }

    private XDBDataSourceModRouter() {
    }

    private static class Assistant {
        final static XDBDataSourceModRouter router = new XDBDataSourceModRouter();
    }

    public static XDBDataSourceModRouter getInstance() {
        return Assistant.router;
    }
}
