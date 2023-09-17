package com.considlia.tictac.network;

import android.util.Log;

import com.considlia.tictac.models.Project;
import com.considlia.tictac.models.TicTactivity;
import com.considlia.tictac.models.TimeLog;
import com.considlia.tictac.models.User;
import com.considlia.tictac.utils.TicTacService;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class FakeService implements TicTacService {

    User user;
    List<Project> projects;

    @Override
    public User getUser(long userId) {
        User user = new User();
        user.setId(10000);
        user.setFirstName("Bill");
        user.setLastName("Weasley");
        user.setUsername("b.weasley");
        user.setEmail("bill.weasley@hogwarts.com");
        user.setPhone("999-989796");
        return user;
    }

    @Override
    public List<Project> getProjectsByUserId(long userId) {
        List<Project> projectList = new ArrayList<>();

        Project project = new Project();
        project.setId(1);
        project.setTitle("Transfiguration");

        List<TicTactivity> activityList = new ArrayList<>();
        TicTactivity activity1 = new TicTactivity();
        activity1.setId(1);
        activity1.setTitle("Transform to a cat");
        TimeLog timeLog1 = new TimeLog();
        timeLog1.setDuration(Duration.ofMinutes(30));
        timeLog1.setDate(LocalDate.of(2021, 5, 1));
        timeLog1.setReported(true);
        timeLog1.setSubmitted(false);
        activity1.setTimeLogs(new ArrayList<>(List.of(timeLog1)));
        activityList.add(activity1);
        TicTactivity activity2 = new TicTactivity();
        activity2.setId(2);
        activity2.setTitle("Transform a mug to a rat");
        TimeLog timeLog2 = new TimeLog();
        timeLog2.setDuration(Duration.ofHours(1));
        timeLog2.setDate(LocalDateTime.of(LocalDate.of(2021, 5, 1), LocalTime.of(12, 0)));
        timeLog2.setReported(true);
        timeLog2.setSubmitted(false);
        activity2.setTimeLogs(new ArrayList<>(List.of(timeLog2)));
        activityList.add(activity2);
        TicTactivity activity3 = new TicTactivity();
        activity3.setId(3);
        activity3.setTitle("Practice wand movements");
        TimeLog timeLog3 = new TimeLog();
        timeLog3.setDuration(Duration.ofHours(1));
        timeLog3.setDate(LocalDateTime.of(LocalDate.of(2021, 5, 1), LocalTime.of(15, 15)));
        timeLog3.setReported(false);
        timeLog3.setSubmitted(false);
        activity3.setTimeLogs(new ArrayList<>(List.of(timeLog3)));

        activityList.add(activity3);
        TicTactivity activity4 = new TicTactivity();
        activity4.setId(4);
        activity4.setTitle("Read 'A Beginner's Guide to Transfiguration' by Emeric Switch");
        TimeLog timeLog4 = new TimeLog();
        timeLog4.setDuration(Duration.ofMinutes(50));
        timeLog4.setDate(LocalDate.of(2021, 5, 13));
        timeLog4.setReported(false);
        timeLog4.setSubmitted(false);
        activity4.setTimeLogs(new ArrayList<>(List.of(timeLog4)));

        activityList.add(activity4);

        Project project1 = new Project();
        project1.setId(2);
        project1.setTitle("Potions");

        List<TicTactivity> activityListPotion = new ArrayList<>();

        TicTactivity activity5 = new TicTactivity();
        activity5.setId(5);
        activity5.setTitle("Make a love potion");
        TimeLog timeLog5 = new TimeLog();
        timeLog5.setDuration(Duration.ofHours(5));
        timeLog5.setDate(LocalDate.of(2021, 5, 15));
        timeLog5.setReported(true);
        timeLog5.setSubmitted(false);
        activity5.setTimeLogs(new ArrayList<>(List.of(timeLog5)));

        activityListPotion.add(activity5);

        TicTactivity activity6 = new TicTactivity();
        activity6.setId(6);
        activity6.setTitle("Make a Polyjuice Potion");
        TimeLog timeLog6 = new TimeLog();
        timeLog6.setDuration(Duration.ofDays(7));
        timeLog6.setDate(LocalDate.of(2021, 5, 17));
        timeLog6.setReported(true);
        timeLog6.setSubmitted(false);
        activity6.setTimeLogs(new ArrayList<>(List.of(timeLog6)));
        activityListPotion.add(activity6);

        Project project2 = new Project();
        project2.setId(3);
        project2.setTitle("Care of magical creatures");

        List<TicTactivity> activityListMagicalCreatures = new ArrayList<>();

        TicTactivity activity7 = new TicTactivity();
        activity7.setId(7);
        activity7.setTitle("Take care of Witherwings");
        TimeLog timeLog7 = new TimeLog();
        timeLog7.setDuration(Duration.ofHours(2));
        timeLog7.setDate(LocalDate.of(2021, 5, 5));
        timeLog7.setReported(false);
        timeLog7.setSubmitted(false);
        activity7.setTimeLogs(new ArrayList<>(new ArrayList<>(List.of(timeLog7))));
        activityListMagicalCreatures.add(activity7);

        TicTactivity activity8 = new TicTactivity();
        activity8.setId(8);
        activity8.setTitle("Feed Hedwig");
        TimeLog timeLog8 = new TimeLog();
        timeLog8.setDuration(Duration.ofMinutes(10));
        timeLog8.setDate(LocalDate.of(2021, 5, 5));
        timeLog8.setReported(true);
        timeLog8.setSubmitted(false);
        activity8.setTimeLogs(new ArrayList<>(List.of(timeLog8)));
        activityListMagicalCreatures.add(activity8);

        TicTactivity activity9 = new TicTactivity();
        activity9.setId(9);
        activity9.setTitle("Read about unicorns");
        TimeLog timeLog9 = new TimeLog();
        timeLog9.setDuration(Duration.ofMinutes(45));
        timeLog9.setDate(LocalDate.of(2021, 5, 8));
        timeLog9.setReported(false);
        timeLog9.setSubmitted(false);
        activity9.setTimeLogs(new ArrayList<>(List.of(timeLog9)));
        activityListMagicalCreatures.add(activity9);

        TicTactivity activity10 = new TicTactivity();
        activity10.setId(10);
        activity10.setTitle("Search for Aragog in the forbidden forest");
        TimeLog timeLog10 = new TimeLog();
        timeLog10.setDuration(Duration.ofDays(2));
        timeLog10.setDate(LocalDate.of(2021, 5, 14));
        timeLog10.setReported(true);
        timeLog10.setSubmitted(false);
        activity10.setTimeLogs(new ArrayList<>(List.of(timeLog10)));
        activityListMagicalCreatures.add(activity10);

        TicTactivity activity11 = new TicTactivity();
        activity11.setId(11);
        activity11.setTitle("Take a ride on Witherwings");
        TimeLog timeLog11 = new TimeLog();
        timeLog11.setDuration(Duration.ZERO);
        timeLog11.setDate(LocalDate.now());
        timeLog11.setReported(true);
        timeLog11.setSubmitted(false);
        activity11.setTimeLogs(new ArrayList<>(List.of(timeLog11)));
        activityListMagicalCreatures.add(activity11);

        project.setActivities(activityList);
        project1.setActivities(activityListPotion);
        project2.setActivities(activityListMagicalCreatures);
        projectList.add(project);
        projectList.add(project1);
        projectList.add(project2);
        Log.i("timeLogs fakeservice", projectList.toString());
        return (projectList);
    }

    @Override
    public TicTactivity getActivity(long activityId) {
        return null;
    }

    @Override
    public void updateActivity(TicTactivity ticTactivity) {

    }

    @Override
    public void deleteTimeLog(long id) {

    }

    @Override
    public TimeLog saveTimeLog(TimeLog timeLog) {
        return null;
    }
}
