import ucApi from './ucApi';
import qs from 'qs';

export const create = ({ name, redirectRules, accessExpiresIn, refreshExpiresIn, isExclusive, tokenCountLimit, isDisabled }) => {
  return ucApi.post(`/v1/clients`, { name, redirectRules, accessExpiresIn, refreshExpiresIn, isExclusive, tokenCountLimit, isDisabled });
};

export const remove = ({ clientId }) => {
  return ucApi.delete(`/v1/clients/${clientId}`);
};

export const update = ({ clientId, name, redirectRules, accessExpiresIn, refreshExpiresIn, isExclusive, tokenCountLimit, isDisabled }) => {
  return ucApi.patch(`/v1/clients/${clientId}`, { name, redirectRules, accessExpiresIn, refreshExpiresIn, isExclusive, tokenCountLimit, isDisabled });
};

export const refreshSecret = ({ clientId }) => {
  return ucApi.patch(`/v1/clients/${clientId}/secret`);
};

export const search = ({ pageNumber, pageSize, name }) => {
  return ucApi.get(`/v1/clients/search?${qs.stringify({ pageNumber, pageSize, name })}`);
};
