<template>
  <div class="admin-recommend-delete">
    <h1>删除内推</h1>
    <div class="filters">
      <input type="text" v-model="searchCompany" placeholder="按公司名搜索" />
      <input type="text" v-model="searchSummary" placeholder="按职务名搜索" />
      <button @click="applyFilters">搜索</button>
    </div>
    <div class="recommend-list">
      <ul v-if="paginatedRecommendations.length > 0">
        <li v-for="recommend in paginatedRecommendations" :key="recommend.id">
          <div class="recommend-header">
            <h2>{{ recommend.companyName }}</h2>
            <button
              @click="confirmDelete(recommend.id)"
              class="action-button delete-button"
            >
              删除
            </button>
          </div>
          <p>城市: {{ recommend.city }}</p>
          <p>职务: {{ recommend.summary }}</p>
          <p>
            起薪范围: {{ recommend.salaryRange.min }} -
            {{ recommend.salaryRange.max }}
          </p>
          <p>正文: {{ recommend.content }}</p>
        </li>
      </ul>
      <div v-else>
        <p>没有匹配的内推...</p>
      </div>
    </div>
    <div class="pagination">
      <button @click="prevPage" :disabled="currentPage === 1">上一页</button>
      <span>第 {{ currentPage }} 页，共 {{ totalPages }} 页</span>
      <button @click="nextPage" :disabled="currentPage === totalPages">
        下一页
      </button>
    </div>
    <div v-if="showDeleteConfirm" class="delete-confirm">
      <div class="delete-confirm-box">
        <p>是否确认删除该内推？</p>
        <div class="confirm-buttons">
          <button @click="deleteRecommend" class="confirm-button">确认</button>
          <button @click="cancelDelete" class="cancel-button">取消</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "@/utils/axios";

export default {
  name: "AdminRecommendDelete",
  data() {
    return {
      recommendations: [],
      searchCompany: "",
      searchSummary: "",
      currentPage: 1,
      recommendationsPerPage: 3,
      showDeleteConfirm: false,
      recommendToDelete: null,
    };
  },
  computed: {
    filteredRecommendations() {
      return this.recommendations.filter((recommend) => {
        return (
          recommend.approved && // 仅显示通过审核的内推
          (!this.searchCompany ||
            recommend.companyName.includes(this.searchCompany)) &&
          (!this.searchSummary ||
            recommend.summary.includes(this.searchSummary))
        );
      });
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
  watch: {
    searchCompany() {
      this.currentPage = 1;
    },
    searchSummary() {
      this.currentPage = 1;
    },
  },
  methods: {
    async fetchRecommendations() {
      try {
        const response = await axios.get("/recommendations");
        this.recommendations = response.data;
      } catch (error) {
        console.error("获取内推列表失败", error);
      }
    },
    confirmDelete(id) {
      this.recommendToDelete = id;
      this.showDeleteConfirm = true;
    },
    async deleteRecommend() {
      if (this.recommendToDelete) {
        try {
          await axios.delete(`/recommendations/${this.recommendToDelete}`);
          this.recommendations = this.recommendations.filter(
            (recommend) => recommend.id !== this.recommendToDelete
          );
          console.log("内推已删除");
        } catch (error) {
          console.error("删除内推失败", error);
        } finally {
          this.cancelDelete();
        }
      }
    },
    cancelDelete() {
      this.showDeleteConfirm = false;
      this.recommendToDelete = null;
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
    setMenu() {
      this.$store.dispatch("setActiveSubMenu", "recommend");
    },
  },
  created() {
    this.fetchRecommendations();
    this.setMenu();
  },
};
</script>

<style scoped>
.admin-recommend-delete {
  padding: 20px;
  background: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  max-width: 800px;
  margin: 0 auto;
  text-align: center;
}

.filters {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}

.filters input {
  flex: 1;
  padding: 10px;
  margin-right: 10px;
  border-radius: 5px;
  border: 1px solid #ccc;
}

.filters button {
  padding: 10px 20px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.filters button:hover {
  background-color: #358a66;
}

.recommend-list ul {
  list-style: none;
  padding: 0;
}

.recommend-list li {
  background: white;
  padding: 20px;
  margin-bottom: 10px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  text-align: left; /* 靠左显示 */
}

.recommend-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.recommend-list li h2 {
  margin: 0;
  display: inline; /* 内联显示 */
}

.recommend-list li p {
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

.delete-button {
  background-color: #e74c3c;
}

.delete-button:hover {
  background-color: #c0392b;
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
  border-radius: 5px;
  cursor: pointer;
}

.pagination button:hover {
  background-color: #358a66;
}

.delete-confirm {
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

.delete-confirm-box {
  background: #2c3e50;
  padding: 20px;
  border-radius: 30px;
  text-align: center;
}

.confirm-buttons {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.confirm-button,
.cancel-button {
  margin: 0 10px;
  padding: 10px 20px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
}

.confirm-button:hover,
.cancel-button:hover {
  background-color: #358a66;
}
</style>
