import { fromJS } from 'immutable';
import * as constants from './constants';

const defaultState = fromJS({
  searchType: 'email',
  searchValue: '',
  pageNumber: 1,
  pageSize: 15,
  total: 0,
  userView: {},
  editView: {}
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

    default:
      return state;
  }
}
