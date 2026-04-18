package com.copyright.saas.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 视频资产实体
 */
@Data
@TableName("asset_video")
public class AssetVideo {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long tenantId;
    private String assetName;
    private String videoUrl;
    private String videoHash;
    private Integer duration;
    private String keyFrames;
    private String audioFingerprint;
    private String subtitleText;
    private String videoFingerprint;
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
