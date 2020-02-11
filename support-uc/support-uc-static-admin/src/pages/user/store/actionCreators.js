import * as constants from './constants';
import { fromJS } from 'immutable';
import { message, Modal } from 'antd';
import co from 'co';
import { isEmail } from '../../../utils/validator';
import { createLock } from '../../../utils/lockFactory';
import * as userService from '../../../services/userService';

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

export const setEditView = (editView) => ({
  type: constants.SET_EDIT_VIEW,
  editView
});

function* doSearch(dispatch, param) {
  const page = yield userService.search(param);
  // 初始化视图
  const view = {};
  for (const item of page.results) {
    view[item.id] = { ...item, editable: false };
  }
  const iview = fromJS(view);
  dispatch(setUserView(iview));
  dispatch(setEditView(iview));

  dispatch(setTotal(page.total));
}

function* doSave(dispatch, editView, editItem, userView) {
  console.log(editItem.toJS())
  const updatedUser = yield userService.update({ ...editItem.toJS(), userId: editItem.get('id') });
  const updatedUserView = fromJS({ ...updatedUser, editable: false });
  dispatch(setUserView(editView.set(updatedUser.id, updatedUserView)));
  dispatch(setEditView(userView.set(updatedUser.id, updatedUserView)));
}

export const search = (param) => {
  return (dispatch) => {
    const lock = createLock('login/search');
    if (!lock.tryLock()) { return; }
    co.wrap(doSearch)(dispatch, param)
      .finally(() => { lock.unlock(); });
  }
};

export const changePage = (param) => {
  return (dispatch) => {
    const lock = createLock('login/changePage');
    if (!lock.tryLock()) { return; }
    dispatch(setPageNumber(param.pageNumber));
    dispatch(setPageSize(param.pageSize));
    co.wrap(doSearch)(dispatch, param)
      .finally(() => { lock.unlock(); });
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
    const lock = createLock('login/save');
    if (!lock.tryLock()) { return; }
    co.wrap(doSave)(dispatch, editView, editItem, userView)
      .finally(() => { lock.unlock(); });
  }
}