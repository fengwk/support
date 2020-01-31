import ucApi from './ucApi';
import qs from 'qs';

export const sendEmailRandom = (email) => {
  return ucApi.post(
    '/reset/sendRandom', 
    qs.stringify({ email }),
    { headers: { 'Content-Type': 'application/x-www-form-urlencoded' } });
};

export const verifyEmailRandom = (param) => {
  return ucApi.post('/reset/verifyRandom', param);
};

export const resetPasswordAndGrant = (param) => {
  return ucApi.post('/reset', param);
};