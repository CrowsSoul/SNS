<template>
  <div class="blog-list">
    <h1>博文展示</h1>
    <div class="filters">
      <input type="text" v-model="searchQuery" placeholder="搜索博文" />
      <button @click="applySearch">搜索</button>
    </div>
    <div class="advanced-filters">
      <label for="dateFilter">按日期筛选:</label>
      <select id="dateFilter" v-model="dateFilter">
        <option value="all">无限制</option>
        <option value="7days">近7日</option>
        <option value="1month">近1个月</option>
        <option value="6months">近6个月</option>
        <option value="2years">近2年</option>
      </select>

      <label for="authorFilter">按作者筛选:</label>
      <input
        type="text"
        id="authorFilter"
        v-model="authorFilter"
        placeholder="输入作者姓名"
      />
      <button @click="applyFilters">应用筛选</button>
    </div>
    <ul v-if="filteredBlogs.length > 0">
      <li
        v-if="!searchQuery && dateFilter === 'all' && !authorFilter"
        class="recommended-blogs"
      >
        <h2>以下为推荐博文</h2>
        <ul>
          <li v-for="blog in paginatedRecommendedBlogs" :key="blog.id">
            <h2>{{ blog.title }}</h2>
            <p>作者: {{ blog.author }}</p>
            <p>发布时间: {{ blog.publishedAt }}</p>
            <router-link
              :to="{
                name: 'BlogDetail',
                params: { id: blog.id },
                query: { page: currentPage },
              }"
              class="action-button"
              >查看详情</router-link
            >
          </li>
        </ul>
      </li>
      <li v-else v-for="blog in paginatedBlogs" :key="blog.id">
        <h2>{{ blog.title }}</h2>
        <p>作者: {{ blog.author }}</p>
        <p>发布时间: {{ blog.publishedAt }}</p>
        <router-link
          :to="{
            name: 'BlogDetail',
            params: { id: blog.id },
            query: { page: currentPage },
          }"
          class="action-button"
          >查看详情</router-link
        >
      </li>
    </ul>
    <div v-else>
      <p>没有相关结果...</p>
    </div>
    <div class="pagination">
      <button @click="prevPage" :disabled="currentPage === 1">上一页</button>
      <span>第 {{ currentPage }} 页，共 {{ totalPages }} 页</span>
      <button @click="nextPage" :disabled="currentPage === totalPages">
        下一页
      </button>
    </div>
  </div>
</template>

<script>
import axios from "@/utils/axios";

export default {
  name: "BlogListView",
  data() {
    return {
      searchQuery: "",
      dateFilter: "all",
      authorFilter: "",
      blogs: [],
      currentPage: 1,
      blogsPerPage: 3, // 每页最多显示 3 篇博文
      loading: true,
    };
  },
  computed: {
    recommendedBlogs() {
      return this.blogs.filter(
        (blog) => blog.isRecommended && blog.status !== "pending"
      );
    },
    filteredBlogs() {
      let filtered = this.blogs.filter((blog) => blog.status !== "pending"); // 过滤掉审核中的博文

      if (this.searchQuery) {
        filtered = filtered.filter((blog) =>
          blog.title.includes(this.searchQuery)
        );
      }

      if (this.dateFilter !== "all") {
        const now = new Date();
        filtered = filtered.filter((blog) => {
          const blogDate = new Date(blog.publishedAt);
          if (this.dateFilter === "7days") {
            return now - blogDate <= 7 * 24 * 60 * 60 * 1000;
          } else if (this.dateFilter === "1month") {
            return now - blogDate <= 30 * 24 * 60 * 60 * 1000;
          } else if (this.dateFilter === "6months") {
            return now - blogDate <= 6 * 30 * 24 * 60 * 60 * 1000;
          } else if (this.dateFilter === "2years") {
            return now - blogDate <= 2 * 365 * 24 * 60 * 60 * 1000;
          }
          return true;
        });
      }

      if (this.authorFilter) {
        filtered = filtered.filter((blog) =>
          blog.author.includes(this.authorFilter)
        );
      }

      return filtered;
    },
    paginatedBlogs() {
      const start = (this.currentPage - 1) * this.blogsPerPage;
      const end = start + this.blogsPerPage;
      return this.filteredBlogs.slice(start, end);
    },
    paginatedRecommendedBlogs() {
      const start = (this.currentPage - 1) * this.blogsPerPage;
      const end = start + this.blogsPerPage;
      return this.recommendedBlogs.slice(start, end);
    },
    totalPages() {
      if (
        !this.searchQuery &&
        this.dateFilter === "all" &&
        !this.authorFilter
      ) {
        return Math.ceil(this.recommendedBlogs.length / this.blogsPerPage);
      }
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
      } finally {
        this.loading = false;
      }
    },
    applySearch() {
      this.currentPage = 1;
    },
    applyFilters() {
      this.currentPage = 1;
    },
    nextPage() {
      if (this.currentPage < this.totalPages) {
        this.currentPage++;
        this.updateRouteQuery();
      }
    },
    prevPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
        this.updateRouteQuery();
      }
    },
    updateRouteQuery() {
      this.$router.push({
        name: this.$route.name,
        query: { page: this.currentPage },
      });
    },
  },
  created() {
    const page = parseInt(this.$route.query.page, 10) || 1;
    this.currentPage = page;
    this.fetchBlogs();
  },
  watch: {
    searchQuery() {
      this.applySearch();
    },
    dateFilter() {
      this.applyFilters();
    },
    authorFilter() {
      this.applyFilters();
    },
  },
};
</script>

<style scoped>
.blog-list {
  padding: 20px;
  background: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  max-width: 800px;
  margin: 0 auto;
}

.filters,
.advanced-filters {
  display: flex;
  justify-content: space-between;
  align-items: center; /* 确保文本与输入框垂直居中对齐 */
  margin-bottom: 20px;
}

.filters label,
.advanced-filters label {
  margin-right: 10px; /* 确保文本与输入框之间有足够的间距 */
}

.filters input,
.advanced-filters input,
.advanced-filters select {
  flex: 1;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #ccc;
  margin-right: 10px;
}

.filters button,
.advanced-filters button {
  padding: 10px 20px;
  border: none;
  background-color: #42b983;
  color: white;
  border-radius: 5px;
  cursor: pointer;
}

.filters button:hover,
.advanced-filters button:hover {
  background-color: #358a66;
}

ul {
  list-style: none;
  padding: 0;
}

.recommended-blogs h2 {
  margin-bottom: 20px; /* 添加这个行来增加间距 */
}

li {
  background: white;
  padding: 20px;
  margin-bottom: 10px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

li h2 {
  margin: 0;
}

li p {
  margin: 10px 0;
}

.action-button {
  padding: 10px 20px;
  margin-right: 10px;
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
.filters button,
.advanced-filters button {
  padding: 10px 20px;
  border: none;
  background-color: #f0ad4e; /* 黄色 */
  color: white;
  border-radius: 5px;
  cursor: pointer;
}

.filters button:hover,
.advanced-filters button:hover {
  background-color: #ec971f; /* 较深的黄色，用于悬停状态 */
}
/* 单独为“应用筛选”按钮设置颜色 */
.advanced-filters button {
  background-color: #6f42c1; /* 紫色 */
}

.advanced-filters button:hover {
  background-color: #5a2d91; /* 较深的紫色，用于悬停状态 */
}
</style>
