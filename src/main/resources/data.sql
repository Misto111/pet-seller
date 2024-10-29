INSERT INTO users (id, active, email, first_name, last_name, password)
VALUES
    (1, 1, 'admin@example.com', 'Admin', 'Adminov', '95c1933b8ffe84f085f2839899d1673260be58dbd9c2c787ac35515805502c996417596dae9a92880aaa50a046fc7151'),
    (2, 1, 'user@example.com', 'User', 'Userov', '95c1933b8ffe84f085f2839899d1673260be58dbd9c2c787ac35515805502c996417596dae9a92880aaa50a046fc7151'),
    (3, 1, 'miroslavstoevv@gmail.com', 'Miroslav', 'Stoev', '95c1933b8ffe84f085f2839899d1673260be58dbd9c2c787ac35515805502c996417596dae9a92880aaa50a046fc7151');

INSERT INTO roles (`id`, `role`)
VALUES
    (1, 'ADMIN'),
    (2, 'USER');

INSERT INTO users_roles(`user_id`, `role_id`)
VALUES
    (1, 1),
    (1, 2),
    (2, 2);


    INSERT INTO kinds (`id`, `name`)
VALUES
    (1, 'Dog'),
    (2, 'Cat'),
    (3, 'Parrot');

INSERT INTO breeds (`id`, `category`, `kind_id`, `name`)
VALUES
    (1, 'DOG', 1, 'Golden Retriever'),
    (2, 'DOG', 1, 'Akita'),
    (3, 'CAT', 2, 'Balinese'),
    (4, 'DOG', 2, 'American Curl'),
    (5, 'PARROT', 3, 'Cockatiel');

INSERT INTO `offers` (`id`, `description`, `color`, `image_url`, `weight`, `price`, `gender`, `uuid`, `years`, `breed_id`, `seller_id`)
VALUES
(1, 'Crazy parrot 1!', 'WHITE', 'https://www.bubblypet.com/wp-content/uploads/2021/10/White-faced-cockatiel.jpg', 1, 60, 'FEMALE', 'b72e6550-e365-43bf-aab2-b57cafc2db7c', 2, 5, 1),
(2, 'Crazy parrot 1!', 'WHITE', 'https://www.bubblypet.com/wp-content/uploads/2021/10/White-faced-cockatiel.jpg', 1, 70, 'FEMALE', 'b72e6550-e365-43bf-aab2-b57cafc2db72', 2, 5, 1),
(3, 'Crazy parrot 2!', 'WHITE', 'https://www.bubblypet.com/wp-content/uploads/2021/10/White-faced-cockatiel.jpg', 1, 80, 'FEMALE', 'b72e6550-e365-43bf-aab2-b57cafc2db73', 2, 5, 2),
(4, 'Crazy parrot 2!', 'WHITE', 'https://www.bubblypet.com/wp-content/uploads/2021/10/White-faced-cockatiel.jpg', 1, 90, 'FEMALE', 'b72e6550-e365-43bf-aab2-b57cafc2db74', 2, 5, 2),
(5, 'Crazy parrot 2!', 'WHITE', 'https://www.bubblypet.com/wp-content/uploads/2021/10/White-faced-cockatiel.jpg', 1, 90, 'FEMALE', 'b72e6550-e365-43bf-aab2-b57cafc2db75', 2, 5, 2);

INSERT INTO `accessories-brands` (`id`, `name`)
VALUES
    (1, 'Hello Doggie'),
    (2, 'Prevue Hendryx');

INSERT INTO `accessories-models` (`id`, `category`, `accessory_brand_id`, `name`)
VALUES
    (1, 'BED', 1, 'Hello Doggie'),
    (2, 'CAGE', 2, 'Prevue Hendryx');

INSERT INTO `accessories` (`id`, `description`, `image_url`, `price`, `uuid`, `accessory_model_id`)
VALUES
    (1, 'Bed for big dog.', 'https://www.bfgcdn.com/600_600_90/870-2346-0111/croozer-rubber-securing-device.jpg', 60, 'b72e6550-e365-43bf-aab2-b57cafc2db71', 1),
    (2, 'Cage for parrots.', 'https://d3d71ba2asa5oz.cloudfront.net/12002419/images/d10-032_276__1.jpg', 70, 'b72e6550-e365-43bf-aab2-b57cafc2db73', 2);



