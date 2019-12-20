select
       domain_id,
       description,
       name,
       district_code,
       type,
       area_id,
       perm_type,
       name_pinyin
from wm_domain
where status = 'A' and update_date >= :sql_last_value
limit 0, 50