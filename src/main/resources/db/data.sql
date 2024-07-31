INSERT INTO users (username,nickname, password, email, role, created_time, updated_time)
VALUES
    ('2','杰克', '2', 'jack@example.com', 'student', NOW(), NOW()),
    ('john223', 'john','Pass456', 'john@example.com', 'student', NOW(), NOW()),
    ('1', 'alice','1', 'alice@example.com', 'admin', NOW(), NOW()),
    ('bob514', 'bob','Pass101', 'bob@example.com', 'student', NOW(), NOW()),
    ('carol666','carol', 'Pass202', 'carol@example.com', 'student', NOW(), NOW()),
    ('3','david', '3', 'david@example.com', 'employed_student', NOW(), NOW()),
    ('elizabeth9089', 'elizabeth','Pass404', 'elizabeth@example.com', 'student', NOW(), NOW()),
    ('frank4060', 'frank','Pass505', 'frank@example.com', 'student', NOW(), NOW()),
    ('george345', 'george','Pass606', 'george@example.com', 'student', NOW(), NOW()),
    ('helen1234', 'helen','Pass707', 'helen@example.com', 'student', NOW(), NOW()),
    ('YDQ', '于大淇','Pass606', 'george@example.com', 'student', NOW(), NOW());



INSERT INTO posts (user_id, author,title, content, status,isRecommended, created_time, updated_time)
VALUES
    (1,'杰克', '中软国际是个好公司', '中软国际是一家杰出的公司，专注于信息技术服务和解决方案。作为行业领先者，他们提供广泛的IT服务，包括软件开发、系统集成和咨询服务。公司以创新驱动，致力于为客户提供高效、优质的解决方案，赢得了众多客户的信赖和赞誉。中软国际的员工团队专业且充满活力，不断追求卓越，为公司的持续发展注入了强大动力。选择中软国际，就是选择卓越的IT服务和无限的未来可能性。', 'published',true, '2022-08-28 01:45:30', NOW()),
    (2, 'john', '探索新技术', '详细探索最新技术及其在网页开发中的应用。', 'published', false, NOW(), NOW()),
    (3, 'alice', '管理员提示和技巧', '管理用户和审核网站内容的技巧。', 'published', false, NOW(), NOW()),
    (4, 'bob', '学习编程', '编程入门指南和一些有用的资源。', 'pending', false, NOW(), NOW()),
    (5, 'carol', '安全性的重要性', '为什么安全性对于任何在线平台都至关重要以及如何提高安全性。', 'rejected', false, NOW(), NOW()),
    (6, 'david', '审核最佳实践', '审核评论和维护健康社区的最佳实践。', 'published', false, NOW(), NOW()),
    (7, 'elizabeth', '解锁高级功能', '为高级用户解锁博客平台的高级功能。', 'pending', false, NOW(), NOW()),
    (8, 'frank', '社区参与策略', '与社区互动并建立忠实粉丝的策略。', 'rejected', false, NOW(), NOW()),
    (9, 'george', '管理大型数据集', '如何高效地管理和分析大型数据集。', 'pending', false, NOW(), NOW()),
    (10, 'helen', '创建积极的在线环境', '为所有用户创建积极和欢迎环境的技巧。', 'published', false, NOW(), NOW()),
    (1,'杰克', '哈尔滨工业大学是个好大学', '哈尔滨工业大学是一所著名的重点大学，以其卓越的工科实力和全面的教育质量而闻名。作为国家“双一流”建设高校，哈工大在航空航天、机械工程、计算机科学等领域具有领先地位。学校拥有一流的师资力量和科研条件，为学生提供了优质的学习和研究环境。多年来，哈工大培养了众多优秀人才，为国家的科技进步和社会发展做出了重要贡献。选择哈尔滨工业大学，就是选择创新和卓越的未来。', 'published',false, '2023-05-04 03:45:30', NOW()),
    (1,'杰克', '原神是个好游戏', '《原神》是一款出色的开放世界冒险游戏，由米哈游开发。游戏以其精美的画面、丰富的剧情和开放的世界设定吸引了大量玩家。玩家可以在广阔的提瓦特大陆上自由探索，体验不同角色的独特技能和战斗风格。游戏中的多样化任务和活动，让每个玩家都能找到自己的乐趣所在。《原神》不仅在玩法上极具创新性，还不断更新，带来新的内容和惊喜，保持了游戏的新鲜感和吸引力。选择《原神》，享受无尽的冒险与探索。', 'rejected',false, '2024-01-06 15:45:30', NOW()),
    (10, 'helen','关于前端>后端的论证', '前端开发在现代Web开发中具有重要地位，甚至优于后端开发。首先，前端直接影响用户体验。一个优秀的用户界面和交互设计能显著提升用户满意度和留存率。其次，随着Web应用的复杂性增加，前端技术如React、Vue.js等不断发展，使得开发高效、灵活且更具维护性。前端开发者需掌握UI/UX设计、响应式布局等多方面技能，具有更广泛的技术覆盖面。此外，前端性能优化直接影响网站的加载速度和搜索引擎排名，对业务价值至关重要。因此，前端的重要性日益凸显。', 'published',true, '2024-01-31 18:45:30', NOW()),
    (8, 'frank','Vue进化简史', 'Vue.js是一款由尤雨溪（Evan You）于2013年开发的渐进式JavaScript框架，自发布以来经历了多次演变，逐步发展成为前端开发的重要工具。2013年发布的初始版本以其简洁和易用性吸引了开发者的关注。2016年发布的Vue 2.0版本引入了虚拟DOM和增强的响应式系统，大幅提升了性能和开发体验。随着Vue CLI、Vue Router和Vuex等工具的成熟，Vue.js的生态系统逐渐完善。2019年，Vue 3的开发启动，2020年正式发布的Vue 3.0版本带来了Composition API、新的虚拟DOM和Tree-shaking功能，显著提升了代码复用性和性能。Vue.js通过持续优化和社区贡献，保持在前端框架中的领先地位，未来将继续致力于提升开发者体验和性能优化。', 'published',true, '2024-07-28 18:45:30', NOW());


