package com.maakservicess.beingparents.app_monitor.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.maakservicess.beingparents.app_monitor.Adapters.CommonGrowthAdapter;
import com.maakservicess.beingparents.app_monitor.DatabaseHandler;
import com.maakservicess.beingparents.app_monitor.R;
import com.maakservicess.beingparents.app_monitor.SessionManager;
import com.maakservicess.beingparents.app_monitor.controllers.CommonGrowthListData;
import com.maakservicess.beingparents.app_monitor.controllers.GrowthSqliteData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Amey on 4/26/2016.
 */
public class Headcircumference extends Fragment {
    DatabaseHandler databaseHandler;
    private ListView userEnterDataListView;
    // ArrayList<GrowthHeadcircumferenceListData> headcircumferenceList;
    ArrayList<CommonGrowthListData> headcircumferenceList;
    //    GrowthHeadcircumferenceAdapter growthHeadcircumferenceAdapter;
    CommonGrowthAdapter growthHeadcircumferenceAdapter;
    SessionManager sessionManager;
    HashMap<String, String> babyDetailsMap;
    String gender_of_user;
    String parameter;
    String userEnter_Date;
    String userEnter_Headcircumference;
    Cursor headcircumferenceCursor;
    float prv = (float) 0.0;
    float calculatedHeadcircumfernce_diff;
    float head_c;
    private TextView noDataAvaiable;
    private Spinner editEntrySpineer;
    private TextView editEntryRangesTextView;
    List<String> editSpinnerValueList;
    private ArrayAdapter<String> editSpinnerValueAdapter;
    private String growth_record_id;
    private String category;
    DatabaseHandler database;
    private View mainViewForSanckBar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHandler = new DatabaseHandler(getActivity());
        sessionManager = new SessionManager(getActivity());
        babyDetailsMap = sessionManager.getBabyDetails();
        gender_of_user = babyDetailsMap.get(SessionManager.KEY_BABY_GENDER);
        //headcircumferenceList = new ArrayList<>();
        editSpinnerValueList = new ArrayList<>();
        database = new DatabaseHandler(getActivity());

        if (gender_of_user.equals("Baby Boy")) {
            gender_of_user = "b";
            parameter = "hc" + gender_of_user;
            System.out.println("parameter that to be send to database" + parameter);
        } else if (gender_of_user.equals("Baby Girl")) {
            gender_of_user = "g";
            parameter = "hc" + gender_of_user;
            System.out.println("parameter that to be send to database" + parameter);
        }

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_enter_data_fragment, container, false);
        userEnterDataListView = (ListView) rootView.findViewById(R.id.growth_user_entered_listView);
        noDataAvaiable = (TextView) rootView.findViewById(R.id.noDataAvailable_Textview);
        HeadCircumGrowthData();
//        userEnterDataListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                CommonGrowthListData growthHeadcircumferenceListData = headcircumferenceList.get(position);
//                ListviewAlertDialog(position, growthHeadcircumferenceListData);
//                System.out.println("List item click");
//            }
//        });

        userEnterDataListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CommonGrowthListData commonGrowthListData = headcircumferenceList.get(position);
