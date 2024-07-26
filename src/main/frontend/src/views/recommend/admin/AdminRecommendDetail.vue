<template>
  <div class="admin-recommend-detail" v-if="recommend">
    <h1>{{ recommend.companyName }}</h1>
    <p>城市: {{ recommend.city }}</p>
    <p>职务: {{ recommend.summary }}</p>
    <p>
      起薪范围: {{ recommend.salaryRange.min }} -
      {{ recommend.salaryRange.max }}
    </p>
    <div v-html="recommend.content" class="content"></div>
    <div class="review-actions">
      <router-link to="/admin/recommend-review" class="action-button"
        >返回列表</router-link
      >
      <button @click="approveRecommend" class="action-button approve-button">
        过审
      </button>
      <button @click="rejectRecommend" class="action-button reject-button">
        不过审
      </button>
    </div>
  </div>
  <div v-else>
    <p>加载中...</p>
  </div>
</template>

<script>
import axios from "@/utils/axios";

export default {
  name: "AdminRecommendDetail",
  data() {
    return {
      recommend: null,
    };
  },
  methods: {
    async fetchRecommend() {
      const recommendId = this.$route.params.id;
      try {
        const response = await axios.get(`/recommendations/${recommendId}`);
        this.recommend = response.data;
      } catch (error) {
        console.error("获取内推详情失败", error);
      }
    },
    async approveRecommend() {
      try {
        const response = await axios.put(
          `/recommendations/${this.recommend.id}`,
          {
            ...this.recommend,
            approved: true,
          }
        );
        console.log("过审成功", response.data);
        this.$router.push("/admin/recommend-review");
      } catch (error) {
        console.error("审核通过失败", error);
      }
    },
    async rejectRecommend() {
      try {
        await axios.delete(`/recommendations/${this.recommend.id}`);
        console.log("内推已删除");
        this.$router.push("/admin/recommend-review");
      } catch (error) {
        console.error("审核不通过失败", error);
      }
    },
    setMenu() {
      this.$store.dispatch("setActiveSubMenu", "recommend");
    },
  },
  created() {
    this.fetchRecommend();
    this.setMenu();
  },
};
</script>

<style scoped>
.admin-recommend-detail {
  padding: 20px;
  background: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  max-width: 800px;
  margin: 0 auto;
}

.admin-recommend-detail h1 {
  margin-top: 0;
}

.admin-recommend-detail p {
  margin: 10px 0;
}

.admin-recommend-detail .content {
  margin-top: 20px;
  padding: 20px;
  background: white;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.review-actions {
  margin-top: 20px;
  display: flex;
  justify-content: space-between;
}

.action-button {
  padding: 10px 20px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  text-align: center;
  text-decoration: none;
  flex: 1;
  margin: 0 5px;
}

.action-button:hover {
  background-color: #358a66;
}

.approve-button {
  background-color: #007bff;
}

.approve-button:hover {
  background-color: #0056b3;
}

.reject-button {
  background-color: #e74c3c;
}

.reject-button:hover {
  background-color: #c0392b;
}
</style>
