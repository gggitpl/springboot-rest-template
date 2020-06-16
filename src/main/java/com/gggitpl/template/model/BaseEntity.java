package com.gggitpl.template.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

/**
 * 所有实体类的父类
 *
 * @author gggitpl
 */
@Data
@FieldNameConstants
public abstract class BaseEntity implements Serializable {

    @TableId
    private Long id;

    @TableField(fill = FieldFill.INSERT)
    private Long createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateTime;

    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Boolean deleted;
}
