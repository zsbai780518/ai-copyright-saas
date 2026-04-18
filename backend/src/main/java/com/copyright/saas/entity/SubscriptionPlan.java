package com.copyright.saas.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 套餐实体
 */
@Data
@TableName("subscription_plan")
public class SubscriptionPlan {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String planName;
    private String planCode;
    private BigDecimal price;
    private String monitorFrequency;
    private Integer maxInfringementCount;
    private Integer maxPlatforms;
    private Integer maxExportCount;
    private String description;
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
