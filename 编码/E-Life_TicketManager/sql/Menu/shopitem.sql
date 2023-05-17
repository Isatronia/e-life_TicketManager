-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商品信息', '2000', '1', 'shopItem', 'feature/shopItem/index', 1, 0, 'C', '0', '0', 'feature:shopItem:list', '#', 'admin', sysdate(), '', null, '商品信息菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商品信息查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'feature:shopItem:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商品信息新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'feature:shopItem:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商品信息修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'feature:shopItem:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商品信息删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'feature:shopItem:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商品信息导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'feature:shopItem:export',       '#', 'admin', sysdate(), '', null, '');