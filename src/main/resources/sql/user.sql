-- Инициализация групп
INSERT INTO usergroup("key", "name")
VALUES ('user',  'Пользователь'),
       ('admin', 'Администратор');

-- Сохранения набора операций по группам
INSERT INTO usergroup_useroperation(usergroupid, useroperation)
VALUES

    -- User
    ((SELECT usergroupid from usergroup where key = 'user'), 'TRANSACTION_LIST'),
    ((SELECT usergroupid from usergroup where key = 'user'), 'TRANSACTION'),

    ((SELECT usergroupid from usergroup where key = 'user'), 'CATEGORY'),

    ((SELECT usergroupid from usergroup where key = 'user'), 'ACCOUNT_LIST'),
    ((SELECT usergroupid from usergroup where key = 'user'), 'ACCOUNT'),


     -- Admin  
    ((SELECT usergroupid from usergroup where key = 'admin'), 'TRANSACTION_LIST'),
    ((SELECT usergroupid from usergroup where key = 'admin'), 'TRANSACTION'),
    ((SELECT usergroupid from usergroup where key = 'admin'), 'TRANSACTION_DELETE'),
    ((SELECT usergroupid from usergroup where key = 'admin'), 'TRANSACTION_CHANGE'),
    ((SELECT usergroupid from usergroup where key = 'admin'), 'TRANSACTION_CREATE'),

    ((SELECT usergroupid from usergroup where key = 'admin'), 'CATEGORY'),
    ((SELECT usergroupid from usergroup where key = 'admin'), 'CATEGORY_DELETE'),
    ((SELECT usergroupid from usergroup where key = 'admin'), 'CATEGORY_CHANGE'),
    ((SELECT usergroupid from usergroup where key = 'admin'), 'CATEGORY_CREATE'),

    ((SELECT usergroupid from usergroup where key = 'admin'), 'ACCOUNT_LIST'),
    ((SELECT usergroupid from usergroup where key = 'admin'), 'ACCOUNT'),
    ((SELECT usergroupid from usergroup where key = 'admin'), 'ACCOUNT_DELETE'),
    ((SELECT usergroupid from usergroup where key = 'admin'), 'ACCOUNT_CHANGE'),
    ((SELECT usergroupid from usergroup where key = 'admin'), 'ACCOUNT_CREATE'),

    ((SELECT usergroupid from usergroup where key = 'admin'), 'USER_LIST'),
    ((SELECT usergroupid from usergroup where key = 'admin'), 'USER'),
    ((SELECT usergroupid from usergroup where key = 'admin'), 'USER_DELETE'),
    ((SELECT usergroupid from usergroup where key = 'admin'), 'USER_CHANGE'),
    ((SELECT usergroupid from usergroup where key = 'admin'), 'USER_CREATE'),
    ((SELECT usergroupid from usergroup where key = 'admin'), 'USER_GROUP_LIST'),

    ((SELECT usergroupid from usergroup where key = 'admin'), 'USER_GROUP'),
    ((SELECT usergroupid from usergroup where key = 'admin'), 'USER_GROUP_DELETE'),
    ((SELECT usergroupid from usergroup where key = 'admin'), 'USER_GROUP_CHANGE'),
    ((SELECT usergroupid from usergroup where key = 'admin'), 'USER_GROUP_CREATE');

-- Инициализация пользователей системы
INSERT INTO "user"(enabled, username, password)
VALUES (TRUE, 'administrator', '$2a$10$meZ4VE74LFY./11d/soxWesFGj5MBaSb2Zbps7sfoE.ZpINfTT7Di'),
       (TRUE, 'user',          '$2a$10$meZ4VE74LFY./11d/soxWesFGj5MBaSb2Zbps7sfoE.ZpINfTT7Di');

-- Добавляем пользователя в группу
INSERT INTO user_usergroup(userid, usergroupid)
VALUES ((SELECT userid from "user" where username = 'user'),          (SELECT usergroupid from usergroup where key = 'user')),
       ((SELECT userid from "user" where username = 'administrator'), (SELECT usergroupid from usergroup where key = 'admin'));
