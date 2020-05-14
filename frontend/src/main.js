import Vue from 'vue'
import App from './App.vue'
import Buefy from 'buefy'
import 'buefy/dist/buefy.css'
import './assets/css/styles.css'
import VueI18n from 'vue-i18n'
import messages from './assets/translations.js';
import * as apiService from './services/apiservice';
import * as helper from './services/helper';
import router from './router'
import vuetify from "@/plugins/vuetify";

Vue.config.devtools = true;
Vue.config.productionTip = false;
Vue.use(Buefy, {
  defaultIconPack: 'mdi'
});
Vue.use(VueI18n);

Vue.prototype.$apiService = apiService;
Vue.prototype.$helper = helper;

const i18n = new VueI18n({
  locale: 'en', // set locale
  messages // set locale messages
});

new Vue({
  router,
  i18n,
  vuetify,
  render: h => h(App),
}).$mount('#app');