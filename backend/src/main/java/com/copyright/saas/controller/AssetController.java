package com.copyright.saas.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.copyright.saas.dto.R;
import com.copyright.saas.entity.AssetText;
import com.copyright.saas.service.AssetTextService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 资产管理控制器
 */
@RestController
@RequestMapping("/asset")
@RequiredArgsConstructor
public class AssetController {

    private final AssetTextService assetTextService;

    /**
     * 获取文字资产列表
     */
    @GetMapping("/text/list")
    public R<?> getTextAssets(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        
        Long tenantId = StpUtil.getLoginIdAsLong(); // 实际应从用户表获取 tenantId
        Page<AssetText> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<AssetText> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AssetText::getTenantId, tenantId)
               .eq(AssetText::getStatus, 1)
               .orderByDesc(AssetText::getCreatedAt);
        
        Page<AssetText> result = assetTextService.page(page, wrapper);
        return R.ok(result);
    }

    /**
     * 创建文字资产
     */
    @PostMapping("/text")
    public R<?> createTextAsset(@RequestBody AssetText asset) {
        Long userId = StpUtil.getLoginIdAsLong();
        asset.setTenantId(userId); // 实际应从用户表获取 tenantId
        asset.setStatus(1);
        assetTextService.save(asset);
        return R.ok("创建成功");
    }

    /**
     * 删除文字资产
     */
    @DeleteMapping("/text/{id}")
    public R<?> deleteTextAsset(@PathVariable Long id) {
        assetTextService.removeById(id);
        return R.ok("删除成功");
    }

    /**
     * 批量导入文字资产
     */
    @PostMapping("/text/batch")
    public R<?> batchImportTextAssets(@RequestBody List<AssetText> assets) {
        Long userId = StpUtil.getLoginIdAsLong();
        for (AssetText asset : assets) {
            asset.setTenantId(userId);
            asset.setStatus(1);
        }
        assetTextService.saveBatch(assets);
        return R.ok("批量导入成功");
    }
}
