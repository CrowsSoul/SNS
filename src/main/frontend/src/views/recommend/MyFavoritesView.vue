<template>
  <div class="my-favorites">
    <h1>我的收藏</h1>
    <div v-if="paginatedFavorites.length > 0">
      <ul>
        <li v-for="recommend in paginatedFavorites" :key="recommend.id">
          <h2>{{ recommend.companyName }}</h2>
          <p>城市: {{ recommend.city }}</p>
          <p>职务: {{ recommend.summary }}</p>
          <p>
            起薪范围: {{ recommend.salaryRange.min }} -
            {{ recommend.salaryRange.max }}
          </p>
          <p>正文: {{ recommend.content }}</p>
          <router-link
            :to="{ name: 'RecommendDetail', params: { id: recommend.id } }"
            class="action-button"
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
      <p>没有收藏的内推...</p>
    </div>
  </div>
</template>

<script>
import axios from "@/utils/axios";

export default {
  name: "MyFavoritesView",
  data() {
    return {
      favorites: [],
      userId: null,
      currentPage: 1,
      favoritesPerPage: 3, // 每页最多显示3个内推信息
    };
  },
  computed: {
    paginatedFavorites() {
      const start = (this.currentPage - 1) * this.favoritesPerPage;
      const end = start + this.favoritesPerPage;
      return this.favorites.slice(start, end);
    },
    totalPages() {
      return Math.ceil(this.favorites.length / this.favoritesPerPage);
    },
  },
  methods: {
    async fetchFavorites() {
      if (this.userId) {
        try {
          console.log("Fetching favorites for user ID:", this.userId);
          const response = await axios.get(`/favorites/${this.userId}`);
          const favoriteIds = response.data;
          console.log("Favorite IDs:", favoriteIds);

          const allRecommendations = await axios.get("/recommendations");
          console.log("All recommendations:", allRecommendations.data);

          this.favorites = allRecommendations.data.filter((recommend) =>
            favoriteIds.includes(recommend.id)
          );
          console.log("Filtered favorites:", this.favorites);
        } catch (error) {
          console.error("获取收藏信息失败", error);
        }
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
    setMenu() {
      this.$store.dispatch("setActiveSubMenu", "recommend");
    },
  },
  async created() {
    this.setMenu();
    const user = JSON.parse(localStorage.getItem("user"));
    console.log("User data from local storage:", user);
    if (user) {
      this.userId = user.username; // 使用用户名作为用户ID
      console.log("User ID:", this.userId);
      await this.fetchFavorites();
    }
  },
};
</script>

<style scoped>
.my-favorites {
  padding: 20px;
  background: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  max-width: 800px;
  margin: 0 auto;
}

h1 {
  margin-bottom: 20px;
  text-align: center;
  font-size: 24px;
  font-weight: bold;
  color: #333;
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

h2 {
  margin: 0 0 10px;
}

p {
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
  transition: background 0.3s;
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
  border-radius: 5px;
  cursor: pointer;
  transition: background 0.3s;
}

.pagination button:hover {
  background-color: #358a66;
}

.pagination span {
  font-size: 16px;
}
</style>
