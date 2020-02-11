import axios from 'axios';
import { message } from 'antd';

export default class AxiosProxy {

  constructor(config) {
    this.axios = axios.create(config);
  }

  wrap(promise) {
    return new Promise((resolve, reject) => {
      promise
      .then(res => {
        resolve(res.data);
      })
      .catch(err => {
        if (err && err.response && err.response.data && err.response.data.code) {
          let data = err.response.data;
          let codeNum = new Number(data.code.substring(2));
          if (codeNum >= 2000) {
            message.error(data.error, 5);
          } else {
            message.warn(data.error, 5);
          }
        } else {
          message.error('服务器繁忙');
        }
        reject(err);
      })
    });
  }

  request(config) {
    return this.wrap(this.axios.request(config));
  }

  get(url, config) {
    return this.wrap(this.axios.get(url, config));
  }

  delete(url, config) {
    return this.wrap(this.axios.delete(url, config));
  }

  head(url, config) {
    return this.wrap(this.axios.head(url, config));
  }

  post(url, data, config) {
    return this.wrap(this.axios.post(url, data, config));
  }

  put(url, data, config) {
    return this.wrap(this.axios.put(url, data, config));
  }

  patch(url, data, config) {
    return this.wrap(this.axios.patch(url, data, config));
  }

}