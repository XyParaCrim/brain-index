select wm_main_page.*
from wm_main_page inner join wm_domain on wm_domain.domain_id = wm_main_page.domain_id
where wm_main_page.update_date >= :sql_last_value