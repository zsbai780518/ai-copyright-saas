package com.copyright.saas.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 侵权记录实体
 */
@Data
@TableName("infringement_record")
public class InfringementRecord {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    /**
     * 任务 ID
     */
    private Long taskId;

    /**
     * 资产 ID
     */
    private Long assetId;

    /**
     * 资产类型：1-文字，2-图片，3-视频
     */
    private Integer assetType;

    /**
     * 侵权链接
     */
    private String infringementUrl;

    /**
     * 侵权标题
     */
    private String infringementTitle;

    /**
     * 侵权内容
     */
    private String infringementContent;

    /**
     * 发布账号
     */
    private String publishAccount;

    /**
     * 发布平台
     */
    private String publishPlatform;

    /**
     * 发布时间
     */
    private LocalDateTime publishTime;

    /**
     * 侵权类型
     */
    private String infringementType;

    /**
     * 相似度分数
     */
    private Double similarityScore;

    /**
     * 风险等级：1-疑似侵权，2-高度侵权，3-确认侵权
     */
    private Integer riskLevel;

    /**
     * 传播数据
     */
    private Integer spreadCount;

    /**
     * 处理状态：0-未处理，1-已处理，2-已忽略
     */
    private Integer processStatus;

    /**
     * 处理备注
     */
    private String processRemark;

    /**
     * 处理人
     */
    private Long processedBy;

    /**
     * 处理时间
     */
    private LocalDateTime processedAt;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
