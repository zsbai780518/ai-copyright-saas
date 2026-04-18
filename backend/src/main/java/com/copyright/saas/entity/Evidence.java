package com.copyright.saas.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 证据实体
 */
@Data
@TableName("evidence")
public class Evidence {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long tenantId;
    private Long infringementId;
    private Integer evidenceType;
    private String evidenceUrl;
    private Long timestamp;
    private String timestampCert;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
