<template>
  <div class="recommend-detail" v-if="recommend.salaryRange">
    <h1>
      {{ recommend.companyName }}
      <font-awesome-icon
        v-if="isStudent"
        :icon="[isFavorited ? 'fas' : 'far', 'star']"
        @click="toggleFavorite"
        class="favorite-icon"
      />
    </h1>
    <p>城市: {{ recommend.city }}</p>
    <p>职务: {{ recommend.summary }}</p>
    <p>
      起薪范围: {{ recommend.salaryRange.min }} -
      {{ recommend.salaryRange.max }}
    </p>
    <p>正文: {{ recommend.content }}</p>
    <button @click="goBack" class="back-button">返回</button>
  </div>
  <div v-else class="loading">加载中...</div>
</template>

<script>
import axios from "@/utils/axios";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";

export default {
  name: "RecommendDetailView",
  components: {
    FontAwesomeIcon,
  },
  data() {
    return {
      recommend: {},
      isFavorited: false,
      isStudent: false,
      userId: null,
    };
  },
  methods: {
    async fetchRecommend() {
      try {
        const response = await axios.get(
          `/recommendations/${this.$route.params.id}`
        );
        this.recommend = response.data;
        console.log("Fetched recommend:", this.recommend); // 添加调试输出
        this.checkFavorite();
      } catch (error) {
        console.error("获取内推详情失败", error);
      }
    },
    goBack() {
      this.$router.go(-1);
    },
    async checkFavorite() {
      if (this.userId) {
        try {
          const response = await axios.get(`/favorites/${this.userId}`);
          const favorites = response.data;
          console.log("Fetched favorites:", favorites); // 添加调试输出
          this.isFavorited = favorites.includes(this.recommend.id);
          console.log("Is favorited:", this.isFavorited); // 添加调试输出
        } catch (error) {
          console.error("获取收藏信息失败", error);
        }
      }
    },
    async toggleFavorite() {
      if (this.userId) {
        try {
          const response = await axios.get(`/favorites/${this.userId}`);
          let favorites = response.data;
          console.log("Current favorites:", favorites);

          if (this.isFavorited) {
            favorites = favorites.filter((id) => id !== this.recommend.id);
          } else {
            favorites.push(this.recommend.id);
          }

          console.log("Updated favorites:", favorites);
          await axios.put(`/favorites/${this.userId}`, favorites);
          this.isFavorited = !this.isFavorited;
          console.log("Updated favorite status:", this.isFavorited);
        } catch (error) {
          console.error("更新收藏信息失败", error);
        }
      }
    },
  },
  async created() {
    const user = JSON.parse(localStorage.getItem("user"));
    console.log("User data from local storage:", user);
    if (user) {
      this.userId = user.username; // 使用用户名作为用户ID
      console.log("User ID:", this.userId);
      this.isStudent = user.role === "student";
      this.fetchRecommend();
    }
  },
};
</script>

<style scoped>
.recommend-detail {
  padding: 20px;
  background: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  max-width: 800px;
  margin: 0 auto;
  background: linear-gradient(145deg, #ffffff, #e6e6e6);
}

.loading {
  padding: 20px;
  text-align: center;
}

h1 {
  display: flex;
  align-items: center;
}

.favorite-icon {
  margin-left: 10px;
  cursor: pointer;
  font-size: 24px;
}

p {
  margin: 10px 0;
}

.back-button {
  margin-bottom: 20px;
  padding: 10px 20px;
  background: #42b983;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  transition: background 0.3s;
}

.back-button:hover {
  background: #358a66;
}
</style>
