select
    wm_file.id,
    wm_file.subject,
    wm_file.status,
    wm_file.domain_id,
    wm_file.update_date,
    wm_file.container_id,
    wm_file.container_type,

    wm_file_body.type as file_type,
    wm_file_body.file_name
from wm_file join wm_file_body on wm_file.id = wm_file_body.id
where wm_file.update_date >= :sql_last_value