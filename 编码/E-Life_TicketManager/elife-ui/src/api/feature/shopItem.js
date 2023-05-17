import request from '@/utils/request'

// 查询商品信息列表
export function listShopItem(query) {
  return request({
    url: '/feature/shopItem/list',
    method: 'get',
    params: query
  })
}

// 查询商品信息详细
export function getShopItem(recordId) {
  return request({
    url: '/feature/shopItem/detail/' + recordId,
    method: 'get'
  })
}

// 新增商品信息
export function addShopItem(data) {
  return request({
    url: '/feature/shopItem',
    method: 'post',
    data: data
  })
}

// 修改商品信息
export function updateShopItem(data) {
  return request({
    url: '/feature/shopItem',
    method: 'put',
    data: data
  })
}

// 删除商品信息
export function delShopItem(recordId) {
  return request({
    url: '/feature/shopItem/' + recordId,
    method: 'delete'
  })
}
