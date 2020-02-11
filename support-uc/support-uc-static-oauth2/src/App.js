import React, { PureComponent } from 'react';
import { HashRouter, Route } from 'react-router-dom';
import { Provider } from 'react-redux';
import { ConfigProvider } from 'antd';
import zhCN from 'antd/es/locale/zh_CN';
import store, { actionCreators } from './store';
import Login from './pages/login';
import SignUp from './pages/signUp';
import Reset from './pages/reset';
import 'normalize.css';
import './App.less';

class App extends PureComponent {

  render() {
    return (
      <Provider store={store}>
        <ConfigProvider locale={zhCN}>
          <HashRouter>
            <Route path="/" exact component={Login} />
            <Route path="/sign-up" exact component={SignUp} />
            <Route path="/reset" exact component={Reset} />
          </HashRouter>
        </ConfigProvider>
      </Provider>
    );
  }

  componentWillMount() {
    const search = (location || window.location).search;
    if (!search || search.lenth <= 1) {
      return;
    }
    const ouathParam = {};
    for (let param of search.substr(1).split('&')) {
      let parts = param.split('=');
      if (parts.lenth <= 1) {
        continue;
      }
      ouathParam[parts[0]] = parts[1];
    }
    store.dispatch(actionCreators.setOAuthParam(ouathParam));
  }

}

export default App;
