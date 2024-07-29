DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS reports;
DROP TABLE IF EXISTS reviews;
DROP TABLE IF EXISTS navs;
DROP TABLE IF EXISTS recommendation;
DROP TABLE IF EXISTS recommendation_comments;
DROP TABLE IF EXISTS recommendation_reviews;
DROP TABLE IF EXISTS activities;
DROP TABLE IF EXISTS user_activities;
DROP TABLE IF EXISTS resumes;
DROP TABLE IF EXISTS resume_reviews;
DROP TABLE IF EXISTS drafts;
DROP TABLE IF EXISTS draft_reviews;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS order_reviews;
DROP TABLE IF EXISTS review_requests;
DROP TABLE IF EXISTS application_success_messages;
DROP TABLE IF EXISTS user_recommendation_favorites;



CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(30) NOT NULL,
    nickname VARCHAR(30) NOT NULL,
    password VARCHAR(30) NOT NULL,
    email VARCHAR(30) ,
    role VARCHAR(30) NOT NULL,
    created_time DATETIME NOT NULL,
    updated_time DATETIME NOT NULL
);

CREATE TABLE posts (
    post_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    author varchar(30) not null ,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    status VARCHAR(20) NOT NULL,
    isRecommended boolean not null ,
    created_time DATETIME NOT NULL,
    updated_time DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);


CREATE TABLE comments (
    comment_id INT AUTO_INCREMENT PRIMARY KEY,
    post_id INT NOT NULL,
    user_id INT NOT NULL,
    author varchar(30) not null,
    content TEXT NOT NULL,
    status VARCHAR(20) NOT NULL,
    isReported boolean not null,
    created_time DATETIME NOT NULL,
    updated_time DATETIME NOT NULL,
    FOREIGN KEY (post_id) REFERENCES posts(post_id) on delete cascade,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);


CREATE TABLE reports (
    report_id INT AUTO_INCREMENT PRIMARY KEY,
    comment_id INT NOT NULL,
    user_id INT NOT NULL,
    reason TEXT NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_time DATETIME NOT NULL,
    updated_time DATETIME NOT NULL,
    FOREIGN KEY (comment_id) REFERENCES comments(comment_id) on delete cascade,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);


CREATE TABLE reviews (
    review_id INT AUTO_INCREMENT PRIMARY KEY,
    post_id INT NOT NULL,
    user_id INT NOT NULL,
    result VARCHAR(20) NOT NULL,
    comment VARCHAR(50) NOT NULL,
    reviewed_time DATETIME NOT NULL,
    FOREIGN KEY (post_id) REFERENCES posts(post_id) on delete cascade,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);


CREATE TABLE navs (
    nav_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    nav_order INT NOT NULL,
    nav_name VARCHAR(30) NOT NULL,
    created_time DATETIME NOT NULL,
    updated_time DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);


CREATE TABLE recommendation (
    recommendation_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    companyName varchar(20) not null ,
    user_id INT  NOT NULL,
    author varchar(30) not null,
    title VARCHAR(200) ,
    content TEXT NOT NULL,
    abstract TINYTEXT ,
    summary varchar(20) not null,
    location VARCHAR(20) NOT NULL,
    status boolean NOT NULL,
    created_time DATETIME NOT NULL,
    publish_time DATETIME NOT NULL,
    max_salary int not null,
    min_salary int not null,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);


