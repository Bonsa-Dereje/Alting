INSERT INTO channels (channel_name, channel_username) VALUES
('Ethio College Prep', 'ethiocollegeprep'),
('Opportunity Alerts', 'opportunity_alerts'),
('AIESEC in Ethiopia', 'AIESEC_in_Ethiopia'),
('Rotaract Ethiopia', 'Rotaract_Ethiopia'),
('Rotaract Club of Hawassa', 'rachawassa'),
('Scholarships Corner', 'scholarshipscorner'),
('Scholarships & Fellowships', 'Scholarg4'),
('GlobeDock Scholar |Study Abroad Experts', 'GlobeDockConsultancy'),
('ETHIO SCHOLARSHIPS CONSULT', 'ethioscholarshipopportunity'),
('RiTZY YOUTH', 'curiousiTea')
ON CONFLICT (channel_username) DO NOTHING;