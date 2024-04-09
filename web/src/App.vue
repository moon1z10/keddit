<template>
  <div id="app">
    <AppHeader />
    <div class="content">
      <AppSidebar />
      <!-- router-view를 사용해 현재 경로에 맞는 컴포넌트를 동적으로 로드합니다 -->
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import AppHeader from './components/AppHeader.vue';
import AppSidebar from './components/AppSidebar.vue';

export default {
  name: 'App',
  components: {
    AppHeader,
    AppSidebar
  }
}
</script>

<style>
/* Global styles */
html {
  font-size: 14px;
}

.content {
  display: flex;
  justify-content: flex-start;
}
</style>
