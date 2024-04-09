<template>
  <header class="app-header">
    <div class="header-section left">
      <img src="@/assets/logo.png" alt="Keddit Logo" class="header-logo">
      <h1 class="header-title">keddit</h1>
    </div>
    <div class="header-section center">
      <!-- 중앙 컨텐츠(필요하다면) -->
    </div>
    <div class="header-section right">
      <button class="login-button" @click="goToLogin()">Login</button>
    </div>
  </header>
</template>

<script>
import { useRouter } from 'vue-router';

export default {
  setup() {
    const router = useRouter();

    const goToLogin = () => {
      router.push({ name: 'Login' });
    };

    return {
      name: 'AppHeader',
      goToLogin,
    }
  }
}
</script>

<style scoped>
.app-header {
  display: flex;
  align-items: center;
  height: 60px;
  background-color: #FFF;
  padding: 0 20px;
}

.header-logo {
  width: 40px; /* 로고의 크기를 조절합니다 */
  height: auto;
  margin-right: 3px; /* h1과의 간격 */
}

.header-section {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-grow: 1;
}

.left {
  justify-content: flex-start;
}

.right {
  justify-content: flex-end;
}

.header-title {
  color: #FFA500;
  -webkit-text-stroke: 1px black;
  font-size: calc(10px + 2vmin);
}

.login-button {
  background-color: #4285F4;
  color: white;
  border: none;
  padding: 10px 15px;
  cursor: pointer;
  border-radius: 4px;
}
</style>
