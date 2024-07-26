<template>
  <div class="admin-comment-detail" v-if="comment && blog">
    <h1>评论详情</h1>
    <p>评论内容: {{ comment.content }}</p>
    <p>评论者: {{ comment.author }}</p>
    <p>博文: {{ blog.title }}</p>
    <div class="review-actions">
      <router-link to="/admin/comment-review" class="action-button"
        >返回列表</router-link
      >
      <button @click="approveComment" class="action-button approve-button">
        过审
      </button>
      <button @click="rejectComment" class="action-button reject-button">
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
  name: "AdminCommentDetail",
  data() {
    return {
      comment: null,
      blog: null,
    };
  },
  methods: {
    async fetchComment() {
      const commentId = this.$route.params.id;
      try {
        const response = await axios.get(`/comments/${commentId}`);
        this.comment = response.data;
        await this.fetchBlog(this.comment.blogId); // 获取评论后获取博文
      } catch (error) {
        console.error("获取评论详情失败", error);
      }
    },
    async fetchBlog(blogId) {
      try {
        const response = await axios.get(`/blogs/${blogId}`);
        this.blog = response.data;
      } catch (error) {
        console.error("获取博文详情失败", error);
      }
    },
    async approveComment() {
      try {
        await axios.put(`/comments/${this.comment.id}`, {
          ...this.comment,
          isReported: false,
        });
        this.$router.push("/admin/comment-review");
      } catch (error) {
        console.error("评论通过审核失败", error);
      }
    },
    async rejectComment() {
      try {
        await axios.delete(`/comments/${this.comment.id}`);
        this.$router.push("/admin/comment-review");
      } catch (error) {
        console.error("评论未通过审核失败", error);
      }
    },
  },
  created() {
    this.fetchComment();
  },
};
</script>

<style scoped>
.admin-comment-detail {
  padding: 20px;
  background: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  max-width: 800px;
  margin: 0 auto;
}

.admin-comment-detail h1 {
  margin-top: 0;
}

.admin-comment-detail p {
  margin: 10px 0;
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
