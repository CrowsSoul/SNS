import axios from 'axios';

const instance = axios.create({
  baseURL: 'http://localhost:3000', // 指向你的本地服务器地址
  timeout: 10000,
  headers: { 'Content-Type': 'application/json' }
});

export default instance;
