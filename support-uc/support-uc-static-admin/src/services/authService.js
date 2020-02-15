import ucApi from './ucApi';
import qs from 'qs';

export const getRedirectUri = ({ redirectUri }) => {
  return ucApi.get(`/v1/sign-in?${qs.stringify({ redirectUri })}`);
};

export const getToken = ({ code }) => {
  return ucApi.post(`/v1/sign-in/token`,
    qs.stringify({ code }),
    { headers: { 'Content-Type': 'application/x-www-form-urlencoded' } });
};

export const revokeToken = () => {
  return ucApi.delete(`/v1/oauth2/token`);
};

export function jumpOAuth2() {
  getRedirectUri({ redirectUri: location.href }).then((uri) => { location.href = uri; });
}
