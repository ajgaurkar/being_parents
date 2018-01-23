package com.maakservicess.beingparents.app_monitor.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.maakservicess.beingparents.app_monitor.Adapters.CalendarEntryAdapter;
import com.maakservicess.beingparents.app_monitor.R;
import com.maakservicess.beingparents.app_monitor.activities.AddCalendarEvent;
import com.maakservicess.beingparents.app_monitor.activities.Doctor_visit_main;
import com.maakservicess.beingparents.app_monitor.controllers.CalendarEntryListData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by SAI PC on 4/20/2016.
 */
public class Calendar_Visits_Fragment extends Fragment {

    private List<CalendarEntryListData> calendarEntryList;
    Map<String, List<CalendarEntryListData>> entriesVisitMap = Doctor_visit_main.entriesVisitMap;
    public static CalendarEntryListData selectedVisitData;

    public Calendar_Visits_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.calendar_lower_fragment, container, false);

        ListView visitsListView = (ListView) rootView.findViewById(R.id.calendar_lower_list_view);
        TextView noDataTitleTextView = (TextView) rootView.findViewById(R.id.no_calendar_entry_available_title);
        TextView noDataBodyTextView = (TextView) rootView.findViewById(R.id.no_calendar_entry_available_body);


        calendarEntryList = new ArrayList<>();
        if (entriesVisitMap.size() == 0) {

            //no data available. SHOW text for no data
            noDataTitleTextView.setVisibility(View.VISIBLE);
            noDataBodyTextView.setVisibility(View.VISIBLE);
            noDataBodyTextView.setTypeface(noDataBodyTextView.getTypeface(), Typeface.BOLD_ITALIC);
            noDataTitleTextView.setTypeface(noDataTitleTextView.getTypeface(), Typeface.BOLD);

        } else {

            // data available. HIDE text for no data
            noDataTitleTextView.setVisibility(View.INVISIBLE);
            noDataBodyTextView.setVisibility(View.INVISIBLE);

            for (List<CalendarEntryListData> values : entriesVisitMap.values()) {

                System.out.println("Calendar_Visits_Fragment entriesVisitMap.values :" + values);
                for (int i = 0; i < values.size(); i++) {
                    calendarEntryList.add(values.get(i));
                }
            }

            System.out.println("Calendar_Visits_Fragment calendarEntryList.size() : " + calendarEntryList.size());
            CalendarEntryAdapter calendarEntryAdapter = new CalendarEntryAdapter(getActivity(), calendarEntryList);
            visitsListView.setAdapter(calendarEntryAdapter);
        }
        visitsListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selectedVisitData = calendarEntryList.get(position);

                        Intent addCalendarEntryIntent = new Intent(getActivity(), AddCalendarEvent.class);
                        addCalendarEntryIntent.putExtra("Category", "Visit");
                        addCalendarEntryIntent.putExtra("PageMode", "Read");
                        addCalendarEntryIntent.putExtra("SelectedCalId", String.valueOf(selectedVisitData.getId()));
                        startActivity(addCalendarEntryIntent);

                    }
                });


        return rootView;
    }


}
