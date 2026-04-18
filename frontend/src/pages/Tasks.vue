<template>
  <div class="tasks-page">
    <el-header class="header">
      <h3>监测任务管理</h3>
      <el-button type="primary" @click="showAddDialog = true">创建任务</el-button>
    </el-header>
    
    <el-main>
      <el-table :data="tasks" style="width: 100%">
        <el-table-column prop="taskName" label="任务名称" />
        <el-table-column prop="assetType" label="资产类型">
          <template #default="{ row }">
            {{ assetTypeText(row.assetType) }}
          </template>
        </el-table-column>
        <el-table-column prop="monitorFrequency" label="监测频率">
          <template #default="{ row }">
            {{ frequencyText(row.monitorFrequency) }}
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'">
              {{ row.status === 1 ? '运行中' : '已暂停' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="totalScanCount" label="检索次数" />
        <el-table-column prop="totalInfringementCount" label="侵权数量" />
        <el-table-column label="操作">
          <template #default="{ row }">
            <el-button size="small" :type="row.status === 1 ? 'warning' : 'success'" @click="toggleTask(row)">
              {{ row.status === 1 ? '暂停' : '启动' }}
            </el-button>
            <el-button size="small" type="danger" @click="handleDelete(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-main>
    
    <!-- 创建任务对话框 -->
    <el-dialog v-model="showAddDialog" title="创建监测任务" width="600px">
      <el-form :model="newTask" label-width="100px">
        <el-form-item label="任务名称">
          <el-input v-model="newTask.taskName" />
        </el-form-item>
        <el-form-item label="资产类型">
          <el-select v-model="newTask.assetType">
            <el-option label="文字" :value="1" />
            <el-option label="图片" :value="2" />
            <el-option label="视频" :value="3" />
          </el-select>
        </el-form-item>
        <el-form-item label="监测平台">
          <el-checkbox-group v-model="newTask.platforms">
            <el-checkbox label="微信公众号" />
            <el-checkbox label="抖音" />
            <el-checkbox label="微博" />
            <el-checkbox label="淘宝" />
            <el-checkbox label="百度" />
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="监测频率">
          <el-select v-model="newTask.monitorFrequency">
            <el-option label="实时" :value="1" />
            <el-option label="小时级" :value="2" />
            <el-option label="每日" :value="3" />
            <el-option label="每周" :value="4" />
          </el-select>
        </el-form-item>
        <el-form-item label="侵权阈值">
          <el-slider v-model="newTask.threshold" :min="0" :max="1" :step="0.01" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showAddDialog = false">取消</el-button>
        <el-button type="primary" @click="handleAdd">创建</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'

const showAddDialog = ref(false)

const tasks = ref([
  { id: 1, taskName: '品牌词监测', assetType: 1, monitorFrequency: 2, status: 1, totalScanCount: 100, totalInfringementCount: 3 },
  { id: 2, taskName: 'LOGO 图片监测', assetType: 2, monitorFrequency: 3, status: 0, totalScanCount: 50, totalInfringementCount: 1 }
])

const newTask = reactive({
  taskName: '',
  assetType: 1,
  platforms: [] as string[],
  monitorFrequency: 2,
  threshold: 0.8
})

const assetTypeText = (type: number) => {
  const map: Record<number, string> = { 1: '文字', 2: '图片', 3: '视频' }
  return map[type] || '未知'
}

const frequencyText = (freq: number) => {
  const map: Record<number, string> = { 1: '实时', 2: '小时级', 3: '每日', 4: '每周' }
  return map[freq] || '未知'
}

const toggleTask = (row: any) => {
  row.status = row.status === 1 ? 0 : 1
  ElMessage.success(row.status === 1 ? '任务已启动' : '任务已暂停')
}

const handleAdd = () => {
  tasks.value.unshift({
    id: Date.now(),
    taskName: newTask.taskName,
    assetType: newTask.assetType,
    monitorFrequency: newTask.monitorFrequency,
    status: 0,
    totalScanCount: 0,
    totalInfringementCount: 0
  })
  showAddDialog.value = false
  ElMessage.success('任务创建成功')
}

const handleDelete = (id: number) => {
  tasks.value = tasks.value.filter(t => t.id !== id)
  ElMessage.success('删除成功')
}
</script>

<style scoped>
.tasks-page {
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
