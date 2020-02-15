import axios from 'axios';
import { message } from 'antd';
import { getAccessToken, removeAccessToken } from "./authorize";

export default class AxiosProxy {

  constructor(config) {
    this.axios = axios.create(config);
    this.wrap = this.wrap.bind(this);
    this.handleConfig = this.handleConfig.bind(this);
  }

  wrap(promise) {
    return new Promise((resolve, reject) => {
      promise.then(res => {
        resolve(res.data);
      }).catch((err) => {
        if (err && err.response && err.response.status === 401) {
          message.error('认证失效', 5);
          removeAccessToken();
        } else if (err && err.response && err.response.data && err.response.data.code) {
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
    return this.wrap(this.axios.request(this.handleConfig(config)));
  }

  get(url, config) {
    return this.wrap(this.axios.get(url, this.handleConfig(config)));
  }

  delete(url, config) {
    return this.wrap(this.axios.delete(url, this.handleConfig(config)));
  }

  head(url, config) {
    return this.wrap(this.axios.head(url, this.handleConfig(config)));
  }

  post(url, data, config) {
    return this.wrap(this.axios.post(url, data, this.handleConfig(config)));
  }

  put(url, data, config) {
    return this.wrap(this.axios.put(url, data, this.handleConfig(config)));
  }

  patch(url, data, config) {
    return this.wrap(this.axios.patch(url, data, this.handleConfig(config)));
  }

  handleConfig(config) {
    let accessToken = getAccessToken();
    if (accessToken) {
      config = config || {};
      const headers = config.headers || {};
      config.headers = headers;
      headers['Authorization'] = accessToken;
      return config;
    } else {
      return config;
    }
  }

}
