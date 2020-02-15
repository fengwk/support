import * as constants from './constants';
import { fromJS } from 'immutable';
import coProxy from '../../../utils/coProxy';
import * as userService from '../../../services/userService';
import * as roleService from '../../../services/roleService';

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

const setUserView = (userView) => ({
  type: constants.SET_USER_VIEW,
  userView
});

const setTableLoading = (loading) => ({
  type: constants.SET_TABLE_LOADING,
  loading
});

export const setRoleModalVisible = (roleModalVisible) => ({
  type: constants.SET_ROLE_MODAL_VISIBLE,
  roleModalVisible
});

export const setRoleModalUserId = (roleModalUserId) => ({
  type: constants.SET_ROLE_MODAL_USER_ID,
  roleModalUserId
});

export const setRoleModalNickname = (roleModalNickname) => ({
  type: constants.SET_ROLE_MODAL_NICKNAME,
  roleModalNickname
});

const setRoleModalOwnedRoles = (roleModalOwnedRoles) => ({
  type: constants.SET_ROLE_MODAL_OWNED_ROLES,
  roleModalOwnedRoles
});

const setRoleModalSearchRoles = (roleModalSearchRoles) => ({
  type: constants.SET_ROLE_MODAL_SEARCH_ROLES,
  roleModalSearchRoles
});

export const setEditView = (editView) => ({
  type: constants.SET_EDIT_VIEW,
  editView
});

function* doSearch(dispatch, param) {
  dispatch(setTableLoading(true));
  try {
    const page = yield userService.search(param);
    // 初始化视图
    const view = {};
    for (const item of page.results) {
      view[item.id] = { ...item, editable: false };
    }
    const iview = fromJS(view);
    dispatch(setUserView(iview));
    dispatch(setEditView(iview));

    dispatch(setPageNumber(page.pageNumber));
    dispatch(setPageSize(page.pageSize));
    dispatch(setTotal(page.total));
  } finally {
    dispatch(setTableLoading(false));
  }
}

function* doSave(dispatch, editView, editItem, userView) {
  const userId = editItem.get('id');
  const email = editItem.get('email');
  const nickname = editItem.get('nickname');

  const prev = userView.get(userId);
  if (prev.get('email') === email && prev.get('nickname') === nickname) {
    // 没有修改
    dispatch(setEditView(editView.set(userId, editItem.set('editable', false))));
  } else {
    // 有修改
    const updatedUser = yield userService.update({ userId, email, nickname });
    const updatedUserView = fromJS({ ...updatedUser, editable: false });
    dispatch(setUserView(userView.set(userId, updatedUserView)));
    dispatch(setEditView(editView.set(userId, updatedUserView)));
  }
}

function* doShowRoleModal(dispatch, userId) {
  const roles = yield userService.listRoles({ userId });
  dispatch(setRoleModalOwnedRoles(fromJS(roles)));
}

function* doSearchRoles(dispatch, value) {
  const page = yield roleService.search({ pageNumber: 1, pageSize: 30, name: value });// 最多返回30条匹配项
  dispatch(setRoleModalSearchRoles(fromJS(page.results)));
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
    cocoProxy.wrap(doSearch)(dispatch, param);
  }
}

export const setSearchType = (searchType) => ({
  type: constants.SET_SEARCH_TYPE,
  searchType
});

export const setSearchValue = (value) => ({
  type: constants.SET_SEARCH_VALUE,
  value
});

export const save = (editView, editItem, userView) => {
  return (dispatch) => {
    coProxy.wrap(doSave)(dispatch, editView, editItem, userView);
  }
}

export const showRoleModal = ({ userId, nickname }) => {
  return (dispatch) => {
    dispatch(setRoleModalVisible(true));
    dispatch(setRoleModalUserId(userId));
    dispatch(setRoleModalNickname(nickname));
    coProxy.wrap(doShowRoleModal)(dispatch, userId);
  }
}

export const searchRoles = (value) => {
  return (dispatch) => {
    coProxy.wrap(doSearchRoles)(dispatch, value);
  }
}

function* doGrantRole(dispatch, { userId, value, roleModalOwnedRoles }) {
  yield userService.grantRole({ userId, roleId: value.key });
  const nweRoleModalOwnedRoles = roleModalOwnedRoles.push(fromJS({ id: value.key, name: value.label }));
  dispatch(setRoleModalOwnedRoles(nweRoleModalOwnedRoles));
}

export const grantRole = ({ userId, value, roleModalOwnedRoles }) => {
  return (dispatch) => {
    coProxy.wrap(doGrantRole)(dispatch, { userId, value, roleModalOwnedRoles });
  }
}

function* doRevokeRole(dispatch, { userId, value, roleModalOwnedRoles }) {
  yield userService.revokeRole({ userId, roleId: value.key });
  const newRoleModalOwnedRoles = [];
  roleModalOwnedRoles.map((item, index) => {
    if (item.get('id') !== value.key) {
      newRoleModalOwnedRoles.push(item);
    }
  });
  dispatch(setRoleModalOwnedRoles(fromJS(newRoleModalOwnedRoles)));
}

export const revokeRole = ({ userId, value, roleModalOwnedRoles }) => {
  return (dispatch) => {
    coProxy.wrap(doRevokeRole)(dispatch, { userId, value, roleModalOwnedRoles });
  }
}