//                if (position == headcircumferenceList.size()-1) {
//                    Toast.makeText(getActivity(), "Birth Values should not be changed", Toast.LENGTH_LONG).show();
//                } else {
//                    ListviewAlertDialog(position, growthHeadcircumferenceListData);
//                    System.out.println("List item click");
//                }

                if (position != headcircumferenceList.size() - 1) {
                    if (position != 0) {
                        ListviewAlertDialog(position, commonGrowthListData);
                        System.out.println("List item click");
                    } else {
                        // Toast.makeText(getActivity(), "Do not change value", Toast.LENGTH_LONG).show();
                        Snackbar.make(view, "Do not change value", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                } else {
//                    Toast.makeText(getActivity(), "Birth Values should not be changed", Toast.LENGTH_LONG).show();
                    Snackbar.make(view, "Birth Values should not be changed", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                }

            }
        });

        return rootView;
    }

    public void HeadCircumGrowthData() {
        headcircumferenceCursor = databaseHandler.getUserEnteredGrowthData(parameter);
        System.out.println("headcircumferenceCursor  ++  " + headcircumferenceCursor.getCount());
//        headcircumferenceList.clear();
        headcircumferenceList = new ArrayList<>();
        headcircumferenceCursor.moveToFirst();
        System.out.println("weightList.size()" + headcircumferenceList.size());

        if (headcircumferenceCursor.moveToFirst()) {
            noDataAvaiable.setVisibility(View.INVISIBLE);


            try {
                String temps = headcircumferenceCursor.getString(headcircumferenceCursor.getColumnIndex("userBaby_1"));
                prv = Float.parseFloat(temps);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Excetion caught in temps variable when stroing vale in variable.");
            }
            for (int loopK = 0; loopK < headcircumferenceCursor.getCount(); loopK++) {

                userEnter_Date = headcircumferenceCursor.getString(headcircumferenceCursor.getColumnIndex("entry_date"));
                userEnter_Headcircumference = headcircumferenceCursor.getString(headcircumferenceCursor.getColumnIndex("userBaby_1"));
                growth_record_id = headcircumferenceCursor.getString(headcircumferenceCursor.getColumnIndex("id"));
                category = headcircumferenceCursor.getString(headcircumferenceCursor.getColumnIndex("category"));
                head_c = Float.parseFloat(userEnter_Headcircumference);
                calculatedHeadcircumfernce_diff = head_c - prv;
                prv = head_c;
                userEnter_Headcircumference = userEnter_Headcircumference + " Cm";
                System.out.println("!!!!!!!!!!!!!!!!!   calculatedHeight_diff " + calculatedHeadcircumfernce_diff);
                String temp_Entry_date = userEnter_Date;
                System.out.println("!.temp_Entry_date " + temp_Entry_date);
                System.out.println("2.growth_record_id " + growth_record_id);
                System.out.println("3.userEnter_Date " + userEnter_Date);
                System.out.println("4.userEnter_Headcircumference " + userEnter_Headcircumference);
                System.out.println("5.calculatedHeadcircumfernce_diff " + calculatedHeadcircumfernce_diff);
                System.out.println("6.loopK " + loopK);
                System.out.println("7.category " + category);
                if (!temp_Entry_date.equals("null")) {
                    CommonGrowthListData commonGrowthListData = new CommonGrowthListData(growth_record_id, userEnter_Date, userEnter_Headcircumference, calculatedHeadcircumfernce_diff, loopK, category);
                    headcircumferenceList.add(0, commonGrowthListData);
                } else {
                    System.out.println("userEnter_Date is ZERO" + userEnter_Date);
                }
                headcircumferenceCursor.moveToNext();
            }
            growthHeadcircumferenceAdapter = new CommonGrowthAdapter(getActivity(), headcircumferenceList);
            userEnterDataListView.setAdapter(growthHeadcircumferenceAdapter);
        } else {
            noDataAvaiable.setVisibility(View.VISIBLE);
        }
    }

    public void ListviewAlertDialog(final int itemPostion, final CommonGrowthListData selectedGrowthHeadcircumferenceListDataListData) {
        LayoutInflater flater = getActivity().getLayoutInflater();
        View view = flater.inflate(R.layout.growth_main_entry_edit_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        editEntrySpineer = (Spinner) view.findViewById(R.id.editEntry);
        editEntryRangesTextView = (TextView) view.findViewById(R.id.editEntryRanges);


        for (int loophe = 32; loophe <= 52; loophe++) {
            String HeadCircumference;
            int int_HeadCircumference;
            int_HeadCircumference = (int) loophe;
            HeadCircumference = Integer.toString(int_HeadCircumference);
            editSpinnerValueList.add(HeadCircumference + " Cm");
        }
        editSpinnerValueAdapter = new ArrayAdapter<String>(getActivity(), R.layout.range_spinner_item, editSpinnerValueList);
        editSpinnerValueAdapter.setDropDownViewResource(R.layout.range_dropdownspinner_item);
        editEntrySpineer.setAdapter(editSpinnerValueAdapter);
        editEntrySpineer.setSelection(editSpinnerValueList.indexOf(selectedGrowthHeadcircumferenceListDataListData.getEnterData()));
        builder.setTitle("Add new ");
        System.out.println(" selectedGrowthHeightListData.getEnterDate()" + selectedGrowthHeadcircumferenceListDataListData.getEnterDate());
        System.out.println("selectedGrowthHeightListData.getEnterHeight()" + selectedGrowthHeadcircumferenceListDataListData.getEnterData());
        editEntryRangesTextView.setText(selectedGrowthHeadcircumferenceListDataListData.getEnterDate());

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String updatedHeadCirum = editEntrySpineer.getSelectedItem().toString();
                updatedHeadCirum = updatedHeadCirum.substring(0, updatedHeadCirum.length() - 2).trim();
                System.out.println("selectedGrowthWeightListData.getEnterDate()" + selectedGrowthHeadcircumferenceListDataListData.getEnterDate());
                System.out.println("updatedWieght" + updatedHeadCirum);
                System.out.println(" selectedGrowthHeightListData.getRecord_id()  " + selectedGrowthHeadcircumferenceListDataListData.getRecord_id());
                GrowthSqliteData userUpdateGrowthData = new GrowthSqliteData(null, updatedHeadCirum, null, selectedGrowthHeadcircumferenceListDataListData.getEnterDate(), null, selectedGrowthHeadcircumferenceListDataListData.getRecord_id());
                int updtedrecord = database.userUpdatedGrowthRecord(userUpdateGrowthData);
                System.out.println("updated Record @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ " + updtedrecord);
                 /*
                    After updating data WeightGrowthData Reccall method to refresh height list.
                 */
                HeadCircumGrowthData();
            }
        });
        builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println(" selectedGrowthHeightListData.getEnterDate()" + selectedGrowthHeadcircumferenceListDataListData.getRecord_id());
                GrowthSqliteData userDeletedGrowthData = new GrowthSqliteData(null, "0", null, null, null, selectedGrowthHeadcircumferenceListDataListData.getRecord_id());
                int updtedrecord = database.updateUserDeletedGrowth(userDeletedGrowthData);
                System.out.println("updated Record @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ " + updtedrecord);
                headcircumferenceList.remove(itemPostion);
                growthHeadcircumferenceAdapter.notifyDataSetChanged();
//                System.out.println(headcircumferenceList.size());

                HeadCircumGrowthData();
            }
        });
        builder.create().show();
    }


}
