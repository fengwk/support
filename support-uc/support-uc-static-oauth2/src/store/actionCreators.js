import * as constants from './constants';

export const setOAuthParam = (oauthParam) => ({
  type: constants.SET_OAUTH_PARAM,
  ...oauthParam
});