CREATE TABLE recommendation_comments (
    comment_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    recommendation_id INT UNSIGNED NOT NULL,
    user_id INT  NOT NULL,
    content TEXT NOT NULL,
    status VARCHAR(20) NOT NULL,
    comment_time DATETIME NOT NULL,
    FOREIGN KEY (recommendation_id) REFERENCES recommendation(recommendation_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);


CREATE TABLE recommendation_reviews (
    review_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    recommendation_id INT UNSIGNED NOT NULL,
    user_id INT  NOT NULL,
    result VARCHAR(20) NOT NULL,
    suggestion TINYTEXT NOT NULL,
    review_time DATETIME NOT NULL,
    FOREIGN KEY (recommendation_id) REFERENCES recommendation(recommendation_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);


CREATE TABLE activities (
    activity_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    activity_name VARCHAR(20) NOT NULL,
    initiator varchar(30) ,
    activity_type VARCHAR(20) ,
    activity_time DATETIME NOT NULL,
    activity_location VARCHAR(20) NOT NULL,
    activity_description TEXT NOT NULL,
    maxParticipants INT NOT NULL,
    currentParticipants int not null,
    status varchar(20) not null
);


CREATE TABLE user_activities (
     user_id INT  NOT NULL,
     /*nickname varchar(30),*/
     activity_id INT UNSIGNED NOT NULL,
     activity_status VARCHAR(20) ,
     PRIMARY KEY (user_id, activity_id),
     FOREIGN KEY (user_id) REFERENCES users(user_id),
     /*FOREIGN KEY (nickname) REFERENCES users(nickname),*/
     FOREIGN KEY (activity_id) REFERENCES activities(activity_id) on delete cascade
);


CREATE TABLE resumes (
    resume_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    user_id INT  NOT NULL,
    nickname_name varchar(30),
    content TEXT NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_time DATETIME NOT NULL,
    work_experience varchar(30),
    education varchar(30),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);


CREATE TABLE resume_reviews (
    review_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    resume_id INT UNSIGNED NOT NULL,
    user_id INT  NOT NULL,
    result VARCHAR(20) NOT NULL,
    comment VARCHAR(50) NOT NULL,
    reviewed_time DATETIME NOT NULL,
    FOREIGN KEY (resume_id) REFERENCES resumes(resume_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);


CREATE TABLE drafts (
    draft_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    user_id INT  NOT NULL,
    draft_content TEXT NOT NULL,
    draft_status VARCHAR(20) NOT NULL,
    created_time DATETIME NOT NULL,
    updated_time DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);


CREATE TABLE draft_reviews (
    review_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    draft_id INT UNSIGNED NOT NULL,
    user_id INT  NOT NULL,
    result VARCHAR(20) NOT NULL,
    comment VARCHAR(50) NOT NULL,
    reviewed_time DATETIME NOT NULL,
    FOREIGN KEY (draft_id) REFERENCES drafts(draft_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);


CREATE TABLE orders (
    orders_id INT  UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    user_id INT  NOT NULL,
    nickname varchar(20),
    orders_name VARCHAR(20) NOT NULL,
    orders_price int not null,
    orders_introduction TEXT NOT NULL,
    orders_status VARCHAR(20) NOT NULL,
    created_time DATETIME NOT NULL,
    successful_bidder varchar(20),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);


CREATE TABLE order_reviews (
    review_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    orders_id INT UNSIGNED NOT NULL,
    user_id INT  NOT NULL,
    result VARCHAR(20) NOT NULL,
    comment VARCHAR(50) NOT NULL,
    reviewed_time DATETIME NOT NULL,
    FOREIGN KEY (orders_id) REFERENCES orders(orders_id) on delete cascade ,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);


CREATE TABLE review_requests (
    review_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    user1_id INT  NOT NULL,
    user2_id INT  NOT NULL,
    result VARCHAR(20) NOT NULL,
    reviewed_time DATETIME NOT NULL,
    FOREIGN KEY (user1_id) REFERENCES users(user_id),
    FOREIGN KEY (user2_id) REFERENCES users(user_id)
);


CREATE TABLE application_success_messages (
    message_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    review_id INT UNSIGNED NOT NULL,
    message_content TEXT NOT NULL,
    created_time DATETIME NOT NULL,
    FOREIGN KEY (review_id) REFERENCES review_requests(review_id)
);



/*用户收藏内推所用的中间表*/
CREATE TABLE user_recommendation_favorites (
  user_id INT NOT NULL,
  recommendation_id INT UNSIGNED NOT NULL,
  PRIMARY KEY (user_id, recommendation_id),
  FOREIGN KEY (user_id) REFERENCES users(user_id),
  FOREIGN KEY (recommendation_id) REFERENCES recommendation(recommendation_id)
);


/*订单竞标的中间表,多对多*/
CREATE TABLE user_order(
    user_id int not null,
    orders_id int UNSIGNED not null,
    PRIMARY KEY(user_id,orders_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (orders_id) REFERENCES orders(orders_id) on delete cascade
);


/*简历中需要的技能表*/
CREATE TABLE skill(
    skill_id int AUTO_INCREMENT PRIMARY KEY,
    skill_name varchar(30)
);


/*用户与技能的中间表*/
CREATE TABLE user_skill(
    user_id int ,
    skill_id int ,
    PRIMARY KEY (user_id,skill_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (skill_id) REFERENCES skill(skill_id)
);