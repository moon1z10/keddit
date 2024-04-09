<template>
  <div class="container">
    <input
        class="subject-input"
        type="text"
        v-model="subject"
        placeholder="제목을 입력해주세요."
    />
    <div class="editor-container">
      <Editor v-model="context" /> <!-- Editor 내용을 v-model을 통해 양방향 바인딩 -->
    </div>
    <button @click="submitPost" class="post-button">Post</button> <!-- Post 버튼 추가 -->
  </div>
</template>

<script>
import { ref } from 'vue';
import Editor from '@/components/Editor.vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

export default {
  components: { Editor },
  setup() {
    const subject = ref('');
    const context = ref('');
    const router = useRouter();

    const submitPost = async () => {
      const baseURL = `${process.env.VUE_APP_API_BASE_URL}:${process.env.VUE_APP_API_USER_COMMUNITY_PORT}`;
      try {
        // console.log(`context: ${context.value}`)
        await axios.post(`${baseURL}/api/community/post`, {
          author: "test_id",
          subject: subject.value,
          context: context.value
        });
        alert("게시글이 성공적으로 등록되었습니다.");
        // 성공 후, subject과 context 초기화 또는 페이지 리디렉션 등의 추가 작업
        subject.value = '';
        context.value = '';
        router.back(); // 이전 페이지로 돌아갑니다. 또는 router.push('/some-path')를 사용하여 특정 경로로 이동할 수도 있습니다.
      } catch (error) {
        console.error("게시글 등록 중 에러 발생: ", error);
      }
    };

    // Composition API를 사용하여 setup 함수에서 반환
    return {
      subject,
      context,
      submitPost
    };
  },
};
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  min-height: 100vh;
  padding-top: 100px; /* 조정하여 원하는 위치로 이동 */
}

.subject {
  font-size: 1.5rem;
  margin-bottom: 20px; /* 제목과 입력 필드 사이의 간격 */
}

.subject-input {
  width: 800px;
  height: 35px;
  margin-bottom: 30px; /* 입력 필드와 에디터 사이의 간격 */
  border-radius: 10px;
  border: 2px solid #2c3e50;
  box-sizing: border-box; /* border 포함한 총 width 유지 */
}

.editor-container {
  width: 800px; /* 에디터 컨테이너의 너비를 입력 필드와 동일하게 설정 */
  height: 400px;
  justify-content: center;
  /* 추가적인 스타일링 필요 시 여기에 작성 */
  margin-bottom: 20px; /* 버튼 포함 공간 확보 */
}

.note-editable {
  height: 400px;
}
.note-editor {
  border-radius: 10px;
}
.editor-page {
  margin-bottom: 120px;
}

.post-button {
  width: 800px;
  height: 35px;
  margin-top: 70px; /* 입력 필드와 에디터 사이의 간격 */
  padding: 10px 20px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
</style>