INSERT INTO comments (post_id, user_id,author, content, status, isReported,created_time, updated_time)
VALUES
    (1, 1,'杰克', 'Great post! I learned a lot about blogging.', 'approved',0, NOW(), NOW()),
    (1, 2,'john', 'I agree, starting with blogging can be challenging but rewarding.', 'approved', 0,NOW(), NOW()),
    (2, 3,'alice', 'Thanks for the tips on exploring new technologies!', 'approved',0, NOW(), NOW()),
    (3, 4,'bob','Admin tips are always appreciated. Thanks for sharing!', 'approved',0, NOW(), NOW()),
    (4, 5,'carol', 'I found the learning programming guide very helpful.', 'approved',0, NOW(), NOW()),
    (5, 6,'david', 'Security is indeed important. Good read!', 'approved', 0,NOW(), NOW()),
    (6, 7,'elizabeth', 'Moderation best practices are essential for community management.', 'approved',0, NOW(), NOW()),
    (7, 8,'frank', 'Advanced features are great for power users. Looking forward to trying them out.', 'approved',0, NOW(), NOW()),
    (8, 9,'george', '非常好文章，使我受益匪浅！', 'approved', 1,NOW(), NOW()),
    (9, 10,'helen', 'Managing large datasets is a challenge I face often. Thanks for the advice.', 'approved',0, NOW(), NOW()),
    (13, 10,'于大淇', '我完全不同意这样的观点！', 'approved',0, NOW(), NOW());



INSERT INTO reports (comment_id, user_id, reason, status, created_time, updated_time)
VALUES
    (1, 3, 'Inappropriate language in this comment.', 'pending', NOW(), NOW()),
    (2, 5, 'Spam content detected.', 'pending', NOW(), NOW()),
    (3, 4, 'Comment contains false information.', 'pending', NOW(), NOW()),
    (4, 2, 'Off-topic comment.', 'pending', NOW(), NOW()),
    (5, 1, 'Comment is not relevant to the post.', 'pending', NOW(), NOW()),
    (6, 6, 'Comment is offensive.', 'pending', NOW(), NOW()),
    (7, 7, 'Comment promotes harmful products.', 'pending', NOW(), NOW()),
    (8, 8, 'Comment contains personal attacks.', 'pending', NOW(), NOW()),
    (9, 9, 'Comment is spam.', 'pending', NOW(), NOW()),
    (10, 10, 'Comment is misleading.', 'pending', NOW(), NOW());



