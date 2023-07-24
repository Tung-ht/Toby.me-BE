/*
 Navicat Premium Data Transfer

 Source Server         : server db
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : 192.168.1.5:3307
 Source Schema         : toby_me

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 07/07/2023 09:27:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for articles
-- ----------------------------
DROP TABLE IF EXISTS `articles`;
CREATE TABLE `articles`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `body` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `slug` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `title` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `author_id` bigint NOT NULL,
  `is_approved` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKe02fs2ut6qqoabfhj325wcjul`(`author_id` ASC) USING BTREE,
  CONSTRAINT `FKe02fs2ut6qqoabfhj325wcjul` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of articles
-- ----------------------------
INSERT INTO `articles` VALUES (1, '2023-07-07 08:26:16', '2023-07-07 08:37:57', '<p><strong>Docker</strong>&nbsp;là một dự án nguồn mở nó cho phép tự động hóa việc triển khai các ứng dụng bên trong các Container (Linux), cung như cung cấp chức năng đóng gói các thành phần cần để chạy ứng dụng vào Container. Docker cung cấp công cụ CLI (Command Line Interface) để quản lý vòng đời của các container. Sử dụng Docker là cách nhanh chóng để phát triển, triển khai, bảo trì các ứng dụng.</p><p><br></p><blockquote>Docker có sự khác biệt so với máy ảo, máy ảo là một hệ thống đầy đủ với tất cả các phần mềm, hệ điều hành. Các Docker Container thì cung cấp cho ứng dụng một môi trường cách ly và được cấu hình tối thiểu để ứng dụng hoạt động được. Với Container nhân và các thành phần của hệ điều hành được chia sẻ.</blockquote><p><br></p><p>Một số ưu điểm của Docker Container so với công nghệ ảo hóa:</p><ul><li>Tạo và hủy container rất nhanh và dễ dàng, Máy áo thì cần cài đặt đầy đủ mọi thứ và cần nhiều tài nguyên hệ thống hơn.</li><li>Container rất nhỏ, vì vậy mà trên một máy Host số container chạy song song với nhau nhiều hơn số máy áo chạy song song.</li></ul><p>Chuyên mục này là các bài viết hướng dẫn sử dụng Docker, chú trọng vào thực hành để có thể nhanh chóng áp dụng thực tế.</p><h2><br></h2><h2>Một số lệnh Docker</h2><p>Kiểm tra phiên bản docker:</p><blockquote>docker --version</blockquote><p>Thông tin hệ thống docker</p><pre class=\"ql-syntax\" spellcheck=\"false\">docker info\n</pre><p>Liệt kê các image</p><pre class=\"ql-syntax\" spellcheck=\"false\">docker images -a\n</pre><p>Tải về một image từ hub.docker.com</p><pre class=\"ql-syntax\" spellcheck=\"false\">docker pull nameimage:tag\n</pre><p>Liệt kê các container đang chạy</p><pre class=\"ql-syntax\" spellcheck=\"false\">docker ps\n</pre><p>Liệt kê các container</p><pre class=\"ql-syntax\" spellcheck=\"false\">docker ps -a\n</pre><p>Liệt kê các container</p><pre class=\"ql-syntax\" spellcheck=\"false\">docker container ls -a\n</pre>', 'Sơ lược về Docker. Docker là gì? Tại sao hiện tại hầu hết các dự án đều phải cần đến nó?', 'Series:-Docker-from-Zero-to-Hero---Phần-1-2023-07-07T08:26:36.365331', 'Series: Docker from Zero to Hero - Phần 1', 1, 1);
INSERT INTO `articles` VALUES (2, '2023-07-07 09:04:38', '2023-07-07 09:27:14', '<pre class=\"ql-syntax\" spellcheck=\"false\">@Transactional(readOnly = true)\n@Override\npublic String login(UserDto.Login login) {\n    UserEntity userEntity = userRepository.findAllByEmailAndStatus(login.getEmail(), EStatus.ACTIVE)\n            .stream()\n            .filter(user -&gt; passwordEncoder.matches(login.getPassword(), user.getPassword()))\n            .findFirst()\n            .orElseThrow(() -&gt; new AppException(Error.LOGIN_INFO_INVALID));\n    String jwt = jwtUtils.encode(userEntity.getEmail());\n    var userDetail = AuthUserDetails.builder()\n            .id(userEntity.getId())\n            .email(userEntity.getEmail())\n            .authorities(userEntity.getRoles())\n            .build();\n    // when a user logged in, (jwt-userInfo)-(string-jsonString) is cached into redis\n    var jsonStr = JsonConverter.serializeObject(userDetail);\n    redisTemplate.opsForValue()\n            .set(jwt, jsonStr, Duration.ofSeconds(jwtUtils.getValidSeconds()));\n    return jwt;\n}\n</pre>', 'e gặp lỗi trong đoạn code này, mọi người giúp em fix với ạ', 'Lỗi-khi-sử-dụng-spring-data-redis-1688695477723', 'Lỗi khi sử dụng spring data redis', 2, 1);
INSERT INTO `articles` VALUES (3, '2023-07-07 09:05:54', '2023-07-07 09:06:47', '<p>const handleDeleteArticle = async (slug: string) =&gt; {</p><p>  try {</p><p>    setLoading(true);</p><p>    const rs = await adminDeleteArticle(slug);</p><p><br></p><p>    if (rs.status === 200) {</p><p>      setOutArticle(false);</p><p>      notifySuccess(\'Bài viết đã bị từ chối\');</p><p>    } else {</p><p>      notifyError(\'Từ chối bài viết thất bại\');</p><p>    }</p><p>  } catch (error) {</p><p>    notifyError(\'Từ chối bài viết thất bại\');</p><p>    <em>console</em>.log(\'🚀 -&gt; handleApproveArticle -&gt; error:\', error);</p><p>  } finally {</p><p>    setLoading(false);</p><p>  }</p><p>};</p>', 'help me bros', 'Reactjs-async-2023-07-07T09:06:46.974176300', 'Reactjs async', 2, 0);

-- ----------------------------
-- Table structure for available_tags
-- ----------------------------
DROP TABLE IF EXISTS `available_tags`;
CREATE TABLE `available_tags`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `is_pinned` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of available_tags
-- ----------------------------
INSERT INTO `available_tags` VALUES (1, 'java', NULL);
INSERT INTO `available_tags` VALUES (2, 'html', NULL);
INSERT INTO `available_tags` VALUES (3, 'css', NULL);
INSERT INTO `available_tags` VALUES (4, 'go', NULL);
INSERT INTO `available_tags` VALUES (5, 'spring-boot', NULL);
INSERT INTO `available_tags` VALUES (6, 'reactjs', NULL);
INSERT INTO `available_tags` VALUES (7, 'javascript', NULL);
INSERT INTO `available_tags` VALUES (8, 'fix-bug', NULL);
INSERT INTO `available_tags` VALUES (9, 'hỏi đáp', NULL);
INSERT INTO `available_tags` VALUES (10, 'docker', NULL);
INSERT INTO `available_tags` VALUES (11, '.net', NULL);
INSERT INTO `available_tags` VALUES (12, 'c#', NULL);
INSERT INTO `available_tags` VALUES (13, 'nosql', NULL);
INSERT INTO `available_tags` VALUES (14, 'sql', NULL);
INSERT INTO `available_tags` VALUES (15, 'tư vấn', NULL);
INSERT INTO `available_tags` VALUES (16, 'tâm sự', NULL);
INSERT INTO `available_tags` VALUES (17, 'chém gió', NULL);
INSERT INTO `available_tags` VALUES (18, 'thông báo', 1);
INSERT INTO `available_tags` VALUES (19, 'nội quy', 1);
INSERT INTO `available_tags` VALUES (20, 'nổi bật', 1);
-- ----------------------------
-- Table structure for comments
-- ----------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime NULL DEFAULT NULL,
  `updated_at` datetime NULL DEFAULT NULL,
  `body` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `article_id` bigint NOT NULL,
  `author_id` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKk4ib6syde10dalk7r7xdl0m5p`(`article_id` ASC) USING BTREE,
  INDEX `FKn2na60ukhs76ibtpt9burkm27`(`author_id` ASC) USING BTREE,
  CONSTRAINT `FKk4ib6syde10dalk7r7xdl0m5p` FOREIGN KEY (`article_id`) REFERENCES `articles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKn2na60ukhs76ibtpt9burkm27` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comments
-- ----------------------------
INSERT INTO `comments` VALUES (1, '2023-07-07 08:27:17', '2023-07-07 08:27:17', 'Các bạn đón chờ phần 2 nhé!\n', 1, 1);
INSERT INTO `comments` VALUES (2, '2023-07-07 08:42:03', '2023-07-07 08:42:03', 'amazing gút chóp em', 1, 2);
INSERT INTO `comments` VALUES (3, '2023-07-07 08:44:08', '2023-07-07 08:44:08', 'dfasdf', 1, 2);
INSERT INTO `comments` VALUES (4, '2023-07-07 09:07:57', '2023-07-07 09:07:57', 'bạn format lại nội dung bài viết cho đỡ đau mắt nhé damm bro', 3, 1);

-- ----------------------------
-- Table structure for favorites
-- ----------------------------
DROP TABLE IF EXISTS `favorites`;
CREATE TABLE `favorites`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime NULL DEFAULT NULL,
  `updated_at` datetime NULL DEFAULT NULL,
  `article_id` bigint NULL DEFAULT NULL,
  `user_id` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKg5bjjgr1bd2g8guv8125gts3s`(`article_id` ASC) USING BTREE,
  INDEX `FKk7du8b8ewipawnnpg76d55fus`(`user_id` ASC) USING BTREE,
  CONSTRAINT `FKg5bjjgr1bd2g8guv8125gts3s` FOREIGN KEY (`article_id`) REFERENCES `articles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKk7du8b8ewipawnnpg76d55fus` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of favorites
-- ----------------------------
INSERT INTO `favorites` VALUES (1, '2023-07-07 08:41:36', '2023-07-07 08:41:36', 1, 2);
INSERT INTO `favorites` VALUES (2, '2023-07-07 09:27:20', '2023-07-07 09:27:20', 2, 1);
INSERT INTO `favorites` VALUES (3, '2023-07-07 09:27:24', '2023-07-07 09:27:24', 1, 1);

-- ----------------------------
-- Table structure for follows
-- ----------------------------
DROP TABLE IF EXISTS `follows`;
CREATE TABLE `follows`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime NULL DEFAULT NULL,
  `updated_at` datetime NULL DEFAULT NULL,
  `followee_id` bigint NOT NULL,
  `follower_id` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `u_follow_followee_pair_must_be_unique`(`followee_id` ASC, `follower_id` ASC) USING BTREE,
  INDEX `FKjnqt4f5bti6niw7afunse4de7`(`follower_id` ASC) USING BTREE,
  CONSTRAINT `FKa58cq5w7xon5bn0m7e7n6lbwt` FOREIGN KEY (`followee_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKjnqt4f5bti6niw7afunse4de7` FOREIGN KEY (`follower_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of follows
-- ----------------------------
INSERT INTO `follows` VALUES (1, '2023-07-07 08:41:39', '2023-07-07 08:41:39', 1, 2);
INSERT INTO `follows` VALUES (2, '2023-07-07 09:27:22', '2023-07-07 09:27:22', 2, 1);

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES (1, 'ROLE_SUPER_ADMIN');
INSERT INTO `roles` VALUES (2, 'ROLE_ADMIN');
INSERT INTO `roles` VALUES (3, 'ROLE_USER');

-- ----------------------------
-- Table structure for tags
-- ----------------------------
DROP TABLE IF EXISTS `tags`;
CREATE TABLE `tags`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime NULL DEFAULT NULL,
  `updated_at` datetime NULL DEFAULT NULL,
  `tag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `article_id` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK2b4i2k07k9ecvfc94946uhat8`(`article_id` ASC) USING BTREE,
  CONSTRAINT `FK2b4i2k07k9ecvfc94946uhat8` FOREIGN KEY (`article_id`) REFERENCES `articles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tags
-- ----------------------------
INSERT INTO `tags` VALUES (2, '2023-07-07 08:26:36', '2023-07-07 08:26:36', 'docker', 1);
INSERT INTO `tags` VALUES (3, '2023-07-07 09:04:38', '2023-07-07 09:04:38', 'java', 2);
INSERT INTO `tags` VALUES (4, '2023-07-07 09:04:38', '2023-07-07 09:04:38', 'spring-boot', 2);
INSERT INTO `tags` VALUES (5, '2023-07-07 09:04:38', '2023-07-07 09:04:38', 'fix-bug', 2);
INSERT INTO `tags` VALUES (6, '2023-07-07 09:04:38', '2023-07-07 09:04:38', 'hỏi đáp', 2);
INSERT INTO `tags` VALUES (13, '2023-07-07 09:06:47', '2023-07-07 09:06:47', 'reactjs', 3);
INSERT INTO `tags` VALUES (14, '2023-07-07 09:06:47', '2023-07-07 09:06:47', 'javascript', 3);
INSERT INTO `tags` VALUES (15, '2023-07-07 09:06:47', '2023-07-07 09:06:47', 'fix-bug', 3);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime NULL DEFAULT NULL,
  `updated_at` datetime NULL DEFAULT NULL,
  `bio` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `image` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `otp` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, '2023-07-07 08:16:11', '2023-07-07 08:18:58', 'sugoiiii', 'tungxtnd0@gmail.com', 'https://wallpapers.com/images/hd/hacker-pictures-4xhiey685feo8stu.jpg', '12345678', 'tungdeptraii', 'ACTIVE', '917494');
INSERT INTO `users` VALUES (2, '2023-07-07 08:39:17', '2023-07-07 08:43:44', 'tommy - xiaomi', 'sogoj35350@kameili.com', 'https://images.unsplash.com/photo-1562860149-691401a306f8?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTF8fGhhY2tlcnxlbnwwfHwwfHx8MA%3D%3D&w=1000&q=80', '12345678', 'tommy1xaomi', 'ACTIVE', '856934');

-- ----------------------------
-- Table structure for users_roles_nn
-- ----------------------------
DROP TABLE IF EXISTS `users_roles_nn`;
CREATE TABLE `users_roles_nn`  (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE,
  INDEX `FK3l81w80fhpyeav0l6ual9uvw9`(`role_id` ASC) USING BTREE,
  CONSTRAINT `FK3l81w80fhpyeav0l6ual9uvw9` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKasbhg5qkd4qqyog36gtqbjp52` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime NULL DEFAULT NULL,
  `updated_at` datetime NULL DEFAULT NULL,
  `to_user_id` varchar(255) NOT NULL ,
  `message` varchar(255),
  `type` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `from_user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `post_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `comment_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `is_read` BOOLEAN NOT NULL DEFAULT FALSE,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of users_roles_nn
-- ----------------------------
INSERT INTO `users_roles_nn` VALUES (1, 1);
INSERT INTO `users_roles_nn` VALUES (1, 2);
INSERT INTO `users_roles_nn` VALUES (1, 3);
INSERT INTO `users_roles_nn` VALUES (2, 3);

SET FOREIGN_KEY_CHECKS = 1;
