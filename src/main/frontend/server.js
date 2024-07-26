const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');
const fs = require('fs');
const path = require('path');
const app = express();
const port = 3000;

// 使用 cors 中间件
app.use(cors());

// 解析 application/json
app.use(bodyParser.json());

// 读取 JSON 文件
const readJsonFile = (filePath) => {
  return JSON.parse(fs.readFileSync(path.resolve(__dirname, filePath), 'utf-8'));
};

// 写入 JSON 文件
const writeJsonFile = (filePath, data) => {
  fs.writeFileSync(path.resolve(__dirname, filePath), JSON.stringify(data, null, 2), 'utf-8');
};

// 获取所有博文
app.get('/blogs', (req, res) => {
  const blogs = readJsonFile('./data/blogs.json');
  res.json(blogs);
});

// 获取单个博文
app.get('/blogs/:id', (req, res) => {
  const blogs = readJsonFile('./data/blogs.json');
  const blog = blogs.find(b => b.id === parseInt(req.params.id));
  if (blog) {
    res.json(blog);
  } else {
    res.status(404).json({ message: '博文未找到' });
  }
});

// 发布新博文
app.post('/blogs', (req, res) => {
  const blogs = readJsonFile('./data/blogs.json');
  const newBlog = {
    id: blogs.length ? blogs[blogs.length - 1].id + 1 : 1,
    title: req.body.title,
    author: req.body.author,
    publishedAt: new Date().toISOString().split('T')[0],
    content: req.body.content,
    isRecommended: false,
    status: 'pending'
  };
  blogs.push(newBlog);
  writeJsonFile('./data/blogs.json', blogs);
  res.json(newBlog);
});

// 修改博文
app.put('/blogs/:id', (req, res) => {
  const blogs = readJsonFile('./data/blogs.json');
  const blogIndex = blogs.findIndex(b => b.id === parseInt(req.params.id));
  if (blogIndex !== -1) {
    // 仅更新传递的属性
    const updatedBlog = {
      ...blogs[blogIndex],
      ...req.body
    };
    blogs[blogIndex] = updatedBlog;
    writeJsonFile('./data/blogs.json', blogs);
    res.json(blogs[blogIndex]);
  } else {
    res.status(404).json({ message: '博文未找到' });
  }
});

// 删除博文
app.delete('/blogs/:id', (req, res) => {
  const blogs = readJsonFile('./data/blogs.json');
  const newBlogs = blogs.filter(b => b.id !== parseInt(req.params.id));
  if (newBlogs.length !== blogs.length) {
    writeJsonFile('./data/blogs.json', newBlogs);
    res.json({ message: '博文删除成功' });
  } else {
    res.status(404).json({ message: '博文未找到' });
  }
});

// 获取所有评论
app.get('/comments', (req, res) => {
  const comments = readJsonFile('./data/comments.json');
  res.json(comments);
});

// 获取单个评论
app.get('/comments/:id', (req, res) => {
  const comments = readJsonFile('./data/comments.json');
  const comment = comments.find(c => c.id === parseInt(req.params.id));
  if (comment) {
    res.json(comment);
  } else {
    res.status(404).json({ message: '评论未找到' });
  }
});

// 获取单个博文的评论
app.get('/blogs/:id/comments', (req, res) => {
  const comments = readJsonFile('./data/comments.json');
  const blogComments = comments.filter(c => c.blogId === parseInt(req.params.id));
  res.json(blogComments);
});


// 发布新评论
app.post('/comments', (req, res) => {
  const comments = readJsonFile('./data/comments.json');
  const newComment = {
    id: comments.length ? comments[comments.length - 1].id + 1 : 1,
    author: req.body.author,
    blogId: req.body.blogId,
    content: req.body.content,
    isReported: false
  };
  comments.push(newComment);
  writeJsonFile('./data/comments.json', comments);
  res.json(newComment);
});

// 修改评论
app.put('/comments/:id', (req, res) => {
  const comments = readJsonFile('./data/comments.json');
  const commentIndex = comments.findIndex(c => c.id === parseInt(req.params.id));
  if (commentIndex !== -1) {
    comments[commentIndex] = {
      ...comments[commentIndex],
      ...req.body
    };
    writeJsonFile('./data/comments.json', comments);
    res.json(comments[commentIndex]);
  } else {
    res.status(404).json({ message: '评论未找到' });
  }
});

// 删除评论
app.delete('/comments/:id', (req, res) => {
  const comments = readJsonFile('./data/comments.json');
  const newComments = comments.filter(c => c.id !== parseInt(req.params.id));
  if (newComments.length !== comments.length) {
    writeJsonFile('./data/comments.json', newComments);
    res.json({ message: '评论删除成功' });
  } else {
    res.status(404).json({ message: '评论未找到' });
  }
});

// 举报评论
app.put('/comments/:id/report', (req, res) => {
  const comments = readJsonFile('./data/comments.json');
  const commentIndex = comments.findIndex(c => c.id === parseInt(req.params.id));
  if (commentIndex !== -1) {
    comments[commentIndex].isReported = true;
    writeJsonFile('./data/comments.json', comments);
    res.json({ success: true });
  } else {
    res.status(404).json({ message: '评论未找到' });
  }
});


// 获取所有内推
app.get('/recommendations', (req, res) => {
  const recommendations = readJsonFile('./data/recommendations.json');
  res.json(recommendations);
});

