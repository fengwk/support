import * as constants from './constants';
import { fromJS, OrderedMap } from 'immutable';
import { message, Modal } from 'antd';
import coProxy from '../../../utils/coProxy';
import { isEmail } from '../../../utils/validator';
import { createLock } from '../../../utils/lockFactory';
import * as roleService from '../../../services/roleService';
import * as permissionService from '../../../services/permissionService';

const setPageNumber = (pageNumber) => ({
  type: constants.SET_PAGE_NUMBER,
  pageNumber
});

const setPageSize = (pageSize) => ({
  type: constants.SET_PAGE_SIZE,
  pageSize
});

const setTotal = (total) => ({
  type: constants.SET_TOTAL,
  total
});

const setRoleView = (roleView) => ({
  type: constants.SET_ROLE_VIEW,
  roleView
});

const setTableLoading = (loading) => ({
  type: constants.SET_TABLE_LOADING,
  loading
});

export const setPermissionModalVisible = (permissionModalVisible) => ({
  type: constants.SET_PERMISSION_MODAL_VISIBLE,
  permissionModalVisible
});

export const setPermissionModalRoleId = (permissionModalRoleId) => ({
  type: constants.SET_PERMISSION_MODAL_ROLE_ID,
  permissionModalRoleId
});

export const setPermissionModalNickname = (permissionModalNickname) => ({
  type: constants.SET_PERMISSION_MODAL_NICKNAME,
  permissionModalNickname
});

const setPermissionModalOwnedPermissions = (permissionModalOwnedPermissions) => ({
  type: constants.SET_PERMISSION_MODAL_OWNED_PERMISSIONS,
  permissionModalOwnedPermissions
});

const setPermissionModalSearchPermissions = (permissionModalSearchPermissions) => ({
  type: constants.SET_PERMISSION_MODAL_SEARCH_PERMISSIONS,
  permissionModalSearchPermissions
});

export const setEditView = (editView) => ({
  type: constants.SET_EDIT_VIEW,
  editView
});

function* doSearch(dispatch, param) {
  dispatch(setTableLoading(true));
  try {
    const page = yield roleService.search({ ...param, name: param.searchValue });
    // 初始化视图
    const view = {};
    for (const item of page.results) {
      view[item.id] = fromJS({ ...item, editable: false });
    }
    const iview = OrderedMap(view);
    dispatch(setRoleView(iview));
    dispatch(setEditView(iview));

    dispatch(setPageNumber(page.pageNumber));
    dispatch(setPageSize(page.pageSize));
    dispatch(setTotal(page.total));
  } finally {
    dispatch(setTableLoading(false));
  }
}

function* doCreate(dispatch, editView, editItem, roleView) {
  const name = editItem.get('name');
  if (!name) {
    message.warning('角色名称不能为空');
    return;
  }
  const createdRole = yield roleService.create({ name });
  const createdRoleView = fromJS({ ...createdRole, editable: false });

  const newEditView = editView.delete(editItem.get('id'));
  dispatch(setRoleView(roleView.set(createdRole.id, createdRoleView)));
  dispatch(setEditView(newEditView.set(createdRole.id, createdRoleView)));
}

function* doUpdate(dispatch, editView, editItem, roleView) {
  const roleId = editItem.get('id');
  const name = editItem.get('name');
  const prev = roleView.get(roleId);
  if (!name) {
    message.warning('角色名称不能为空');
    return;
  }
  if (prev.get('name') === name) {
    // 没有修改
    dispatch(setEditView(editView.set(roleId, editItem.set('editable', false))));
  } else {
    // 有修改
    const updatedRole = yield roleService.update({ roleId, name });
    const updatedRoleView = fromJS({ ...updatedRole, editable: false });
    dispatch(setRoleView(roleView.set(roleId, updatedRoleView)));
    dispatch(setEditView(editView.set(roleId, updatedRoleView)));
  }
}

function* doShowPermissionModal(dispatch, roleId) {
  const permissions = yield roleService.listPermissions({ roleId });
  dispatch(setPermissionModalOwnedPermissions(fromJS(permissions)));
}

function* doSearchPermissions(dispatch, value) {
  const page = yield permissionService.search({ pageNumber: 1, pageSize: 30, name: value });// 最多返回30条匹配项
  dispatch(setPermissionModalSearchPermissions(fromJS(page.results)));
}

export const search = (param) => {
  return (dispatch) => {
    coProxy.wrap(doSearch)(dispatch, param);
  }
};

export const changePage = (param) => {
  return (dispatch) => {
    dispatch(setPageNumber(param.pageNumber));
    dispatch(setPageSize(param.pageSize));
    coProxy.wrap(doSearch)(dispatch, param);
  }
}

export const setSearchValue = (value) => ({
  type: constants.SET_SEARCH_VALUE,
  value
});

export const save = (editView, editItem, roleView) => {
  return (dispatch) => {
    if (Number(editItem.get('id')) < 0) {
      // 新增
      coProxy.wrap(doCreate)(dispatch, editView, editItem, roleView);
    } else {
      // 更新
      coProxy.wrap(doUpdate)(dispatch, editView, editItem, roleView);
    }
  }
}

export const showPermissionModal = ({ roleId, nickname }) => {
  return (dispatch) => {
    dispatch(setPermissionModalVisible(true));
    dispatch(setPermissionModalRoleId(roleId));
    dispatch(setPermissionModalNickname(nickname));
    coProxy.wrap(doShowPermissionModal)(dispatch, roleId);
  }
}

export const searchPermissions = (value) => {
  return (dispatch) => {
    coProxy.wrap(doSearchPermissions)(dispatch, value);
  }
}

function* doGrantPermission(dispatch, { roleId, value, permissionModalOwnedPermissions }) {
  yield roleService.grantPermission({ roleId, permissionId: value.key });
  const nwePermissionModalOwnedPermissions = permissionModalOwnedPermissions.push(fromJS({ id: value.key, name: value.label }));
  dispatch(setPermissionModalOwnedPermissions(nwePermissionModalOwnedPermissions));
}

export const grantPermission = ({ roleId, value, permissionModalOwnedPermissions }) => {
  return (dispatch) => {
    coProxy.wrap(doGrantPermission)(dispatch, { roleId, value, permissionModalOwnedPermissions });
  }
}

function* doRevokePermission(dispatch, { roleId, value, permissionModalOwnedPermissions }) {
  yield roleService.revokePermission({ roleId, permissionId: value.key });
  const newPermissionModalOwnedPermissions = [];
  permissionModalOwnedPermissions.map((item, index) => {
    if (item.get('id') !== value.key) {
      newPermissionModalOwnedPermissions.push(item);
    }
  });
  dispatch(setPermissionModalOwnedPermissions(fromJS(newPermissionModalOwnedPermissions)));
}

export const revokePermission = ({ roleId, value, permissionModalOwnedPermissions }) => {
  return (dispatch) => {
    coProxy.wrap(doRevokePermission)(dispatch, { roleId, value, permissionModalOwnedPermissions });
  }
}

function* doRemoveRole(dispatch, { roleId, editView, roleView, resolve }) {
  yield roleService.remove({ roleId });
  dispatch(setRoleView(roleView.delete(roleId)));
  dispatch(setEditView(editView.delete(roleId)));
  resolve();
}

export const removeRole = ({ roleId, editView, roleView , resolve }) => {
  return (dispatch) => {
    coProxy.wrap(doRemoveRole)(dispatch, { roleId, editView, roleView , resolve });
  }
}
