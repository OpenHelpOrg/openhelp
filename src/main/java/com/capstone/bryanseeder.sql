use openhelp_db;

#
# this seeder can be used to insert records into user_events
# user 1 should have 2 events on their profile
#one as an owner and another as an attendee
insert into user_event (is_creator, user_id, event_id) values (false,1,1);
insert into user_event (is_creator, user_id, event_id) values (true,1,2);