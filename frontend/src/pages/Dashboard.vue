<template>
  <div class="dashboard-container">
    <el-header class="header">
      <h3>AI 版权侵权舆情管控系统</h3>
      <div class="header-right">
        <span>{{ userStore.userInfo?.username }}</span>
        <el-button type="danger" size="small" @click="handleLogout">退出</el-button>
      </div>
    </el-header>
    
    <el-container>
      <el-aside width="200px" class="aside">
        <el-menu :default-active="activeMenu" router>
          <el-menu-item index="/dashboard">
            <el-icon><HomeFilled /></el-icon>
            <span>控制台</span>
          </el-menu-item>
          <el-menu-item index="/assets">
            <el-icon><Document /></el-icon>
            <span>原创资产</span>
          </el-menu-item>
          <el-menu-item index="/tasks">
            <el-icon><Timer /></el-icon>
            <span>监测任务</span>
          </el-menu-item>
          <el-menu-item index="/infringements">
            <el-icon><Warning /></el-icon>
            <span>侵权记录</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      
      <el-main>
        <el-row :gutter="20">
          <el-col :span="6">
            <el-statistic title="原创资产" :value="128" />
          </el-col>
          <el-col :span="6">
            <el-statistic title="监测任务" :value="12" />
          </el-col>
          <el-col :span="6">
            <el-statistic title="今日检索" :value="1024" />
          </el-col>
          <el-col :span="6">
            <el-statistic title="侵权记录" :value="5" />
          </el-col>
        </el-row>
        
        <el-card style="margin-top: 20px">
          <template #header>最近侵权预警</template>
          <el-table :data="recentInfringements" style="width: 100%">
            <el-table-column prop="title" label="标题" />
            <el-table-column prop="platform" label="平台" />
            <el-table-column prop="riskLevel" label="风险等级">
              <template #default="{ row }">
                <el-tag :type="row.riskLevel === 3 ? 'danger' : 'warning'">
                  {{ row.riskLevel === 3 ? '确认侵权' : '高度侵权' }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="time" label="发现时间" />
          </el-table>
        </el-card>
      </el-main>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { HomeFilled, Document, Timer, Warning } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()
const activeMenu = ref(route.path)

const recentInfringements = ref([
  { title: '某文章抄袭', platform: '微信公众号', riskLevel: 3, time: '10 分钟前' },
  { title: 'LOGO 盗用', platform: '淘宝', riskLevel: 2, time: '1 小时前' }
])

onMounted(async () => {
  try {
    await userStore.getCurrentUserInfo()
  } catch (e) {
    ElMessage.error('获取用户信息失败')
  }
})

const handleLogout = async () => {
  await userStore.logoutAction()
  router.push('/login')
  ElMessage.success('已退出登录')
}
</script>

<style scoped>
.dashboard-container {
  min-height: 100vh;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #fff;
  border-bottom: 1px solid #e6e6e6;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 10px;
}

.aside {
  background: #fff;
  border-right: 1px solid #e6e6e6;
}
</style>
