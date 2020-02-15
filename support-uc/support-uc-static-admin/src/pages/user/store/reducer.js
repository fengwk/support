import { fromJS } from 'immutable';
import * as constants from './constants';

const defaultState = fromJS({
  searchType: 'email',
  searchValue: '',
  pageNumber: 1,
  pageSize: 10,
  total: 0,
  userView: {},
  editView: {},
  tableLoading: false,
  roleModalVisible: false,
  roleModalUserId: 0,
  roleModalNickname: 0,
  roleModalOwnedRoles: [],
  roleModalSearchRoles: []
});

export default (state = defaultState, action) => {
  switch(action.type) {

    case constants.SET_SEARCH_TYPE:
      return state.set('searchType', action.searchType);

    case constants.SET_SEARCH_VALUE:
      return state.set('searchValue', action.value);

    case constants.SET_PAGE_NUMBER:
      return state.set('pageNumber', action.pageNumber);

    case constants.SET_PAGE_SIZE:
      return state.set('pageSize', action.pageSize);

    case constants.SET_TOTAL:
      return state.set('total', action.total);

    case constants.SET_USER_VIEW:
      return state.set('userView', action.userView);

    case constants.SET_EDIT_VIEW:
      return state.set('editView', action.editView);

    case constants.SET_TABLE_LOADING:
      return state.set('tableLoading', action.loading);

    case constants.SET_ROLE_MODAL_VISIBLE:
      return state.set('roleModalVisible', action.roleModalVisible);

    case constants.SET_ROLE_MODAL_USER_ID:
      return state.set('roleModalUserId', action.roleModalUserId);

    case constants.SET_ROLE_MODAL_NICKNAME:
      return state.set('roleModalNickname', action.roleModalNickname);

    case constants.SET_ROLE_MODAL_OWNED_ROLES:
      return state.set('roleModalOwnedRoles', action.roleModalOwnedRoles);

    case constants.SET_ROLE_MODAL_SEARCH_ROLES:
      return state.set('roleModalSearchRoles', action.roleModalSearchRoles);

    default:
      return state;
  }
}