// 获取单个内推
app.get('/recommendations/:id', (req, res) => {
  const recommendations = readJsonFile('./data/recommendations.json');
  const recommendation = recommendations.find(r => r.id === parseInt(req.params.id));
  if (recommendation) {
    res.json(recommendation);
  } else {
    res.status(404).json({ message: '内推未找到' });
  }
});

// 发布新内推
app.post('/recommendations', (req, res) => {
  const recommendations = readJsonFile('./data/recommendations.json');
  const newRecommendation = {
    id: recommendations.length ? recommendations[recommendations.length - 1].id + 1 : 1,
    companyName: req.body.companyName,
    city: req.body.city,
    salaryRange: req.body.salaryRange,
    author: req.body.author,
    summary: req.body.summary,
    content: req.body.content,
    approved: false
  };
  recommendations.push(newRecommendation);
  writeJsonFile('./data/recommendations.json', recommendations);
  res.json(newRecommendation);
});

// 修改内推
app.put('/recommendations/:id', (req, res) => {
  const recommendations = readJsonFile('./data/recommendations.json');
  const recommendIndex = recommendations.findIndex(r => r.id === parseInt(req.params.id));
  if (recommendIndex !== -1) {
    const updatedRecommend = {
      ...recommendations[recommendIndex],
      ...req.body
    };
    recommendations[recommendIndex] = updatedRecommend;
    writeJsonFile('./data/recommendations.json', recommendations);
    res.json(recommendations[recommendIndex]);
  } else {
    res.status(404).json({ message: '内推未找到' });
  }
});

// 删除内推
app.delete('/recommendations/:id', (req, res) => {
  const recommendations = readJsonFile('./data/recommendations.json');
  const newRecommendations = recommendations.filter(r => r.id !== parseInt(req.params.id));
  if (newRecommendations.length !== recommendations.length) {
    writeJsonFile('./data/recommendations.json', newRecommendations);
    res.json({ message: '内推删除成功' });
  } else {
    res.status(404).json({ message: '内推未找到' });
  }
});


// 获取用户收藏的内推
app.get('/favorites/:userId', (req, res) => {
  const favorites = readJsonFile('./data/favorites.json');
  const userFavorites = favorites[req.params.userId] || [];
  res.json(userFavorites);
});

// 更新用户收藏的内推
app.put('/favorites/:userId', (req, res) => {
  const favorites = readJsonFile('./data/favorites.json');
  favorites[req.params.userId] = req.body;
  writeJsonFile('./data/favorites.json', favorites);
  res.json({ message: '收藏更新成功' });
});

// 检查用户名是否已存在
app.get('/check-username', (req, res) => {
  const users = readJsonFile('./data/users.json');
  const userExists = users.some(u => u.username === req.query.username);
  res.json({ available: !userExists });
});

// 用户登录
app.post('/login', (req, res) => {
  const users = readJsonFile('./data/users.json');
  const user = users.find(u => u.username === req.body.username && u.password === req.body.password);
  if (user) {
    res.json({
      success: true,
      nickname: user.nickname,
      role: user.role,
      id: user.username // 返回用户 ID
    });
  } else {
    res.json({
      success: false,
      message: '用户名或密码错误'
    });
  }
});


// 用户注册
app.post('/register', (req, res) => {
  const users = readJsonFile('./data/users.json');
  const userExists = users.some(u => u.username === req.body.username);
  if (userExists) {
    res.json({
      success: false,
      message: '用户名已存在'
    });
  } else {
    const newUser = {
      username: req.body.username,
      password: req.body.password,
      nickname: req.body.nickname,
      role: req.body.role
    };
    users.push(newUser);
    writeJsonFile('./data/users.json', users);
    res.json({
      success: true,
      message: '注册成功'
    });
  }
});

// 获取所有活动
app.get('/activities', (req, res) => {
  const activities = readJsonFile('./data/activities.json');
  res.json(activities);
});

// 获取单个活动
app.get('/activities/:id', (req, res) => {
  const activities = readJsonFile('./data/activities.json');
  const activity = activities.find(a => a.id === parseInt(req.params.id));
  if (activity) {
    res.json(activity);
  } else {
    res.status(404).json({ message: '活动未找到' });
  }
});

// 发布新活动
app.post('/activities', (req, res) => {
  const activities = readJsonFile('./data/activities.json');
  const newActivity = req.body;
  activities.push(newActivity);
  writeJsonFile('./data/activities.json', activities);
  res.json(newActivity);
});

// 修改活动
app.put('/activities/:id', (req, res) => {
  const activities = readJsonFile('./data/activities.json');
  const activityIndex = activities.findIndex(a => a.id === parseInt(req.params.id));
  if (activityIndex !== -1) {
    const updatedActivity = {
      ...activities[activityIndex],
      ...req.body
    };
    activities[activityIndex] = updatedActivity;
    writeJsonFile('./data/activities.json', activities);
    res.json(activities[activityIndex]);
  } else {
    res.status(404).json({ message: '活动未找到' });
  }
});
// 删除活动
app.delete('/activities/:id', (req, res) => {
  const activities = readJsonFile('./data/activities.json');
  const newActivities = activities.filter(a => a.id !== parseInt(req.params.id));
  if (newActivities.length !== activities.length) {
    writeJsonFile('./data/activities.json', newActivities);
    res.json({ message: '活动删除成功' });
  } else {
    res.status(404).json({ message: '活动未找到' });
  }
});

app.listen(port, () => {
  console.log(`Mock server running at http://localhost:${port}`);
});
