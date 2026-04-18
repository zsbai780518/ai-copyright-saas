<template>
  <div class="assets-page">
    <el-header class="header">
      <h3>原创资产管理</h3>
      <el-button type="primary" @click="showAddDialog = true">新增资产</el-button>
    </el-header>
    
    <el-main>
      <el-tabs v-model="activeTab">
        <el-tab-pane label="文字资产" name="text">
          <el-table :data="textAssets" style="width: 100%">
            <el-table-column prop="assetName" label="资产名称" />
            <el-table-column prop="assetType" label="类型">
              <template #default="{ row }">
                {{ assetTypeText(row.assetType) }}
              </template>
            </el-table-column>
            <el-table-column prop="keywords" label="关键词" />
            <el-table-column prop="createdAt" label="创建时间" />
            <el-table-column label="操作">
              <template #default="{ row }">
                <el-button size="small" @click="handleDelete(row.id)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="图片资产" name="image">
          <el-empty description="暂无图片资产" />
        </el-tab-pane>
        <el-tab-pane label="视频资产" name="video">
          <el-empty description="暂无视频资产" />
        </el-tab-pane>
      </el-tabs>
    </el-main>
    
    <!-- 新增文字资产对话框 -->
    <el-dialog v-model="showAddDialog" title="新增文字资产" width="500px">
      <el-form :model="newAsset" label-width="80px">
        <el-form-item label="资产名称">
          <el-input v-model="newAsset.assetName" />
        </el-form-item>
        <el-form-item label="类型">
          <el-select v-model="newAsset.assetType">
            <el-option label="品牌词" :value="1" />
            <el-option label="商标词" :value="2" />
            <el-option label="原创文案" :value="3" />
            <el-option label="文章全文" :value="4" />
          </el-select>
        </el-form-item>
        <el-form-item label="内容">
          <el-input v-model="newAsset.content" type="textarea" :rows="5" />
        </el-form-item>
        <el-form-item label="关键词">
          <el-input v-model="newAsset.keywords" placeholder="多个关键词用逗号分隔" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showAddDialog = false">取消</el-button>
        <el-button type="primary" @click="handleAdd">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'

const activeTab = ref('text')
const showAddDialog = ref(false)

const textAssets = ref([
  { id: 1, assetName: '品牌名称', assetType: 1, keywords: '品牌，商标', createdAt: '2026-04-18' },
  { id: 2, assetName: '原创文案', assetType: 3, keywords: '文案，广告语', createdAt: '2026-04-17' }
])

const newAsset = reactive({
  assetName: '',
  assetType: 1,
  content: '',
  keywords: ''
})

const assetTypeText = (type: number) => {
  const map: Record<number, string> = {
    1: '品牌词',
    2: '商标词',
    3: '原创文案',
    4: '文章全文'
  }
  return map[type] || '未知'
}

const handleAdd = () => {
  textAssets.value.unshift({
    id: Date.now(),
    assetName: newAsset.assetName,
    assetType: newAsset.assetType,
    keywords: newAsset.keywords,
    createdAt: new Date().toISOString().split('T')[0]
  })
  showAddDialog.value = false
  ElMessage.success('添加成功')
}

const handleDelete = (id: number) => {
  textAssets.value = textAssets.value.filter(a => a.id !== id)
  ElMessage.success('删除成功')
}
</script>

<style scoped>
.assets-page {
  min-height: 100vh;
  background: #f5f5f5;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #fff;
  padding: 0 20px;
}
</style>
