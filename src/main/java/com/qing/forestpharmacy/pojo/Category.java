package com.qing.forestpharmacy.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author baomidou
 * @since 2024-12-09
 */
@Getter
@Setter
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 药品类型的唯一标识
     */
    private Long id;

    /**
     * 上级分类
     */
    private Long fatherId;

    /**
     * 药品类型名称，如感冒药、消炎药等
     */
    private String typeName;

    /**
     * 对药品类型的备注信息，可用于记录一些特殊情况或额外说明等
     */
    private String remark;

    /**
     * 顺序
     */
    private Integer sort;

    /**
     * 0表示启用，1表示删除
     */
    private Byte isDeleted;

    /**
     * 记录药品类型数据的创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 创建该药品类型数据的用户标识
     */
    @TableField(fill = FieldFill.INSERT)
    private Long creator;

    /**
     * 记录药品类型数据的最后更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    /**
     * 最后更新该药品类型数据的用户标识
     */
    @TableField(fill = FieldFill.UPDATE)
    private Long updateUser;
}
