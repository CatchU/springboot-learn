package com.catchu.routers;


import com.catchu.constants.XDBConstant;

/**
 * 分表路由
 */
public class XDBTableSourceModRouter extends XDBRouter<Object[], String> {
    @Override
    public String getRouterIndexW(Object[] params) {
        Long routingKey = Long.valueOf(params[0].toString());
        String tableName = params[1].toString();
        Integer tbCount = Integer.valueOf(XDBConstant.KOALA_DISDB_PROPERTIES.getProperty(XDBConstant.KOALA_DISDB_TBCOUNT.replace("{}", tableName)));
        Integer index = (int) (routingKey % tbCount);
        tableName += "_" + index;
        return tableName;
    }

    private XDBTableSourceModRouter() {
    }

    private static class Assistant {
        final static XDBTableSourceModRouter router = new XDBTableSourceModRouter();
    }

    public static XDBTableSourceModRouter getInstance() {
        return Assistant.router;
    }
}
