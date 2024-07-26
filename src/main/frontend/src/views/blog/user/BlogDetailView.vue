<template>
  <div class="blog-detail" v-if="blog">
    <h1>{{ blog.title }}</h1>
    <p>作者: {{ blog.author }}</p>
    <p>发布时间: {{ blog.publishedAt }}</p>
    <div v-html="blog.content" class="content"></div>
    <div v-if="isReviewMode" class="review-actions">
      <button @click="approveBlog" class="approve-button">过审核</button>
      <button @click="rejectBlog" class="reject-button">不过审</button>
    </div>
    <router-link :to="getBackRoute()" class="action-button"
      >返回列表</router-link
    >

    <h2>评论</h2>
    <div v-if="comments.length > 0" class="comments">
      <ul>
        <li v-for="comment in paginatedComments" :key="comment.id">
          <p class="comment-author">
            {{ comment.author }}
            <button @click="reportComment(comment.id)" class="report-button">
              举报
            </button>
          </p>
          <p class="comment-content">{{ comment.content }}</p>
        </li>
      </ul>
      <div class="pagination">
        <button @click="prevPage" :disabled="currentPage === 1">上一页</button>
        <span>第 {{ currentPage }} 页，共 {{ totalPages }} 页</span>
        <button @click="nextPage" :disabled="currentPage === totalPages">
          下一页
        </button>
      </div>
    </div>
    <div v-else>
      <p>还没有评论...</p>
    </div>

    <div class="add-comment">
      <h3>发表评论</h3>
      <textarea v-model="newComment" placeholder="输入评论内容"></textarea>
      <button @click="addComment" class="action-button">发表</button>
    </div>

    <div v-if="showReportSuccess" class="report-success">
      <div class="report-success-box">
        <p>举报成功</p>
        <button @click="closeReportSuccess" class="confirm-button">确定</button>
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
  name: "BlogDetailView",
  data() {
    return {
      blog: null,
      comments: [],
      newComment: "",
      currentPage: 1,
      commentsPerPage: 3,
      showReportSuccess: false,
    };
  },
  computed: {
    paginatedComments() {
      const start = (this.currentPage - 1) * this.commentsPerPage;
      const end = start + this.commentsPerPage;
      return this.comments.slice(start, end);
    },
    totalPages() {
      return Math.ceil(this.comments.length / this.commentsPerPage);
    },
    isReviewMode() {
      return this.$route.params.isReview === "true";
    },
  },
  methods: {
    getBackRoute() {
      if (this.isReviewMode) {
        return "/admin/blog-review";
      }
      return this.$route.query.fromMyBlogs
        ? { name: "MyBlogs", query: { page: this.$route.query.page } }
        : "/blogs";
    },
    async fetchBlog() {
      const blogId = this.$route.params.id;
      try {
        const response = await axios.get(`/blogs/${blogId}`);
        this.blog = response.data;
      } catch (error) {
        console.error("获取博文详情失败", error);
      }
    },
    async fetchComments() {
      const blogId = this.$route.params.id;
      try {
        const response = await axios.get(`/blogs/${blogId}/comments`);
        this.comments = response.data;
      } catch (error) {
        console.error("获取评论失败", error);
      }
    },
    async addComment() {
      const user = JSON.parse(localStorage.getItem("user"));
      if (this.newComment.trim() === "") return;
      try {
        const response = await axios.post("/comments", {
          author: user.nickname,
          blogId: this.blog.id,
          content: this.newComment,
        });
        this.comments.push(response.data);
        this.newComment = "";
      } catch (error) {
        console.error("发表评论失败", error);
      }
    },
    async reportComment(id) {
      try {
        const response = await axios.put(`/comments/${id}/report`);
        if (response.data.success) {
          this.showReportSuccess = true;
        }
      } catch (error) {
        console.error("举报评论失败", error);
      }
    },
    async approveBlog() {
      try {
        await axios.put(`/blogs/${this.blog.id}`, {
          title: this.blog.title,
          content: this.blog.content,
          status: "published",
        });
        this.$router.push("/admin/blog-review");
      } catch (error) {
        console.error("审核通过失败", error);
      }
    },
    async rejectBlog() {
      try {
        await axios.put(`/blogs/${this.blog.id}`, {
          title: this.blog.title,
          content: this.blog.content,
          status: "rejected",
        });
        this.$router.push("/admin/blog-review");
      } catch (error) {
        console.error("审核不通过失败", error);
      }
    },
    closeReportSuccess() {
      this.showReportSuccess = false;
      this.fetchComments();
    },
    nextPage() {
      if (this.currentPage < this.totalPages) {
        this.currentPage++;
      }
    },
    prevPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
      }
    },
  },
  created() {
    this.fetchBlog();
    this.fetchComments();
  },
};
</script>

<style scoped>
.blog-detail {
  padding: 20px;
  background: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  max-width: 800px;
  margin: 0 auto;
}

.blog-detail h1 {
  margin-top: 0;
}

.blog-detail p {
  margin: 10px 0;
}

.blog-detail .content {
  margin-top: 20px;
  padding: 20px;
  background: white;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.review-actions {
  margin-top: 20px;
  display: flex;
  gap: 10px;
}

.approve-button {
  padding: 10px 20px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
}

.approve-button:hover {
  background-color: #358a66;
}

.reject-button {
  padding: 10px 20px;
  background-color: #e74c3c;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
}

.reject-button:hover {
  background-color: #c0392b;
}

.action-button {
  padding: 10px 20px;
  margin: 20px 0;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  display: inline-block;
  text-align: center;
  text-decoration: none;
}

.action-button:hover {
  background-color: #358a66;
}

.comments {
  margin-top: 20px;
  background: #eef2f3;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.comment-author {
  font-size: 18px;
  font-weight: bold;
  display: flex;
  align-items: center;
}

.comment-content {
  font-size: 14px;
  margin-bottom: 10px;
}

.report-button {
  padding: 5px 10px;
  margin-left: 10px;
  background-color: #e74c3c;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.report-button:hover {
  background-color: #c0392b;
}

.add-comment {
  margin-top: 20px;
}

.add-comment textarea {
  width: 100%;
  height: 100px;
  padding: 10px;
  box-sizing: border-box;
  border-radius: 10px;
  border: 1px solid #ccc;
  margin-bottom: 10px;
}

.pagination {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 20px;
}

.pagination button {
  padding: 10px 20px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
}

.pagination button:hover {
  background-color: #358a66;
}

.report-success {
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

.report-success-box {
  background: #2c3e50;
  padding: 40px;
  border-radius: 30px;
  text-align: center;
  width: 400px;
}

.confirm-button {
  padding: 15px 30px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  margin-top: 20px;
  font-size: 16px;
}

.confirm-button:hover {
  background-color: #358a66;
}
</style>
