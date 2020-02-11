import * as constants from './constants';
import { actionCreators as loginActionCreators } from '../../login/store';
import { message } from 'antd';
import co from 'co';
import { isEmail } from '../../../utils/validator';
import { createLock } from '../../../utils/lockFactory';
import { handleGrantCodeRedirect } from '../../../utils/commonHandler';
import * as userService from '../../../services/userService';
import * as resetService from '../../../services/resetService';

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
    // 邮箱存在,发送验证码
  const randomExpiresIn = yield resetService.sendEmailRandom(email);
    dispatch(changeRandomBtnState(randomExpiresIn));
    const ref = setInterval(() => {
      dispatch(decreaseRandomRemainingSecond(() => clearInterval(ref)));
    }, 1000);
    randomInput.focus();
  } else {
    // 邮箱不存在
    message.error('该邮箱尚未注册', 5);
  }
}

function* doNextStep1(dispatch, history, param, emailInput, randomInput) {
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
    // 邮箱存在
    if (!random || random.length == 0) {
      message.warning('请输入您收到的邮箱验证码', 5);
      randomInput.focus();
      return;
    }

    // 邮箱不存在,验证邮箱验证码
    const isCorrect = yield resetService.verifyEmailRandom(param);
    if (!isCorrect) {
      message.error('邮箱验证码错误', 5);
      return;
    }
    dispatch(setStep('2'));
  } else {
    message.error('该邮箱尚未注册', 5);
  }
}

function* doNextStep2(dispatch, history, param, passwordInput, confirmPasswordInput) {
  const { password, confirmPassword } = param;
  if (!password || password.length == 0) {
    message.warning('请输入您的密码', 5);
    passwordInput.focus();
    return;
  }
  if (!confirmPassword || confirmPassword.length == 0) {
    message.warning('请再次输入您的密码', 5);
    confirmPasswordInput.focus();
    return;
  }
  if (password !== confirmPassword) {
    message.warning('两次输入的密码不一致', 5);
    confirmPasswordInput.focus();
    return;
  }

  param.newPassword = param.password;
  const code = yield resetService.resetPasswordAndAuthorize(param);
  handleGrantCodeRedirect(history, param.redirectUri, code, param.state);
}

export const setStep = (step) => ({
  type: constants.SET_STEP,
  step
});

export const changeEmail = (email) => ({
  type: constants.CHANGE_EMAIL,
  email
});

export const changeRandom = (random) => ({
  type: constants.CHANGE_RANDOM,
  random
});

export const sendRandom = (history, email, emailInput, randomInput) => {
  return (dispatch) => {
    const lock = createLock('reset/sendRandom');
    if (!lock.tryLock()) { return; }
    co.wrap(doSendRandom)(dispatch, history, email, emailInput, randomInput)
      .finally(() => { lock.unlock(); });
  };
};

export const nextStep1 = (history, param, emailInput, randomInput) => {
  return (dispatch) => {
    const lock = createLock('reset/nextStep1');
    if (!lock.tryLock()) { return; }
    co.wrap(doNextStep1)(dispatch, history, param, emailInput, randomInput)
      .finally(() => { lock.unlock(); });
  };
};

export const changeNickname = (nickname) => ({
  type: constants.CHANGE_NICKNAME,
  nickname
});

export const changePassword = (password) => ({
  type: constants.CHANGE_PASSWORD,
  password
});

export const changeConfirmPassword = (confirmPassword) => ({
  type: constants.CHANGE_CONFIRM_PASSWORD,
  confirmPassword
});

export const nextStep2 = (history, param, passwordInput, confirmPasswordInput) => {
  return (dispatch) => {
    const lock = createLock('reset/nextStep2');
    if (!lock.tryLock()) { return; }
    co.wrap(doNextStep2)(dispatch, history, param, passwordInput, confirmPasswordInput)
      .finally(() => { lock.unlock(); });
  };
};

export const backLogin = (history, email) => {
  return (dispatch) => {
    dispatch(loginActionCreators.changeEmail(email));
    history.push('/' + (location || window.location).search);
  };
};
