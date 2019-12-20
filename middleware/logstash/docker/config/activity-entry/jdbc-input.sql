select *
from wm_activity_entry
where wm_activity_entry.update_date >= :sql_last_value
