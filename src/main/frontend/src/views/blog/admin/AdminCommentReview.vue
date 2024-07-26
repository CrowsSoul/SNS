<template>
  <div class="admin-comment-review">
    <h1>评论审核</h1>
    <div class="comment-list">
      <ul v-if="paginatedComments.length > 0">
        <li v-for="comment in paginatedComments" :key="comment.id">
          <p>{{ comment.content }}</p>
          <p>评论者: {{ comment.author }}</p>
          <p>博文: {{ getBlogTitle(comment.blogId) }}</p>
          <router-link
            :to="{ name: 'AdminCommentDetail', params: { id: comment.id } }"
            class="action-button"
            >查看详情</router-link
          >
        </li>
      </ul>
      <div v-else>
        <p>暂无被举报的评论~</p>
      </div>
      <div class="pagination" v-if="totalPages > 1">
        <button @click="prevPage" :disabled="currentPage === 1">上一页</button>
        <span>第 {{ currentPage }} 页，共 {{ totalPages }} 页</span>
        <button @click="nextPage" :disabled="currentPage === totalPages">
          下一页
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "@/utils/axios";

export default {
  name: "AdminCommentReview",
  data() {
    return {
      comments: [],
      blogs: [],
      currentPage: 1,
      commentsPerPage: 3,
    };
  },
  computed: {
    reportedComments() {
      return this.comments.filter((comment) => comment.isReported === true);
    },
    paginatedComments() {
      const start = (this.currentPage - 1) * this.commentsPerPage;
      const end = start + this.commentsPerPage;
      return this.reportedComments.slice(start, end);
    },
    totalPages() {
      return Math.ceil(this.reportedComments.length / this.commentsPerPage);
    },
  },
  methods: {
    async fetchComments() {
      try {
        const response = await axios.get("/comments");
        this.comments = response.data;
        await this.fetchBlogs(); // 确保先获取评论后获取博文
      } catch (error) {
        console.error("获取评论列表失败", error);
      }
    },
    async fetchBlogs() {
      try {
        const response = await axios.get("/blogs");
        this.blogs = response.data;
      } catch (error) {
        console.error("获取博文列表失败", error);
      }
    },
    getBlogTitle(blogId) {
      const blog = this.blogs.find((b) => b.id === blogId);
      return blog ? blog.title : "未知博文";
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
    this.fetchComments();
  },
};
</script>

<style scoped>
.admin-comment-review {
  padding: 20px;
  background: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  max-width: 800px;
  margin: 0 auto;
  text-align: center;
}

.comment-list ul {
  list-style: none;
  padding: 0;
}

.comment-list li {
  background: white;
  padding: 20px;
  margin-bottom: 10px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.comment-list li p {
  margin: 10px 0;
}

.action-button {
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  display: inline-block;
  text-align: center;
  text-decoration: none;
}

.action-button:hover {
  background-color: #0056b3;
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
</style>
