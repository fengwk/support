import { fromJS } from 'immutable';
import * as constants from './constants';

const defaultState = fromJS({
  way: "1",// 1-密码登录;2-验证码登录;
  email: '',
  password: '',
  random: '',
  isSendedRandom: false,
  randomRemainingSecond: 0
});

export default (state = defaultState, action) => {
  switch(action.type) {

    case constants.SWITCH_LOGIN_WAY:
      return state.set('way', action.way);

    case constants.CHANGE_EMAIL:
      return state.set('email', action.email);

    case constants.CHANGE_PASSWORD:
      return state.set('password', action.password);

    case constants.CHANGE_RANDOM:
      return state.set('random', action.random);

    case constants.CHANGE_RANDOM_BTN_STATE:
      return state.set('isSendedRandom', true).set('randomRemainingSecond', 120);

    case constants.DECREASE_RANDOM_REMAINING_SECOND:
      const remaining = state.get('randomRemainingSecond');
      if (remaining <= 0) {
        action.stopDecrease();
        return state.set('isSendedRandom', false);
      } else {
        return state.set('randomRemainingSecond', remaining - 1);
      }

    default:
      return state;
  }
}
