import request from '@/utils/request'

// 查询黑名单列表
export function listBlock(query) {
  return request({
    url: '/feature/block/list',
    method: 'get',
    params: query
  })
}

// 查询黑名单详细
export function getBlock(userId) {
  return request({
    url: '/feature/block/' + userId,
    method: 'get'
  })
}

// 新增黑名单
export function addBlock(data) {
  return request({
    url: '/feature/block',
    method: 'post',
    data: data
  })
}

// 修改黑名单
export function updateBlock(data) {
  return request({
    url: '/feature/block',
    method: 'put',
    data: data
  })
}

// 删除黑名单
export function delBlock(userId) {
  return request({
    url: '/feature/block/' + userId,
    method: 'delete'
  })
}
