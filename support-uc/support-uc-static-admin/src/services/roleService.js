import ucApi from './ucApi';
import qs from 'qs';

export const create = ({ name }) => {
  return ucApi.post(`/v1/roles`, {name});
};

export const remove = ({ roleId }) => {
  return ucApi.delete(`/v1/roles/${roleId}`);
};

export const update = ({ roleId, name }) => {
  return ucApi.patch(`/v1/roles/${roleId}`, { name });
};

export const search = ({ pageNumber, pageSize, name }) => {
  return ucApi.get(`/v1/roles/search?${qs.stringify({ pageNumber, pageSize, name })}`);
};

export const listPermissions = ({ roleId }) => {
  return ucApi.get(`/v1/roles/${roleId}/permissions`);
};

export const grantPermission = ({ roleId, permissionId }) => {
  return ucApi.post(`/v1/roles/${roleId}/permissions/${permissionId}`);
};

export const revokePermission = ({ roleId, permissionId }) => {
  return ucApi.delete(`/v1/roles/${roleId}/permissions/${permissionId}`);
};
