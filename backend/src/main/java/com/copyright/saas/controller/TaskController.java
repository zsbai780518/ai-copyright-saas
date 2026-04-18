package com.copyright.saas.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.copyright.saas.dto.R;
import com.copyright.saas.entity.MonitorTask;
import com.copyright.saas.service.MonitorTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 监测任务控制器
 */
@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final MonitorTaskService taskService;

    /**
     * 获取任务列表
     */
    @GetMapping("/list")
    public R<?> getTaskList() {
        Long tenantId = StpUtil.getLoginIdAsLong();
        LambdaQueryWrapper<MonitorTask> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MonitorTask::getTenantId, tenantId)
               .orderByDesc(MonitorTask::getCreatedAt);
        
        List<MonitorTask> list = taskService.list(wrapper);
        return R.ok(list);
    }

    /**
     * 创建监测任务
     */
    @PostMapping
    public R<?> createTask(@RequestBody MonitorTask task) {
        Long userId = StpUtil.getLoginIdAsLong();
        task.setTenantId(userId);
        task.setStatus(0); // 默认暂停
        task.setTotalScanCount(0L);
        task.setTotalInfringementCount(0L);
        task.setCreatedBy(userId);
        taskService.save(task);
        return R.ok("任务创建成功");
    }

    /**
     * 启动任务
     */
    @PutMapping("/{id}/start")
    public R<?> startTask(@PathVariable Long id) {
        MonitorTask task = taskService.getById(id);
        if (task != null) {
            task.setStatus(1);
            task.setNextRunTime(LocalDateTime.now());
            taskService.updateById(task);
            return R.ok("任务已启动");
        }
        return R.error("任务不存在");
    }

    /**
     * 暂停任务
     */
    @PutMapping("/{id}/stop")
    public R<?> stopTask(@PathVariable Long id) {
        MonitorTask task = taskService.getById(id);
        if (task != null) {
            task.setStatus(0);
            taskService.updateById(task);
            return R.ok("任务已暂停");
        }
        return R.error("任务不存在");
    }

    /**
     * 删除任务
     */
    @DeleteMapping("/{id}")
    public R<?> deleteTask(@PathVariable Long id) {
        taskService.removeById(id);
        return R.ok("删除成功");
    }
}
