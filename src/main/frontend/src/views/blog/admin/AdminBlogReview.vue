<template>
  <div class="admin-blog-review">
    <h1>博文审核</h1>
    <div class="blog-list">
      <ul v-if="paginatedBlogs.length > 0">
        <li v-for="blog in paginatedBlogs" :key="blog.id">
          <h2>{{ blog.title }}</h2>
          <p>作者: {{ blog.author }}</p>
          <p>发布时间: {{ blog.publishedAt }}</p>
          <router-link
            :to="{ name: 'AdminBlogDetail', params: { id: blog.id } }"
            class="action-button"
            >查看详情</router-link
          >
        </li>
      </ul>
      <div v-else>
        <p>暂无审核中的文章~</p>
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
  name: "AdminBlogReview",
  data() {
    return {
      blogs: [],
      currentPage: 1,
      blogsPerPage: 3,
    };
  },
  computed: {
    filteredBlogs() {
      return this.blogs.filter((blog) => blog.status === "pending");
    },
    paginatedBlogs() {
      const start = (this.currentPage - 1) * this.blogsPerPage;
      const end = start + this.blogsPerPage;
      return this.filteredBlogs.slice(start, end);
    },
    totalPages() {
      return Math.ceil(this.filteredBlogs.length / this.blogsPerPage);
    },
  },
  methods: {
    async fetchBlogs() {
      try {
        const response = await axios.get("/blogs");
        this.blogs = response.data;
      } catch (error) {
        console.error("获取博文列表失败", error);
      }
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
    this.fetchBlogs();
  },
};
</script>

<style scoped>
.admin-blog-review {
  padding: 20px;
  background: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  max-width: 800px;
  margin: 0 auto;
  text-align: center;
}

.blog-list ul {
  list-style: none;
  padding: 0;
}

.blog-list li {
  background: white;
  padding: 20px;
  margin-bottom: 10px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.blog-list li h2 {
  margin: 0;
}

.blog-list li p {
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
