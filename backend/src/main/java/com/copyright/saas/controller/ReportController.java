package com.copyright.saas.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.copyright.saas.dto.R;
import com.copyright.saas.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 报告控制器
 */
@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    /**
     * 导出侵权分析报告
     */
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportReport(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) throws Exception {
        
        String tenantName = "测试企业"; // 实际应从租户表获取
        String period = (startDate != null ? startDate : "2026-04-01") + " 至 " + 
                       (endDate != null ? endDate : LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        
        // 模拟侵权数据
        List<Map<String, Object>> infringements = Arrays.asList(
            createInfringement("某文章涉嫌抄袭", "微信公众号", 0.95, "确认侵权"),
            createInfringement("LOGO 被盗用", "淘宝", 0.88, "高度侵权"),
            createInfringement("文案被搬运", "微博", 0.75, "疑似侵权")
        );
        
        // 统计数据
        Map<String, Object> statistics = new HashMap<>();
        statistics.put("scanCount", 1024);
        statistics.put("infringementCount", infringements.size());
        statistics.put("confirmedCount", 1);
        statistics.put("highRiskCount", 1);
        
        byte[] reportBytes = reportService.generateInfringementReport(tenantName, period, infringements, statistics);
        
        String filename = "侵权分析报告_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".docx";
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", new String(filename.getBytes("UTF-8"), "ISO-8859-1"));
        
        return ResponseEntity.ok().headers(headers).body(reportBytes);
    }
    
    private Map<String, Object> createInfringement(String title, String platform, double similarity, String riskLevel) {
        Map<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("platform", platform);
        map.put("url", "https://example.com/link");
        map.put("similarity", similarity);
        map.put("riskLevel", riskLevel);
        return map;
    }
}
