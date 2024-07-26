<template>
  <div class="publish-recommend">
    <h1>发布内推</h1>
    <form @submit.prevent="submitRecommend">
      <div class="form-group">
        <label for="company">公司</label>
        <input type="text" id="company" v-model="company" required />
      </div>
      <div class="form-group">
        <label for="city">城市</label>
        <input type="text" id="city" v-model="city" required />
      </div>
      <div class="form-group">
        <label for="job">职务</label>
        <input type="text" id="job" v-model="job" required />
      </div>
      <div class="form-group salary-group">
        <label for="salaryMin">起薪范围</label>
        <input type="number" id="salaryMin" v-model="salaryMin" required />
        <span>-</span>
        <input type="number" id="salaryMax" v-model="salaryMax" required />
      </div>
      <div class="form-group">
        <label for="content">正文</label>
        <textarea
          id="content"
          v-model="content"
          placeholder="请在此输入正文..."
          required
        ></textarea>
      </div>
      <button type="submit">发布</button>
    </form>
  </div>
</template>

<script>
import axios from "@/utils/axios";

export default {
  name: "PublishRecommendView",
  data() {
    return {
      company: "",
      city: "",
      job: "",
      salaryMin: "",
      salaryMax: "",
      content: "",
      loading: false,
      error: null,
      recommendId: null,
    };
  },
  methods: {
    async submitRecommend() {
      if (this.loading) return;
      this.loading = true;
      this.error = null;

      try {
        const newRecommend = {
          companyName: this.company,
          city: this.city,
          summary: this.job,
          salaryRange: {
            min: this.salaryMin,
            max: this.salaryMax,
          },
          author: JSON.parse(localStorage.getItem("user")).nickname,
          content: this.content,
        };

        if (this.recommendId) {
          await axios.put(`/recommendations/${this.recommendId}`, newRecommend);
        } else {
          await axios.post("/recommendations", newRecommend);
        }

        this.$router.push("/recommend-list");
      } catch (error) {
        this.error = "发布失败，请稍后再试";
        console.error("发布内推失败", error);
      } finally {
        this.loading = false;
      }
    },
  },
  mounted() {
    this.$store.dispatch("setActiveSubMenu", "recommend");
    const recommend = this.$route.query;
    console.log("路由参数：", recommend); // 调试输出
    if (recommend && recommend.id) {
      this.recommendId = recommend.id;
      this.company = recommend.companyName;
      this.city = recommend.city;
      this.job = recommend.summary;
      if (recommend.salaryRange) {
        const salaryRange = JSON.parse(recommend.salaryRange);
        this.salaryMin = salaryRange.min;
        this.salaryMax = salaryRange.max;
      }
      this.content = recommend.content;
      console.log(
        "预填充表单数据：",
        this.company,
        this.city,
        this.job,
        this.salaryMin,
        this.salaryMax,
        this.content
      ); // 调试输出
    }
  },
};
</script>

<style scoped>
.publish-recommend {
  padding: 20px;
  background: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  max-width: 600px;
  margin: 0 auto;
  background: linear-gradient(145deg, #ffffff, #e6e6e6);
}

h1 {
  margin-bottom: 20px;
  text-align: center;
  font-size: 24px;
  font-weight: bold;
  color: #333;
}

.form-group {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
}

.form-group label {
  width: 80px;
  font-size: 14px;
  color: #333;
}

.form-group input,
.form-group textarea {
  flex: 1;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #ccc;
  font-size: 14px;
}

.form-group span {
  margin: 0 10px;
  color: #333;
}

.salary-group input {
  width: calc(50% - 15px); /* 使两个输入框之间有一定的间距 */
}

textarea::placeholder {
  color: #999;
}

button {
  width: 100%;
  padding: 10px;
  background: #42b983;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-top: 20px;
  font-size: 16px;
  font-weight: bold;
  transition: background 0.3s;
}

button:hover {
  background: #358a66;
}

@media (max-width: 600px) {
  .publish-recommend {
    padding: 10px;
  }

  .form-group label {
    width: auto;
    margin-right: 10px;
  }
}
</style>
