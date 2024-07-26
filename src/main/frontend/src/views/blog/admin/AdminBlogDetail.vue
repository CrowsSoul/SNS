<template>
  <div class="admin-blog-detail" v-if="blog">
    <h1>{{ blog.title }}</h1>
    <p>作者: {{ blog.author }}</p>
    <p>发布时间: {{ blog.publishedAt }}</p>
    <div v-html="blog.content" class="content"></div>
    <div class="review-actions">
      <router-link to="/admin/blog-review" class="action-button"
        >返回列表</router-link
      >
      <button @click="approveBlog" class="action-button approve-button">
        过审
      </button>
      <button @click="rejectBlog" class="action-button reject-button">
        不过审
      </button>
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
    async approveBlog() {
      try {
        const response = await axios.put(`/blogs/${this.blog.id}`, {
          title: this.blog.title,
          content: this.blog.content,
          status: "published", // 设置status为published
        });
        console.log("过审成功", response.data);
        this.$router.push("/admin/blog-review");
      } catch (error) {
        console.error("审核通过失败", error);
      }
    },
    async rejectBlog() {
      try {
        const response = await axios.put(`/blogs/${this.blog.id}`, {
          title: this.blog.title,
          content: this.blog.content,
          status: "rejected", // 设置status为rejected
        });
        console.log("不过审成功", response.data);
        this.$router.push("/admin/blog-review");
      } catch (error) {
        console.error("审核不通过失败", error);
      }
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
  justify-content: space-between;
}

.action-button {
  padding: 10px 20px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  text-align: center;
  text-decoration: none;
  flex: 1;
  margin: 0 5px;
}

.action-button:hover {
  background-color: #358a66;
}

.approve-button {
  background-color: #007bff;
}

.approve-button:hover {
  background-color: #0056b3;
}

.reject-button {
  background-color: #e74c3c;
}

.reject-button:hover {
  background-color: #c0392b;
}
</style>
