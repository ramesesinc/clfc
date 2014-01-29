USE clfc2;

INSERT INTO sys_var(NAME, VALUE, description, datatype, category) VALUES('device_host_online', '121.97.60.200', 'Device online host address', NULL, 'SYSTEM');
INSERT INTO sys_var(NAME, VALUE, description, datatype, category) VALUES('device_host_offline', '121.97.60.200', 'Device offline host address', NULL, 'SYSTEM');
INSERT INTO sys_var(NAME, VALUE, description, datatype, category) VALUES('device_host_port', '8070', 'Device host port', NULL, 'SYSTEM');
INSERT INTO sys_var(NAME, VALUE, description, datatype, category) VALUES('device_timeout_session', '3', 'Session timeout (min)', NULL, 'SYSTEM');
INSERT INTO sys_var(NAME, VALUE, description, datatype, category) VALUES('device_timeout_upload', '5', 'Uploading interval (sec)', NULL, 'SYSTEM');
INSERT INTO sys_var(NAME, VALUE, description, datatype, category) VALUES('device_timeout_tracker', '10', 'Tracker posting interval (sec)', NULL, 'SYSTEM');

