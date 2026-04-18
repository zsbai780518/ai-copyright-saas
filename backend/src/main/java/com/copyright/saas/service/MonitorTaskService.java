package com.copyright.saas.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.copyright.saas.entity.MonitorTask;
import com.copyright.saas.mapper.MonitorTaskMapper;
import org.springframework.stereotype.Service;

/**
 * 监测任务服务
 */
@Service
public class MonitorTaskService extends ServiceImpl<MonitorTaskMapper, MonitorTask> {
}
