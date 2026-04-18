package com.copyright.saas.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 租户实体
 */
@Data
@TableName("tenant")
public class Tenant {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 租户名称
     */
    private String tenantName;

    /**
     * 联系人
     */
    private String contactName;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 联系邮箱
     */
    private String contactEmail;

    /**
     * 套餐 ID
     */
    private Long subscriptionPlanId;

    /**
     * 套餐开始时间
     */
    private LocalDateTime subscriptionStartTime;

    /**
     * 套餐结束时间
     */
    private LocalDateTime subscriptionEndTime;

    /**
     * 状态：0-禁用，1-正常
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
