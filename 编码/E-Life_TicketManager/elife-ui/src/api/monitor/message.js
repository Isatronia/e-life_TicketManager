import request from "@/utils/request";

// 查询消息查询列表
export function listMessage(query) {
  return request({
    url: "/monitor/message/list",
    method: "get",
    params: query,
  });
}

// 查询消息查询详细
export function getMessage(messageId) {
  return request({
    url: "/monitor/message/detail/" + messageId,
    method: "get",
  });
}

// 查询消息 - 服务单
export function getMessageByTicketId(ticketId) {
  return request({
    url: "/monitor/message/ticket/" + ticketId,
    method: "get",
  });
}

// 查询消息 - 服务单
export function getDirectMessage(userId = -1) {
  return request({
    url: "/monitor/message/dm/" + userId,
    method: "get",
  });
}

// 发送消息
export function sendMessage(data) {
  return request({
    url: "/monitor/message/send",
    method: "post",
    data: data,
  });
}

// 新增消息查询
export function addMessage(data) {
  return request({
    url: "/monitor/message",
    method: "post",
    data: data,
  });
}

// 修改消息查询
export function updateMessage(data) {
  return request({
    url: "/monitor/message",
    method: "put",
    data: data,
  });
}

// 删除消息查询
export function delMessage(messageId) {
  return request({
    url: "/monitor/message/force-del/" + messageId,
    method: "delete",
  });
}

// 删除消息查询
export function safeDelMessage(messageId) {
  return request({
    url: "/monitor/message/safe-del/" + messageId,
    method: "delete",
  });
}
