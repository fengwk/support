import React, { PureComponent } from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter, Route } from 'react-router-dom';
import { Provider } from 'react-redux';
import { ConfigProvider } from 'antd';
import zhCN from 'antd/es/locale/zh_CN';
import store, { actionCreators } from './store';
import Login from './pages/login';
import Register from './pages/register';
import Reset from './pages/reset';
import 'normalize.css';
import './App.less';

console.log(store.dispatch)

class App extends PureComponent {

  render() {
    return (
      <Provider store={store}>
        <ConfigProvider locale={zhCN}>
          <BrowserRouter>
            <Route path="/" exact component={Login} />
            <Route path="/register" exact component={Register} />
            <Route path="/reset" exact component={Reset} />
          </BrowserRouter>
        </ConfigProvider>
      </Provider>
    );
  }

  componentWillMount() {
    const search = (location || window.location).search;
    console.log(search)
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