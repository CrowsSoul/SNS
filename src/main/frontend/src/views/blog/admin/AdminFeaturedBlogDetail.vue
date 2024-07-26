<template>
  <div class="admin-blog-detail" v-if="blog">
    <h1>{{ blog.title }}</h1>
    <p>作者: {{ blog.author }}</p>
    <p>发布时间: {{ blog.publishedAt }}</p>
    <div v-html="blog.content" class="content"></div>
    <div class="review-actions">
      <router-link :to="getBackRoute()" class="action-button">
        返回列表
      </router-link>
      <button @click="toggleRecommend" :class="recommendButtonClass">
        {{ blog.isRecommended ? "不推荐" : "推荐" }}
      </button>
    </div>
    <div v-if="showToggleSuccess" class="toggle-success">
      <div class="toggle-success-box">
        <p>{{ toggleMessage }}</p>
        <button @click="closeToggleSuccess" class="confirm-button">确定</button>
      </div>
    </div>
  </div>
  <div v-else>
    <p>加载中...</p>
  </div>
</template>

<script>
import axios from "@/utils/axios";

export default {
  name: "AdminBlogDetail",
  data() {
    return {
      blog: null,
      showToggleSuccess: false,
      toggleMessage: "",
    };
  },
  methods: {
    async fetchBlog() {
      const blogId = this.$route.params.id;
      try {
        const response = await axios.get(`/blogs/${blogId}`);
        this.blog = response.data;
      } catch (error) {
        console.error("获取博文详情失败", error);
      }
    },
    async toggleRecommend() {
      try {
        const updatedStatus = !this.blog.isRecommended;
        await axios.put(`/blogs/${this.blog.id}`, {
          isRecommended: updatedStatus,
        });
        this.blog.isRecommended = updatedStatus;
        this.toggleMessage = updatedStatus ? "推荐成功！" : "取消推荐成功！";
        this.showToggleSuccess = true;
      } catch (error) {
        console.error("更新推荐状态失败", error);
      }
    },
    closeToggleSuccess() {
      this.showToggleSuccess = false;
      this.fetchBlog(); // 刷新博文信息
    },
    getBackRoute() {
      if (this.$route.query.fromAddFeatured) {
        return "/admin/add-featured-blog";
      } else {
        return "/admin/featured-blogs";
      }
    },
  },
  computed: {
    recommendButtonClass() {
      return {
        "action-button": true,
        "recommend-button": !this.blog.isRecommended,
        "unrecommend-button": this.blog.isRecommended,
      };
    },
  },
  created() {
    this.fetchBlog();
  },
};
</script>

<style scoped>
.admin-blog-detail {
  padding: 20px;
  background: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  max-width: 800px;
  margin: 0 auto;
}

.admin-blog-detail h1 {
  margin-top: 0;
}

.admin-blog-detail p {
  margin: 10px 0;
}

.admin-blog-detail .content {
  margin-top: 20px;
  padding: 20px;
  background: white;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.review-actions {
  margin-top: 20px;
  display: flex;
  justify-content: flex-start;
  gap: 10px; /* 间距调整 */
}

.action-button {
  background-color: #42b983;
  padding: 10px 20px;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  text-align: center;
  text-decoration: none;
}

.action-button:hover {
  background-color: #358a66;
}

.recommend-button {
  background-color: #6f42c1;
}

.recommend-button:hover {
  background-color: #5a2d91;
}

.unrecommend-button {
  background-color: #e74c3c;
}

.unrecommend-button:hover {
  background-color: #c0392b;
}

.toggle-success {
  background: rgba(0, 0, 0, 0.8);
  color: white;
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  justify-content: center;
  align-items: center;
}

.toggle-success-box {
  background: #2c3e50;
  padding: 40px;
  border-radius: 30px;
  text-align: center;
  width: 400px;
}

.confirm-button {
  padding: 5px 10px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-top: 20px;
  font-size: 14px;
}

.confirm-button:hover {
  background-color: #358a66;
}
</style>
