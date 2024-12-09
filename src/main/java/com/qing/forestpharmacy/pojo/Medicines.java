package com.qing.forestpharmacy.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
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
public class Medicines implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 药品ID
     */
    private Long id;

    /**
     * 药品类型id
     */
    private Long categoryId;

    /**
     * 药品名称
     */
    private String medicineName;

    /**
     * 药品的规格信息，如剂量、剂型等
     */
    private String specification;

    /**
     * 药品的生产企业名称
     */
    private String manufacturer;

    /**
     * 药品的现有库存数量
     */
    private Integer stock;

    /**
     * 药品已出售的数量
     */
    private Integer soldQuantity;

    /**
     * 药品的主要治疗功能和适应症
     */
    private String mainFunctions;

    /**
     * 药品的品牌名称
     */
    private String brand;

    /**
     * 药品的当前状态，0在售、1缺货
     */
    @TableField("`status`")
    private Boolean status;

    /**
     * 可能用于展示药品的图片（轮播形式）
     */
    private String carouselImages;

    /**
     * 对药品的补充说明或特殊备注
     */
    private String remark;

    /**
     * 药品的销售价格
     */
    private BigDecimal sellingPrice;

    /**
     * 药品的进货价格
     */
    private BigDecimal purchasePrice;

    /**
     * 药品的保质期限
     */
    private LocalDate expiryDate;

    /**
     * 药品的生产时间
     */
    private LocalDate productionDate;

    /**
     * 可能用于展示药品详细信息的图片
     */
    private String detailImages;

    /**
     * 0表示启用，1表示删除
     */
    private Boolean isDeleted;

    /**
     * 记录数据的创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 创建该数据的用户标识
     */
    @TableField(fill = FieldFill.INSERT)
    private Long creator;

    /**
     * 记录数据的最后更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    /**
     * 最后更新该数据的用户标识
     */
    @TableField(fill = FieldFill.UPDATE)
    private Integer updateUser;
}
