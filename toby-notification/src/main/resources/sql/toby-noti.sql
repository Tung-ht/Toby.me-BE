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
