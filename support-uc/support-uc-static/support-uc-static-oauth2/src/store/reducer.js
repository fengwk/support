import { fromJS } from 'immutable';
import { combineReducers } from 'redux-immutable';
import { reducer as loginReducer } from '../pages/login/store';
import { reducer as registerReducer } from '../pages/register/store';
import { reducer as resetReducer } from '../pages/reset/store';
import * as constants from './constants';

const defaultGlobalReducer = fromJS({
  responseType: 'code',
  clientId: 1,
  redirectUri: '/',
  scope: 'normal',
  state: ''
});

const globalReducer = (state = defaultGlobalReducer, action) => {
    switch(action.type) {
      case constants.SET_OAUTH_PARAM:
        return state
          .set('responseType', decodeURIComponent(action.response_type))
          .set('clientId', new Number(decodeURIComponent(action.client_id)))
          .set('redirectUri', decodeURIComponent(action.redirect_uri))
          .set('scope', decodeURIComponent(action.scope))
          .set('state', decodeURIComponent(action.state));

      default:
        return state;
    }
};

const reducer = combineReducers({
  global: globalReducer,
  login: loginReducer,
  register: registerReducer,
  reset: resetReducer
});

export default reducer;
