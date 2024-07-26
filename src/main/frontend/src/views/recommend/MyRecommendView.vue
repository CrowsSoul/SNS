<template>
  <div class="my-recommends">
    <h1>我的内推</h1>
    <div class="recommend-list">
      <div v-if="filteredRecommends.length > 0">
        <ul>
          <li v-for="recommend in paginatedRecommends" :key="recommend.id">
            <h2>
              {{ recommend.companyName }}
              <span v-if="!recommend.approved" class="status status-pending"
                >审核中</span
              >
            </h2>
            <p>城市: {{ recommend.city }}</p>
            <p>职务: {{ recommend.summary }}</p>
            <p>
              起薪范围: {{ recommend.salaryRange.min }} -
              {{ recommend.salaryRange.max }}
            </p>
            <router-link
              :to="{
                name: 'RecommendDetail',
                params: { id: recommend.id },
                query: { page: currentPage, fromMyRecommends: true },
              }"
              class="action-button"
            >
              查看详情
            </router-link>
            <button
              @click="editRecommend(recommend)"
              class="action-button edit-button"
            >
              编辑
            </button>
            <button
              @click="confirmDelete(recommend.id)"
              class="action-button delete-button"
            >
              删除
            </button>
          </li>
        </ul>
        <button @click="createNewRecommend" class="new-recommend-button">
          发布新内推
        </button>
        <div class="pagination">
          <button @click="prevPage" :disabled="currentPage === 1">
            上一页
          </button>
          <span>第 {{ currentPage }} 页，共 {{ totalPages }} 页</span>
          <button @click="nextPage" :disabled="currentPage === totalPages">
            下一页
          </button>
        </div>
      </div>
      <div v-else>
        <p>没有相关内推...</p>
      </div>
    </div>
    <div v-if="showDeleteConfirm" class="delete-confirm">
      <div class="delete-confirm-box">
        <p>是否确认删除？</p>
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
  name: "MyRecommendsView",
  data() {
    return {
      recommends: [],
      currentPage: this.$route.query.page
        ? parseInt(this.$route.query.page)
        : 1,
      recommendsPerPage: 3, // 每页最多显示 3 条内推
      showDeleteConfirm: false,
      recommendToDelete: null,
    };
  },
  computed: {
    filteredRecommends() {
      const user = JSON.parse(localStorage.getItem("user"));
      if (user) {
        return this.recommends.filter(
          (recommend) => recommend.author === user.nickname
        );
      }
      return [];
    },
    paginatedRecommends() {
      const start = (this.currentPage - 1) * this.recommendsPerPage;
      const end = start + this.recommendsPerPage;
      return this.filteredRecommends.slice(start, end);
    },
    totalPages() {
      return Math.ceil(this.filteredRecommends.length / this.recommendsPerPage);
    },
  },
  methods: {
    async fetchRecommends() {
      try {
        const response = await axios.get("/recommendations");
        this.recommends = response.data;
        const queryPage = parseInt(this.$route.query.page);
        if (
          !isNaN(queryPage) &&
          queryPage > 0 &&
          queryPage <= this.totalPages
        ) {
          this.currentPage = queryPage;
        }
      } catch (error) {
        console.error("获取内推列表失败", error);
      }
    },
    createNewRecommend() {
      this.$router.push({ name: "PublishRecommend" });
    },
    editRecommend(recommend) {
      const query = {
        ...recommend,
        salaryRange: JSON.stringify(recommend.salaryRange),
        page: this.currentPage,
      };
      this.$router.push({ name: "PublishRecommend", query });
    },
    confirmDelete(id) {
      this.showDeleteConfirm = true;
      this.recommendToDelete = id;
    },
    async deleteRecommend() {
      if (this.recommendToDelete) {
        try {
          await axios.delete(`/recommendations/${this.recommendToDelete}`);
          this.fetchRecommends().then(() => {
            const currentPageRecommends = this.filteredRecommends.slice(
              (this.currentPage - 1) * this.recommendsPerPage,
              this.currentPage * this.recommendsPerPage
            );
            if (currentPageRecommends.length === 0 && this.currentPage > 1) {
              this.currentPage--;
            }
          });
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
    this.fetchRecommends();
    this.setMenu();
  },
};
</script>

<style scoped>
.my-recommends {
  padding: 20px;
  background: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  max-width: 800px;
  margin: 0 auto;
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
}

.recommend-list li h2 {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.recommend-list li h2 .status {
  padding: 5px 10px;
  border-radius: 10px;
  color: white;
}

.status-pending {
  background-color: #f0ad4e;
}

.recommend-list li p {
  margin: 10px 0;
}

.action-button {
  padding: 10px 20px;
  margin-right: 10px;
  background-color: #1c7dbd;
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

.new-recommend-button {
  display: block;
  margin: 20px auto;
  padding: 15px 30px;
  font-size: 18px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
}

.new-recommend-button:hover {
  background-color: #358a66;
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

/* 编辑按钮 */
.edit-button {
  background-color: #f0ad4e; /* 黄色 */
}

.edit-button:hover {
  background-color: #ec971f; /* 较深的黄色，用于悬停状态 */
}

/* 删除按钮 */
.delete-button {
  background-color: #d9534f; /* 红色 */
}

.delete-button:hover {
  background-color: #c9302c; /* 较深的红色，用于悬停状态 */
}
</style>
