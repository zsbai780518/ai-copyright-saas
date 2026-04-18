import { defineStore } from 'pinia'
import { ref } from 'vue'
import { login, logout, getCurrentUser } from '@/api/auth'

export const useUserStore = defineStore('user', () => {
  const token = ref<string>(localStorage.getItem('token') || '')
  const userInfo = ref<any>(null)

  async function loginAction(username: string, password: string) {
    const res = await login({ username, password })
    token.value = res.data.tokenValue
    localStorage.setItem('token', token.value)
    await getCurrentUserInfo()
  }

  async function getCurrentUserInfo() {
    const res = await getCurrentUser()
    userInfo.value = res.data
  }

  async function logoutAction() {
    await logout()
    token.value = ''
    userInfo.value = null
    localStorage.removeItem('token')
  }

  return { token, userInfo, loginAction, logoutAction, getCurrentUserInfo }
})
