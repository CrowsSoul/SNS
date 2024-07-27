DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS reports;
DROP TABLE IF EXISTS reviews;
DROP TABLE IF EXISTS navs;
DROP TABLE IF EXISTS pushinfor;
DROP TABLE IF EXISTS pushinfor_comments;
DROP TABLE IF EXISTS pushinfor_reviews;
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




CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(10) NOT NULL,
    nickname VARCHAR(10) NOT NULL,
    password VARCHAR(15) NOT NULL,
    email VARCHAR(30) ,
    role VARCHAR(10) NOT NULL,
    created_time DATETIME NOT NULL,
    updated_time DATETIME NOT NULL
);

CREATE TABLE posts (
    post_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    author varchar(10) not null ,
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
    author varchar(10) not null,
    content TEXT NOT NULL,
    status VARCHAR(20) NOT NULL,
    isReported boolean not null,
    created_time DATETIME NOT NULL,
    updated_time DATETIME NOT NULL,
    FOREIGN KEY (post_id) REFERENCES posts(post_id),
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
    FOREIGN KEY (comment_id) REFERENCES comments(comment_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);


CREATE TABLE reviews (
    review_id INT AUTO_INCREMENT PRIMARY KEY,
    post_id INT NOT NULL,
    user_id INT NOT NULL,
    result VARCHAR(20) NOT NULL,
    comment VARCHAR(50) NOT NULL,
    reviewed_time DATETIME NOT NULL,
    FOREIGN KEY (post_id) REFERENCES posts(post_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);


CREATE TABLE navs (
    nav_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    nav_order INT NOT NULL,
    nav_name VARCHAR(10) NOT NULL,
    created_time DATETIME NOT NULL,
    updated_time DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);


CREATE TABLE pushinfor (
    pushinfor_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    companyName varchar(20) not null ,
    user_id INT  NOT NULL,
    author varchar(10) not null,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    abstract TINYTEXT NOT NULL,
    summary varchar(20) not null,
    location VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_time DATETIME NOT NULL,
    publish_time DATETIME NOT NULL,
    max_salary int not null,
    min_salary int not null,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);


CREATE TABLE pushinfor_comments (
    comment_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    pushinfor_id INT UNSIGNED NOT NULL,
    user_id INT  NOT NULL,
    content TEXT NOT NULL,
    status VARCHAR(20) NOT NULL,
    comment_time DATETIME NOT NULL,
    FOREIGN KEY (pushinfor_id) REFERENCES pushinfor(pushinfor_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);


CREATE TABLE pushinfor_reviews (
    review_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    pushinfor_id INT UNSIGNED NOT NULL,
    user_id INT  NOT NULL,
    result VARCHAR(20) NOT NULL,
    suggestion TINYTEXT NOT NULL,
    review_time DATETIME NOT NULL,
    FOREIGN KEY (pushinfor_id) REFERENCES pushinfor(pushinfor_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);


CREATE TABLE activities (
    activity_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    activity_name VARCHAR(20) NOT NULL,
    activity_type VARCHAR(20) NOT NULL,
    activity_time DATETIME NOT NULL,
    activity_address VARCHAR(20) NOT NULL,
    activity_content TEXT NOT NULL,
    activity_amount INT NOT NULL
);


CREATE TABLE user_activities (
    user_id INT  NOT NULL,
    activity_id INT UNSIGNED NOT NULL,
    activity_status VARCHAR(20) NOT NULL,
    PRIMARY KEY (user_id, activity_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (activity_id) REFERENCES activities(activity_id)
);


CREATE TABLE resumes (
    resume_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    user_id INT  NOT NULL,
    content TEXT NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_time DATETIME NOT NULL,
    updated_time DATETIME NOT NULL,
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
    orders_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    user_id INT  NOT NULL,
    orders_name VARCHAR(20) NOT NULL,
    orders_introduction TEXT NOT NULL,
    orders_status VARCHAR(20) NOT NULL,
    created_time DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);


CREATE TABLE order_reviews (
    review_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    orders_id INT UNSIGNED NOT NULL,
    user_id INT  NOT NULL,
    result VARCHAR(20) NOT NULL,
    comment VARCHAR(50) NOT NULL,
    reviewed_time DATETIME NOT NULL,
    FOREIGN KEY (orders_id) REFERENCES orders(orders_id),
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

