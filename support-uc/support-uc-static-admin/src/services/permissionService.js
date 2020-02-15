import ucApi from './ucApi';
import qs from 'qs';

export const create = ({ name, path }) => {
  return ucApi.post(`/v1/permissions`, {name, path});
};

export const remove = ({ permissionId }) => {
  return ucApi.delete(`/v1/permissions/${permissionId}`);
};

export const update = ({ permissionId, name, path }) => {
  return ucApi.patch(`/v1/permissions/${permissionId}`, { name, path });
};

export const search = ({ pageNumber, pageSize, name }) => {
  return ucApi.get(`/v1/permissions/search?${qs.stringify({ pageNumber, pageSize, name })}`);
};
