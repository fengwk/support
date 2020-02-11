import { fromJS } from 'immutable';
import * as constants from './constants';

const defaultState = fromJS({
  step: '1',// 1-设置邮箱;2-设置密码;3-完成;
  email: '',
  random: '',
  isSendedRandom: false,
  randomRemainingSecond: 0,
  nickname: '',
  password: '',
  confirmPassword: ''
  
});

export default (state = defaultState, action) => {
  switch(action.type) {

    case constants.SET_STEP:
      return state.set('step', action.step);
      
    case constants.CHANGE_EMAIL:
      return state.set('email', action.email);

    case constants.CHANGE_RANDOM:
      return state.set('random', action.random);

    case constants.CHANGE_RANDOM_BTN_STATE:
      return state.set('isSendedRandom', true).set('randomRemainingSecond', action.randomRemainingSecond);

    case constants.DECREASE_RANDOM_REMAINING_SECOND:
      const remaining = state.get('randomRemainingSecond');
      if (remaining <= 0) {
        action.stopDecrease();
        return state.set('isSendedRandom', false);
      } else {
        return state.set('randomRemainingSecond', remaining - 1);
      }

    case constants.CHANGE_NICKNAME:
      return state.set('nickname', action.nickname);

    case constants.CHANGE_PASSWORD:
      return state.set('password', action.password);

    case constants.CHANGE_CONFIRM_PASSWORD:
      return state.set('confirmPassword', action.confirmPassword);

    default:
      return state;
  }
}