INSERT INTO reviews (post_id, user_id, result, comment, reviewed_time)
VALUES
    (1, 3, 'approved', 'Good post, keep up the good work!', NOW()),
    (2, 5, 'approved', 'Interesting insights, well-written!', NOW()),
    (3, 4, 'rejected', 'Needs more details and references.', NOW()),
    (4, 2, 'approved', 'Draft looks fine, go ahead and publish.', NOW()),
    (5, 1, 'approved', 'Excellent conent, no changes needed.', NOW()),
    (6, 6, 'approved', 'Moderation tips are spot on.', NOW()),
    (7, 7, 'rejected', 'Too technical for the average reader.', NOW()),
    (8, 8, 'approved', 'Engaging content, well done!', NOW()),
    (9, 9, 'rejected', 'Needs more practical examples.', NOW()),
    (10, 10, 'approved', 'Positive and constructive advice.', NOW());




INSERT INTO navs (user_id, nav_order, nav_name, created_time, updated_time)
VALUES
    (1, 1, 'Home', NOW(), NOW()),
    (1, 2, 'About', NOW(), NOW()),
    (1, 3, 'Contact', NOW(), NOW()),
    (2, 1, 'Home', NOW(), NOW()),
    (2, 2, 'Blog', NOW(), NOW()),
    (2, 3, 'Portfolio', NOW(), NOW()),
    (3, 1, 'Home', NOW(), NOW()),
    (3, 2, 'Admin', NOW(), NOW()),
    (3, 3, 'Users', NOW(), NOW()),
    (4, 1, 'Home', NOW(), NOW());



INSERT INTO recommendation (companyName,user_id,author, title, content, abstract, summary,location, status, created_time, publish_time,max_salary,min_salary)
VALUES
    ('华为', 1, '杰克', '软件工程师实习', '我们正在寻找有才华的软件工程师加入我们的团队...', '寻找软件工程师...', '员工', '旧金山', true, NOW(), NOW(), '10000', '5000'),
    ('阿里', 2, 'john', '产品经理职位', '这是一个令人兴奋的机会，让产品经理领导我们的新产品发布...', '产品经理机会...', '员工', '纽约', true, NOW(), NOW(), '8000', '6000'),
    ('腾讯', 3, 'alice', 'UI/UX设计师职位', '加入我们的设计团队，帮助塑造我们产品的未来...', '加入我们的设计团队...', '领导', '洛杉矶', true, NOW(), NOW(), '18000', '12000'),
    ('小米', 4, 'bob', '需要前端开发人员', '我们需要有经验的前端开发人员，可以构建响应式Web应用程序...', '需要有经验的前端开发人员...', '员工', '芝加哥', false, NOW(), NOW(), '8000', '6000'),
    ('华为', 5, 'carol', '招聘后端开发人员', '我们正在寻找有Java和Python经验的后端开发人员...', '招聘后端开发人员...', '领导', '波士顿', true, NOW(), NOW(), '12000', '6000'),
    ('抖音', 6, 'david', '数据科学家机会', '寻找数据科学家来分析大数据集并提供洞见...', '数据科学家机会...', '员工', '西雅图', true, NOW(), NOW(), '6000', '3000'),
    ('抖音', 7, 'elizabeth', 'DevOps工程师职位', '加入我们的DevOps团队，帮助我们自动化和优化基础设施...', '加入我们的DevOps团队...', '员工', '奥斯汀', true, NOW(), NOW(), '12000', '10000'),
    ('比亚迪', 8, 'frank', '质量保证测试员', '我们需要测试人员来确保我们的软件发布质量...', '需要质量保证测试员...', '领导', '亚特兰大', true, NOW(), NOW(), '25000', '20000'),
    ('华为', 9, 'george', '全栈开发人员职位', '寻找有前端和后端技术经验的全栈开发人员...', '全栈开发人员职位...', '员工', '丹佛', false, NOW(), NOW(), '11000', '10000'),
    ('小米', 10, 'helen', '技术写作职位', '加入我们的文档团队，帮助我们创建清晰简明的技术文档...', '技术写作职位...', '员工', '费城', true, NOW(), NOW(), '9000', '8000');




