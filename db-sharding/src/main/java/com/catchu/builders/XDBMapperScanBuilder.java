package com.catchu.builders;

import com.catchu.constants.XDBConstant;
import com.catchu.utils.MapperScannerUtil;
import com.google.common.base.Strings;

/**
 * MAPPER SCAN 创建者
 */
public class XDBMapperScanBuilder extends XDBBuilder<String> {
    @Override
    public void build(String params) {
        String packages = XDBConstant.KOALA_DISDB_PROPERTIES.getProperty(XDBConstant.KOALA_DISDB_MAPPER_SCAN_PACKAGES);
        String[] packageNames = Strings.isNullOrEmpty(packages) ? null : packages.split(",");
        MapperScannerUtil.getInstance().scanMappers(packageNames);
    }
}
