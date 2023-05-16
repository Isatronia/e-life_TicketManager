import request from '@/utils/request'

// 查询问题类型列表
export function listTicketType(query) {
  return request({
    url: '/feature/ticketType/list',
    method: 'get',
    params: query
  })
}

// 查询问题类型详细
export function getTicketType(typeId) {
  return request({
    url: '/feature/ticketType/' + typeId,
    method: 'get'
  })
}

// 新增问题类型
export function addTicketType(data) {
  return request({
    url: '/feature/ticketType',
    method: 'post',
    data: data
  })
}

// 修改问题类型
export function updateTicketType(data) {
  return request({
    url: '/feature/ticketType',
    method: 'put',
    data: data
  })
}

// 删除问题类型
export function delTicketType(typeId) {
  return request({
    url: '/feature/ticketType/' + typeId,
    method: 'delete'
  })
}
