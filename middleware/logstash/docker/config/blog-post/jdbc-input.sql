select
    wm_blog_post.id,
    wm_blog_post.subject,
    wm_blog_post.status,
    wm_blog_post.domain_id,
    wm_blog_post.update_date,
    wm_blog_post.container_id,
    wm_blog_post.container_type,
    wm_blog_post.preview_body
from wm_blog_post
where wm_blog_post.update_date >= :sql_last_value