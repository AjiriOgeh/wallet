truncate table wallets cascade;
truncate table wallets_transactions cascade;
truncate table transactions cascade;
truncate table users cascade;

insert into wallets(wallet_id, balance) values
    (100, '50000'),
    (101, '25000'),
    (102, '45000'),
    (103, '10000');

insert into transactions(transaction_id, amount, transaction_status, date) values
   (100, '50000', 'SUCCESS', '2024-10-20T10:41:55.377819600'),
   (101, '25000', 'ABANDONED', '2024-10-20T13:28:28.891123600'),
   (102, '45000', 'SUCCESS', '2024-10-20T15:12:19.943209600'),
   (103, '10000', 'SUCCESS', '2024-10-20T19:51:44.452218600');

insert into wallets_transactions(transactions_transaction_id, wallet_entity_wallet_id) values
   (100, 100),
   (101, 101),
   (102, 102),
   (103, 103);

insert into users(user_id, firstname, lastname, email, phone_number, time_created, wallet_id) values
    (100, 'alex', 'hunt', 'alexhunt@gmail.com', '08012345678', '2024-10-18T10:13:30.996779300', 100),
    (101, 'eva', 'price', 'evaprice@gmail.com', '08023456789', '2024-10-18T10:16:49.327239900', 101),
    (102, 'jake', 'ross', 'jakeross@gmail.com', '08034567890', '2024-10-18T10:18:15.426931500', 102),
    (103, 'zoe', 'davis', 'zoedavis@gmail.com', '08045678901', '2024-10-18T10:21:27.168261100', 103);

