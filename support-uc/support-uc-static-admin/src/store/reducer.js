import { combineReducers } from 'redux-immutable';
import { reducer as userReducer } from '../pages/user/store';
import { reducer as roleReducer } from '../pages/role/store';
import { reducer as permissionReducer } from '../pages/permission/store';
import { reducer as clientReducer } from '../pages/client/store';

const reducer = combineReducers({
  user: userReducer,
  role: roleReducer,
  permission: permissionReducer,
  client: clientReducer
});

export default reducer;
