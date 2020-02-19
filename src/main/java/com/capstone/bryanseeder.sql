use openhelp_db;

#
# this seeder can be used to insert records into user_events
# user 1 should have 2 events on their profile
#one as an owner and another as an attendee
insert into user_event (is_creator, user_id, event_id) values (false,1,1);
insert into user_event (is_creator, user_id, event_id) values (true,1,2);

insert into events (address, date_time, location, notes, summary, title, vol_limit) values ('testaddy', '2020-05-05T13:55', 'teatloc','testnot','testsumm','testtitle',5);

# drop database openhelp_db;

insert into user_event (id, is_creator, story, event_id, user_id)  values (1, false, null, 1,1);
insert into user_event (id, is_creator, story, event_id, user_id)  values (2, false, null, 2,1);

