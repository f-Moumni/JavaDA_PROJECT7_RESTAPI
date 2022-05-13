DELETE FROM users;
DELETE FROM curve_point;
DELETE FROM bid_list;
DELETE FROM rating;
DELETE FROM rule_name;
DELETE FROM trade;

INSERT INTO users(`id`,`fullname`, `password`, `role`, `username`) VALUES(1,'admin User', 'admin', '$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa', 'ADMIN');
INSERT INTO users(`id`,`fullname`, `password`, `role`, `username`) VALUES(2,'user User', 'user', '$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa', 'USER');

INSERT INTO trade (`trade_id`, `account`, `buy_quantity`) VALUES (1, 'account test',  33);
INSERT INTO trade (`trade_id`, `account`, `buy_quantity`) VALUES (2, 'account test',  20);

INSERT INTO rule_name (`id`, `description`, `json`, `name`, `sql_part`, `sql_str`, `template`) VALUES (1, 'Description test', 'json test', 'name', 'sql part test', 'sql test', 'template test');

INSERT INTO rating (`id`, `fitch_rating`, `moodys_rating`, `order_number`, `sandp_rating`) VALUES ( 3, 'fitch test', 'moddy test',2, 'sand rating test');
INSERT INTO rating (`id`, `fitch_rating`, `moodys_rating`, `order_number`, `sandp_rating`) VALUES ( 2, 'fitch test', 'moddy test',1, 'sand rating test');

INSERT INTO bid_list (`bid_list_id`, `account`, `ask`, `ask_quantity`, `bid`, `bid_quantity`) VALUES (1, 'account test', 22, 22, 22, 222);
INSERT INTO bid_list (`bid_list_id`, `account`, `ask`, `ask_quantity`, `bid`, `bid_quantity`) VALUES (2, 'account test', 33, 32, 34, 323);

INSERT INTO curve_point (`id`, `curve_id`, `term`, `value`) VALUES (1, 1, 11, 11);
