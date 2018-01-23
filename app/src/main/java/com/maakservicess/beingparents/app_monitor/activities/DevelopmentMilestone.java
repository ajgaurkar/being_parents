package com.maakservicess.beingparents.app_monitor.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.maakservicess.beingparents.app_monitor.Adapters.MilestonesAdapter;
import com.maakservicess.beingparents.app_monitor.R;
import com.maakservicess.beingparents.app_monitor.SessionManager;
import com.maakservicess.beingparents.app_monitor.controllers.MilestoneListData;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by SAI PC on 5/10/2016.
 */
public class DevelopmentMilestone extends AppCompatActivity {

    ListView milestoneListView;
    ArrayList<MilestoneListData> milestoneList = new ArrayList<>();
    MilestonesAdapter milestonesAdapter;
    SessionManager sessionManager;
    private String babyDOB;
    private AdView mAdViewHeader;
    private AdView mAdViewFooter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.development_milestones_main);

        milestoneListView = (ListView) findViewById(R.id.devMilestoneListView);
        sessionManager = new SessionManager(getApplicationContext());
        babyDOB = sessionManager.getSpecificBabyDetail(SessionManager.KEY_BABY_DOB);

        //two ads here. due to more space
        mAdViewHeader = (AdView) findViewById(R.id.dev_milestone_header_AdView);
        mAdViewFooter = (AdView) findViewById(R.id.dev_milestone_footer_AdView);
        AdRequest adRequestTop = new AdRequest.Builder().build();
        AdRequest adRequestBottom = new AdRequest.Builder().build();
        mAdViewHeader.loadAd(adRequestTop);
        mAdViewFooter.loadAd(adRequestBottom);

        milestoneList.add(new MilestoneListData(1, "Momentarily holds chin off couch in prone position", "Hand predominantly closed", null, null, "Follows moving objects, less than 90 degree", getCurrentRangeStatus(customizeDate(0, 0, 1, 0), customizeDate(-1, 0, 2, 0)), false));
        milestoneList.add(new MilestoneListData(2, "In prone position head in plane of body", "Hand frequently open", "coos", "Social smile (smile when talk)", "Follows objects 180 degree", getCurrentRangeStatus(customizeDate(0, 0, 2, 0), customizeDate(-1, 0, 3, 0)), false));
        milestoneList.add(new MilestoneListData(3, "Lifts head and chest, bears weight on forearms. ", "Reaches for objects , look at own hands", "Says aah, naah", null, null, getCurrentRangeStatus(customizeDate(0, 0, 3, 0), customizeDate(-1, 0, 4, 0)), false));
        milestoneList.add(new MilestoneListData(4, "Head steady. Sitting with support, when erect pushes with feet, hold chest and head off couch", "Reaches and grasps object and brings to mouth. Plays with own hands, plays with rattle when place in hand", null, "Laugh out loud, excited at the sight of food", "Turns head towards a sound at same level", getCurrentRangeStatus(customizeDate(0, 0, 4, 0), customizeDate(-1, 0, 5, 0)), false));
        milestoneList.add(new MilestoneListData(5, "Full head control", "Holds object with both hands deliberately (bidexterous grasp)", null, "Look at the things when fallen from hand", "Turns head towards a sound below the level", getCurrentRangeStatus(customizeDate(0, 0, 5, 0), customizeDate(-1, 0, 6, 0)), false));
        milestoneList.add(new MilestoneListData(6, "Lifts chest and abdomen off couch. Bears weight on extended arms. Rolls over from prone to supine ", "Grasps his feet and bring it to mouth. Hold bottle. Enjoys mirror image. Drinks from cup when it is held t lip.", null, "May protrude tongue as imitation, stranger anxiety, laugh with peep- boo (luka- chupi)game", null, getCurrentRangeStatus(customizeDate(0, 0, 6, 0), customizeDate(-1, 0, 7, 0)), false));
        milestoneList.add(new MilestoneListData(7, "Rolls from supine to prone. Weight bearing on one hand. Erect position – bounce actively ", "Transfer objects from one hand to other, take all objects to mouth, can feed self with biscuits. ", "Says da, ma, ba.", "Prefer mother. Resist s if toy is pulled from hand", "Turns head towards sound above the level", getCurrentRangeStatus(customizeDate(0, 0, 7, 0), customizeDate(-1, 0, 8, 0)), false));
        milestoneList.add(new MilestoneListData(8, "Sit alone, cruises", "Uncovers hidden toy, take objects from hand of other person. Grasps object with thumb and forefinger ", "Says mama, dada.", "Respond to sound of name. Respond to no. waves bye- bye. Plays peek a boo or pat a cake", null, getCurrentRangeStatus(customizeDate(0, 0, 8, 0), customizeDate(-1, 0, 9, 0)), false));
        milestoneList.add(new MilestoneListData(9, "Stands holding on to furniture. ", null, null, "Waves bye -bye", null, getCurrentRangeStatus(customizeDate(0, 0, 9, 0), customizeDate(-1, 0, 10, 0)), false));
        milestoneList.add(new MilestoneListData(10, "Pulls self to standing or sitting position. Crawls with abdomen on couch", "Picks up pellets neatly", "Can understand meaning of some words", "Pats a doll, can be placed on toilet seat", null, getCurrentRangeStatus(customizeDate(0, 0, 10, 0), customizeDate(-1, 0, 11, 0)), false));
        milestoneList.add(new MilestoneListData(11, "Creeps – abdomen off ground. Sitting can lean sideways, can turn around.  Walks with two hands held. ", "Rolls ball.", "Says one word with meaning", null, null, getCurrentRangeStatus(customizeDate(0, 0, 11, 0), customizeDate(-1, 0, 12, 0)), false));
        milestoneList.add(new MilestoneListData(12, "Walks with one hand held. Rises independently", "Feeds self with spoon with spilling. Throw object on request. Shakes head for no.", "2 to 3 words with meaning ", "Plays simple ball game. Mimicry. May kiss on request. ", null, getCurrentRangeStatus(customizeDate(0, 0, 12, 0), customizeDate(-1, 0, 3, 1)), false));
        milestoneList.add(new MilestoneListData(15, "Walk alone with broad base, crawls upstairs. Kneels without support.", "Feeds with spoon without spilling, can feed with cup with spillage. Makes line ", "Follows simple command. name object", "Ask for objects by pointing. Indicate wet pants. ", null, getCurrentRangeStatus(customizeDate(0, 0, 3, 1), customizeDate(-1, 0, 6, 1)), false));
        milestoneList.add(new MilestoneListData(18, "Walks upstairs with one hand held, walks normally, throws ball ", "Turns 2-3 pages at a time. Feed with cup without spilling. Manage spoon wel.", "Average 10 words, name one or two body parts", "Feeds self, tells when wet or soiled, dry by day. Copies mother in dusting, washing.", null, getCurrentRangeStatus(customizeDate(0, 0, 6, 1), customizeDate(-1, 0, 9, 1)), false));
        milestoneList.add(new MilestoneListData(21, "Walks backwards, walks upstairs with 2 feet per step. ", null, "Ask for food, drink & toilet. Knows 4 parts of body. Speaks two words together", "Obeys 3 simple orders", null, getCurrentRangeStatus(customizeDate(0, 0, 9, 1), customizeDate(-1, 0, 0, 2)), false));
        milestoneList.add(new MilestoneListData(24, "Runs well, walks up and down one step at a time, opens door, and climbs on furniture. ", "Horizontal stokes, turns pages one at a time, washes and dries hands. Turns door knobs, unscrews lids, kicks ball", "Speaks 3 words together, simple sentences. ", "Feeds with spoon well. Dry at night, wears socks or shoes, ", null, getCurrentRangeStatus(customizeDate(0, 0, 0, 2), customizeDate(-1, 0, 6, 2)), false));
        milestoneList.add(new MilestoneListData(30, "Goes upstairs with alternating feet. Jumps with both feet. ", "Holds pencil in hand", "Use pronoun I . knows full name, repeat 2 digits", null, null, getCurrentRangeStatus(customizeDate(0, 0, 6, 2), customizeDate(-1, 0, 0, 3)), false));
        milestoneList.add(new MilestoneListData(36, "Rides tricycles, walks upstairs with 1 foot per step and downstairs with 2 feet per step.", "Copies circle.", "Counts 3 objects, constantly ask questions, knows some nursery rhymes", "Knows age and sex, help in dressing. ", null, getCurrentRangeStatus(customizeDate(0, 0, 0, 3), customizeDate(-1, 0, 0, 4)), false));
        milestoneList.add(new MilestoneListData(48, "Hops on one foot, goes downstairs with one step at a time", "Copies a square", "Counts 4 number, tells a story", "Goes to toilet alone, right left discrimination. Play with doll, can button clothes fully. ", null, getCurrentRangeStatus(customizeDate(0, 0, 0, 4), customizeDate(-1, 0, 0, 5)), false));
        milestoneList.add(new MilestoneListData(60, "skips", "Copies a triangles", "Counts to 10 , repeats 4 digits, names 4 colors ", "Dresses and undresses, ask meaning of words", null, getCurrentRangeStatus(customizeDate(0, 0, 0, 5), customizeDate(-1, 0, 6, 5)), false));
        milestoneList.add(new MilestoneListData(66, null, null, "Repeats 5 digits", "Knows number of fingers, names week days", null, getCurrentRangeStatus(customizeDate(0, 0, 6, 5), customizeDate(-1, 0, 0, 10)), false));

        int listViewSelection = 0;
        for (MilestoneListData loopData : milestoneList) {
            if (loopData.getMileCurrentRangeStatus()) {
                loopData.setExpandStatus(true);
                listViewSelection = milestoneList.indexOf(loopData);
                break;
            }
        }
        milestonesAdapter = new MilestonesAdapter(getApplicationContext(), milestoneList);
        milestoneListView.setAdapter(milestonesAdapter);
        milestoneListView.setSelection(listViewSelection);

        milestoneListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MilestoneListData selectedMilestoneListData = milestoneList.get(position);
                //logic to toggle expanded status boolean value
                selectedMilestoneListData.setExpandStatus(!selectedMilestoneListData.getExpandStatus());
                milestonesAdapter.notifyDataSetChanged();
            }
        });
        //ad listener to set visibility of banner
        mAdViewHeader.setAdListener(new AdListener() {

            @Override
            public void onAdLoaded() {
                mAdViewHeader.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(int error) {
                mAdViewHeader.setVisibility(View.GONE);
            }

        });
        //ad listener to set visibility of banner
        mAdViewFooter.setAdListener(new AdListener() {

            @Override
            public void onAdLoaded() {
                mAdViewFooter.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(int error) {
                mAdViewFooter.setVisibility(View.GONE);
            }

        });
    }

    private boolean getCurrentRangeStatus(String toDate, String fromDate) {

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy");
        Calendar Todayscalendar = Calendar.getInstance();
        Date rangeTo = null;
        Date rangeFrom = null;
        Date currentDate = new Date();
        try {
            rangeFrom = format.parse(fromDate);
            rangeTo = format.parse(toDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("currentDate.compareTo(rangeFrom) : " + currentDate.compareTo(rangeFrom));
        System.out.println("currentDate.compareTo(rangeTo) : " + currentDate.compareTo(rangeTo));
        System.out.println("currentDate.compareTo(rangeFrom) <= 0 && currentDate.compareTo(rangeTo) >= 0 : " + (currentDate.compareTo(rangeFrom) <= 0 && currentDate.compareTo(rangeTo) >= 0));
        if (currentDate.compareTo(rangeFrom) <= 0 && currentDate.compareTo(rangeTo) >= 0) {
            return true;
        } else {
            return false;
        }
    }

    //LOGIC FOR CUSTOM DATE INSERTION
    private String customizeDate(int day, int week, int month, int year) {

        //  System.out.println("babyDOB for custom date entry : " + babyDOB);
        String customdate = null;

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy");
        try {
            Date date = format.parse(babyDOB);
            //System.out.println("regular : " + date);

            Calendar c = Calendar.getInstance();
            c.setTime(date);

// setting custom values
            c.add(Calendar.DATE, day);
            //day is substracted (or add -1) for "range to" to be one less than next "range from"
            c.add(Calendar.DATE, (week * 7));
            c.add(Calendar.MONTH, month);
            c.add(Calendar.YEAR, year);

            Date resultdate = new Date(c.getTimeInMillis());
            //  System.out.println("resultdate : " + resultdate);
            customdate = format.format(resultdate);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("customdate :" + customdate);
        return customdate;
    }
}
