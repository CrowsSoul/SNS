<template>
  <div class="admin-recommend-review">
    <h1>内推审核</h1>
    <div class="recommend-list">
      <ul v-if="pendingRecommendations.length > 0">
        <li v-for="recommend in pendingRecommendations" :key="recommend.id">
          <h2>{{ recommend.companyName }}</h2>
          <p>城市: {{ recommend.city }}</p>
          <p>职务: {{ recommend.summary }}</p>
          <router-link
            :to="{ name: 'AdminRecommendDetail', params: { id: recommend.id } }"
            class="action-button"
          >
            查看详情
          </router-link>
        </li>
      </ul>
      <div v-else>
        <p>暂无审核中的内推...</p>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "@/utils/axios";

export default {
  name: "AdminRecommendReview",
  data() {
    return {
      recommendations: [],
    };
  },
  computed: {
    pendingRecommendations() {
      return this.recommendations.filter((recommend) => !recommend.approved);
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
.admin-recommend-review {
  padding: 20px;
  background: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  max-width: 800px;
  margin: 0 auto;
  text-align: center;
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
  margin: 0;
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
</style>
