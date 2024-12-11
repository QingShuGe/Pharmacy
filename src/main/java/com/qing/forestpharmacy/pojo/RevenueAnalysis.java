package com.qing.forestpharmacy.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author baomidou
 * @since 2024-12-10
 */
@Getter
@Setter
@TableName("revenue_analysis")
public class RevenueAnalysis implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 记录唯一标识
     */
    private Long id;

    /**
     * 药品唯一标识
     */
    private Long medicineId;

    /**
     * 年份
     */
    @TableField("`year`")
    private Integer year;

    /**
     * 月份
     */
    @TableField("`month`")
    private Integer month;

    /**
     * 总销售额
     */
    private Long saleIncome;

    /**
     * 收益总数
     */
    private BigDecimal totalEarnings;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDate createTime;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private Long creator;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDate updateTime;

    /**
     * 更新人
     */
    @TableField(fill = FieldFill.UPDATE)
    private Long updateUser;

    /**
     * 备注
     */
    private String notes;

    /**
     * 是否删除（0 - 正常，1 - 停用）
     */
    private Boolean isDeleted;
}
