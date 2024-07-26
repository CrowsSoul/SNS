<template>
  <div
    class="login-page"
    :style="{
      backgroundImage: 'url(' + require('@/assets/background.jpg') + ')',
    }"
  >
    <div class="login-container">
      <h2>登录</h2>
      <form @submit.prevent="login">
        <div class="form-group">
          <label for="username">用户名</label>
          <input type="text" id="username" v-model="username" required />
        </div>
        <div class="form-group">
          <label for="password">密码</label>
          <input type="password" id="password" v-model="password" required />
        </div>
        <div v-if="error" class="error">{{ error }}</div>
        <button type="submit">登录</button>
      </form>
      <p>没有账号？ <router-link to="/register">注册</router-link></p>
    </div>
  </div>
</template>

<script>
import axios from "@/utils/axios";

export default {
  name: "LoginView",
  data() {
    return {
      username: "",
      password: "",
      error: null,
      loading: false,
    };
  },
  methods: {
    async login() {
      if (this.loading) return;
      this.loading = true;
      this.error = null;

      try {
        const response = await axios.post("/login", {
          username: this.username,
          password: this.password,
        });

        if (response.data.success) {
          // 保存用户信息到本地存储
          const userData = {
            username: this.username, // 将用户名存储在本地存储中
            nickname: response.data.nickname,
            role: response.data.role,
          };
          console.log("Login successful, user data:", userData);
          localStorage.setItem("user", JSON.stringify(userData));
          // 等待用户信息刷新，然后导航到主页
          setTimeout(() => {
            this.$router.push("/");
          }, 100); // 100 毫秒的延迟
        } else {
          this.error = response.data.message;
        }
      } catch (error) {
        this.error = "登录失败，请稍后再试";
      } finally {
        this.loading = false;
      }
    },
  },
};
</script>

<style scoped>
.login-page {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-size: cover;
  background-position: center;
  text-align: center; /* 新增 */
}

.login-container {
  background: rgba(255, 255, 255, 0.8);
  padding: 20px;
  border-radius: 30px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  width: 400px;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
}

.form-group input {
  width: 70%;
  padding: 8px;
  box-sizing: border-box;
  border-radius: 20px;
  border: 1px solid #ccc;
}

button {
  width: 30%;
  padding: 10px;
  background: #42b983;
  color: white;
  border: none;
  border-radius: 20px;
  cursor: pointer;
  margin: 20px;
}

button:hover {
  background: #358a66;
}

.error {
  color: red;
  margin-bottom: 10px;
}
</style>
