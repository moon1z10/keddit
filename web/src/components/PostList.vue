<template>
  <div class="post-list">
    <button @click="goToPostCreation" style="margin-bottom: 20px;">+ 글 작성</button>
    <h2>게시글 목록</h2>
    <div class="card" v-for="post in posts" :key="post.id" @click="toggleFullBody(post)">
      <div class="card-body">
        <h3>{{ post.subject }}</h3>
        <!-- 디코딩된 HTML 콘텐츠를 렌더링 -->
        <div v-html="post.isFullBodyVisible ? decodeHtml(post.context) : truncatedBody(post.context)"></div>
        <div class="meta-info">
          <span>👍 {{ post.upVotes }} {{ post.vote }} 👎 {{ post.downVotes }}</span>
          <span @click="toggleComments(post.id)" style="cursor:pointer;">
            💬 {{ post.comments ? post.comments.length : 'No comments' }}
          </span>
          <ul v-if="post.showComments">
            <li v-for="comment in post.comments" :key="comment.id">{{ comment.text }}</li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {onMounted, ref, watch} from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
import { getCurrentUser } from 'aws-amplify/auth';

export default {
  props: {
    communityId: {
      type: String,
      required: false
    },
  },
  setup(props) {
    const posts = ref([]);
    const router = useRouter();


    const currentAuthenticatedUser = async () => {
      try {
        await getCurrentUser();
        return true;
        // const { username, userId, signInDetails } = await getCurrentUser();
        // console.log(`The username: ${username}`);
        // console.log(`The userId: ${userId}`);
        // console.log(`The signInDetails: ${signInDetails}`);
      } catch (err) {
        return false;
        // console.log(err);
      }
    }

    const getPosts = async () => {
      const baseURL = `${process.env.VUE_APP_API_BASE_URL}:${process.env.VUE_APP_API_USER_COMMUNITY_PORT}`;
      try {
        const communityId = (props.communityId && props.communityId > 0) ? props.communityId : "0";
        // console.log(`getPosts(${communityId})`)
        const response = await axios.get(`${baseURL}/api/community/recentPosts/${communityId}`);
        // console.log(`getPosts(${communityId}) Response : ${response.data}`)
        response.data.forEach(post => {
          post.showComments = false;
        });
        posts.value = response.data;
      } catch (error) {
        console.error("API 호출 중 에러 발생: ", error);
      }
    };

    const decodeHtml = (html) => {
      const txt = document.createElement("textarea");
      txt.innerHTML = html;
      return txt.value;
    };

    const toggleFullBody = (post) => {
      post.isFullBodyVisible = !post.isFullBodyVisible;
    };

    const toggleComments = (postId) => {
      const post = posts.value.find(p => p.id === postId);
      if (post) {
        post.showComments = !post.showComments;
      }
    };

    const truncatedBody = (body) => {
      if (!body) return ''; // body가 undefined 또는 null인 경우 빈 문자열 반환
      return body.length > 100 ? body.substring(0, 100) + '...' : body;
    };

    const goToPostCreation = () => {
      currentAuthenticatedUser()
          .then((result) => {
            if (result) {
              router.push({ name: 'PostCreation' });
            } else {
              alert("글 작성은 로그인이 필요합니다.");
            }
          });
    };

    onMounted(() => {
      getPosts();
    });

    watch(() => props.communityId, (newCommunityId, oldCommunityId) => {
      if (newCommunityId !== oldCommunityId){
        getPosts();
      }
    })

    return {
      posts,
      toggleFullBody,
      toggleComments,
      truncatedBody,
      decodeHtml,
      goToPostCreation,
    };
  },
};
</script>

<style scoped>
.card {
  border: 1px solid #eee;
  border-radius: 10px;
  margin: 10px 0;
  display: flex;
  flex-direction: column; /* 내용에 맞게 높이를 조정하기 위해 column으로 변경 */
  max-width: 100%;
  overflow: hidden; /* 내부 오버플로우 처리 */
}
.card-body {
  padding: 20px;
  max-height: 100%; /* 최대 높이를 제한하고 싶은 경우, 선택적으로 사용 */
  overflow-y: auto; /* 내용이 최대 높이를 초과하는 경우 스크롤 표시 */
}
.thumbnail {
  width: 100px;
  height: auto;
  border-radius: 10px 0 0 10px;
}
.meta-info span {
  margin-right: 10px;
}
</style>
