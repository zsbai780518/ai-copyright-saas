package com.copyright.saas.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 白名单实体
 */
@Data
@TableName("whitelist")
public class Whitelist {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long tenantId;
    private Integer whitelistType;
    private String whitelistValue;
    private String description;
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