INSERT INTO recommendation_comments (recommendation_id, user_id, content, status, comment_time)
VALUES
    (1, 2, 'This sounds like a great opportunity!', 'approved', NOW()),
    (1, 3, 'I have experience in software engineering and would love to apply.', 'approved', NOW()),
    (2, 4, 'Is this position still open?', 'approved', NOW()),
    (3, 5, 'I am interested in UI/UX roles. Can you tell me more about the team?', 'approved', NOW()),
    (4, 6, 'I have several years of experience in frontend development. How can I apply?', 'approved', NOW()),
    (5, 7, 'What kind of projects will the backend developers be working on?', 'approved', NOW()),
    (6, 8, 'Can you provide more details about the data science responsibilities?', 'approved', NOW()),
    (7, 9, 'I have experience with DevOps tools like Ansible and Jenkins. Is there a way to submit my resume?', 'approved', NOW()),
    (8, 10, 'What testing frameworks do you use for quality assurance?', 'approved', NOW()),
    (9, 1, 'I am a full stack developer and interested in this position. Can we schedule an interview?', 'approved', NOW());


INSERT INTO recommendation_reviews (recommendation_id, user_id, result, suggestion, review_time)
VALUES
    (1, 3, 'approved', 'Great opportunity, proceed with posting.', NOW()),
    (2, 4, 'approved', 'Post is clear and detailed.', NOW()),
    (3, 5, 'rejected', 'Needs more information about the company culture.', NOW()),
    (4, 6, 'approved', 'Looks good, ready to publish.', NOW()),
    (5, 7, 'approved', 'Include more details about the technology stack.', NOW()),
    (6, 8, 'rejected', 'Suggest adding more specific requirements.', NOW()),
    (7, 9, 'approved', 'Well-written, no changes needed.', NOW()),
    (8, 10, 'approved', 'Add a link to the application form.', NOW()),
    (9, 1, 'rejected', 'Needs clarification on the role responsibilities.', NOW()),
    (10, 2, 'approved', 'Ready to go live.', NOW());



INSERT INTO activities (activity_name,initiator, activity_type, activity_time, activity_location, activity_description,maxParticipants, currentParticipants,status)
VALUES
    ('企业实训', 'john', '研讨会', '2025-12-22 20:12:10', '大连', '大连中软国际企业实训活动旨在为学生和职场新人提供真实的企业项目实战体验。参与者将有机会在专业导师的指导下，参与软件开发、系统集成等实际项目，提高实战技能，了解行业动态，积累宝贵的工作经验，为未来职业发展打下坚实基础。此次实训活动不仅提升技术能力，更注重培养团队协作和项目管理能力。', 10, 7, 'approved'),
    ('黑客马拉松', 'john', '活动', '2024-11-22 20:12:10', '纽约', '为期一个周末的编程比赛，设有奖品。', 10, 4, 'approved'),
    ('社交活动', 'alice', '社交', '2023-12-22 20:12:10', '洛杉矶', '与行业专业人士的社交晚会。', 15, 4, 'approved'),
    ('工作坊', 'bob', '培训', '2024-08-22 20:12:10', '芝加哥', '关于云计算的实践工作坊。', 30, 9, 'approved'),
    ('读书俱乐部会议', 'carol', '讨论', '2024-10-22 20:12:10', '波士顿', '每月一次的技术书籍讨论读书俱乐部会议。', 120, 1, 'rejected'),
    ('创业展示之夜', 'david', '活动', '2025-06-22 20:12:10', '西雅图', '初创公司向投资者展示他们的创意的夜晚。', 150, 1, 'pending'),
    ('编程训练营', 'elizabeth', '培训', '2025-04-22 20:12:10', '奥斯汀', '为期两周的密集训练营，面向有志成为开发者的人士。', 25, 1, 'pending'),
    ('慈善跑', 'frank', '活动', '2025-01-22 20:12:10', '亚特兰大', '支持本地技术教育计划的慈善跑活动。', 10, 5, 'approved'),
    ('产品发布派对', '杰克', '庆祝', '2025-02-22 20:12:10', '丹佛', '庆祝新产品发布的派对。', 5, 3, 'approved');



