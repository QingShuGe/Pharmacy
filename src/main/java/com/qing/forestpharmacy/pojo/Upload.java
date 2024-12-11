package com.qing.forestpharmacy.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2024-12-10
 */
@Getter
@Setter
public class Upload implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 上传ID
     */
    @TableId(value = "upload_id", type = IdType.AUTO)
    private Integer uploadId;

    /**
     * 文件名
     */
    @TableField("`name`")
    private String name;

    /**
     * 访问路径
     */
    @TableField("`path`")
    private String path;

    /**
     * 文件大小
     */
    private Integer fileSize;

    /**
     * 上传时间
     */
    private LocalDateTime uploadTime;

    /**
     * 上传人ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long creator;

    /**
     * 上传人姓名
     */
    private String userName;
}
