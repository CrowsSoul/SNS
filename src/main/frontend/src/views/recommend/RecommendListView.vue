<template>
  <div class="recommend-list">
    <h1>内推展示</h1>
    <div class="filters">
      <input type="text" v-model="searchCity" placeholder="搜索城市" />
      <input type="text" v-model="searchCompany" placeholder="搜索公司" />
      <input type="text" v-model="searchJob" placeholder="搜索职务" />
      <button @click="applySearch">搜索</button>
    </div>
    <div class="advanced-filters">
      <label for="salaryFilter">按平均起薪筛选:</label>
      <select id="salaryFilter" v-model="salaryFilter">
        <option value="all">无限制</option>
        <option value="below10000">低于10000</option>
        <option value="10000-15000">10000-15000</option>
        <option value="15000-20000">15000-20000</option>
        <option value="above20000">20000以上</option>
      </select>
      <button @click="applyFilters">应用筛选</button>
    </div>
    <div v-if="loading">
      <p>加载中...</p>
    </div>
    <ul v-else-if="filteredRecommendations.length > 0">
      <li v-for="recommend in paginatedRecommendations" :key="recommend.id">
        <h2>{{ recommend.companyName }}</h2>
        <p>城市: {{ recommend.city }}</p>
        <p>发布者: {{ recommend.author }}</p>
        <p>
          起薪范围: {{ recommend.salaryRange.min }} -
          {{ recommend.salaryRange.max }}
        </p>
        <p>简介: {{ recommend.summary }}</p>
        <router-link
          :to="{ name: 'RecommendDetail', params: { id: recommend.id } }"
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
  name: "RecommendListView",
  data() {
    return {
      searchCity: "",
      searchCompany: "",
      searchJob: "",
      salaryFilter: "all",
      recommendations: [],
      currentPage: 1,
      recommendationsPerPage: 3,
      loading: true,
    };
  },
  computed: {
    filteredRecommendations() {
      let filtered = this.recommendations.filter(
        (recommend) => recommend.approved
      );

      if (this.searchCity) {
        filtered = filtered.filter((recommend) =>
          recommend.city.includes(this.searchCity)
        );
      }

      if (this.searchCompany) {
        filtered = filtered.filter((recommend) =>
          recommend.companyName.includes(this.searchCompany)
        );
      }

      if (this.searchJob) {
        filtered = filtered.filter((recommend) =>
          recommend.summary.includes(this.searchJob)
        );
      }

      if (this.salaryFilter !== "all") {
        filtered = filtered.filter((recommend) => {
          const avgSalary =
            (recommend.salaryRange.min + recommend.salaryRange.max) / 2;
          if (this.salaryFilter === "below10000") {
            return avgSalary < 10000;
          } else if (this.salaryFilter === "10000-15000") {
            return avgSalary >= 10000 && avgSalary <= 15000;
          } else if (this.salaryFilter === "15000-20000") {
            return avgSalary >= 15000 && avgSalary <= 20000;
          } else if (this.salaryFilter === "above20000") {
            return avgSalary > 20000;
          }
          return true;
        });
      }

      return filtered;
    },
    paginatedRecommendations() {
      const start = (this.currentPage - 1) * this.recommendationsPerPage;
      const end = start + this.recommendationsPerPage;
      return this.filteredRecommendations.slice(start, end);
    },
    totalPages() {
      return Math.ceil(
        this.filteredRecommendations.length / this.recommendationsPerPage
      );
    },
  },
  methods: {
    async fetchRecommendations() {
      try {
        const response = await axios.get("/recommendations");
        this.recommendations = response.data;
        this.restorePageState();
      } catch (error) {
        console.error("获取内推列表失败", error);
      } finally {
        this.loading = false;
      }
    },
    applySearch() {
      this.currentPage = 1;
      this.savePageState();
    },
    applyFilters() {
      this.currentPage = 1;
      this.savePageState();
    },
    nextPage() {
      if (this.currentPage < this.totalPages) {
        this.currentPage++;
        this.savePageState();
      }
    },
    prevPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
        this.savePageState();
      }
    },
    savePageState() {
      localStorage.setItem("currentPage", this.currentPage);
    },
    restorePageState() {
      const savedPage = localStorage.getItem("currentPage");
      if (savedPage) {
        this.currentPage = parseInt(savedPage, 10);
      }
    },
  },
  created() {
    this.fetchRecommendations();
  },
  watch: {
    searchCity() {
      this.applySearch();
    },
    searchCompany() {
      this.applySearch();
    },
    searchJob() {
      this.applySearch();
    },
    salaryFilter() {
      this.applyFilters();
    },
  },
};
</script>

<style scoped>
.recommend-list {
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
  align-items: center;
  margin-bottom: 20px;
}

.filters label,
.advanced-filters label {
  margin-right: 10px;
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

ul {
  list-style: none;
  padding: 0;
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
</style>
