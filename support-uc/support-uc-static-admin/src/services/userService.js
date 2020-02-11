import ucApi from './ucApi';
import qs from 'qs';

export const update = ({ userId, email, nickname }) => {
  return ucApi.put(`/v1/users/${userId}`, { email, nickname });
};

export const search = ({ pageNumber, pageSize, email, nickname }) => {
  return ucApi.get(`/v1/users/search?${qs.stringify({ pageNumber, pageSize, email, nickname })}`);
};