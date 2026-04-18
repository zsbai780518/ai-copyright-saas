package com.copyright.saas.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 图片资产实体
 */
@Data
@TableName("asset_image")
public class AssetImage {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long tenantId;
    private String assetName;
    private String imageUrl;
    private String imageHash;
    private Integer assetType;
    private String globalFeature;
    private String localFeature;
    private String watermarkInfo;
    private String imageFingerprint;
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
