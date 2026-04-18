package com.copyright.saas.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 监测任务实体
 */
@Data
@TableName("monitor_task")
public class MonitorTask {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 资产类型：1-文字，2-图片，3-视频
     */
    private Integer assetType;

    /**
     * 关联资产 ID 列表
     */
    private String assetIds;

    /**
     * 监测平台列表
     */
    private String monitorPlatforms;

    /**
     * 监测频率：1-实时，2-小时级，3-每日，4-每周
     */
    private Integer monitorFrequency;

    /**
     * 监测时段
     */
    private String monitorTimeRange;

    /**
     * 侵权判定阈值
     */
    private Double infringementThreshold;

    /**
     * 优先级：1-低，2-中，3-高
     */
    private Integer priority;

    /**
     * 状态：0-暂停，1-运行中，2-已停止
     */
    private Integer status;

    /**
     * 最后运行时间
     */
    private LocalDateTime lastRunTime;

    /**
     * 下次运行时间
     */
    private LocalDateTime nextRunTime;

    /**
     * 累计检索次数
     */
    private Long totalScanCount;

    /**
     * 累计侵权数量
     */
    private Long totalInfringementCount;

    /**
     * 创建人
     */
    private Long createdBy;

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
