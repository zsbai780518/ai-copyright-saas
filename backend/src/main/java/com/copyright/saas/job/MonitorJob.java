package com.copyright.saas.job;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.copyright.saas.entity.MonitorTask;
import com.copyright.saas.service.MonitorTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 监测任务定时调度
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MonitorJob {

    private final MonitorTaskService taskService;

    /**
     * 每分钟检查需要执行的任务
     */
    @Scheduled(cron = "0 * * * * ?")
    public void checkAndExecuteTasks() {
        log.info("开始检查监测任务...");
        
        // 查询所有运行中的任务
        LambdaQueryWrapper<MonitorTask> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MonitorTask::getStatus, 1);
        wrapper.and(w -> w.isNull(MonitorTask::getNextRunTime)
                .or().le(MonitorTask::getNextRunTime, LocalDateTime.now()));
        
        List<MonitorTask> tasks = taskService.list(wrapper);
        
        for (MonitorTask task : tasks) {
            try {
                executeTask(task);
            } catch (Exception e) {
                log.error("执行任务 {} 失败", task.getTaskName(), e);
            }
        }
    }

    /**
     * 执行单个监测任务
     */
    private void executeTask(MonitorTask task) {
        log.info("执行监测任务：{}", task.getTaskName());
        
        // TODO: 实现具体的监测逻辑
        // 1. 根据 assetType 和 assetIds 获取原创资产
        // 2. 调用分布式爬虫抓取目标平台数据
        // 3. 调用 AI 模型进行侵权比对
        // 4. 保存侵权记录
        // 5. 发送预警通知
        // 6. 更新任务执行信息
        
        // 更新下次运行时间
        LocalDateTime nextRunTime = calculateNextRunTime(task.getMonitorFrequency());
        task.setNextRunTime(nextRunTime);
        task.setLastRunTime(LocalDateTime.now());
        task.setTotalScanCount(task.getTotalScanCount() + 1);
        taskService.updateById(task);
        
        log.info("任务 {} 执行完成，下次运行时间：{}", task.getTaskName(), nextRunTime);
    }

    /**
     * 计算下次运行时间
     */
    private LocalDateTime calculateNextRunTime(Integer frequency) {
        LocalDateTime now = LocalDateTime.now();
        return switch (frequency) {
            case 1 -> now.plusMinutes(5); // 实时：5 分钟后
            case 2 -> now.plusHours(1);   // 小时级
            case 3 -> now.plusDays(1);    // 每日
            case 4 -> now.plusWeeks(1);   // 每周
            default -> now.plusHours(1);
        };
    }
}
