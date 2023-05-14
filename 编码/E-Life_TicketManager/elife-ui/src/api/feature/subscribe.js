import request from "@/utils/request";

// 列出订阅关系
export function listSubscribe(data) {
  return request({
    url: "/feature/subscribe/list",
    data: data,
    method: "get",
  });
}

// 强制增加订阅关系，仅应当用于调试
export function addSubscribe(data) {
  return request({
    url: "/feature/subscribe/add",
    data: data,
    method: "post",
  });
}

// 订阅公司
export function subscribeCompany(companyId) {
  return request({
    url: "/feature/subscribe/subscribe/" + companyId,
    method: "post",
  });
}

// 取消订阅公司
export function unsubscribeCompany(companyId) {
  return request({
    url: "/feature/subscribe/unsubscribe/" + companyId,
    method: "delete",
  });
}

// 更新订阅关系
export function updateSubscriveStatus(data) {
  return request({
    url: "/feature/subscribe/",
    data: data,
    method: "put"
  });
}

export function deleteSubscribe(data){
  return request({
    url: "/feature/subscribe/",
    data: data,
    method: "delete"
  })
}

export function acceptSubscribe(companyId){
  return request({
    url: "/feature/subscribe/accept/" + companyId,
    method: "post"
  })
}
