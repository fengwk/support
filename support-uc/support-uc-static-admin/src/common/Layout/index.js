import React, { PureComponent } from 'react';
import { withRouter } from 'react-router-dom';
import { Layout, Menu, Icon, Affix, Button } from 'antd';
import { revokeToken, jumpOAuth2 } from "../../services/authService";
import styles from './index.less';

const { Sider, Content } = Layout;

class MyLayout extends PureComponent {

  constructor(props) {
    super(props);

    this.state = {
      collapsed: false

    };

    this.toggleMenu = this.toggleMenu.bind(this);
    this.selectMenu = this.selectMenu.bind(this);
  }

  render() {
    const { children } = this.props;
    const { collapsed } = this.state;
    const path = this.props.location.pathname;
    return (
      <Layout className={styles.container}>
        <Sider collapsed={collapsed}>
          <div className={styles.logo}>
            { collapsed ? 'UC' : 'UC ADMIN' }
          </div>
          <Menu theme="dark" mode="inline" selectedKeys={[path]} onSelect={this.selectMenu}>
            <Menu.Item key="/">
              <Icon type="user" />
              <span>用户</span>
            </Menu.Item>
            <Menu.Item key="/role">
              <Icon type="team" />
              <span>角色</span>
            </Menu.Item>
            <Menu.Item key="/permission">
              <Icon type="safety" />
              <span>权限</span>
            </Menu.Item>
            <Menu.Item key="/client">
              <Icon type="cluster" />
              <span>客户端</span>
            </Menu.Item>
          </Menu>
        </Sider>
        <Content>
          <Affix offsetTop={1}>
            <div className={styles.header}>
              <Icon
                className={styles.trigger}
                type={collapsed ? 'menu-unfold' : 'menu-fold'}
                onClick={this.toggleMenu}
              />
              <div className={styles.headerRight}>
                <span className={styles.userinfo}>欢迎您，admin</span>
                <Button
                  className={styles.logout}
                  type="danger"
                  icon="poweroff"
                  onClick={() => { revokeToken().then(() => { jumpOAuth2(); }); }}
                >
                  注销
                </Button>
              </div>
            </div>
          </Affix>
          { children }
        </Content>
      </Layout>
    );
  }

  toggleMenu() {
    this.setState((prevState) => ({collapsed: !prevState.collapsed}));
  }

  selectMenu({ key }) {
    this.props.history.push(key);
  }

}

export default withRouter(MyLayout);
