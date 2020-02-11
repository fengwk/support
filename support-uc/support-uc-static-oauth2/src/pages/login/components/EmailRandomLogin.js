import React, { PureComponent } from 'react';
import { withRouter } from 'react-router-dom';
import { connect } from 'react-redux';
import { actionCreators } from '../store';
import { Input, Icon, Button } from 'antd';
import EmailSvg from '../../../statics/email.svg';
import RandomSvg from '../../../statics/random.svg';
import SendButton from '../../../common/SendButton';
import styles from './EmailRandomLogin.less';

class EmailRandomLogin extends PureComponent {

  render() {
    const { history } = this.props;
    const { email, random, responseType, clientId, redirectUri, scope, state, isSendedRandom, randomRemainingSecond } = this.props;
    const { changeEmail, changeRandom, sendRandom, signUpOrLogin } = this.props;
    const param = { email, random, responseType, clientId, redirectUri, scope, state };
    return (
      <div>
        <Input
          className={styles.emailInput}
          autoFocus
          prefix={<Icon component={EmailSvg} fill="#AAAAAA" />}
          placeholder="邮箱"
          value={email}
          onChange={changeEmail}
          onPressEnter={() => { signUpOrLogin(history, param, this.emailInput, this.randomInput); }}
          ref={ (emailInput) => {this.emailInput = emailInput} }
        />
        <div className={styles.randomLine}>
          <Input
            className={styles.randomInput}
            prefix={<Icon component={RandomSvg} fill="#AAAAAA" />}
            placeholder="邮箱验证码"
            value={random}
            onChange={changeRandom}
            onPressEnter={() => { signUpOrLogin(history, param, this.emailInput, this.randomInput); }}
            ref={ (randomInput) => {this.randomInput = randomInput} }
          />
          <SendButton 
            className={styles.sendBtn} 
            defaultText="发送验证码" 
            isSended={isSendedRandom}
            remainingSecond={randomRemainingSecond}
            onClick={() => { sendRandom(history, email, this.emailInput, this.randomInput) }}
          />
        </div>
        <Button 
          className={styles.submitBtn} 
          type="primary" 
          block
          onClick={() => { signUpOrLogin(history, param, this.emailInput, this.randomInput); }}
        >
          注册/登录
        </Button>
      </div>
    );
  }

}

const mapStateToProps = (state) => ({
  email: state.getIn(['login', 'email']),
  random: state.getIn(['login', 'random']),
  isSendedRandom: state.getIn(['login', 'isSendedRandom']),
  randomRemainingSecond: state.getIn(['login', 'randomRemainingSecond']),

  responseType: state.getIn(['global', 'responseType']),
  clientId: state.getIn(['global', 'clientId']),
  redirectUri: state.getIn(['global', 'redirectUri']),
  scope: state.getIn(['global', 'scope']),
  state: state.getIn(['global', 'state'])
})

const mapDispatchToProps = (dispatch) => ({
  changeEmail(e) {
    const value = e.target.value;
    dispatch(actionCreators.changeEmail(value));
  },
  changeRandom(e) {
    const value = e.target.value;
    dispatch(actionCreators.changeRandom(value));
  },
  sendRandom(history, email, emailInput, randomInput) {
    dispatch(actionCreators.sendRandom(history, email, emailInput, randomInput));
  },
  signUpOrLogin(history, param, emailInput, randomInput) {
    dispatch(actionCreators.signUpOrLoginByEmailAndRandom(history, param, emailInput, randomInput));
  }
})

export default connect(mapStateToProps, mapDispatchToProps)(withRouter(EmailRandomLogin));