INSERT INTO user_activities (user_id, activity_id, activity_status)
VALUES
    (1, 1, null),(2, 1, null),(3, 1, null),(4, 1, null),(5, 1, null),(6, 1, null),(7, 1, null),
    (2, 2, null),(5, 2, null),(8, 2, null),(9, 2, null),
    (1, 3, null),(3, 3, null),(7, 3, null),(10, 3, null),
    (4, 4, null),(1, 4, null),(2, 4, null),(3, 4, null),(9, 4, null),(5, 4, null),(6, 4, null),(7, 4, null),(8, 4, null),
    (5, 5, null),
    (6, 6, null),
    (7, 7, null),
    (8, 8, null),
    (9, 9, null),(3, 9, null),(4, 9, null);



INSERT INTO resumes (user_id,nickname, content, status, created_time, work_experience,education)
VALUES
    (1,'杰克', '20岁，是学生，熟练c++,python,java等单词的拼写，擅长idea,visual studio code,dev c++等ide的安装。', 'active', NOW(),'学生','高中或以下'),
    (2,'john', 'Objective: A highly motivated product manager with over 5 years of experience in launching successful products. Skills include Agile methodologies, product strategy, and stakeholder management. Education: MBA from Stanford Graduate School of Business.', 'active', NOW(),'已就业三年以内','硕士'),
    (3,'alice', 'Objective: Experienced UI/UX designer with a passion for creating intuitive and user-friendly interfaces. Skills include Adobe Creative Suite, Sketch, and Figma. Education: B.F.A. in Graphic Design from Rhode Island School of Design.', 'active', NOW(),'已就业三年以上','博士'),
    (4,'bob', 'Objective: Talented front-end developer with expertise in React, Angular, and Vue.js. Strong background in responsive web design and accessibility. Education: B.S. in Information Technology from Georgia Tech.', 'active', NOW(),'已就业三年以上','本科'),
    (5,'carol', 'Objective: Seasoned back-end developer with extensive knowledge of Java, Python, and Node.js. Experience in database design and RESTful API development. Education: M.S. in Computer Science from Carnegie Mellon University.', 'active', NOW(),'已就业三年以内','大专'),
    (6,'david', 'Objective: Data scientist with a proven track record of leveraging machine learning algorithms to drive business decisions. Skills include Python, R, and SQL. Education: Ph.D. in Statistics from University of Michigan.', 'active', NOW(),'已就业三年以上','博士'),
    (7,'elizabeth', 'Objective: DevOps engineer with a focus on automation and infrastructure as code. Proficient in CI/CD pipelines, Kubernetes, and Docker. Education: B.S. in Computer Engineering from University of Texas at Austin.', 'active', NOW(),'已就业三年以内','硕士'),
    (8,'frank', 'Objective: Quality assurance specialist with a strong attention to detail and a commitment to delivering high-quality software. Experience in manual and automated testing. Education: Certificate in Software Testing from Udacity.', 'active', NOW(),'已就业三年以内','高中或以下'),
    (9,'george', 'Objective: Full-stack developer with experience in both front-end and back-end technologies. Skills include JavaScript, TypeScript, and Ruby on Rails. Education: B.S. in Computer Science from University of Illinois at Urbana-Champaign.', 'active', NOW(),'已就业三年以上','硕士'),
    (10,'helen', 'Objective: Technical writer with a knack for simplifying complex concepts into clear and concise documentation. Experience in writing user manuals, tutorials, and API documentation. Education: B.A. in English from University of Wisconsin-Madison.', 'active', NOW(),'已就业三年以内','本科');

INSERT INTO resume_reviews (resume_id, user_id, result, comment, reviewed_time)
VALUES
    (1, 3, 'approved', 'Strong resume, ready to submit.', NOW()),
    (2, 4, 'approved', 'Well-structured, good experience highlighted.', NOW()),
    (3, 5, 'rejected', 'Needs more details about projects.', NOW()),
    (4, 6, 'approved', 'Skills are clearly presented.', NOW()),
    (5, 7, 'approved', 'Experience matches the job description.', NOW()),
    (6, 8, 'rejected', 'Missing relevant certifications.', NOW()),
    (7, 9, 'approved', 'Clear and concise.', NOW()),
    (8, 10, 'approved', 'Add a link to portfolio.', NOW()),
    (9, 1, 'rejected', 'Needs more technical keywords.', NOW()),
    (10, 2, 'approved', 'Ready for submission.', NOW());




