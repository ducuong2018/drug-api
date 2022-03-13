use drug;
SET NAMES utf8mb4;
ALTER DATABASE drug CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci;
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
                            `sale_price` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                            `description` varchar(10000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                            `long_description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
                            `slug` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                            `create_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                            `update_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8mb3;
CREATE TABLE `images` (
                          `id` int NOT NULL AUTO_INCREMENT,
                          `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                          `product_id` int DEFAULT NULL,
                          `create_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                          `update_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                          PRIMARY KEY (`id`),
                          KEY `fk_image` (`product_id`),
                          CONSTRAINT `fk_image` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
CREATE TABLE `product_category` (
                                    `id` int NOT NULL AUTO_INCREMENT,
                                    `product_id` int DEFAULT NULL,
                                    `category_id` int DEFAULT NULL,
                                    `create_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                    `update_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                    PRIMARY KEY (`id`),
                                    KEY `fk_product` (`product_id`),
                                    KEY `fk_category` (`category_id`),
                                    CONSTRAINT `fk_category` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`),
                                    CONSTRAINT `fk_product` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;