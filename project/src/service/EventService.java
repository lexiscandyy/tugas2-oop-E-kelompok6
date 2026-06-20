package service;

import repository.EventRepository;
import repository.UserRepository;

public class EventService {
    private EventRepository eventRepo = new EventRepository();
    private UserRepository userRepo = new UserRepository();
}
