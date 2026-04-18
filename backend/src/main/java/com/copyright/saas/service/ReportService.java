package com.copyright.saas.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * Word 报告生成服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReportService {

    /**
     * 生成侵权分析报告
     */
    public byte[] generateInfringementReport(
            String tenantName,
            String reportPeriod,
            List<Map<String, Object>> infringements,
            Map<String, Object> statistics) throws IOException {
        
        XWPFDocument document = new XWPFDocument();
        
        // 标题
        XWPFParagraph titlePara = document.createParagraph();
        titlePara.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = titlePara.createRun();
        titleRun.setText("AI 版权侵权舆情分析报告");
        titleRun.setBold(true);
        titleRun.setFontSize(18);
        titleRun.setFontFamily("微软雅黑");
        
        // 报告信息
        addReportInfo(document, tenantName, reportPeriod);
        
        // 统计概览
        addStatisticsSection(document, statistics);
        
        // 侵权详情
        addInfringementDetails(document, infringements);
        
        // AI 分析结论
        addAIAnalysis(document, statistics);
        
        // 输出
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        document.write(out);
        document.close();
        
        log.info("报告生成完成，大小：{} bytes", out.size());
        return out.toByteArray();
    }
    
    private void addReportInfo(XWPFDocument doc, String tenantName, String period) {
        XWPFParagraph para = doc.createParagraph();
        XWPFRun run = para.createRun();
        run.setText("报告单位：" + tenantName);
        run.addBreak();
        run.setText("报告周期：" + period);
        run.addBreak();
        run.setText("生成时间：" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
    
    private void addStatisticsSection(XWPFDocument doc, Map<String, Object> stats) {
        addSectionTitle(doc, "一、统计概览");
        
        XWPFParagraph para = doc.createParagraph();
        XWPFRun run = para.createRun();
        run.setText("本期全网检索次数：" + stats.getOrDefault("scanCount", 0));
        run.addBreak();
        run.setText("发现侵权记录：" + stats.getOrDefault("infringementCount", 0));
        run.addBreak();
        run.setText("确认侵权：" + stats.getOrDefault("confirmedCount", 0));
        run.addBreak();
        run.setText("高度疑似：" + stats.getOrDefault("highRiskCount", 0));
    }
    
    private void addInfringementDetails(XWPFDocument doc, List<Map<String, Object>> list) {
        addSectionTitle(doc, "二、侵权详情");
        
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> item = list.get(i);
            XWPFParagraph para = doc.createParagraph();
            XWPFRun run = para.createRun();
            run.setBold(true);
            run.setText((i + 1) + ". " + item.get("title"));
            run.addBreak();
            run.setBold(false);
            run.setText("平台：" + item.get("platform"));
            run.addBreak();
            run.setText("链接：" + item.get("url"));
            run.addBreak();
            run.setText("相似度：" + item.get("similarity"));
            run.addBreak();
            run.setText("风险等级：" + item.get("riskLevel"));
            para.setSpacingAfter(200);
        }
    }
    
    private void addAIAnalysis(XWPFDocument doc, Map<String, Object> stats) {
        addSectionTitle(doc, "三、AI 分析结论");
        
        XWPFParagraph para = doc.createParagraph();
        XWPFRun run = para.createRun();
        run.setText("根据 AI 智能分析，本期侵权风险整体处于");
        run.setBold(true);
        int count = (int) stats.getOrDefault("infringementCount", 0);
        if (count > 10) {
            run.setText("较高水平");
        } else if (count > 5) {
            run.setText("中等水平");
        } else {
            run.setText("较低水平");
        }
        run.setBold(false);
        run.addBreak();
        run.setText("建议重点关注确认侵权项，及时采取维权措施。");
    }
    
    private void addSectionTitle(XWPFDocument doc, String title) {
        XWPFParagraph para = doc.createParagraph();
        XWPFRun run = para.createRun();
        run.setBold(true);
        run.setFontSize(14);
        run.setText(title);
        para.setSpacingBefore(200);
        para.setSpacingAfter(100);
    }
}
