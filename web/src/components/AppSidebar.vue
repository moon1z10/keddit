<template>
  <aside class="sidebar">
    <ul class="list-unstyled">
      <li @click="goToCommunityPosts(0)"><i class="bi bi-house-door-fill"></i> Home</li>
<!--      <li><i class="bi bi-stars"></i> Popular</li>-->
      <hr>
      <li class="menu-title" @click="toggleTopicsVisibility">
        <i class="bi" :class="showTopics ? 'bi-chevron-up' : 'bi-chevron-down'"></i> TOPICS
      </li>
      <transition-group name="fade" tag="div">
        <div v-if="showTopics" class="topics-container" key="topics-container">
          <li v-for="topic in displayedTopics" :key="topic.id" @click="goToCommunityPosts(topic.id)">
            <i :class="'bi ' + topic.icon"></i> {{ topic.title }}
          </li>
          <button @click="toggleShowAll" class="see-more-btn">
            {{ showAllTopics ? 'See Less' : 'See More' }}
          </button>
        </div>
      </transition-group>
      <hr>
      <li class="menu-title">RESOURCES</li>
      <li><i class="bi bi-info-square-fill"></i> About Keddit</li>
      <li><i class="bi bi-megaphone-fill"></i> Advertise</li>
      <li><i class="bi bi-file-earmark-text-fill"></i> Content Policy</li>
      <li><i class="bi bi-shield-lock-fill"></i> Privacy Policy</li>
      <li><i class="bi bi-file-text-fill"></i> User Agreement</li>
    </ul>
  </aside>
</template>

<script>
import { ref, onMounted, getCurrentInstance } from 'vue';
import { useRouter } from 'vue-router';

export default {
  setup() {
    const instance = getCurrentInstance();
    const axios = instance.appContext.config.globalProperties.$axios;
    const baseURL = `${process.env.VUE_APP_API_BASE_URL}:${process.env.VUE_APP_API_USER_COMMUNITY_PORT}`;

    const topics = ref([]); // 초기 상태는 빈 배열
    const showAllTopics = ref(false);
    const showTopics = ref(true);
    const displayedTopics = ref([]);
    const router = useRouter();

    // 데이터를 불러오는 함수
    const getCommunityList = async () => {
      try {
        const response = await axios.get(`${baseURL}/api/community/list`);
        // 받아온 데이터로 topics 업데이트
        topics.value = response.data;
        // 초기에 표시될 항목들 설정
        displayedTopics.value = topics.value.slice(0, 6);
      } catch (error) {
        console.error("API 호출 중 에러 발생: ", error);
      }
    }

    function toggleShowAll() {
      showAllTopics.value = !showAllTopics.value;
      displayedTopics.value = showAllTopics.value ? topics.value : topics.value.slice(0, 6);
    }

    function toggleTopicsVisibility() {
      showTopics.value = !showTopics.value;
    }

    const goToCommunityPosts = (communityId) => {
      router.push({ name: 'PostList', params: { communityId: communityId }, replace: true });
    }

    // 컴포넌트가 마운트되었을 때 getCommunityList 함수 호출
    onMounted(() => {
      getCommunityList();
    })

    return {
      topics,
      showAllTopics,
      showTopics,
      displayedTopics,
      toggleShowAll,
      toggleTopicsVisibility,
      goToCommunityPosts,
    }
  },
};
</script>

<style scoped>
/* Topics 목록의 표시 및 숨김에 부드러운 전환 효과를 적용 */
@keyframes fade-in {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

@keyframes fade-out {
  from {
    opacity: 1;
  }
  to {
    opacity: 0;
  }
}

.fade-enter-active, .fade-leave-active {
  transition: opacity 0.5s;
}

.fade-enter-from, .fade-leave-to {
  opacity: 0;
}

.fade-enter-to, .fade-leave-from {
  opacity: 1;
}

.topics-container {
  animation: fade-in 0.5s;
}

.sidebar {
  width: 15%; /* 사이드바의 너비를 전체 화면의 15%로 설정 */
  max-width: 15%; /* 최대 너비도 15%로 설정하여 확장되지 않도록 함 */
  padding: 20px;
  overflow: hidden; /* 넘치는 컨텐츠를 숨김 */
}

.menu-title {
  margin-top: 20px;
  font-weight: bold;
  cursor: pointer;
}

li {
  margin-bottom: 10px;
  list-style-type: none;
}

.see-more-btn {
  background: none;
  border: none;
  color: blue;
  cursor: pointer;
  padding: 10px;
  margin-left: -10px;
}

i {
  margin-right: 10px;
}

li {
  margin-bottom: 10px;
  list-style-type: none;
  cursor: pointer; /* 마우스 오버 시 커서를 포인터로 변경 */
  transition: background-color 0.2s, color 0.2s; /* 부드러운 배경 및 텍스트 색상 변경 효과 */
}

li:hover {
  background-color: #f0f0f0; /* 마우스 오버 시 배경 색상 변경 */
  color: #333; /* 마우스 오버 시 텍스트 색상 변경 */
}

/* 선택된 항목을 나타내기 위한 클래스 추가 */
li.active {
  background-color: #007bff; /* 선택된 항목의 배경 색상 */
  color: white; /* 선택된 항목의 텍스트 색상 */
}

/* 마우스 오버 및 클릭 효과를 위한 추가 스타일 */
.see-more-btn:hover, .menu-title:hover {
  text-decoration: underline; /* 마우스 오버 시 밑줄 */
}
</style>
