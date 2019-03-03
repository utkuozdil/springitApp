INSERT INTO comment
(id, created_by, creation_date, last_modified_by, last_modified_date, body, link_id)
VALUES
(1, null, NOW(), null, NOW(), 'example comment', 1);

INSERT INTO link
(id, created_by, creation_date, last_modified_by, last_modified_date, title, url)
VALUES
(1, null, NOW(), null, NOW(), 'getting started', 'https://www.google.com');

INSERT INTO vote
(id, created_by, creation_date, last_modified_by, last_modified_date, vote)
VALUES
(1, null, NOW(), null, NOW(), 1);