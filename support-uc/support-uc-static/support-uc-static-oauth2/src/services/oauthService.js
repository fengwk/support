import ucApi from './ucApi';
import qs from 'qs';


export const sendEmailRandom = (email) => {
  return ucApi.post(
    '/oauth2/sendRandom', 
    qs.stringify({ email }),
    { headers: { 'Content-Type': 'application/x-www-form-urlencoded' } });
};

export const grantCodeByEmailAndPassword = (param) => {
  return ucApi.post('/oauth2/authorizeByEmailAndPassword', param);
};

export const grantCodeByEmailAndRandom = (param) => {
  return ucApi.post('/oauth2/authorizeByEmailAndRandom', param);
};