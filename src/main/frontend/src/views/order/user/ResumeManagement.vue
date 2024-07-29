<template>
  <div class="resume-management" v-if="resume">
    <h1>简历管理</h1>
    <form @submit.prevent="submitForm">
      <div class="form-group">
        <label for="user">用户名</label>
        <input type="text" id="user" v-model="resume.user" disabled />
      </div>
      <div class="form-group">
        <label for="content">自我介绍</label>
        <textarea id="content" v-model="resume.content"></textarea>
      </div>
      <div class="form-group">
        <label for="work_experience">工作经历</label>
        <select id="work_experience" v-model="resume.work_experience">
          <option value="学生">学生</option>
          <option value="已就业3年以内">已就业3年以内</option>
          <option value="已就业3年以上">已就业3年以上</option>
        </select>
      </div>
      <div class="form-group">
        <label for="education">学历</label>
        <select id="education" v-model="resume.education">
          <option value="高中或以下">高中或以下</option>
          <option value="大专">大专</option>
          <option value="本科">本科</option>
          <option value="硕士">硕士</option>
          <option value="博士">博士</option>
        </select>
      </div>
      <div class="form-group">
        <label>主要技能</label>
        <div class="skills">
          <div v-for="skill in availableSkills" :key="skill">
            <input
              type="checkbox"
              :id="skill"
              :value="skill"
              v-model="resume.skills"
            />
            <label :for="skill">{{ skill }}</label>
          </div>
        </div>
      </div>
      <div class="form-actions">
        <button type="submit" class="save-button">保存</button>
      </div>
    </form>
    <div v-if="showSuccessMessage" class="success-message">
      <div class="success-message-box">
        <p>简历更新成功</p>
        <button @click="closeSuccessMessage" class="confirm-button">
          确定
        </button>
      </div>
    </div>
  </div>
  <div v-else>
    <p>加载中...</p>
  </div>
</template>

<script>
import axios from "@/utils/axios";

export default {
  name: "ResumeManagement",
  data() {
    return {
      resume: null,
      availableSkills: [
        "前端",
        "后端",
        "软件设计",
        "平面设计",
        "网络管理",
        "数据分析",
        "数据库管理",
      ],
      showSuccessMessage: false,
    };
  },
  methods: {
    async fetchResume() {
      const user = JSON.parse(localStorage.getItem("user"));
      if (!user) {
        console.error("用户未登录");
        return;
      }
      try {
        const response = await axios.get(`/resumes`);
        this.resume = response.data.resumes.find(
          (r) => r.user === user.nickname
        );
      } catch (error) {
        console.error("获取简历失败", error);
      }
    },
    async submitForm() {
      try {
        await axios.put(`/resumes/${this.resume.resume_id}`, this.resume);
        this.showSuccessMessage = true;
      } catch (error) {
        console.error("更新简历失败", error);
      }
    },
    closeSuccessMessage() {
      this.showSuccessMessage = false;
    },
    setMenu() {
      this.$store.dispatch("setActiveSubMenu", "order");
    },
  },
  created() {
    this.fetchResume();
    this.setMenu();
  },
};
</script>

<style scoped>
.resume-management {
  padding: 20px;
  background: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  max-width: 800px;
  margin: 0 auto;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
}

.form-group input,
.form-group textarea,
.form-group select {
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
  border-radius: 5px;
  border: 1px solid #ccc;
}

.skills {
  display: flex;
  flex-wrap: wrap;
}

.skills div {
  margin-right: 10px;
  margin-bottom: 10px;
}

.form-actions {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}

.save-button {
  padding: 10px 20px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  text-decoration: none;
  text-align: center;
}

.save-button:hover {
  background-color: #358a66;
}

.success-message {
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

.success-message-box {
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
