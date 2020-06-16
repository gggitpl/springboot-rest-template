package com.gggitpl.template.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.gggitpl.template.model.BaseEntity;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * mybatis-plus统一设置创建时间/更新时间
 *
 * @author gggitpl
 */
@Component
public class ModelMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        long currentTimeMillis = System.currentTimeMillis();
        this.setFieldValByName(BaseEntity.Fields.createTime, currentTimeMillis, metaObject);
        this.setFieldValByName(BaseEntity.Fields.updateTime, currentTimeMillis, metaObject);
        this.setFieldValByName(BaseEntity.Fields.deleted, false, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName(BaseEntity.Fields.updateTime, System.currentTimeMillis(), metaObject);
    }
}
