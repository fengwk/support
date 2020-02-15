import * as constants from './constants';
import { fromJS, OrderedMap } from 'immutable';
import { message } from 'antd';
import coProxy from '../../../utils/coProxy';
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

const setPermissionView = (permissionView) => ({
  type: constants.SET_PERMISSION_VIEW,
  permissionView
});

const setTableLoading = (loading) => ({
  type: constants.SET_TABLE_LOADING,
  loading
});

export const setEditView = (editView) => ({
  type: constants.SET_EDIT_VIEW,
  editView
});

function* doSearch(dispatch, param) {
  dispatch(setTableLoading(true));
  try {
    const page = yield permissionService.search({ ...param, name: param.searchValue });
    // 初始化视图
    const view = {};
    for (const item of page.results) {
      view[item.id] = fromJS({ ...item, editable: false });
    }
    const iview = OrderedMap(view);
    dispatch(setPermissionView(iview));
    dispatch(setEditView(iview));

    dispatch(setPageNumber(page.pageNumber));
    dispatch(setPageSize(page.pageSize));
    dispatch(setTotal(page.total));
  } finally {
    dispatch(setTableLoading(false));
  }
}

function* doCreate(dispatch, editView, editItem, permissionView) {
  const name = editItem.get('name');
  const path = editItem.get('path');
  if (!name) {
    message.warning('权限名称不能为空');
    return;
  }
  if (!path) {
    message.warning('权限路径不能为空');
    return;
  }
  const createdPermission = yield permissionService.create({ name, path });
  const createdPermissionView = fromJS({ ...createdPermission, editable: false });

  const newEditView = editView.delete(editItem.get('id'));
  dispatch(setPermissionView(permissionView.set(createdPermission.id, createdPermissionView)));
  dispatch(setEditView(newEditView.set(createdPermission.id, createdPermissionView)));
}

function* doUpdate(dispatch, editView, editItem, permissionView) {
  const permissionId = editItem.get('id');
  const name = editItem.get('name');
  const path = editItem.get('path');
  if (!name) { return message.warning('角色名称不能为空'); }
  if (!path) { return message.warning('权限路径不能为空'); }

  const prev = permissionView.get(permissionId);
  if (prev.get('name') === name && prev.get('path') === path) {
    // 没有修改
    dispatch(setEditView(editView.set(permissionId, editItem.set('editable', false))));
  } else {
    // 有修改
    const updatedPermission = yield permissionService.update({ permissionId, name, path });
    const updatedPermissionView = fromJS({ ...updatedPermission, editable: false });
    dispatch(setPermissionView(permissionView.set(permissionId, updatedPermissionView)));
    dispatch(setEditView(editView.set(permissionId, updatedPermissionView)));
  }
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

export const save = (editView, editItem, permissionView) => {
  return (dispatch) => {
    if (Number(editItem.get('id')) < 0) {
      // 新增
      coProxy.wrap(doCreate)(dispatch, editView, editItem, permissionView);
    } else {
      // 更新
      coProxy.wrap(doUpdate)(dispatch, editView, editItem, permissionView);
    }
  }
}

function* doRemovePermission(dispatch, { permissionId, editView, permissionView, resolve }) {
  yield permissionService.remove({ permissionId });
  dispatch(setPermissionView(permissionView.delete(permissionId)));
  dispatch(setEditView(editView.delete(permissionId)));
  resolve();
}

export const removePermission = ({ permissionId, editView, permissionView , resolve }) => {
  return (dispatch) => {
    coProxy.wrap(doRemovePermission)(dispatch, { permissionId, editView, permissionView , resolve });
  }
}
