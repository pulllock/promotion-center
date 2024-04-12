-- rule
INSERT INTO rule (id, create_time, update_time, name, description, type, scope, mode, status, start_time, end_time, channel, exclusive, `order`, rules) VALUES (1, now(), now(), '单商品优惠券', null, 1, 1, 1, 1, null, null, '["all"]', 0, 1, null);
INSERT INTO rule (id, create_time, update_time, name, description, type, scope, mode, status, start_time, end_time, channel, exclusive, `order`, rules) VALUES (2, now(), now(), '多商品优惠券', null, 1, 1, 2, 1, null, null, '["all"]', 0, 2, null);
INSERT INTO rule (id, create_time, update_time, name, description, type, scope, mode, status, start_time, end_time, channel, exclusive, `order`, rules) VALUES (3, now(), now(), '单商户优惠券', null, 1, 2, 1, 1, null, null, '["all"]', 0, 3, null);
INSERT INTO rule (id, create_time, update_time, name, description, type, scope, mode, status, start_time, end_time, channel, exclusive, `order`, rules) VALUES (4, now(), now(), '多商户优惠券', null, 1, 2, 2, 1, null, null, '["all"]', 0, 4, null);
INSERT INTO rule (id, create_time, update_time, name, description, type, scope, mode, status, start_time, end_time, channel, exclusive, `order`, rules) VALUES (5, now(), now(), '单类目优惠券', null, 1, 3, 1, 1, null, null, '["all"]', 0, 5, null);
INSERT INTO rule (id, create_time, update_time, name, description, type, scope, mode, status, start_time, end_time, channel, exclusive, `order`, rules) VALUES (6, now(), now(), '多类目优惠券', null, 1, 3, 2, 1, null, null, '["all"]', 0, 6, null);
INSERT INTO rule (id, create_time, update_time, name, description, type, scope, mode, status, start_time, end_time, channel, exclusive, `order`, rules) VALUES (7, now(), now(), '单商品满减', null, 2, 1, 1, 1, null, null, '["all"]', 0, 7, '[{"threshold":100,"discount":30}]');

-- coupon_rule
INSERT INTO coupon_rule (id, create_time, update_time, rule_id, name, description, rule_description, status, redirect_url, channel, type, validity_type, validity_start_time, validity_end_time, validity_days, claim_start_time, claim_end_time, total, claimed, user_total, user_daily_total, threshold, discount, exclusive) VALUES (1, now(), now(), 1, '单商品满30减10', null, null, 1, '#', '["all"]', 1, 1, null, null, null, null, null, 1000, 0, 10, 2, 30, 10, 0);
INSERT INTO coupon_rule (id, create_time, update_time, rule_id, name, description, rule_description, status, redirect_url, channel, type, validity_type, validity_start_time, validity_end_time, validity_days, claim_start_time, claim_end_time, total, claimed, user_total, user_daily_total, threshold, discount, exclusive) VALUES (2, now(), now(), 2, '多商品满40减20', null, null, 1, '#', '["all"]', 1, 1, null, null, null, null, null, 0, 0, 0, 0, 40, 20, 0);

-- rule_target
INSERT INTO rule_target (id, create_time, update_time, rule_type, rule_id, target_id, target_type) VALUES (1, now(), now(), 1, 1, 1, 1);
INSERT INTO rule_target (id, create_time, update_time, rule_type, rule_id, target_id, target_type) VALUES (2, now(), now(), 1, 2, 2, 1);
INSERT INTO rule_target (id, create_time, update_time, rule_type, rule_id, target_id, target_type) VALUES (3, now(), now(), 1, 2, 3, 1);
INSERT INTO rule_target (id, create_time, update_time, rule_type, rule_id, target_id, target_type) VALUES (4, now(), now(), 1, 2, 4, 1);
INSERT INTO rule_target (id, create_time, update_time, rule_type, rule_id, target_id, target_type) VALUES (5, now(), now(), 2, 7, 1, 1);
