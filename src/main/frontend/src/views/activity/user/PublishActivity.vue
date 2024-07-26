<template>
  <div class="publish-activity">
    <h1>发布活动</h1>
    <form @submit.prevent="submitActivity">
      <div class="form-group">
        <label for="name">活动名称</label>
        <input type="text" id="name" v-model="name" required />
      </div>
      <div class="form-group">
        <label for="time">时间</label>
        <input type="datetime-local" id="time" v-model="time" required />
      </div>
      <div class="form-group">
        <label for="location">地点</label>
        <input type="text" id="location" v-model="location" required />
      </div>
      <div class="form-group">
        <label for="maxParticipants">最多参与人数</label>
        <input
          type="number"
          id="maxParticipants"
          v-model.number="maxParticipants"
          min="3"
          required
        />
      </div>
      <div class="form-group">
        <label for="description">简介</label>
        <textarea
          id="description"
          v-model="description"
          placeholder="请在此输入活动简介..."
          required
        ></textarea>
      </div>
      <button type="submit">发布</button>
    </form>
    <div v-if="showSuccess" class="report-success">
      <div class="report-success-box">
        <p>活动发布成功</p>
        <button @click="closeSuccess" class="confirm-button">确定</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "@/utils/axios";

export default {
  name: "PublishActivity",
  data() {
    return {
      name: "",
      time: "",
      location: "",
      maxParticipants: 3,
      description: "",
      user: null,
      showSuccess: false,
    };
  },
  methods: {
    async submitActivity() {
      const user = JSON.parse(localStorage.getItem("user"));
      if (!user) {
        this.error = "用户信息错误，请重新登录";
        return;
      }

      const activity = {
        id: Date.now(),
        name: this.name,
        initiator: user.nickname,
        time: [
          parseInt(this.year),
          parseInt(this.month),
          parseInt(this.day),
          parseInt(this.hour),
          parseInt(this.minute),
        ],
        location: this.location,
        description: this.description,
        maxParticipants: parseInt(this.maxParticipants),
        currentParticipants: 1,
        participantList: [user.nickname],
        status: "审核中",
      };

      try {
        await axios.post("/activities", activity);
        this.$router.push("/activity-preparation");
      } catch (error) {
        this.error = "发布活动失败，请稍后再试";
      }
    },
    closeSuccess() {
      this.showSuccess = false;
      this.$router.push("/activity-hall");
    },
    setMenu() {
      this.$store.dispatch("setActiveSubMenu", "activity");
    },
  },
  created() {
    const userData = localStorage.getItem("user");
    if (userData) {
      this.user = JSON.parse(userData);
    }
    this.setMenu();
  },
  watch: {
    $route() {
      this.setMenu();
    },
  },
};
</script>

<style scoped>
.publish-activity {
  padding: 20px;
  background: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  max-width: 800px;
  margin: 0 auto;
}

.publish-activity h1 {
  text-align: center;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
}

.form-group input,
.form-group textarea {
  width: 100%;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #ccc;
  box-sizing: border-box;
}

button {
  width: 100%;
  padding: 10px;
  background: #42b983;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  font-weight: bold;
  transition: background 0.3s;
}

button:hover {
  background: #358a66;
}

.report-success {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.8);
  color: white;
  display: flex;
  justify-content: center;
  align-items: center;
}

.report-success-box {
  background: #2c3e50;
  padding: 40px;
  border-radius: 30px;
  text-align: center;
  width: 400px;
}

.confirm-button {
  padding: 15px 30px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  margin-top: 20px;
  font-size: 16px;
}

.confirm-button:hover {
  background-color: #358a66;
}
</style>
