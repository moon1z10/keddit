// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router'
import PostList from '@/components/PostList.vue'
import PostCreation from '@/components/PostCreation.vue'
import LoginForm from '@/components/LoginForm.vue'

const routes = [
    {
        path: '/',
        name: 'Home',
        redirect: { name: 'PostList', params: { communityId: '0' }, replace: true }
    },
    {
        path: '/login',
        name: 'Login',
        component: LoginForm
    },
    {
        path: '/community/:communityId',
        name: 'PostList',
        component: PostList,
        props: true
    },
    {
        path: '/community/create',
        name: 'PostCreation',
        component: PostCreation
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router
