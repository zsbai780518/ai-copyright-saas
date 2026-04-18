package com.copyright.saas.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 文字资产实体
 */
@Data
@TableName("asset_text")
public class AssetText {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    /**
     * 资产名称
     */
    private String assetName;

    /**
     * 文字内容
     */
    private String content;

    /**
     * 类型：1-品牌词，2-商标词，3-原创文案，4-文章全文
     */
    private Integer assetType;

    /**
     * 关键词
     */
    private String keywords;

    /**
     * 语义指纹
     */
    private String semanticFingerprint;

    /**
     * 状态：0-删除，1-正常
     */
    private Integer status;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
