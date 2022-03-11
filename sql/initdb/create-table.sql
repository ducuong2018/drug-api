use drug;
CREATE TABLE `categories` (
                              `id` int NOT NULL AUTO_INCREMENT,
                              `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                              `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                              `level` int DEFAULT NULL,
                              `parent_id` int DEFAULT NULL,
                              `slug` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                              `create_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                              `update_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=260 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `products` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                            `price` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                            `sale_price`varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                            `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                            `create_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                            `update_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                            PRIMARY KEY (`id`)
);