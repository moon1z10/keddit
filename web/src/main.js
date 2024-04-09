import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap-icons/font/bootstrap-icons.css';
import axios from 'axios';
import { quillEditor } from 'vue3-quill';
import { Amplify } from 'aws-amplify';
import config from './amplifyconfiguration.json';
Amplify.configure(config);

const app = createApp(App)
    .use(router)
    .use(quillEditor);

// axios를 전역 속성으로 추가합니다. 이제 모든 컴포넌트 내부에서 this.$axios로 axios에 접근할 수 있습니다.
app.config.globalProperties.$axios = axios;

app.mount('#app');