INSERT INTO drafts (user_id, draft_content, draft_status, created_time, updated_time)
VALUES
    (1, 'Draft for a blog post about the importance of software documentation...', 'in_progress', NOW(), NOW()),
    (2, 'Draft for a proposal to improve our product launch strategy...', 'in_progress', NOW(), NOW()),
    (3, 'Draft for a presentation on agile methodologies...', 'in_progress', NOW(), NOW()),
    (4, 'Draft for an article on the benefits of remote work...', 'in_progress', NOW(), NOW()),
    (5, 'Draft for a case study on successful project management techniques...', 'in_progress', NOW(), NOW()),
    (6, 'Draft for a newsletter highlighting recent tech trends...', 'in_progress', NOW(), NOW()),
    (7, 'Draft for a tutorial on using Git for version control...', 'in_progress', NOW(), NOW()),
    (8, 'Draft for a white paper on cybersecurity best practices...', 'in_progress', NOW(), NOW()),
    (9, 'Draft for a guide on optimizing database performance...', 'in_progress', NOW(), NOW()),
    (10, 'Draft for a summary of the latest AI advancements...', 'in_progress', NOW(), NOW());



INSERT INTO draft_reviews (draft_id, user_id, result, comment, reviewed_time)
VALUES
    (1, 3, 'approved', 'Good structure and flow.', NOW()),
    (2, 4, 'approved', 'Needs more concrete examples.', NOW()),
    (3, 5, 'rejected', 'Too much technical jargon, simplify.', NOW()),
    (4, 6, 'approved', 'Well-researched and informative.', NOW()),
    (5, 7, 'approved', 'Great insights, ready to publish.', NOW()),
    (6, 8, 'rejected', 'Lacks a clear conclusion.', NOW()),
    (7, 9, 'approved', 'Engaging and relevant.', NOW()),
    (8, 10, 'approved', 'Add references for further reading.', NOW()),
    (9, 1, 'rejected', 'Needs more visuals and examples.', NOW()),
    (10, 2, 'approved', 'Ready for final edits.', NOW());




INSERT INTO orders (user_id,nickname, orders_name,orders_price, orders_introduction, orders_status, created_time,successful_bidder)
VALUES
    (1,'杰克', 'Order 101',500, 'This order includes 5 units of Product A and 3 units of Product B.', 'completed', '2024-07-28 01:45:30','alice'),
    (2,'john', 'Order 102',5000, 'This order includes 2 units of Product C and 1 unit of Product D.', 'processing', '2023-05-25 01:45:30',null),
    (3,'alice', 'Order 103',1500, 'This order includes 10 units of Product E and 5 units of Product F.', 'completed', '2024-07-15 01:45:30','bob'),
    (4,'bob', 'Order 104',3500, 'This order includes 1 unit of Product G and 2 units of Product H.', 'processing', '2023-01-28 01:45:30','carol'),
    (5,'carol', 'Order 105',2000, 'This order includes 3 units of Product I and 4 units of Product J.', 'processing', '2024-06-28 01:45:30','frank'),
    (6,'david', 'Order 106',12000, 'This order includes 4 units of Product K and 2 units of Product L.', 'processing', '2024-05-20 01:45:30',null),
    (7,'elizabeth', 'Order 107',8000, 'This order includes 7 units of Product M and 5 units of Product N.', 'processing', '2023-07-01 01:45:30',null),
    (8,'frank', 'Order 108',11000, 'This order includes 6 units of Product O and 1 unit of Product P.', 'pending','2022-08-28 01:45:30',null),
    (9,'george', 'Order 109',18000, 'This order includes 2 units of Product Q and 3 units of Product R.', 'processing', '2024-03-28 01:45:30','杰克'),
    (10,'helen', 'Order 110',7500, 'This order includes 8 units of Product S and 4 units of Product T.', 'completed', '2024-01-28 01:45:30','elizabeth'),
    (10,'helen', 'Order 111',200, 'This order includes 8 units of Product S and 4 units of Product T.', 'completed', '2023-01-28 01:45:30','杰克'),
    (1,'杰克', 'Order 112',7500, 'This order includes 8 units of Product S and 4 units of Product T.', 'pending', '2024-07-28 01:45:30',null),
    (1,'杰克', 'Order 113',7500, 'This order includes 8 units of Product S and 4 units of Product T.', 'processing', '2024-07-29 01:45:30',null),
    (1,'杰克', 'Order 114',7500, 'This order includes 8 units of Product S and 4 units of Product T.', 'completed', '2024-07-30 01:45:30','elizabeth');




