import React, { PureComponent } from 'react';
import { HashRouter, Route } from 'react-router-dom';
import { Provider } from 'react-redux';
import { ConfigProvider } from 'antd';
import zhCN from 'antd/es/locale/zh_CN';
import store, { actionCreators } from './store';
import Layout from './common/Layout';
import User from './pages/user';
import Role from './pages/role';
import Permission from './pages/permission';
import Client from './pages/client';
import * as authService from './services/authService';
import { tryGetCode, replaceSearch, getAccessToken, setAccessToken, registerOnTokenInvail } from "./utils/authorize";
import 'normalize.css';
import './App.less';

class App extends PureComponent {

  constructor(props) {
    super(props);
    this.state = {
      init: false
    }
  }

  render() {
    if (this.state.init) {
      return (
        <Provider store={store}>
          <ConfigProvider locale={zhCN}>
            <HashRouter>
              <Route path="/" component={() => (
                <Layout>
                  <Route path="/" exact component={User} />
                  <Route path="/role" exact component={Role} />
                  <Route path="/permission" exact component={Permission} />
                  <Route path="/client" exact component={Client} />
                </Layout>
              )}
              />
            </HashRouter>
          </ConfigProvider>
        </Provider>
      );
    } else {
      return <span></span>;
    }
  }

  componentDidMount() {
    const code = tryGetCode();
    if (code) {
      // 若有授权码优先使用授权码重新登录
      replaceSearch();
      authService.getToken({ code }).then((accessToken) => {
        setAccessToken(accessToken);

        // 注册令牌失效的回调函数,一旦失效则跳回登录页
        registerOnTokenInvail(() => {
          authService.jumpOAuth2();// 授权失败直接跳转
        });

        this.setState((prevState) => ({ init: true }));// 初始化完成
      }).catch((err) => {
        console.error(err);
        authService.jumpOAuth2();// 授权失败直接跳转
      });

    } else if (getAccessToken()) {
      // 没有授权码,但是之前有存储访问令牌
      this.setState((prevState) => ({ init: true }));// 初始化完成

    } else {
      // 既没有授权码,也没有令牌直接跳转到oauth2页面
      authService.jumpOAuth2();

    }
  }

}

export default App;
