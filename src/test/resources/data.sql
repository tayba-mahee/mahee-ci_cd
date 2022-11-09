INSERT INTO "PERSON" VALUES
                         (1, DATE '3900-02-01', 'alex@baylor.com', 'Alex', 'Baylor University'),
                         (2, DATE '3901-02-01', 'becky@baylor.com', 'Becky', 'Baylor University'),
                         (3, DATE '3902-02-01', 'cathy@baylor.com', 'Cathy', 'Baylor University'),
                         (4, DATE '3900-02-01', 'dillan@baylor.com', 'Dillan', 'Baylor University'),
                         (5, DATE '3900-02-01', 'el@baylor.com', 'El', 'Baylor University'),
                         (6, DATE '3900-02-01', 'fiona@baylor.com', 'Fiona', 'Baylor University'),
                         (7, DATE '3904-02-01', 'gini@baylor.com', 'Gini', 'Baylor University'),
                         (8, DATE '3905-02-01', 'han@baylor.com', 'Han', 'Baylor University'),
                         (9, DATE '3900-02-01', 'ian@baylor.com', 'Ian', 'Baylor University'),
                         (10, DATE '3906-02-01', 'alex@baylor.com', 'Alex', 'Baylor University'),
                         (11, DATE '3900-02-01', 'alex@baylor.com', 'Alex', 'Baylor University');

INSERT INTO "TEAM" VALUES
                       (12, 'A', 0, 1, 11),
                       (13, 'B', 1, 0, 11),
                       (14, 'C', 2, 0, 11);

INSERT INTO "TEAM_MEMBER" VALUES
                              (12, 1),
                              (12, 2),
                              (12, 3),
                              (13, 4),
                              (13, 5),
                              (13, 6),
                              (14, 7),
                              (14, 8),
                              (14, 9);


INSERT INTO "CONTEST" VALUES
                          (1, 100, TIMESTAMP '3921-04-03 00:00:00', NULL, 'Final Contest', TRUE, TIMESTAMP '3921-04-01 00:00:00', TIMESTAMP '3921-04-01 00:00:00', NULL),
                          (2, 100, TIMESTAMP '3921-03-03 00:00:00', NULL, 'Regional Contest', TRUE, TIMESTAMP '3921-02-01 00:00:00', TIMESTAMP '3921-03-01 00:00:00', 1);

INSERT INTO "CONTEST_MANAGER" VALUES
                                  (1, 10),
                                  (2, 10);

INSERT INTO "CONTEST_TEAMS" VALUES
                                         (2, 12),
                                         (2, 13),
                                         (2, 14);

