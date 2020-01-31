import ucApi from './ucApi';
import qs from 'qs';

export const existsByEmail = (email) => {
  return ucApi.post(
    '/user/existsEmail', 
    qs.stringify({ email }),
    { headers: { 'Content-Type': 'application/x-www-form-urlencoded' } });
};