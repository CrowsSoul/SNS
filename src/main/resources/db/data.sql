INSERT INTO users (username,nickname, password, email, role, created_time, updated_time)
VALUES
    ('jack','hello', 'Pass123', 'jack@example.com', 'user', NOW(), NOW()),
    ('john', 'hello000','Pass456', 'john@example.com', 'user', NOW(), NOW()),
    ('alice', 'helloooo','Pass789', 'alice@example.com', 'admin', NOW(), NOW()),
    ('bob', 'oooello','Pass101', 'bob@example.com', 'user', NOW(), NOW()),
    ('carol','hellooo', 'Pass202', 'carol@example.com', 'user', NOW(), NOW()),
    ('david','helggjlo', 'Pass303', 'david@example.com', 'moderator', NOW(), NOW()),
    ('elizabeth', 'hjfjfello','Pass404', 'elizabeth@example.com', 'user', NOW(), NOW()),
    ('frank', 'helthlo','Pass505', 'frank@example.com', 'user', NOW(), NOW()),
    ('george', 'hefthllo','Pass606', 'george@example.com', 'user', NOW(), NOW()),
    ('helen', 'helfhtlo','Pass707', 'helen@example.com', 'user', NOW(), NOW());



INSERT INTO posts (user_id, title, content, status, created_time, updated_time)
VALUES
    (1, 'My First Post', 'This is my first blog post about getting started with blogging.', 'published', NOW(), NOW()),
    (2, 'Exploring New Technologies', 'A detailed exploration of the latest technologies and how they can be used in web development.', 'published', NOW(), NOW()),
    (3, 'Admin Tips and Tricks', 'Tips for managing users and moderating content on your website.', 'published', NOW(), NOW()),
    (4, 'Learning Programming', 'A beginner\'s guide to programming and some useful resources.', 'draft', NOW(), NOW()),
    (5, 'The Importance of Security', 'Why security is crucial for any online platform and how to improve it.', 'published', NOW(), NOW()),
    (6, 'Moderation Best Practices', 'Best practices for moderating comments and maintaining a healthy community.', 'published', NOW(), NOW()),
    (7, 'Advanced Features Unlocked', 'Unlocking advanced features in the blogging platform for power users.', 'draft', NOW(), NOW()),
    (8, 'Community Engagement Strategies', 'Strategies for engaging with your community and building a loyal following.', 'published', NOW(), NOW()),
    (9, 'Managing Large Datasets', 'How to manage and analyze large datasets efficiently.', 'draft', NOW(), NOW()),
    (10, 'Creating a Positive Online Environment', 'Tips for creating a positive and welcoming environment for all users.', 'published', NOW(), NOW());


INSERT INTO comments (post_id, user_id, content, status, created_time, updated_time)
VALUES
    (1, 1, 'Great post! I learned a lot about blogging.', 'approved', NOW(), NOW()),
    (1, 2, 'I agree, starting with blogging can be challenging but rewarding.', 'approved', NOW(), NOW()),
    (2, 3, 'Thanks for the tips on exploring new technologies!', 'approved', NOW(), NOW()),
    (3, 4, 'Admin tips are always appreciated. Thanks for sharing!', 'approved', NOW(), NOW()),
    (4, 5, 'I found the learning programming guide very helpful.', 'approved', NOW(), NOW()),
    (5, 6, 'Security is indeed important. Good read!', 'approved', NOW(), NOW()),
    (6, 7, 'Moderation best practices are essential for community management.', 'approved', NOW(), NOW()),
    (7, 8, 'Advanced features are great for power users. Looking forward to trying them out.', 'approved', NOW(), NOW()),
    (8, 9, 'Community engagement strategies are key to building a strong community.', 'approved', NOW(), NOW()),
    (9, 10, 'Managing large datasets is a challenge I face often. Thanks for the advice.', 'approved', NOW(), NOW());



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
    (5, 1, 'approved', 'Excellent content, no changes needed.', NOW()),
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



