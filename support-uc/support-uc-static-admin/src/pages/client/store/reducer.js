import { fromJS } from 'immutable';
import * as constants from './constants';

const defaultState = fromJS({
  searchValue: '',
  pageNumber: 1,
  pageSize: 10,
  total: 0,
  clientView: {},
  editView: {},
  tableLoading: false,
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

    case constants.SET_CLIENT_VIEW:
      return state.set('clientView', action.clientView);

    case constants.SET_EDIT_VIEW:
      return state.set('editView', action.editView);

    case constants.SET_TABLE_LOADING:
      return state.set('tableLoading', action.loading);

    default:
      return state;
  }
}
