import axios from 'axios';

let _baseURL;
if (process.env.NODE_ENV == 'development') {
  _baseURL = 'http://localhost:8080'
} else {
  _baseURL = 'http://localhost'
}

const ucApi = axios.create({
  baseURL: _baseURL,
  timeout: 5000,
  headers: {
    'System-Name': 'support-uc-static-auth'
  }
});

export default ucApi;