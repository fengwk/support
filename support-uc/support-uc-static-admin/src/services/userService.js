import ucApi from './ucApi';
import qs from 'qs';

export const update = ({ userId, email, nickname }) => {
  return ucApi.patch(`/v1/users/${userId}`, { email, nickname });
};

export const search = ({ pageNumber, pageSize, email, nickname }) => {
  return ucApi.get(`/v1/users/search?${qs.stringify({ pageNumber, pageSize, email, nickname })}`);
};

export const listRoles = ({ userId }) => {
  return ucApi.get(`/v1/users/${userId}/roles`);
};

export const grantRole = ({ userId, roleId }) => {
  return ucApi.post(`/v1/users/${userId}/roles/${roleId}`);
};

export const revokeRole = ({ userId, roleId }) => {
  return ucApi.delete(`/v1/users/${userId}/roles/${roleId}`);
};
