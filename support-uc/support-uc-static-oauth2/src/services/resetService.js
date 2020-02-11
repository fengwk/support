import ucApi from './ucApi';
import qs from 'qs';

export const sendEmailRandom = (email) => {
  return ucApi.post(
    '/v1/reset-password/random',
    qs.stringify({ email }),
    { headers: { 'Content-Type': 'application/x-www-form-urlencoded' } });
};

export const verifyEmailRandom = (param) => {
  return ucApi.patch('/v1/reset-password/random/verify', param);
};

export const resetPasswordAndAuthorize = (param) => {
  return ucApi.post('/v1/reset-password', param);
};
