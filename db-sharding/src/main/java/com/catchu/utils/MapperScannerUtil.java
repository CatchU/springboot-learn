package com.catchu.utils;

import com.catchu.constants.XDBConstant;
import org.apache.ibatis.annotations.Mapper;
import org.reflections.Reflections;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MapperScannerUtil {

    private ConcurrentHashMap<String, Class<?>> mappers = null;
    private String[] packages;

    private MapperScannerUtil() {
        mappers = new ConcurrentHashMap<>();
    }

    private static class Assistant {
        final static MapperScannerUtil scanner = new MapperScannerUtil();
    }

    public static MapperScannerUtil getInstance() {
        return Assistant.scanner;
    }

    public ConcurrentHashMap<String, Class<?>> getMappers() {
        return mappers;
    }

    /**
     * 扫描所有mybatis的mapper文件
     * @param packages
     */
    public void scanMappers(String... packages) {
        if (Objects.isNull(packages)) {
            packages = new String[]{XDBConstant.DEFAULT_DISDB_MAPPER_SCAN_PACKAGE};
        }
        for (String packageName : packages) {
            Reflections reflections = new Reflections(packageName);
            Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(Mapper.class);
            Iterator<Class<?>> it = classSet.iterator();
            while (it.hasNext()) {
                Class<?> clazz = it.next();
                mappers.put(clazz.getName(), clazz);
            }
        }

    }

}
