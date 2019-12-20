select wump.*
from wm_user_main_page wump join wm_acct wa on wa.id = wump.acct_id
where wump.update_date >= :sql_last_value