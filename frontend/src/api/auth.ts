import request from './request'

export function login(data: { username: string; password: string }) {
  return request({
    url: '/auth/login',
    method: 'post',
    data
  })
}

export function register(data: { username: string; password: string; phone: string; tenantName?: string }) {
  return request({
    url: '/auth/register',
    method: 'post',
    data
  })
}

export function logout() {
  return request({
    url: '/auth/logout',
    method: 'post'
  })
}

export function getCurrentUser() {
  return request({
    url: '/auth/current',
    method: 'get'
  })
}
