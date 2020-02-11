import React, { PureComponent } from 'react';
import { connect } from "react-redux";
import { actionCreators } from './store';
import { Tabs } from 'antd';
import EmailPasswordLogin from './components/EmailPasswordLogin';
import EmailRandomLogin from './components/EmailRandomLogin';
import styles from './index.less';

class Login extends PureComponent {

  constructor(props) {
    super(props);
  }

  render() {
    const { way } = this.props;
    const { switchLoginWay } = this.props;
    return (
      <div className={styles.loginPage}>
        <div className={styles.loginPosition}>
          <div className={styles.loginWrapper}>
            <Tabs activeKey={way} onTabClick={switchLoginWay}>
              <Tabs.TabPane tab="密码登录" key="1">
                <EmailPasswordLogin />
              </Tabs.TabPane>
              <Tabs.TabPane tab="免密登录" key="2">
                <EmailRandomLogin />
              </Tabs.TabPane>
            </Tabs>
          </div>
          <div className={styles.signatureWrapper}>
            Power by <span className={styles.signature}>fengwk.com</span>
          </div>
        </div>
      </div>
    );
  }

}

const mapStateToProps = (state) => ({
  way: state.getIn(['login', 'way'])
});

const mapDispatchToProps = (dispatch) => ({
  switchLoginWay(way) { dispatch(actionCreators.switchLoginWay(way)) }
});

export default connect(mapStateToProps, mapDispatchToProps)(Login);