INSERT INTO pushinfor (user_id, title, content, abstract, location, status, created_time, publish_time)
VALUES
    (1, 'Software Engineer Internship', 'We are looking for talented software engineers to join our team...', 'Looking for software engineers...', 'San Francisco', 'open', NOW(), NOW()),
    (2, 'Product Manager Role', 'Exciting opportunity for a product manager to lead our new product launch...', 'Opportunity for a product manager...', 'New York', 'closed', NOW(), NOW()),
    (3, 'UI/UX Designer Position', 'Join our design team and help shape the future of our product...', 'Join our design team...', 'Los Angeles', 'open', NOW(), NOW()),
    (4, 'Frontend Developer Needed', 'We need experienced frontend developers who can build responsive web applications...', 'Experienced frontend developers needed...', 'Chicago', 'open', NOW(), NOW()),
    (5, 'Backend Developer Wanted', 'Seeking backend developers with experience in Java and Python...', 'Seeking backend developers...', 'Boston', 'open', NOW(), NOW()),
    (6, 'Data Scientist Opportunity', 'Looking for data scientists to analyze large datasets and provide insights...', 'Data scientist opportunity...', 'Seattle', 'closed', NOW(), NOW()),
    (7, 'DevOps Engineer Role', 'Join our DevOps team and help us automate and optimize our infrastructure...', 'Join our DevOps team...', 'Austin', 'open', NOW(), NOW()),
    (8, 'Quality Assurance Tester', 'We need testers to ensure the quality of our software releases...', 'Quality assurance tester needed...', 'Atlanta', 'open', NOW(), NOW()),
    (9, 'Full Stack Developer Position', 'Seeking full stack developers with experience in both frontend and backend technologies...', 'Full stack developer position...', 'Denver', 'open', NOW(), NOW()),
    (10, 'Technical Writer Job', 'Join our documentation team and help us create clear and concise technical documentation...', 'Technical writer job...', 'Philadelphia', 'open', NOW(), NOW());




INSERT INTO pushinfor_comments (pushinfor_id, user_id, content, status, comment_time)
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


INSERT INTO pushinfor_reviews (pushinfor_id, user_id, result, suggestion, review_time)
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



INSERT INTO activities (activity_name, activity_type, activity_time, activity_address, activity_content, activity_amount)
VALUES
    ('Tech Talk', 'Seminar', NOW(), 'San Francisco', 'A seminar on the latest tech trends and innovations.', 50),
    ('Hackathon', 'Event', NOW(), 'New York', 'A weekend-long coding competition with prizes.', 100),
    ('Networking Event', 'Social', NOW(), 'Los Angeles', 'An evening of networking with industry professionals.', 75),
    ('Workshop', 'Training', NOW(), 'Chicago', 'A hands-on workshop on cloud computing.', 30),
    ('Book Club Meeting', 'Discussion', NOW(), 'Boston', 'Monthly book club meeting discussing tech books.', 20),
    ('Startup Pitch Night', 'Event', NOW(), 'Seattle', 'A night where startups pitch their ideas to investors.', 150),
    ('Coding Bootcamp', 'Training', NOW(), 'Austin', 'A two-week intensive bootcamp for aspiring developers.', 25),
    ('Charity Run', 'Event', NOW(), 'Atlanta', 'A charity run to support local tech education initiatives.', 300),
    ('Product Launch Party', 'Celebration', NOW(), 'Denver', 'A party celebrating the launch of a new product.', 100),
    ('Volunteer Day', 'Community', NOW(), 'Philadelphia', 'A day dedicated to volunteering at local tech nonprofits.', 40);



INSERT INTO user_activities (user_id, activity_id, activity_status)
VALUES
    (1, 1, 'registered'),
    (2, 2, 'registered'),
    (3, 3, 'attended'),
    (4, 4, 'registered'),
    (5, 5, 'attended'),
    (6, 6, 'registered'),
    (7, 7, 'attended'),
    (8, 8, 'registered'),
    (9, 9, 'attended'),
    (10, 10, 'registered');



