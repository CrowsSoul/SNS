<template>
  <div class="add-featured-blog">
    <h1>添加精选博文</h1>
    <div class="search-bar">
      <input type="text" v-model="searchQuery" placeholder="搜索博文" />
      <button @click="searchBlogs" class="action-button2">搜索</button>
    </div>
    <div v-if="filteredBlogs.length > 0">
      <ul>
        <li v-for="blog in paginatedBlogs" :key="blog.id">
          <h2>{{ blog.title }}</h2>
          <p>作者: {{ blog.author }}</p>
          <p>发布时间: {{ blog.publishedAt }}</p>
          <router-link
            :to="{ name: 'AdminFeaturedBlogDetail', params: { id: blog.id } }"
            class="action-button blue-button"
          >
            查看详情
          </router-link>
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
      <p>暂无相关博文~</p>
    </div>
    <div v-if="showRecommendSuccess" class="recommend-success">
      <div class="recommend-success-box">
        <p>推荐成功！</p>
        <button @click="closeRecommendSuccess" class="confirm-button">
          确定
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "@/utils/axios";

export default {
  name: "AddFeaturedBlog",
  data() {
    return {
      searchQuery: "",
      blogs: [],
      currentPage: 1,
      blogsPerPage: 3,
      showRecommendSuccess: false,
    };
  },
  computed: {
    filteredBlogs() {
      if (!this.searchQuery) {
        return this.blogs.filter((blog) => !blog.isRecommended);
      }
      return this.blogs.filter((blog) => {
        return (
          !blog.isRecommended &&
          ((blog.title && blog.title.includes(this.searchQuery)) ||
            (blog.author && blog.author.includes(this.searchQuery)))
        );
      });
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
    async recommendBlog(blogId) {
      try {
        const response = await axios.put(`/blogs/${blogId}`, {
          isRecommended: true,
        });
        console.log("推荐博文成功", response.data);
        this.showRecommendSuccess = true;
        this.fetchBlogs(); // 更新列表
      } catch (error) {
        console.error("推荐博文失败", error);
      }
    },
    searchBlogs() {
      this.currentPage = 1;
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
    closeRecommendSuccess() {
      this.showRecommendSuccess = false;
      this.fetchBlogs(); // 刷新页面
    },
  },
  created() {
    this.fetchBlogs();
  },
};
</script>

<style scoped>
.add-featured-blog {
  padding: 20px;
  background: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  max-width: 800px;
  margin: 0 auto;
}

.search-bar {
  display: flex;
  margin-bottom: 20px;
}

.search-bar input {
  flex: 1;
  padding: 10px;
  border-radius: 10px;
  border: 1px solid #ccc;
  margin-right: 10px;
}

.action-button {
  padding: 10px 20px;
  background-color: #6f42c1;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  text-align: center;
  text-decoration: none;
  margin-top: 10px; /* 添加此行 */
}

.action-button:hover {
  background-color: #5a2d91;
}

.action-button2 {
  padding: 10px 20px;
  background-color: #f0ad4e;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  text-align: center;
  text-decoration: none;
}

.action-button2:hover {
  background-color: #ec971f;
}

.blue-button {
  background-color: #007bff;
}

.blue-button:hover {
  background-color: #0056b3;
}

.add-featured-blog ul {
  list-style: none;
  padding: 0;
}

.add-featured-blog li {
  background: white;
  padding: 20px;
  margin-bottom: 10px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.add-featured-blog li h2 {
  margin: 0;
}

.add-featured-blog li p {
  margin: 10px 0;
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
