import ucApi from './ucApi';

export const exists = (email) => {
  return ucApi.get(`/v1/users/exists?email=${email}`);
};
