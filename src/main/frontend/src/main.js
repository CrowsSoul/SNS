import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import './assets/global.css'; // 引入全局样式

import { library } from '@fortawesome/fontawesome-svg-core';
import { faHome, faFileAlt, faCalendarAlt, faTasks } from '@fortawesome/free-solid-svg-icons';
import { faStar as fasStar } from '@fortawesome/free-solid-svg-icons';
import { faStar as farStar } from '@fortawesome/free-regular-svg-icons';

// 添加图标到库
library.add(faHome, faFileAlt, fasStar, farStar, faCalendarAlt, faTasks);

createApp(App)
  .component('font-awesome-icon', FontAwesomeIcon)
  .use(store)
  .use(router)
  .mount('#app');
