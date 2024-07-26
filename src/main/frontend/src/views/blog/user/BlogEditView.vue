<template>
  <div class="blog-edit">
    <h1>{{ isEditing ? "编辑博文" : "发布博文" }}</h1>
    <form @submit.prevent="submitForm">
      <div class="form-group">
        <label for="title">标题</label>
        <input type="text" id="title" v-model="title" required />
      </div>
      <div class="form-group">
        <label for="content">内容</label>
        <textarea id="content" v-model="content" required></textarea>
      </div>
      <div class="form-actions">
        <button type="submit" class="publish-button">
          {{ isEditing ? "保存" : "发布" }}
        </button>
        <router-link :to="getCancelLink()" class="action-button"
          >取消</router-link
        >
      </div>
    </form>
  </div>
</template>

<script>
import axios from "@/utils/axios";

export default {
  name: "BlogEditView",
  data() {
    return {
      title: "",
      content: "",
      isEditing: false,
      blogId: null,
      currentPage: 1,
    };
  },
  methods: {
    async fetchBlog() {
      const blogId = this.$route.params.id;
      if (blogId) {
        try {
          const response = await axios.get(`/blogs/${blogId}`);
          const blog = response.data;
          this.title = blog.title;
          this.content = blog.content;
          this.isEditing = true;
          this.blogId = blogId;
        } catch (error) {
          console.error("获取博文详情失败", error);
        }
      }
      if (this.$route.query.page) {
        this.currentPage = parseInt(this.$route.query.page);
      }
    },
    async submitForm() {
      const user = JSON.parse(localStorage.getItem("user"));
      if (!user) {
        console.error("用户未登录");
        return;
      }
      const blogData = {
        title: this.title,
        content: this.content,
        author: user.nickname,
        status: "pending",
      };
      try {
        if (this.isEditing) {
          await axios.put(`/blogs/${this.blogId}`, blogData);
        } else {
          await axios.post("/blogs", blogData);
        }
        this.$router.push({
          path: "/my-blogs",
          query: { page: this.currentPage },
        });
      } catch (error) {
        console.error("提交博文失败", error);
      }
    },
    resetForm() {
      this.title = "";
      this.content = "";
      this.isEditing = false;
      this.blogId = null;
    },
    getCancelLink() {
      return { path: "/my-blogs", query: { page: this.currentPage } };
    },
  },
  created() {
    this.fetchBlog();
  },
  watch: {
    $route(to, from) {
      if (to.query.reset) {
        this.resetForm();
      } else {
        this.fetchBlog();
      }
    },
  },
};
</script>

<style scoped>
.blog-edit {
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
.form-group textarea {
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
  border-radius: 5px;
  border: 1px solid #ccc;
}

.form-actions {
  margin-top: 20px;
  display: flex;
  gap: 10px;
}

.action-button {
  padding: 10px 20px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  text-decoration: none;
  text-align: center;
}

.action-button:hover {
  background-color: #358a66;
}

.publish-button {
  padding: 10px 20px;
  background-color: #f0ad4e;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  text-decoration: none;
  text-align: center;
}

.publish-button:hover {
  background-color: #ec971f;
}
</style>
