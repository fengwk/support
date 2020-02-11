import * as constants from './constants';
import { actionCreators as signUpActionCreators } from '../../signUp/store';
import { actionCreators as resetActionCreators } from '../../reset/store';
import { message, Modal } from 'antd';
import co from 'co';
import { isEmail } from '../../../utils/validator';
import { createLock } from '../../../utils/lockFactory';
import { handleGrantCodeRedirect } from '../../../utils/commonHandler';
import * as userService from '../../../services/userService';
import * as oauthService from '../../../services/oauthService';

const changeRandomBtnState = (randomRemainingSecond) => ({
  type: constants.CHANGE_RANDOM_BTN_STATE,
  randomRemainingSecond
});

const decreaseRandomRemainingSecond = (stopDecrease) => ({
  type: constants.DECREASE_RANDOM_REMAINING_SECOND,
  stopDecrease
});

function* doSendRandom(dispatch, history, email, emailInput, randomInput) {
  if (!email || email.length == 0) {
    message.warning('请输入您的邮箱', 5);
    emailInput.focus();
    return;
  }
  if (!isEmail(email)) { 
    message.warning('邮箱格式错误', 5);
    emailInput.focus();
    return;
  }

  const isExists = yield userService.exists(email);
  if (isExists) {
    // 邮箱存在,直接发送验证码
    const randomExpiresIn = yield oauthService.sendEmailRandom(email);
    // 验证码计时
    dispatch(changeRandomBtnState(randomExpiresIn));
    const ref = setInterval(() => {
      dispatch(decreaseRandomRemainingSecond(() => clearInterval(ref)));
    }, 1000);
    randomInput.focus();
  } else {
    // 邮箱不存在,询问是否注册
    Modal.confirm({
      title: '该邮箱尚未注册，是否立即注册？',
      onOk() {
        dispatch(signUpActionCreators.changeEmail(email));
        dispatch(signUpActionCreators.setStep('1'));
        history.push('/sign-up' + (location || window.location).search);
      }
    });
  }
}

function* doSignUpOrLoginByEmailAndPassword(dispatch, history, param, emailInput, pwdInput) {
  const { email, password } = param;
  if (!email || email.length == 0) { 
    message.warning('请输入您的邮箱', 5); 
    emailInput.focus();
    return;
  }
  if (!isEmail(email)) { 
    message.warning('邮箱格式错误', 5); 
    emailInput.focus();
    return;
  }

  const isExists = yield userService.exists(email);
  if (isExists) {
    // 邮箱存在,走登录流程
    if (!password || password.length == 0) { 
      message.warning('请输入您的密码', 5); 
      pwdInput.focus();
      return;
    }
    const code = yield oauthService.authorizeByEmailAndPassword(param);
    handleGrantCodeRedirect(history, param.redirectUri, code, param.state);
  } else {
    // 邮箱不存在,走注册流程
    Modal.confirm({
      title: '该邮箱尚未注册，是否立即注册？',
      onOk() {
        dispatch(signUpActionCreators.changeEmail(email));
        dispatch(signUpActionCreators.setStep('1'));
        if (password && password.length > 0) {
          dispatch(signUpActionCreators.changePassword(password));
        }
        history.push('/sign-up' + (location || window.location).search);
      }
    });
  }
}

function* doSignUpOrLoginByEmailAndRandom(dispatch, history, param, emailInput, randomInput) {
  const { email, random } = param;
  if (!email || email.length == 0) { 
    message.warning('请输入您的邮箱', 5);
    emailInput.focus();
    return;
  }
  if (!isEmail(email)) { 
    message.warning('邮箱格式错误', 5);
    emailInput.focus();
    return;
  }

  const isExists = yield userService.exists(email);
  if (isExists) {
    // 邮箱存在,走登录流程
    if (!random || random.length == 0) {
      message.warning('请输入您的收到的邮箱验证码', 5);
      randomInput.focus();
      return;
    }
    const code = yield oauthService.authorizeByEmailAndRandom(param);
    handleGrantCodeRedirect(history, param.redirectUri, code, param.state);
  } else {
    // 邮箱不存在,走注册流程
    Modal.confirm({
      title: '该邮箱尚未注册，是否立即注册？',
      onOk() {
        dispatch(signUpActionCreators.changeEmail(email));
        dispatch(signUpActionCreators.setStep('1'));
        history.push('/sign-up' + (location || window.location).search);
      }
    });
  }
}

export const switchLoginWay = (way) => ({
  type: constants.SWITCH_LOGIN_WAY,
  way
});

export const changeEmail = (email) => ({
  type: constants.CHANGE_EMAIL,
  email
});

export const changePassword = (password) => ({
  type: constants.CHANGE_PASSWORD,
  password
});

export const changeRandom = (random) => ({
  type: constants.CHANGE_RANDOM,
  random
});

export const sendRandom = (history, email, emailInput, randomInput) => {
  return (dispatch) => {
    const lock = createLock('login/sendRandom');
    if (!lock.tryLock()) { return; }
    co.wrap(doSendRandom)(dispatch, history, email, emailInput, randomInput)
      .finally(() => { lock.unlock(); });
  };
};

export const signUpOrLoginByEmailAndPassword = (history, param, emailInput, pwdInput) => {
  return (dispatch) => {
    const lock = createLock('login/signUpOrLoginByEmailAndPassword');
    if (!lock.tryLock()) { return; }
    co.wrap(doSignUpOrLoginByEmailAndPassword)(dispatch, history, param, emailInput, pwdInput)
      .finally(() => { lock.unlock(); });
  };
};

export const signUpOrLoginByEmailAndRandom = (history, param, emailInput, randomInput) => {
  return (dispatch) => {
    const lock = createLock('login/signUpOrLoginByEmailAndRandom');
    if (!lock.tryLock()) { return; }
    co.wrap(doSignUpOrLoginByEmailAndRandom)(dispatch, history, param, emailInput, randomInput)
      .finally(() => { lock.unlock(); });
  };
};

export const forgetPwd = (history, email) => {
  return (dispatch) => {
    dispatch(resetActionCreators.changeEmail(email));
    dispatch(resetActionCreators.setStep('1'));
    history.push('/reset' + (location || window.location).search);
  }
}
