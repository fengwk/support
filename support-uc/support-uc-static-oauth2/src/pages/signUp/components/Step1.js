import React, { PureComponent } from 'react';
import { withRouter } from 'react-router-dom';
import { connect } from 'react-redux';
import { actionCreators } from '../store';
import { Input, Icon, Button } from 'antd';
import EmailSvg from '../../../statics/email.svg';
import RandomSvg from '../../../statics/random.svg';
import SendButton from '../../../common/SendButton';
import styles from './Step1.less';

class Step1 extends PureComponent {

  render() {
    const { history } = this.props;
    const { email, random, isSendedRandom, randomRemainingSecond } = this.props;
    const { changeEmail, changeRandom, sendRandom, nextStep, backLogin } = this.props;
    const param = { email, random };
    return (
      <div>
        <Input
          className={styles.emailInput}
          autoFocus
          prefix={<Icon component={EmailSvg} fill="#AAAAAA" />}
          placeholder="请输入邮箱"
          value={email}
          onChange={changeEmail}
          onPressEnter={() => { nextStep(history, param, this.emailInput, this.randomInput); }}
          ref={ (emailInput) => {this.emailInput = emailInput} }
        />
        <div className={styles.randomLine}>
          <Input
            className={styles.randomInput}
            prefix={<Icon component={RandomSvg} fill="#AAAAAA" />}
            placeholder="请输入邮箱验证码"
            value={random}
            onChange={changeRandom}
            onPressEnter={() => { nextStep(history, param, this.emailInput, this.randomInput); }}
            ref={ (randomInput) => {this.randomInput = randomInput} }
          />
          <SendButton 
            className={styles.sendBtn} 
            defaultText="发送验证码" 
            isSended={isSendedRandom}
            remainingSecond={randomRemainingSecond}
            onClick={() => sendRandom(history, email, this.emailInput, this.randomInput)}
          />
        </div>
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
            onClick={() => { nextStep(history, param, this.emailInput, this.randomInput); }}
          >
            下一步
          </Button>
        </div>
      </div>
    );
  }

}

const mapStateToProps = (state) => ({
  email: state.getIn(['signUp', 'email']),
  random: state.getIn(['signUp', 'random']),
  isSendedRandom: state.getIn(['signUp', 'isSendedRandom']),
  randomRemainingSecond: state.getIn(['signUp', 'randomRemainingSecond']),
});

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
    dispatch(actionCreators.sendRandom(history, email, emailInput,randomInput));
  },
  nextStep(history, param, emailInput, randomInput) {
    dispatch(actionCreators.nextStep1(history, param, emailInput, randomInput));
  },
  backLogin(history, email) {
    dispatch(actionCreators.backLogin(history, email));
  }
});

export default connect(mapStateToProps, mapDispatchToProps)(withRouter(Step1));
