-- Индексы на аккаунт
create unique index account_name_uidx on account (name);
create index account_type_idx on account (type);
create index account_created_idx on account (created DESC NULLS LAST);


-- Индексы на категорию
create unique index category_name_uidx on category (name);
create index category_created_idx on category (created DESC NULLS LAST);


-- Индексы на транзакции
create index transaction_processdate_idx on transaction (created DESC NULLS LAST);
create index transaction_type_idx on transaction (type);
-- и на их FK - postgres по дефолту такое не делает
create index transaction_accountid_idx on transaction (accountid);
create index transaction_categoryid_idx on transaction (categoryid);


-- Индексы на группы
create unique index usergroup_key_uidx on usergroup (key);
create index usergroup_useroperation_usergroupid_idx on usergroup_useroperation(usergroupid);

-- Много ко многих на пользователей и группы
create index user_usergroup_usergroup_idx on user_usergroup(usergroupid);
create index user_usergroup_user_idx on user_usergroup(userid);

-- Индексы на пользователей
create unique index user_username_uidx on "user"(username);
