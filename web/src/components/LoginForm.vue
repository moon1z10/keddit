<template>
  <div class="auth-container">
    <authenticator>
      <template v-slot="{ user, signOut }">
        <div class="user-greeting">
          <h1>Hello, {{ user.username }}!</h1>
          <button @click="signOut" class="sign-out-btn">Sign Out</button>
        </div>
      </template>
    </authenticator>
  </div>
</template>

<script setup>
import { Authenticator } from "@aws-amplify/ui-vue";
import "@aws-amplify/ui-vue/styles.css";
</script>

<style scoped>
.auth-container {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  text-align: center;
  padding-top: 5%;
  padding-right: 15%;
  padding-bottom: 30%;
}

.user-greeting {
  display: flex;
  align-items: center;
  justify-content: center; /* 가로축에서 중앙 정렬 */
  flex-direction: column; /* 요소들을 세로로 쌓기 위해 변경 */
  background-color: #f0f0f0;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  width: 90%; /* 컨테이너 너비를 조정 */
  max-width: 600px; /* 최대 너비 설정 */
}

h1 {
  margin: 0 0 20px 0; /* 하단 여백 추가 */
  font-size: 1.5rem;
  color: #333;
}

.sign-out-btn {
  padding: 10px 20px;
  font-size: 1rem;
  color: white;
  background-color: #ff4757;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.sign-out-btn:hover {
  background-color: #ff6b81;
}
</style>

