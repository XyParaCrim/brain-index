select
    wm_document.id,
    wm_document.subject,
    wm_document.status,
    wm_document.domain_id,
    wm_document.update_date,
    wm_document.container_id,
    wm_document.container_type,
    wm_document.preview_body,
    wm_document.service_code
from wm_document
where wm_document.update_date >= :sql_last_value