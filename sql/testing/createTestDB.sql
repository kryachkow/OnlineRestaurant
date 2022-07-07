
DROP table if exists `role`;
CREATE TABLE `role` (

                        `id` int NOT NULL AUTO_INCREMENT,
                        `name` varchar(45) NOT NULL,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `name_UNIQUE` (`name`),
                        UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
DROP table if exists `user`;
CREATE TABLE `user` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `email` varchar(100) NOT NULL,
                        `name` varchar(50) NOT NULL,
                        `password` varchar(160) NOT NULL,
                        `phone` varchar(200) NOT NULL,
                        `address` varchar(255) NOT NULL,
                        `role_id` int DEFAULT '1',
                        `is_banned` tinyint DEFAULT '0',
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `email_UNIQUE` (`email`),
                        UNIQUE KEY `id_UNIQUE` (`id`),
                        UNIQUE KEY `phone_UNIQUE` (`phone`),
                        KEY `role_id_idx` (`role_id`),
                        CONSTRAINT `role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
DROP table if exists `order_status`;
CREATE TABLE `order_status` (
                                `id` int NOT NULL AUTO_INCREMENT,
                                `name` varchar(45) NOT NULL,
                                PRIMARY KEY (`id`),
                                UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
DROP table if exists `category`;
CREATE TABLE `category` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `name` varchar(45) NOT NULL,
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `id_UNIQUE` (`id`),
                            UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
DROP table if exists `dish`;
CREATE TABLE `dish` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `name` varchar(30) NOT NULL,
                        `ingredients` varchar(200) NOT NULL,
                        `weight` int NOT NULL,
                        `calories` int NOT NULL,
                        `price` int NOT NULL,
                        `category_id` int DEFAULT NULL,
                        `image_path` varchar(1000) DEFAULT NULL,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `id_UNIQUE` (`id`),
                        UNIQUE KEY `name_UNIQUE` (`name`),
                        KEY `catrgory_id_idx` (`category_id`),
                        CONSTRAINT `category_id` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
DROP table if exists `order`;
CREATE TABLE `order` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `user_id` int DEFAULT NULL,
                         `delivery_address` varchar(225) NOT NULL,
                         `status_id` int DEFAULT '1',
                         `total_price` int DEFAULT NULL,
                         `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         UNIQUE KEY `id_UNIQUE` (`id`),
                         KEY `user_order_id_idx` (`user_id`),
                         KEY `status_id_idx` (`status_id`),
                         CONSTRAINT `status_id` FOREIGN KEY (`status_id`) REFERENCES `order_status` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                         CONSTRAINT `user_order_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
DROP table if exists `cart_content`;
CREATE TABLE `cart_content` (
                                `id` int NOT NULL AUTO_INCREMENT,
                                `dish_id` int NOT NULL,
                                `order_id` int DEFAULT NULL,
                                `quantity` int NOT NULL,
                                `cart_price` int DEFAULT NULL,
                                PRIMARY KEY (`id`),
                                UNIQUE KEY `id_UNIQUE` (`id`),
                                KEY `dish_id_idx` (`dish_id`),
                                KEY `order_id_idx` (`order_id`),
                                CONSTRAINT `dish_id` FOREIGN KEY (`dish_id`) REFERENCES `dish` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                                CONSTRAINT `order_id` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
