truncate table users cascade;
truncate table wallets cascade;

insert into users(user_id, firstname, lastname, email, phone_number, bank_verification_number, time_created) values
    (100, 'bugs', 'bunny', 'bugsbunny@gmail.com', '08012345678', '12345678901', '2024-10-18T10:13:30.996779300'),
    (101, 'porky', 'pig', 'porkypig@gmail.com', '08023456789', '23456789012', '2024-10-18T10:16:49.327239900');

insert into wallets(wallet_id, balance) values
    (100, '5000');

insert into transactions(transaction_id, amount, transaction_status, date) values
    (100, '5000', 'SUCCESS', '2024-10-20T10:32:08.643689600'),
    (101, '10000', 'SUCCESS', '2024-10-20T10:32:08.643689600'),
    (102, '15000', 'SUCCESS', '2024-10-21T12:26:45.376915400');

insert into wallets_transactions(transactions_transaction_id, wallet_entity_wallet_id) values
    (100, 100),
    (101, 100),
    (102, 100);


