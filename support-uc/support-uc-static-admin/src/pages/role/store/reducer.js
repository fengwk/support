import { fromJS } from 'immutable';
import * as constants from './constants';

const defaultState = fromJS({
  searchValue: '',
  pageNumber: 1,
  pageSize: 10,
  total: 0,
  roleView: {},
  editView: {},
  tableLoading: false,
  permissionModalVisible: false,
  permissionModalRoleId: 0,
  permissionModalNickname: 0,
  permissionModalOwnedPermissions: [],
  permissionModalSearchPermissions: []
});

export default (state = defaultState, action) => {
  switch(action.type) {

    case constants.SET_SEARCH_VALUE:
      return state.set('searchValue', action.value);

    case constants.SET_PAGE_NUMBER:
      return state.set('pageNumber', action.pageNumber);

    case constants.SET_PAGE_SIZE:
      return state.set('pageSize', action.pageSize);

    case constants.SET_TOTAL:
      return state.set('total', action.total);

    case constants.SET_ROLE_VIEW:
      return state.set('roleView', action.roleView);

    case constants.SET_EDIT_VIEW:
      return state.set('editView', action.editView);

    case constants.SET_TABLE_LOADING:
      return state.set('tableLoading', action.loading);

    case constants.SET_PERMISSION_MODAL_VISIBLE:
      return state.set('permissionModalVisible', action.permissionModalVisible);

    case constants.SET_PERMISSION_MODAL_ROLE_ID:
      return state.set('permissionModalRoleId', action.permissionModalRoleId);

    case constants.SET_PERMISSION_MODAL_NICKNAME:
      return state.set('permissionModalNickname', action.permissionModalNickname);

    case constants.SET_PERMISSION_MODAL_OWNED_PERMISSIONS:
      return state.set('permissionModalOwnedPermissions', action.permissionModalOwnedPermissions);

    case constants.SET_PERMISSION_MODAL_SEARCH_PERMISSIONS:
      return state.set('permissionModalSearchPermissions', action.permissionModalSearchPermissions);

    default:
      return state;
  }
}
