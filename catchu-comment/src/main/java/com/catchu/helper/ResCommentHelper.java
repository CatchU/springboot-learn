package com.catchu.helper;

import com.catchu.beans.enums.ResourceTypeEnum;
import com.catchu.factorys.XIDWorkerFactory;
import com.catchu.routers.XDBDataSourceModRouter;
import com.catchu.routers.XDBTableSourceModRouter;
import com.catchu.utils.XDBUtil;
import org.apache.commons.lang3.time.DateFormatUtils;
import java.util.Date;

public class ResCommentHelper {

    public static final String TABLE_NAME = "resource_comments_record";

    // 生成库路由ID
    public long generateRouteId4Db(long resourceId, int resourceType) {
        return XDBUtil.getDataSourceModRouterKey(resourceId, resourceType);
    }

    // 生成表路由ID
    public long generateRouteId4Tb(long resourceId, int resourceType) {
        return XDBUtil.getTableSourceModRouterKey(resourceId, resourceType);
    }

    // 生成库号
    public int generateDbNumber(long resourceId, int resourceType) {
        long dbRouteId = generateRouteId4Db(resourceId, resourceType);

        return XDBDataSourceModRouter.getInstance().getRouterIndexW(dbRouteId);
    }

    // 生成表名
    public String generateTableName(long resourceId, int resourceType) {
        long tbRouteId = generateRouteId4Tb(resourceId, resourceType);

        return XDBTableSourceModRouter.getInstance().getRouterIndexW(new Object[]{tbRouteId, TABLE_NAME});
    }

    // 生成主键
    public long generatePrimaryKey(long resourceId, int resourceType) {
        long dbRouteId = generateRouteId4Db(resourceId, resourceType);

        return XIDWorkerFactory.getInstance().getXSnowFlakeIDWorker().nextId(dbRouteId);
    }

    // 生成ES索引
    public String generateEsIndex(ResourceTypeEnum rt, Date createTime) {
        return String.format("index_comment_%s_%s", rt.name().toLowerCase(), DateFormatUtils.format(createTime, "yyyy"));
    }

    // 生成ES类型
    public String generateEsType(String esIndex) {
        return esIndex.substring(6, esIndex.lastIndexOf("_"));
    }
}
