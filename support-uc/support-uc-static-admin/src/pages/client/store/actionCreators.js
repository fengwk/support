import * as constants from './constants';
import { fromJS, OrderedMap } from 'immutable';
import { message } from 'antd';
import coProxy from '../../../utils/coProxy';
import * as clientService from '../../../services/clientService';

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

const setClientView = (clientView) => ({
  type: constants.SET_CLIENT_VIEW,
  clientView
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
    const page = yield clientService.search({ ...param, name: param.searchValue });
    // 初始化视图
    const view = {};
    for (const item of page.results) {
      view[item.id] = fromJS({ ...item, editable: false });
    }
    const iview = OrderedMap(view);
    dispatch(setClientView(iview));
    dispatch(setEditView(iview));

    dispatch(setPageNumber(page.pageNumber));
    dispatch(setPageSize(page.pageSize));
    dispatch(setTotal(page.total));
  } finally {
    dispatch(setTableLoading(false));
  }
}

function* doCreate(dispatch, editView, editItem, clientView) {
  const name = editItem.get('name');
  const redirectRules = editItem.get('redirectRules');
  const accessExpiresIn = editItem.get('accessExpiresIn');
  const refreshExpiresIn = editItem.get('refreshExpiresIn');
  const isExclusive = editItem.get('isExclusive');
  const tokenCountLimit = editItem.get('tokenCountLimit');
  const isDisabled = editItem.get('isDisabled');
  if (!name) { return message.warning('角色名称不能为空'); }
  if (!redirectRules || redirectRules.length == 0) { return message.warning('重定向规则不能为空'); }
  if (!accessExpiresIn) { return message.warning('访问令牌超时不能为空'); }
  if (accessExpiresIn <= 0) { return message.warning('访问令牌超时必须大于0'); }
  if (!refreshExpiresIn) { return message.warning('访问令牌超时不能为空'); }
  if (refreshExpiresIn <= 0) { return message.warning('访问令牌超时必须大于0'); }
  if (isExclusive === null || isExclusive === undefined) { return message.warning('模式不能为空'); }
  if (!tokenCountLimit) { return message.warning('限制令牌数量不能为空'); }
  if (tokenCountLimit <= 0) { return message.warning('限制令牌数量时必须大于0'); }
  if (isDisabled === null || isDisabled === undefined) { return message.warning('请选择禁用状态'); }

  const createdClient = yield clientService.create({ name, redirectRules, accessExpiresIn, refreshExpiresIn, isExclusive, tokenCountLimit, isDisabled });
  const createdClientView = fromJS({ ...createdClient, editable: false });

  const newEditView = editView.delete(editItem.get('id'));
  dispatch(setClientView(clientView.set(createdClient.id, createdClientView)));
  dispatch(setEditView(newEditView.set(createdClient.id, createdClientView)));
}

function* doUpdate(dispatch, editView, editItem, clientView) {
  const clientId = editItem.get('id');
  const name = editItem.get('name');
  const redirectRules = editItem.get('redirectRules');
  const accessExpiresIn = editItem.get('accessExpiresIn');
  const refreshExpiresIn = editItem.get('refreshExpiresIn');
  const isExclusive = editItem.get('isExclusive');
  const tokenCountLimit = editItem.get('tokenCountLimit');
  const isDisabled = editItem.get('isDisabled');
  if (!name) { return message.warning('角色名称不能为空'); }
  if (!redirectRules || redirectRules.length == 0) { return message.warning('重定向规则不能为空'); }
  if (!accessExpiresIn) { return message.warning('访问令牌超时不能为空'); }
  if (accessExpiresIn <= 0) { return message.warning('访问令牌超时必须大于0'); }
  if (!refreshExpiresIn) { return message.warning('访问令牌超时不能为空'); }
  if (refreshExpiresIn <= 0) { return message.warning('访问令牌超时必须大于0'); }
  if (isExclusive === null || isExclusive === undefined) { return message.warning('模式不能为空'); }
  if (!tokenCountLimit) { return message.warning('限制令牌数量不能为空'); }
  if (tokenCountLimit <= 0) { return message.warning('限制令牌数量时必须大于0'); }
  if (isDisabled === null || isDisabled === undefined) { return message.warning('请选择禁用状态'); }

  const prev = clientView.get(clientId);
  if (prev.get('name') === name
    && prev.get('redirectRules') === redirectRules
    && prev.get('accessExpiresIn') === accessExpiresIn
    && prev.get('refreshExpiresIn') === refreshExpiresIn
    && prev.get('isExclusive') === isExclusive
    && prev.get('tokenCountLimit') === tokenCountLimit
    && prev.get('isDisabled') === isDisabled) {
    // 没有修改
    dispatch(setEditView(editView.set(clientId, editItem.set('editable', false))));
  } else {
    // 有修改
    const updatedClient = yield clientService.update({ clientId, name, redirectRules, accessExpiresIn, refreshExpiresIn, isExclusive, tokenCountLimit, isDisabled });
    const updatedClientView = fromJS({ ...updatedClient, editable: false });
    dispatch(setClientView(clientView.set(clientId, updatedClientView)));
    dispatch(setEditView(editView.set(clientId, updatedClientView)));
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

export const save = (editView, editItem, clientView) => {
  return (dispatch) => {
    if (Number(editItem.get('id')) < 0) {
      // 新增
      coProxy.wrap(doCreate)(dispatch, editView, editItem, clientView);
    } else {
      // 更新
      coProxy.wrap(doUpdate)(dispatch, editView, editItem, clientView);
    }
  }
}

function* doRemoveClient(dispatch, { clientId, editView, clientView, resolve }) {
  yield clientService.remove({ clientId });
  dispatch(setClientView(clientView.delete(clientId)));
  dispatch(setEditView(editView.delete(clientId)));
  resolve();
}

export const removeClient = ({ clientId, editView, clientView , resolve }) => {
  return (dispatch) => {
    coProxy.wrap(doRemoveClient)(dispatch, { clientId, editView, clientView , resolve });
  }
}

function* doRefreshSecret(dispatch, { clientId, editView, clientView }) {
  const newSecret = yield clientService.refreshSecret({ clientId });
  dispatch(setClientView(clientView.setIn([ clientId, 'secret' ], newSecret)));
  dispatch(setEditView(editView.setIn([ clientId, 'secret' ], newSecret)));
}

export const refreshSecret = ({ clientId, editView, clientView }) => {
  return (dispatch) => {
    coProxy.wrap(doRefreshSecret)(dispatch, { clientId, editView, clientView });
  }
}
