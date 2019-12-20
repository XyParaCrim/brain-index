select
    wm_question.id,
    wm_question.subject,
    wm_question.status,
    wm_question.domain_id,
    wm_question.update_date,
    wm_question.preview_body,
    wm_question_map.container_id,
    wm_question_map.container_type,
    wm_question_map.source_flag
from wm_question join wm_question_map on wm_question_map.question_id = wm_question.id
where wm_question.update_date >= :sql_last_value