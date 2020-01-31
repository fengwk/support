import ucApi from './ucApi';
import qs from 'qs';

export const sendEmailRandom = (email) => {
  return ucApi.post(
    '/register/sendRandom', 
    qs.stringify({ email }),
    { headers: { 'Content-Type': 'application/x-www-form-urlencoded' } });
};

export const verifyEmailRandom = (param) => {
  return ucApi.post('/register/verifyRandom', param);
};

export const registerAndGrant = (param) => {
  return ucApi.post('/register', param);
};