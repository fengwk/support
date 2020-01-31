import React, { PureComponent } from 'react';
import { withRouter } from 'react-router-dom';
import { connect } from 'react-redux';
import { actionCreators } from '../store';
import { Input, Icon, Button } from 'antd';
import PasswordSvg from '../../../statics/password.svg';
import RepeatSvg from '../../../statics/repeat.svg';
import styles from './Step2.less';

class Step2 extends PureComponent {

  render() {
    const { history } = this.props;
    const { email, random, nickname, password, confirmPassword, responseType, clientId, redirectUri, scope, state } = this.props;
    const { changeNickname, changePassword, changeConfirmPassword, nextStep, backLogin } = this.props;
    const param = { email, random, nickname, password, confirmPassword, responseType, clientId, redirectUri, scope, state };
    return (
      <div>
        <Input.Password
          className={styles.passwordInput}
          autoFocus
          prefix={<Icon component={PasswordSvg} fill="#AAA" />}
          placeholder="请输入新密码"
          value={password}
          onChange={changePassword}
          onPressEnter={() => { nextStep(history, param, this.passwordInput, this.confirmPasswordInput); }}
          ref={ (passwordInput) => {this.passwordInput = passwordInput} }
        />
        <Input.Password
          className={styles.confirmPasswordInput}
          prefix={<Icon component={RepeatSvg} fill="#AAA" />}
          placeholder="请确认新密码"
          value={confirmPassword}
          onChange={changeConfirmPassword}
          onPressEnter={() => { nextStep(history, param, this.passwordInput, this.confirmPasswordInput); }}
          ref={ (confirmPasswordInput) => {this.confirmPasswordInput = confirmPasswordInput} }
        />
        <div className={styles.btnLine}>
          <Button 
            className={styles.backBtn}
            type="primary" 
            onClick={() => { backLogin(history, email); }}
          >
            回去登录
          </Button>
          <Button 
            className={styles.submitBtn}
            type="primary" 
            block
            onClick={() => { nextStep(history, param, this.passwordInput, this.confirmPasswordInput); }}
          >
            重置并登录
          </Button>
        </div>
      </div>
    );
  }

}

const mapStateToProps = (state) => ({
  email: state.getIn(['reset', 'email']), 
  random: state.getIn(['reset', 'random']), 
  nickname: state.getIn(['reset', 'nickname']), 
  password: state.getIn(['reset', 'password']), 
  confirmPassword: state.getIn(['reset', 'confirmPassword']),

  responseType: state.getIn(['global', 'responseType']),
  clientId: state.getIn(['global', 'clientId']),
  redirectUri: state.getIn(['global', 'redirectUri']),
  scope: state.getIn(['global', 'scope']),
  state: state.getIn(['global', 'state'])
});

const mapDispatchToProps = (dispatch) => ({
  changeNickname(e) {
    dispatch(actionCreators.changeNickname(e.target.value));
  },
  changePassword(e) {
    dispatch(actionCreators.changePassword(e.target.value));
  },
  changeConfirmPassword(e) {
    dispatch(actionCreators.changeConfirmPassword(e.target.value));
  },
  nextStep(history, param, passwordInput, confirmPasswordInput) {
    dispatch(actionCreators.nextStep2(history, param, passwordInput, confirmPasswordInput));
  },
  backLogin(history, email) {
    dispatch(actionCreators.backLogin(history, email));
  }
});

export default connect(mapStateToProps, mapDispatchToProps)(withRouter(Step2));