import * as actionCreators from './actionCreators';
import { createStore, applyMiddleware } from 'redux';
import thunk from 'redux-thunk';
import reducer from './reducer';

let _store;
try {
  // https://github.com/zalmoxisus/redux-devtools-extension
  const composeEnhancers = typeof window === 'object' && window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ ? window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__({}) : compose;
  const enhancer = composeEnhancers(applyMiddleware(thunk));
  _store = createStore(reducer, enhancer);
} catch(e) {
   _store = createStore(reducer, applyMiddleware(thunk));
}

export { actionCreators };
export default _store;