INSERT INTO resumes (user_id, content, status, created_time, updated_time)
VALUES
    (1, 'Objective: Seeking a software engineer position with XYZ Corp. Skills include Java, Python, and C++. Education: B.S. in Computer Science from University of California, Berkeley.', 'active', NOW(), NOW()),
    (2, 'Objective: A highly motivated product manager with over 5 years of experience in launching successful products. Skills include Agile methodologies, product strategy, and stakeholder management. Education: MBA from Stanford Graduate School of Business.', 'active', NOW(), NOW()),
    (3, 'Objective: Experienced UI/UX designer with a passion for creating intuitive and user-friendly interfaces. Skills include Adobe Creative Suite, Sketch, and Figma. Education: B.F.A. in Graphic Design from Rhode Island School of Design.', 'active', NOW(), NOW()),
    (4, 'Objective: Talented front-end developer with expertise in React, Angular, and Vue.js. Strong background in responsive web design and accessibility. Education: B.S. in Information Technology from Georgia Tech.', 'active', NOW(), NOW()),
    (5, 'Objective: Seasoned back-end developer with extensive knowledge of Java, Python, and Node.js. Experience in database design and RESTful API development. Education: M.S. in Computer Science from Carnegie Mellon University.', 'active', NOW(), NOW()),
    (6, 'Objective: Data scientist with a proven track record of leveraging machine learning algorithms to drive business decisions. Skills include Python, R, and SQL. Education: Ph.D. in Statistics from University of Michigan.', 'active', NOW(), NOW()),
    (7, 'Objective: DevOps engineer with a focus on automation and infrastructure as code. Proficient in CI/CD pipelines, Kubernetes, and Docker. Education: B.S. in Computer Engineering from University of Texas at Austin.', 'active', NOW(), NOW()),
    (8, 'Objective: Quality assurance specialist with a strong attention to detail and a commitment to delivering high-quality software. Experience in manual and automated testing. Education: Certificate in Software Testing from Udacity.', 'active', NOW(), NOW()),
    (9, 'Objective: Full-stack developer with experience in both front-end and back-end technologies. Skills include JavaScript, TypeScript, and Ruby on Rails. Education: B.S. in Computer Science from University of Illinois at Urbana-Champaign.', 'active', NOW(), NOW()),
    (10, 'Objective: Technical writer with a knack for simplifying complex concepts into clear and concise documentation. Experience in writing user manuals, tutorials, and API documentation. Education: B.A. in English from University of Wisconsin-Madison.', 'active', NOW(), NOW());

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




INSERT INTO orders (user_id, orders_name, orders_introduction, orders_status, created_time)
VALUES
    (1, 'Order 101', 'This order includes 5 units of Product A and 3 units of Product B.', 'processing', NOW()),
    (2, 'Order 102', 'This order includes 2 units of Product C and 1 unit of Product D.', 'processing', NOW()),
    (3, 'Order 103', 'This order includes 10 units of Product E and 5 units of Product F.', 'completed', NOW()),
    (4, 'Order 104', 'This order includes 1 unit of Product G and 2 units of Product H.', 'processing', NOW()),
    (5, 'Order 105', 'This order includes 3 units of Product I and 4 units of Product J.', 'canceled', NOW()),
    (6, 'Order 106', 'This order includes 4 units of Product K and 2 units of Product L.', 'processing', NOW()),
    (7, 'Order 107', 'This order includes 7 units of Product M and 5 units of Product N.', 'completed', NOW()),
    (8, 'Order 108', 'This order includes 6 units of Product O and 1 unit of Product P.', 'processing', NOW()),
    (9, 'Order 109', 'This order includes 2 units of Product Q and 3 units of Product R.', 'processing', NOW()),
    (10, 'Order 110', 'This order includes 8 units of Product S and 4 units of Product T.', 'completed', NOW());




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