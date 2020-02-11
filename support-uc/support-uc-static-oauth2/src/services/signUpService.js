import ucApi from './ucApi';
import qs from 'qs';

export const sendEmailRandom = (email) => {
  return ucApi.post(
    '/v1/sign-up/random', 
    qs.stringify({ email }),
    { headers: { 'Content-Type': 'application/x-www-form-urlencoded' } });
};

export const verifyEmailRandom = (param) => {
  return ucApi.patch('/v1/sign-up/random/verify', param);
};

export const signUpAndAuthorize = (param) => {
  return ucApi.post('/v1/sign-up', param);
};