INSERT INTO order_reviews (orders_id, user_id, result, comment, reviewed_time)
VALUES
    (1, 3, 'approved', 'Order processed successfully.', NOW()),
    (2, 4, 'approved', 'Items matched the description.', NOW()),
    (3, 5, 'approved', 'Order completed without issues.', NOW()),
    (4, 6, 'rejected', 'Incorrect item shipped.', NOW()),
    (5, 7, 'rejected', 'Order canceled by customer.', NOW()),
    (6, 8, 'approved', 'Customer satisfied with delivery.', NOW()),
    (7, 9, 'approved', 'All items received in good condition.', NOW()),
    (8, 10, 'approved', 'Processing time was reasonable.', NOW()),
    (9, 1, 'rejected', 'Late delivery, customer unhappy.', NOW()),
    (10, 2, 'approved', 'Order fulfilled as expected.', NOW());



INSERT INTO review_requests (user1_id, user2_id, result, reviewed_time)
VALUES
    (1, 2, 'accepted', NOW()),
    (2, 3, 'accepted', NOW()),
    (3, 4, 'rejected', NOW()),
    (4, 5, 'accepted', NOW()),
    (5, 6, 'accepted', NOW()),
    (6, 7, 'rejected', NOW()),
    (7, 8, 'accepted', NOW()),
    (8, 9, 'accepted', NOW()),
    (9, 10, 'rejected', NOW()),
    (10, 1, 'accepted', NOW());




INSERT INTO application_success_messages (review_id, message_content, created_time)
VALUES
    (1, 'Your application has been successfully reviewed and accepted. We look forward to your contribution.', NOW()),
    (2, 'Thank you for your application. It has been accepted. Please proceed with the next steps.', NOW()),
    (3, 'Unfortunately, your application was not accepted this time. You may try again after addressing the feedback.', NOW()),
    (4, 'Your application has been successfully reviewed and accepted. Welcome aboard!', NOW()),
    (5, 'Congratulations! Your application has been accepted. Further instructions will follow.', NOW()),
    (6, 'We regret to inform you that your application was not accepted. Please review the feedback provided.', NOW()),
    (7, 'Your application has been successfully reviewed and accepted. Looking forward to working with you.', NOW()),
    (8, 'Thank you for applying. Your application has been accepted. Please check your email for more details.', NOW()),
    (9, 'Unfortunately, your application was not accepted. Please consider applying for other opportunities.', NOW()),
    (10, 'Your application has been successfully reviewed and accepted. Welcome to the team!', NOW());




/*向用户收藏内推的中间表中加入数据*/
INSERT INTO user_recommendation_favorites (user_id, recommendation_id)
VALUES (1, 1), (1, 2), (2, 3), (2, 4), (3, 5),
       (3, 1), (4, 2), (4, 4), (5, 1), (5, 3);


/*向订单竞标的中间表中添加数据*/
INSERT INTO user_order (user_id,orders_id)
VALUES (2,1),(3,1),(4,1),(3,2),(4,2),(5,2),(4,3),(5,3),(6,3),(5,4),
       (6,4),(7,4),(6,5),(7,5),(8,5),(7,6),(8,6),(9,6),(8,7),(9,7),
       (10,7),(9,8),(10,8),(1,8),(2,9),(3,9),(4,9),(5,10),(6,10),(7,10);


/*向技能表中插入数据*/
INSERT INTO skill(skill_name)
VALUES ('前端'),('后端'),('软件设计'),('平面设计'),('网络管理'),('数据分析'),('数据库管理');

/*向用户技能中间表中插入数据*/
INSERT INTO user_skill(user_id,skill_id)
VALUES (1,1),(1,2),(1,3),(2,2),(2,3),(2,4),(3,3),(3,4),(3,5),(4,6),(4,2),(5,1),(6,3),(6,4),(6,6),(6,7),(7,4),(7,7),(7,6),(8,5),(8,4),(8,3),(9,1),(9,2),(9,3),(10,5),(10,4),(10,7);