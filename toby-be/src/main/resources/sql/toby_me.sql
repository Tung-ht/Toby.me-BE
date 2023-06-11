/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : localhost:3306
 Source Schema         : toby_me

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 11/06/2023 13:56:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for articles
-- ----------------------------
DROP TABLE IF EXISTS `articles`;
CREATE TABLE `articles`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime NULL DEFAULT NULL,
  `updated_at` datetime NULL DEFAULT NULL,
  `body` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `slug` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `title` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `author_id` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKe02fs2ut6qqoabfhj325wcjul`(`author_id` ASC) USING BTREE,
  CONSTRAINT `FKe02fs2ut6qqoabfhj325wcjul` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of articles
-- ----------------------------
INSERT INTO `articles` VALUES (1, '2023-03-12 11:37:33', '2023-03-12 11:37:33', 'Java là ngôn ngữ lập trình máy tính có tính chất hướng đối tượng, dựa trên các lớp, thường được sử dụng cho các hệ thống có tính độc lập cao. Nó được sử dụng để hướng tới các lập trình viên viết ứng dụng \"write one, run everywhere\" (viết một lần, chạy mọi nơi, nghĩa là đoạn code Java sau khi được biên dịch có thể chạy được trên tất cả các nền tảng hỗ trợ Java mà không cần phải được biên dịch lại. Các ứng dụng Java sau khi đã được biên dịch thành bytecode có thể chạy trên bất kỳ máy ảo Java nào (Java virtual machine)\n\nCho đến năm 2018, Java là một trong những ngôn ngữ được dùng phổ biến nhất trên thế giới, đặc biệt cho các úng dựng web client- server. Theo thống kê trên thế giới có khoảng 9 triệu lập trình viên Java\n\nCác bạn ở Hà Nội có thể xem các bạn khóa 6 được học gì, các bạn khóa 7 (đang tuyển sinh đến ngày 20/06) sẽ làm lớn hơn các project nhé Video một phần các dự án khóa 6 \n\nCác bạn ở xa học không có điều kiện thời gian có thể tham dự khóa Java online để chủ động cho việc học tập. Trong năm 2018, giá khóa học chỉ còn 200k, liên hệ facebook admin fb.com/tuyen.vietjack để thanh toán chuyển khoản hoặc thẻ điện thoại, khóa học bằng Tiếng Việt với gần 100 video, các bạn có thể chủ động bất cứ lúc nào, và xem mãi mãi. Thông tin khóa học tại Khóa học Java Online trên Udemy\n\nVietJackTeam sẽ hỗ trợ cho mọi người 8 videos miễn phí cho các bạn xem thử để các bạn quyết định nên tham gia khóa online hay offline tại Hà Nội hay không. Các bạn có thể xem tại địa chỉ Video demo\n\nGiới thiệu khóa học\n\nRegular Expression trong Java, cách validate email và cách trường đặc biệt.\n\nVòng lặp for trong Java, với các bài toán in hình đặc biệt\n\nConstructor 2- Thực hành quản lý tài khoản ngân hàng.\n\nPackage, cách tạo file Jar, import file Jar trong Java.\n\nXử lý BLOB data (cách ghi dữ liệu file vào Database) trong JDBC\n\nCollection Set trong Java, ý nghĩa hàm hashCode và equals trong Java\n\nCollection trong Java- Thực hành bài tập quản lý việc đặt ghế trong rạp phim.\n\n', 'Java là ngôn ngữ lập trình máy tính có tính chất hướng đối tượng, dựa trên các lớp, thường được sử dụng cho các hệ thống có tính độc lập cao. Nó được sử dụng để hướng tới các lập trình viên viết ứng dụng \"write one, run everywhere\" (viết một lần, chạy mọi nơi, nghĩa là đoạn code Java sau khi được biên dịch có thể chạy được trên tất cả các nền tảng hỗ trợ Java mà không cần phải được biên dịch lại. Các ứng dụng Java sau khi đã được biên dịch thành bytecode có thể chạy trên bất kỳ máy ảo Java nào (Java virtual machine)', 'Học-Java-cơ-bản-và-nâng-cao', 'Học Java cơ bản và nâng cao', 1);
INSERT INTO `articles` VALUES (2, '2023-03-12 18:32:06', '2023-05-16 20:51:38', 'HTML là viết tắt của Hyper Text Markup Language, là ngôn ngữ được sử dụng rộng rãi nhất trên thế giới để phát triển các trang Web.\n\nHTML bây giờ không còn xa lạ với bất kỳ sinh viên ngành công nghệ thông nào. Với HTML, bạn có thể tạo các trang Web từ đơn giản tới phức tạp, từ các trang hiển thị thông tin sinh viên đơn giản cho đến các trang bao gồm nhiều nghiệp vụ phức tạp. Đối với sinh viên, dù cho sau này định hướng là Back-End hay Front-End thì HTML là ngôn ngữ web bạn bắt buộc phải học.', 'HTML là viết tắt của Hyper Text Markup Language, là ngôn ngữ được sử dụng rộng rãi nhất trên thế giới để phát triển các trang Web.', 'Học-HTML-cơ-bản-và-nâng-c00-2023-05-16T20:51:38.049067100', 'Học HTML cơ bản và nâng c00', 2);
INSERT INTO `articles` VALUES (8, '2023-03-13 14:10:16', '2023-03-13 14:10:48', 'Ngôn ngữ lập trình: Java là một ngôn ngữ lập trình có tính bảo mật cao, hướng đối tượng, bậc cao và mạnh mẽ.\n\nPlatform: Bất cứ môi trường phần cứng hoặc phần mền nào mà trong đó một chương trình chạy, thì được biết đến như là một Platform. Với môi trường runtime riêng cho mình là JRE và API, Java được gọi là Platform.\n\nVí dụ về Java\nBạn theo dõi ví dụ đơn giản sau để in Hello World, phần giải thích chi tiết sẽ được trình bày trong chương tiếp theo.\n\nclass Simple{  \n  public static void main(String args[]){  \n   System.out.println(\"Hello World\");  \n    }  \n}  \nNơi Java được sử dụng?\nCó rất nhiều thiết bị hiện tại đang sử dụng Java. Bao gồm:\n\nDesktop App như media player, antivirus, reader, …\n\nWeb App như irctc.co.in, javatpoint.com, …\n\nEnterprise App như các ứng dụng về xử lý nghiệp vụ ngân hàng, …\n\nTrên các thiết bị Mobile.\n\nCác loại Java App\nCó 4 loại ứng dụng chính mà có thể được tạo bởi sử dụng ngôn ngữ lập trình Java:\n\nStandalone App\nNó còn được biết đến với tên gọi khác là Destop App hoặc Windows-based App. Một ứng dụng mà chúng ta cần cài đặt trên mỗi thiết bị như media player, antivirus, … AWT và Swing được sử dụng trong Java để tạo các Standalone App.\n\nWeb App\nMột ứng dụng mà chạy trên Server Side và tạo Dynamic Page, được gọi là Web App. Hiện tại, các công nghệ Servlet, JSP, Struts, JSF, … được sử dụng để tạo Web App trong Java.\n\nEnterprise App\nMột ứng dụng dạng như Banking App, có lợi thế là tính bảo mật cao, cân bằng tải (load balancing) và clustering. Trong java, EJB được sử dụng để tạo các Enterprise App.\n\nMobile App\nĐây là loại ứng dụng được tạo cho thiết bị mobile. Hiện tại thì Android và Java ME được sử dụng để tạo loại ứng dụng này.', 'Java là một Ngôn ngữ lập trình và là một Platform.', 'Java-là-gì-2023-03-13T14:10:47.929290400', 'Java là gì', 1);
INSERT INTO `articles` VALUES (9, '2023-03-13 17:35:03', '2023-03-13 17:35:30', 'demo', 'demo', 'huong-dan-html-2023-03-13T17:35:29.689972100', 'huong dan html', 1);

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
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comments
-- ----------------------------
INSERT INTO `comments` VALUES (1, '2023-03-12 18:23:32', '2023-03-12 18:23:32', 'bài viết hay lắm! cảm ơn tác giả', 1, 1);
INSERT INTO `comments` VALUES (8, '2023-03-13 14:07:18', '2023-03-13 14:07:18', 'hay lam', 2, 1);
INSERT INTO `comments` VALUES (9, '2023-03-13 17:35:11', '2023-03-13 17:35:11', 'cmt1\n', 9, 1);

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
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of favorites
-- ----------------------------
INSERT INTO `favorites` VALUES (3, '2023-03-12 18:31:03', '2023-03-12 18:31:03', 1, 2);
INSERT INTO `favorites` VALUES (14, '2023-03-13 17:34:17', '2023-03-13 17:34:17', 2, 1);
INSERT INTO `favorites` VALUES (15, '2023-04-25 00:10:51', '2023-04-25 00:10:51', 9, 2);
INSERT INTO `favorites` VALUES (16, '2023-06-05 23:27:19', '2023-06-05 23:27:19', 8, 2);

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
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of follows
-- ----------------------------
INSERT INTO `follows` VALUES (10, '2023-03-13 17:34:09', '2023-03-13 17:34:09', 2, 1);
INSERT INTO `follows` VALUES (11, '2023-05-16 20:51:03', '2023-05-16 20:51:03', 1, 2);

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
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tags
-- ----------------------------
INSERT INTO `tags` VALUES (1, '2023-03-12 11:37:33', '2023-03-12 11:37:33', 'java', 1);
INSERT INTO `tags` VALUES (2, '2023-03-12 18:32:06', '2023-03-12 18:32:06', 'html', 2);
INSERT INTO `tags` VALUES (4, '2023-03-13 14:10:16', '2023-03-13 14:10:16', 'java', 8);
INSERT INTO `tags` VALUES (5, '2023-03-13 17:35:03', '2023-03-13 17:35:03', 'html', 9);
INSERT INTO `tags` VALUES (6, '2023-03-13 17:35:03', '2023-03-13 17:35:03', 'css', 9);

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
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_6dotkott2kjsp8vw4d0m25fb7`(`email` ASC) USING BTREE,
  UNIQUE INDEX `UK_r43af9ap4edm43mmtq01oddj6`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, '2023-03-12 11:36:09', '2023-03-12 18:12:33', 'rất đẹp trai', 'tunght@gmail.com', 'https://scontent.fhan15-2.fna.fbcdn.net/v/t1.6435-9/90155461_2600448260185318_5783751734165766144_n.jpg?_nc_cat=100&ccb=1-7&_nc_sid=09cbfe&_nc_ohc=Ul8Fgo7GFO4AX8QvOKQ&_nc_oc=AQmrEGnseIDgOh_LIDYFCdVNe0qt2GXcXXOYPnkVpxEBw0KxiPS2BfDNqlZtQtyXLrrHU8zebVBrWgO9dPpGAZ65&_nc_ht=scontent.fhan15-2.fna&oh=00_AfDAjC8Mhk0iwYNl12CpmPjMznyPLNQOcs6Cq-lPOf8lXw&oe=6434E2B0', 'tunght@gmail.com', 'tunght184325');
INSERT INTO `users` VALUES (2, '2023-03-12 18:30:57', '2023-05-16 22:54:32', 'khá đẹp traiii', 'hieund@gmail.com', 'https://scontent.fhan15-2.fna.fbcdn.net/v/t1.6435-9/70699157_459001288016167_6392871611061501952_n.jpg?_nc_cat=110&ccb=1-7&_nc_sid=09cbfe&_nc_ohc=_6G9gxyEc5wAX8vsyiC&_nc_oc=AQmFgoaYSOqbjYL1PjbBAFM0r34kSuhOhJLUatT9FO598hXtcb8gHeXIrmyIXz2urWte-NvDeV1jJGxtEIWqTVyn&_nc_ht=scontent.fhan15-2.fna&oh=00_AfBda3WNJk8vQCJC2z7PbtxvoZzj_WsIq8CO94ZGjar8xQ&oe=64353315', 'hieund@gmail.com', 'hieunddd');

SET FOREIGN_KEY_CHECKS = 1;
