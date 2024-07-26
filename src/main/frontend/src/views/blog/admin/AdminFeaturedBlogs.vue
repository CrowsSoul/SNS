<template>
  <div class="featured-blogs">
    <h1>精选博文</h1>
    <div v-if="featuredBlogs.length > 0">
      <ul>
        <li v-for="blog in paginatedBlogs" :key="blog.id">
          <h2>{{ blog.title }}</h2>
          <p>作者: {{ blog.author }}</p>
          <p>发布时间: {{ blog.publishedAt }}</p>
          <router-link
            :to="{ name: 'AdminFeaturedBlogDetail', params: { id: blog.id } }"
            class="action-button"
            style="margin-top: 20px"
            >查看详情</router-link
          >
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
      <p>暂无精选博文~</p>
    </div>
    <div class="add-button-container">
      <button
        @click="navigateToAddFeaturedBlog"
        class="action-button add-button"
      >
        添加精选
      </button>
    </div>
  </div>
</template>

<script>
import axios from "@/utils/axios";

export default {
  name: "AdminFeaturedBlogs",
  data() {
    return {
      blogs: [],
      currentPage: 1,
      blogsPerPage: 3,
    };
  },
  computed: {
    featuredBlogs() {
      return this.blogs.filter((blog) => blog.isRecommended);
    },
    paginatedBlogs() {
      const start = (this.currentPage - 1) * this.blogsPerPage;
      const end = start + this.blogsPerPage;
      return this.featuredBlogs.slice(start, end);
    },
    totalPages() {
      return Math.ceil(this.featuredBlogs.length / this.blogsPerPage);
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
    navigateToAddFeaturedBlog() {
      this.$router.push({ name: "AddFeaturedBlog" });
    },
  },
  created() {
    this.fetchBlogs();
  },
};
</script>

<style scoped>
.featured-blogs {
  padding: 20px;
  background: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  max-width: 800px;
  margin: 0 auto;
}

.featured-blogs h1 {
  margin-top: 0;
}

.featured-blogs ul {
  list-style: none;
  padding: 0;
}

.featured-blogs li {
  background: white;
  padding: 20px;
  margin-bottom: 10px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  position: relative; /* 确保内边距定位正常 */
}

.featured-blogs li .action-button {
  display: block; /* 确保按钮是块级元素 */
  text-align: left;
  width: 105px;
  margin-top: 10px; /* 为按钮添加顶部边距 */
}

.featured-blogs li h2 {
  margin: 0;
}

.featured-blogs li p {
  margin: 10px 0;
}

.action-button {
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  text-align: center;
  text-decoration: none;
}

.action-button:hover {
  background-color: #0056b3;
}

.add-button-container {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.add-button {
  background-color: #eebf04;
}

.add-button:hover {
  background-color: #d4ac0d;
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
