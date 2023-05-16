import request from '@/utils/request'

// 查询服务单列表
export function listTicket(query) {
  return request({
    url: '/feature/ticket/list',
    method: 'get',
    params: query
  })
}

// 查询服务单详细
export function getTicket(ticketId) {
  return request({
    url: '/feature/ticket/detail/' + ticketId,
    method: 'get'
  })
}

// 查询服务单详细
export function getTicketParent(ticketId) {
  return request({
    url: '/feature/ticket/parent/' + ticketId,
    method: 'get'
  })
}

// 新增服务单
export function addTicket(data) {
  return request({
    url: '/feature/ticket',
    method: 'post',
    data: data
  })
}

// 修改服务单
export function updateTicket(data) {
  return request({
    url: '/feature/ticket',
    method: 'put',
    data: data
  })
}

// 删除服务单
export function delTicket(ticketId) {
  return request({
    url: '/feature/ticket/' + ticketId,
    method: 'delete'
  })
}

// 申请服务单
export function applyTicket(data){
  return request({
    url: '/feature/ticket/apply',
    method: 'post',
    data: data
  })
}