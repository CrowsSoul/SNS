<template>
  <div
    class="register-page"
    :style="{
      backgroundImage: 'url(' + require('@/assets/background.jpg') + ')',
    }"
  >
    <div class="register-container">
      <h2>注册</h2>
      <form @submit.prevent="register">
        <div class="form-group">
          <label for="username">用户名</label>
          <input type="text" id="username" v-model="username" required />
        </div>
        <div class="form-group">
          <label for="password">密码</label>
          <input type="password" id="password" v-model="password" required />
        </div>
        <div class="form-group">
          <label for="confirmPassword">确认密码</label>
          <input
            type="password"
            id="confirmPassword"
            v-model="confirmPassword"
            required
          />
        </div>
        <div class="form-group">
          <label for="nickname">昵称</label>
          <input type="text" id="nickname" v-model="nickname" required />
        </div>
        <div class="form-group">
          <label for="role">身份</label>
          <select id="role" v-model="role" required>
            <option value="student">在校学生</option>
            <option value="employed_student">已就业学生</option>
            <option value="admin">管理员</option>
          </select>
        </div>
        <div v-if="error" class="error">{{ error }}</div>
        <button type="submit">注册</button>
      </form>
      <p>已有账号？ <router-link to="/login">登录</router-link></p>
    </div>
  </div>
</template>

<script>
import axios from "@/utils/axios";

export default {
  name: "RegisterView",
  data() {
    return {
      username: "",
      password: "",
      confirmPassword: "",
      nickname: "",
      role: "",
      error: null,
    };
  },
  methods: {
    validatePassword() {
      const hasUpperCase = /[A-Z]/.test(this.password);
      const hasLowerCase = /[a-z]/.test(this.password);
      const hasNumber = /[0-9]/.test(this.password);
      const LongEnough = this.password.length >= 6;

      if (!LongEnough) {
        return "密码长度至少为6位";
      }

      if (!hasUpperCase) {
        return "密码需要包含至少一个大写字母";
      }

      if (!hasLowerCase) {
        return "密码需要包含至少一个小写字母";
      }

      if (!hasNumber) {
        return "密码需要包含至少一个数字";
      }

      if (this.password !== this.confirmPassword) {
        return "两次输入的密码不同";
      }

      return null;
    },
    async register() {
      if (!this.username) {
        this.error = "用户名不能为空";
        return;
      }
      if (!this.password) {
        this.error = "密码不能为空";
        return;
      }

      const passwordError = this.validatePassword();
      if (passwordError) {
        this.error = passwordError;
        return;
      }

      if (!this.nickname) {
        this.error = "昵称不能为空";
        return;
      }
      if (!this.role) {
        this.error = "请选择身份";
        return;
      }

      try {
        // 检查用户名是否已存在
        const checkUsernameResponse = await axios.get("/check-username", {
          params: {
            username: this.username,
          },
        });

        if (!checkUsernameResponse.data.available) {
          this.error = "用户名已被注册";
          return;
        }

        const response = await axios.post("/register", {
          username: this.username,
          password: this.password,
          nickname: this.nickname,
          role: this.role,
        });

        if (response.data.success) {
          this.$router.push("/login");
        } else {
          this.error = response.data.message;
        }
      } catch (error) {
        this.error = "注册失败，请稍后再试";
      }
    },
  },
};
</script>

<style scoped>
.register-page {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-size: cover;
  background-position: center;
  text-align: center;
}

.register-container {
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

.form-group input,
.form-group select {
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
