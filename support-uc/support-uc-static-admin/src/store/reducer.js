import { fromJS } from 'immutable';
import { combineReducers } from 'redux-immutable';
import { reducer as userReducer } from '../pages/user/store';
import * as constants from './constants';

const reducer = combineReducers({
  user: userReducer
});

export default reducer;