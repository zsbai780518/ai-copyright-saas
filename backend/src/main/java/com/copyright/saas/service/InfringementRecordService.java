package com.copyright.saas.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.copyright.saas.entity.InfringementRecord;
import com.copyright.saas.mapper.InfringementRecordMapper;
import org.springframework.stereotype.Service;

/**
 * 侵权记录服务
 */
@Service
public class InfringementRecordService extends ServiceImpl<InfringementRecordMapper, InfringementRecord> {
}
