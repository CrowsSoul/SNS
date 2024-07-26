import { createRouter, createWebHistory } from 'vue-router';

import LoginView from '../views/main/LoginView.vue';
import RegisterView from '../views/main/RegisterView.vue';
import HomeView from '../views/main/HomeView.vue';

import BlogListView from '../views/blog/user/BlogListView.vue';
import BlogDetailView from '../views/blog/user/BlogDetailView.vue';
import MyBlogsView from '../views/blog/user/MyBlogsView.vue';
import BlogEditView from '../views/blog/user/BlogEditView.vue';
import AdminBlogReview from '../views/blog/admin/AdminBlogReview.vue';
import AdminBlogDetail from '../views/blog/admin/AdminBlogDetail.vue';
import AdminCommentReview from '../views/blog/admin/AdminCommentReview.vue';
import AdminCommentDetail from '../views/blog/admin/AdminCommentDetail.vue';
import AdminFeaturedBlogs from '../views/blog/admin/AdminFeaturedBlogs.vue';
import AddFeaturedBlog from '../views/blog/admin/AddFeaturedBlog.vue';
import AdminFeaturedBlogDetail from '../views/blog/admin/AdminFeaturedBlogDetail.vue';

import RecommendListView from '../views/recommend/RecommendListView.vue';
import RecommendDetailView from '../views/recommend/RecommendDetailView.vue';
import PublishRecommendView from '../views/recommend/PublishRecommendView.vue';
import MyRecommendView from '../views/recommend/MyRecommendView.vue';
import MyFavoritesView from '../views/recommend/MyFavoritesView.vue';
import AdminRecommendReview from '../views/recommend/admin/AdminRecommendReview.vue';
import AdminRecommendDetail from '../views/recommend/admin/AdminRecommendDetail.vue';
import AdminRecommendDelete from '../views/recommend/admin/AdminRecommendDelete.vue';

import ActivityHall from '../views/activity/user/ActivityHall.vue';
import ActivityDetail from '../views/activity/user/ActivityDetail.vue';
import PublishActivity from '../views/activity/user/PublishActivity.vue';
import ActivityPreparation from '../views/activity/user/ActivityPreparation.vue';
import MyParticipation from '../views/activity/user/MyParticipation.vue';
import ActivityRecord from '../views/activity/user/ActivityRecord.vue';
import AdminActivityReview from '../views/activity/admin/AdminActivityReview.vue';
import AdminActivityDelete from '../views/activity/admin/AdminActivityDelete.vue';

const routes = [
  {
    path: '/login',
    name: '登录',
    component: LoginView
  },
  {
    path: '/register',
    name: '注册',
    component: RegisterView
  },
  {
    path: '/',
    name: '主页',
    component: HomeView
  },
  {
    path: '/blogs',
    name: 'BlogList',
    component: BlogListView
  },
  {
    path: '/blogs/:id',
    name: 'BlogDetail',
    component: BlogDetailView
  },
  {
    path: '/my-blogs',
    name: 'MyBlogs',
    component: MyBlogsView
  },
  {
    path: '/publish-blog',
    name: 'PublishBlog',
    component: BlogEditView
  },
  {
    path: '/edit-blog/:id?',
    name: 'EditBlog',
    component: BlogEditView
  },
  {
    path: '/admin/blog-review',
    name: 'AdminBlogReview',
    component: AdminBlogReview
  },
  {
    path: '/admin/blog-review/:id',
    name: 'AdminBlogDetail',
    component: AdminBlogDetail
  },
  {
    path: '/admin/comment-review',
    name: 'AdminCommentReview',
    component: AdminCommentReview
  },
  {
    path: '/admin/comment-review/:id',
    name: 'AdminCommentDetail',
    component: AdminCommentDetail
  },
  {
    path: '/admin/featured-blogs',
    name: 'AdminFeaturedBlogs',
    component: AdminFeaturedBlogs
  },
  {
    path: '/admin/featured-blogs/add',
    name: 'AddFeaturedBlog',
    component: AddFeaturedBlog
  },
  { 
    path: '/admin/featured-blogs/:id', 
    name: 'AdminFeaturedBlogDetail', 
    component: AdminFeaturedBlogDetail 
  },
  {
    path: '/recommend-list',
    name: 'RecommendList',
    component: RecommendListView
  },
  {
    path: '/recommend-list',
    name: 'RecommendList',
    component: RecommendListView
  },
  {
    path: '/recommendations/:id',
    name: 'RecommendDetail',
    component: RecommendDetailView
  },
  {
    path: '/publish-recommend',
    name: 'PublishRecommend',
    component: PublishRecommendView
  },
  {
    path: '/my-recommend',
    name: 'MyRecommend',
    component: MyRecommendView
  },
  {
    path: '/recommend/:id',
    name: 'RecommendDetail',
    component: () => import('@/views/recommend/RecommendDetailView.vue')
  },
  {
    path: '/my-favorites',
    name: 'MyFavorites',
    component: MyFavoritesView
  },
  {
    path: '/admin/recommend-review',
    name: 'AdminRecommendReview',
    component: AdminRecommendReview,
  },
  {
    path: '/admin/recommend-review/:id',
    name: 'AdminRecommendDetail',
    component: AdminRecommendDetail,
  },
  {
    path: '/my-blogs',
    name: 'MyBlogsView',
    component: MyBlogsView,
  },
  {
    path: '/blogs/:id',
    name: 'BlogDetail',
    component: BlogDetailView,
  },
  {
    path: '/edit-blog/:id?',
    name: 'BlogEditView',
    component: BlogEditView,
    props: true,
  },
  {
    path: '/admin/recommend-delete',
    name: 'AdminRecommendDelete',
    component: AdminRecommendDelete,
  },
  {
    path: '/:catchAll(.*)',
    redirect: '/' // 处理所有其他路径的重定向
  },
  {
    path: '/activity-hall',
    name: 'ActivityHall',
    component: ActivityHall
  },
  {
    path: '/activity-detail/:id',
    name: 'ActivityDetail',
    component: ActivityDetail
  },
  {
    path: '/publish-activity',
    name: 'PublishActivity',
    component: PublishActivity
  },
  {
    path: '/activity-preparation',
    name: 'ActivityPreparation',
    component: ActivityPreparation
  },
  {
    path: '/my-participation',
    name: 'MyParticipation',
    component: MyParticipation
  },
  {
    path: '/activity-record',
    name: 'ActivityRecord',
    component: ActivityRecord
  },
  {
    path: '/admin/activity-review',
    name: 'AdminActivityReview',
    component: AdminActivityReview
  },
  {
    path: '/admin/activity-delete',
    name: 'AdminActivityDelete',
    component: AdminActivityDelete
  },
  
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
  linkActiveClass: 'active' // 设置激活链接的类名
});

export default router;
