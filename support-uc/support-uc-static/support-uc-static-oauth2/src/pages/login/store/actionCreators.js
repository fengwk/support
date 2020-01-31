import * as constants from './constants';
import { actionCreators as registerActionCreators } from '../../register/store';
import { actionCreators as resetActionCreators } from '../../reset/store';
import { message, Modal } from 'antd';
import co from 'co';
import { isEmail } from '../../../utils/validator';
import { createLock } from '../../../utils/lockFactory';
import { handleGrantCodeRedirect } from '../../../utils/commonHandler';
import * as userService from '../../../services/userService';
import * as oauthService from '../../../services/oauthService';
import * as registerService from '../../../services/registerService';

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

  const existsEmailRes = yield userService.existsByEmail(email);
  if (existsEmailRes.data.code !== '0') {
    message.error(existsEmailRes.data.message, 5);
    return;
  }

  if (existsEmailRes.data.data) {
    // 邮箱存在,直接发送验证码
    const sendRandomRes = yield oauthService.sendEmailRandom(email);
    if (sendRandomRes.data.code !== '0') {
      message.error(sendRandomRes.data.message, 5);
      return;
    }
    // 验证码计时
    dispatch(changeRandomBtnState(sendRandomRes.data.data));
    const ref = setInterval(() => {
      dispatch(decreaseRandomRemainingSecond(() => clearInterval(ref)));
    }, 1000);
    randomInput.focus();
  } else {
    // 邮箱不存在,询问是否注册
    Modal.confirm({
      title: '该邮箱尚未注册，是否立即注册？',
      onOk() {
        dispatch(registerActionCreators.changeEmail(email));
        dispatch(registerActionCreators.setStep('1'));
        history.push('/register' + (location || window.location).search);
      }
    });
  }
}

function* doRegisterOrLoginByEmailAndPassword(dispatch, history, param, emailInput, pwdInput) {
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

  const existsEmailRes = yield userService.existsByEmail(email);
  if (existsEmailRes.data.code !== '0') {
    message.error(existsEmailRes.data.message, 5);
    return;
  }
  if (existsEmailRes.data.data) {
    // 邮箱存在,走登录流程
    if (!password || password.length == 0) { 
      message.warning('请输入您的密码', 5); 
      pwdInput.focus();
      return;
    }
    const grantCodeRes = yield oauthService.grantCodeByEmailAndPassword(param);
    if (grantCodeRes.data.code !== '0') {
      message.error(grantCodeRes.data.message, 5);
      return;
    }
    handleGrantCodeRedirect(history, param.redirectUri, grantCodeRes.data.data, param.state);
  } else {
    // 邮箱不存在,走注册流程
    Modal.confirm({
      title: '该邮箱尚未注册，是否立即注册？',
      onOk() {
        dispatch(registerActionCreators.changeEmail(email));
        dispatch(registerActionCreators.setStep('1'));
        if (password && password.length > 0) {
          dispatch(registerActionCreators.changePassword(password));
        }
        history.push('/register' + (location || window.location).search);
      }
    });
  }
}

function* doRegisterOrLoginByEmailAndRandom(dispatch, history, param, emailInput, randomInput) {
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

  const existsEmailRes = yield userService.existsByEmail(email);
  if (existsEmailRes.data.code !== '0') {
    message.error(existsEmailRes.data.message, 5);
    return;
  }
  if (existsEmailRes.data.data) {
    // 邮箱存在,走登录流程
    if (!random || random.length == 0) {
      message.warning('请输入您的收到的邮箱验证码', 5);
      randomInput.focus();
      return;
    }
    const grantCodeRes = yield oauthService.grantCodeByEmailAndRandom(param);
    if (grantCodeRes.data.code !== '0') {
      message.error(grantCodeRes.data.message, 5);
      return;
    }
    handleGrantCodeRedirect(history, param.redirectUri, grantCodeRes.data.data, param.state);
  } else {
    // 邮箱不存在,走注册流程
    Modal.confirm({
      title: '该邮箱尚未注册，是否立即注册？',
      onOk() {
        dispatch(registerActionCreators.changeEmail(email));
        dispatch(registerActionCreators.setStep('1'));
        history.push('/register' + (location || window.location).search);
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
      .catch((err) => {
        console.error(err);
        message.error('服务器繁忙', 5);
      })
      .finally(() => {
        lock.unlock();
      });
  };
};

export const registerOrLoginByEmailAndPassword = (history, param, emailInput, pwdInput) => {
  return (dispatch) => {
    const lock = createLock('login/registerOrLoginByEmailAndPassword');
    if (!lock.tryLock()) { return; }
    co.wrap(doRegisterOrLoginByEmailAndPassword)(dispatch, history, param, emailInput, pwdInput)
      .catch((err) => {
        console.error(err);
        message.error('服务器繁忙', 5);
      })
      .finally(() => {
        lock.unlock();
      });
  };
};

export const registerOrLoginByEmailAndRandom = (history, param, emailInput, randomInput) => {
  return (dispatch) => {
    const lock = createLock('login/registerOrLoginByEmailAndRandom');
    if (!lock.tryLock()) { return; }
    co.wrap(doRegisterOrLoginByEmailAndRandom)(dispatch, history, param, emailInput, randomInput)
      .catch((err) => {
        console.error(err);
        message.error('服务器繁忙', 5);
      })
      .finally(() => {
        lock.unlock();
      });
  };
};

export const forgetPwd = (history, email) => {
  return (dispatch) => {
    dispatch(resetActionCreators.changeEmail(email));
    dispatch(resetActionCreators.setStep('1'));
    console.log(history)
    history.push('/reset' + (location || window.location).search);
  }
}