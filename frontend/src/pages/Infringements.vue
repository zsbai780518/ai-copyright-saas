<template>
  <div class="infringements-page">
    <el-header class="header">
      <h3>侵权记录管理</h3>
      <div class="header-actions">
        <el-select v-model="filterRiskLevel" placeholder="风险等级" clearable style="width: 120px">
          <el-option label="疑似侵权" :value="1" />
          <el-option label="高度侵权" :value="2" />
          <el-option label="确认侵权" :value="3" />
        </el-select>
        <el-select v-model="filterStatus" placeholder="处理状态" clearable style="width: 120px">
          <el-option label="未处理" :value="0" />
          <el-option label="已处理" :value="1" />
          <el-option label="已忽略" :value="2" />
        </el-select>
        <el-button type="primary" @click="handleExport">导出报告</el-button>
      </div>
    </el-header>
    
    <el-main>
      <el-table :data="filteredRecords" style="width: 100%">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="title" label="侵权标题" />
        <el-table-column prop="url" label="侵权链接" width="200">
          <template #default="{ row }">
            <el-link :href="row.url" target="_blank" type="primary">查看</el-link>
          </template>
        </el-table-column>
        <el-table-column prop="platform" label="平台" />
        <el-table-column prop="publishTime" label="发布时间" />
        <el-table-column prop="similarityScore" label="相似度" />
        <el-table-column prop="riskLevel" label="风险等级">
          <template #default="{ row }">
            <el-tag :type="riskLevelType(row.riskLevel)">
              {{ riskLevelText(row.riskLevel) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="processStatus" label="处理状态">
          <template #default="{ row }">
            <el-tag :type="statusType(row.processStatus)">
              {{ statusText(row.processStatus) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button size="small" @click="viewEvidence(row)">证据</el-button>
            <el-button size="small" type="success" v-if="row.processStatus === 0" @click="handleProcess(row)">处理</el-button>
            <el-button size="small" type="info" v-if="row.processStatus === 0" @click="handleIgnore(row)">忽略</el-button>
          </template>
        </el-table-column>
      </el-table>
      
      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :total="total"
        layout="total, prev, pager, next"
        style="margin-top: 20px; justify-content: flex-end"
      />
    </el-main>
    
    <!-- 处理对话框 -->
    <el-dialog v-model="processDialogVisible" title="处理侵权记录" width="500px">
      <el-input v-model="processRemark" type="textarea" :rows="4" placeholder="请输入处理备注" />
      <template #footer>
        <el-button @click="processDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmProcess">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { ElMessage } from 'element-plus'

const filterRiskLevel = ref<number | null>(null)
const filterStatus = ref<number | null>(null)
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(50)
const processDialogVisible = ref(false)
const processRemark = ref('')
const currentRecord = ref<any>(null)

const records = ref([
  {
    id: 1,
    title: '某文章涉嫌抄袭',
    url: 'https://example.com/article/123',
    platform: '微信公众号',
    publishTime: '2026-04-17 10:30',
    similarityScore: 0.95,
    riskLevel: 3,
    processStatus: 0
  },
  {
    id: 2,
    title: 'LOGO 被盗用',
    url: 'https://example.com/shop/456',
    platform: '淘宝',
    publishTime: '2026-04-16 15:20',
    similarityScore: 0.88,
    riskLevel: 2,
    processStatus: 0
  },
  {
    id: 3,
    title: '文案被搬运',
    url: 'https://example.com/weibo/789',
    platform: '微博',
    publishTime: '2026-04-15 09:00',
    similarityScore: 0.75,
    riskLevel: 1,
    processStatus: 1
  }
])

const filteredRecords = computed(() => {
  return records.value.filter(r => {
    if (filterRiskLevel.value !== null && r.riskLevel !== filterRiskLevel.value) return false
    if (filterStatus.value !== null && r.processStatus !== filterStatus.value) return false
    return true
  })
})

const riskLevelText = (level: number) => {
  const map: Record<number, string> = { 1: '疑似侵权', 2: '高度侵权', 3: '确认侵权' }
  return map[level] || '未知'
}

const riskLevelType = (level: number) => {
  const map: Record<number, any> = { 1: 'warning', 2: 'orange', 3: 'danger' }
  return map[level] || 'info'
}

const statusText = (status: number) => {
  const map: Record<number, string> = { 0: '未处理', 1: '已处理', 2: '已忽略' }
  return map[status] || '未知'
}

const statusType = (status: number) => {
  const map: Record<number, any> = { 0: 'info', 1: 'success', 2: 'info' }
  return map[status] || 'info'
}

const viewEvidence = (row: any) => {
  ElMessage.info('查看证据功能开发中')
}

const handleProcess = (row: any) => {
  currentRecord.value = row
  processRemark.value = ''
  processDialogVisible.value = true
}

const confirmProcess = () => {
  if (currentRecord.value) {
    currentRecord.value.processStatus = 1
    processDialogVisible.value = false
    ElMessage.success('已标记为已处理')
  }
}

const handleIgnore = (row: any) => {
  row.processStatus = 2
  ElMessage.success('已忽略')
}

const handleExport = () => {
  ElMessage.success('报告导出功能开发中')
}
</script>

<style scoped>
.infringements-page {
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

.header-actions {
  display: flex;
  gap: 10px;
}
</style>
