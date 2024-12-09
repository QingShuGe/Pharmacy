package com.qing.forestpharmacy.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.math.BigDecimal;
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
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 订单号
     */
    private String orderNumber;

    /**
     * 快递单号
     */
    private String expressNumber;

    /**
     * 订单状态 1待付款，2待派送，3已派送，4已完成，5已取消
     */
    @TableField("`status`")
    private Integer status;

    /**
     * 下单用户
     */
    private Long userId;

    /**
     * 地址id
     */
    private Long addressBookId;

    /**
     * 下单时间
     */
    private LocalDateTime orderTime;

    /**
     * 结账时间
     */
    private LocalDateTime checkoutTime;

    /**
     * 支付方式 1微信,2支付宝
     */
    private Integer payMethod;

    /**
     * 药品ID
     */
    private Long medicineId;

    /**
     * 药品名称
     */
    private String medicineName;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 原价
     */
    private BigDecimal priceAgo;

    /**
     * 购买数量
     */
    private Integer purchaseQuantity;

    /**
     * 总价
     */
    private BigDecimal priceCount;

    /**
     * 备注
     */
    private String remark;

    /**
     * 地址id
     */
    private Long addressId;
}
