package com.copyright.saas.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.copyright.saas.dto.R;
import com.copyright.saas.entity.InfringementRecord;
import com.copyright.saas.service.InfringementRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 侵权记录控制器
 */
@RestController
@RequestMapping("/infringement")
@RequiredArgsConstructor
public class InfringementController {

    private final InfringementRecordService infringementService;

    /**
     * 获取侵权记录列表
     */
    @GetMapping("/list")
    public R<?> getInfringementList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Integer riskLevel,
            @RequestParam(required = false) Integer processStatus) {
        
        Long tenantId = StpUtil.getLoginIdAsLong();
        Page<InfringementRecord> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<InfringementRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(InfringementRecord::getTenantId, tenantId);
        
        if (riskLevel != null) {
            wrapper.eq(InfringementRecord::getRiskLevel, riskLevel);
        }
        if (processStatus != null) {
            wrapper.eq(InfringementRecord::getProcessStatus, processStatus);
        }
        
        wrapper.orderByDesc(InfringementRecord::getCreatedAt);
        
        Page<InfringementRecord> result = infringementService.page(page, wrapper);
        return R.ok(result);
    }

    /**
     * 标记为已处理
     */
    @PutMapping("/{id}/process")
    public R<?> processInfringement(@PathVariable Long id, @RequestBody String remark) {
        Long userId = StpUtil.getLoginIdAsLong();
        InfringementRecord record = infringementService.getById(id);
        if (record != null) {
            record.setProcessStatus(1);
            record.setProcessRemark(remark);
            record.setProcessedBy(userId);
            record.setProcessedAt(java.time.LocalDateTime.now());
            infringementService.updateById(record);
            return R.ok("已标记为已处理");
        }
        return R.error("记录不存在");
    }

    /**
     * 忽略侵权记录
     */
    @PutMapping("/{id}/ignore")
    public R<?> ignoreInfringement(@PathVariable Long id) {
        InfringementRecord record = infringementService.getById(id);
        if (record != null) {
            record.setProcessStatus(2);
            infringementService.updateById(record);
            return R.ok("已忽略");
        }
        return R.error("记录不存在");
    }
}
