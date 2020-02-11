import ucApi from './ucApi';
import qs from 'qs';

export const sendEmailRandom = (email) => {
  return ucApi.post(
    '/v1/oauth2/random',
    qs.stringify({ email }),
    { headers: { 'Content-Type': 'application/x-www-form-urlencoded' } });
};

export const authorizeByEmailAndPassword = (param) => {
  return ucApi.post('/v1/oauth2/code/email-and-password', param);
};

export const authorizeByEmailAndRandom = (param) => {
  return ucApi.post('/v1/oauth2/code/email-and-random', param);
};
