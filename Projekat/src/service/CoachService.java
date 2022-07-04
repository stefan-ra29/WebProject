package service;

import com.google.gson.Gson;
import repository.CoachRepository;

public class CoachService {
    private CoachRepository coachRepository = new CoachRepository();
    Gson gson = new Gson();

    public boolean registerCoach(){

        return true;
    }
}
