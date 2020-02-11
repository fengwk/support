import React, { PureComponent } from 'react';
import { withRouter } from 'react-router-dom';
import { connect } from 'react-redux';
import { actionCreators } from '../store';
import { Input, Icon, Button } from 'antd';
import EmailSvg from '../../../statics/email.svg';
import PasswordSvg from '../../../statics/password.svg';
import styles from './EmailPasswordLogin.less';

class EmailPasswordLogin extends PureComponent {

  render() {
    const { history } = this.props;
    const { email, password, responseType, clientId, redirectUri, scope, state } = this.props;
    const { changeEmail, changePassword, loginByRandom, signUpOrLogin, forgetPwd } = this.props;
    const param = { email, password, responseType, clientId, redirectUri, scope, state };
    return (
      <div>
        <Input
          className={styles.emailInput}
          autoFocus
          prefix={<Icon component={EmailSvg} fill="#AAAAAA" />}
          placeholder="邮箱"
          value={email}
          onChange={changeEmail}
          onPressEnter={() => { signUpOrLogin(history, param, this.emailInput, this.pwdInput); }}
          ref={ (emailInput) => {this.emailInput = emailInput} }
        />
        <Input.Password
          className={styles.pwdInput}
          prefix={<Icon component={PasswordSvg} fill="#AAAAAA" />}
          placeholder="密码"
          value={password}
          onChange={changePassword}
          onPressEnter={() => { signUpOrLogin(history, param, this.emailInput, this.pwdInput); }}
          ref={ (pwdInput) => {this.pwdInput = pwdInput} }
        />
        <div className={styles.funcLine}>
          <span className={styles.resetPwd} onClick={loginByRandom}>使用验证码登录</span>
          <span className={styles.forgetPwd} onClick={() => { forgetPwd(history, email); } }>忘记密码？</span>
        </div>
        <Button 
          className={styles.submitBtn} 
          type="primary" 
          block
          onClick={() => { signUpOrLogin(history, param, this.emailInput, this.pwdInput); }}
        >
          注册/登录
        </Button>
      </div>
    );
  }

}

const mapStateToProps = (state) => ({
  email: state.getIn(['login', 'email']),
  password: state.getIn(['login', 'password']),

  responseType: state.getIn(['global', 'responseType']),
  clientId: state.getIn(['global', 'clientId']),
  redirectUri: state.getIn(['global', 'redirectUri']),
  scope: state.getIn(['global', 'scope']),
  state: state.getIn(['global', 'state'])
});

const mapDispatchToProps = (dispatch) => ({
  changeEmail(e) {
    const value = e.target.value;
    dispatch(actionCreators.changeEmail(value));
  },
  changePassword(e) {
    const value = e.target.value;
    dispatch(actionCreators.changePassword(value));
  },
  loginByRandom() {
    dispatch(actionCreators.switchLoginWay('2'));
  },
  signUpOrLogin(history, param, emailInput, pwdInput) {
    dispatch(actionCreators.signUpOrLoginByEmailAndPassword(history, param, emailInput, pwdInput));
  },
  forgetPwd(history, email) {
    dispatch(actionCreators.forgetPwd(history, email));
  }
});

export default connect(mapStateToProps, mapDispatchToProps)(withRouter(EmailPasswordLogin));
