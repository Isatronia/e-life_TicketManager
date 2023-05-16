import request from '@/utils/request'

// 查询回复模板管理列表
export function listPattern(query) {
  return request({
    url: '/feature/pattern/list',
    method: 'get',
    params: query
  })
}

// 查询回复模板管理详细
export function getPattern(patternId) {
  return request({
    url: '/feature/pattern/' + patternId,
    method: 'get'
  })
}

// 新增回复模板管理
export function addPattern(data) {
  return request({
    url: '/feature/pattern',
    method: 'post',
    data: data
  })
}

// 修改回复模板管理
export function updatePattern(data) {
  return request({
    url: '/feature/pattern',
    method: 'put',
    data: data
  })
}

// 删除回复模板管理
export function delPattern(patternId) {
  return request({
    url: '/feature/pattern/' + patternId,
    method: 'delete'
  })
}
