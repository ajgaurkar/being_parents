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
public class Height extends Fragment {
    DatabaseHandler databaseHandler;
    ListView userEnterGrowthDataListView;
    SessionManager sessionManager;
    HashMap<String, String> babyDetailsMap;
    String gender_of_user;
    String parameter;
    String growth_record_id;
    String userEnter_Date;
    String userEnter_Height;
    String category;
    Cursor heightCursor;
    float prv = (float) 0.0;
    float calculatedHeight_diff;
    float ht;
    ArrayList<CommonGrowthListData> heightList;
    // private GrowthHeightAdapter growthHeightAdapter;
    private CommonGrowthAdapter growthHeightAdapter;
    private TextView noDataAvaiable;
    private Spinner editEntrySpineer;
    private TextView editEntryRangesTextView;
    List<String> editSpinnerValueList;
    private ArrayAdapter<String> editSpinnerValueAdapter;
    DatabaseHandler database;
    private View mainViewForSanckBar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHandler = new DatabaseHandler(getActivity());
        sessionManager = new SessionManager(getActivity());
        babyDetailsMap = sessionManager.getBabyDetails();
        gender_of_user = babyDetailsMap.get(SessionManager.KEY_BABY_GENDER);
//        heightList = new ArrayList<>();
        editSpinnerValueList = new ArrayList<>();
        database = new DatabaseHandler(getActivity());

        if (gender_of_user.equals("Baby Boy")) {
            gender_of_user = "b";
            parameter = "h" + gender_of_user;
            System.out.println("parameter that to be send to database" + parameter);
        } else if (gender_of_user.equals("Baby Girl")) {
            gender_of_user = "g";
            parameter = "h" + gender_of_user;
            System.out.println("parameter that to be send to database" + parameter);
        }


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_enter_data_fragment, container, false);
        userEnterGrowthDataListView = (ListView) rootView.findViewById(R.id.growth_user_entered_listView);
        noDataAvaiable = (TextView) rootView.findViewById(R.id.noDataAvailable_Textview);
        HeightGrowthData();

//        heightCursor = databaseHandler.getUserEnteredGrowthData(parameter);
//        System.out.println("!!!!!!!!!!!!!!!!!  !!!!!!!!  heightCursor " + heightCursor.getCount());
//        heightList.clear();
//        heightCursor.moveToFirst();
//        System.out.println("weightList.size()" + heightList.size());
//        if (heightCursor.moveToFirst()) {
//            noDataAvaiable.setVisibility(View.INVISIBLE);
//
//
//            try {
//                String temps_height = heightCursor.getString(heightCursor.getColumnIndex("userBaby_1"));
//                prv = Float.parseFloat(temps_height);
//                System.out.println("prv" + prv);
//            } catch (Exception e) {
//                e.printStackTrace();
//                System.out.println("Excetion caught in temps variable when stroing vale in variable.");
//            }
//            for (int loopJ = 0; loopJ < heightCursor.getCount(); loopJ++) {
//                growth_record_id = heightCursor.getString(heightCursor.getColumnIndex("id"));
//                System.out.println("growth_table_id@@@@@@@@@@@@ " + growth_record_id);
//                userEnter_Date = heightCursor.getString(heightCursor.getColumnIndex("entry_date"));
//                userEnter_Height = heightCursor.getString(heightCursor.getColumnIndex("userBaby_1"));
//                category = heightCursor.getString(heightCursor.getColumnIndex("category"));
//                ht = Float.parseFloat(userEnter_Height);
//                calculatedHeight_diff = ht - prv;
//                prv = ht;
//                userEnter_Height = userEnter_Height + " Cm";
//                System.out.println("!!!!!!!!!!!!!!!!!   calculatedHeight_diff " + calculatedHeight_diff);
//                CommonGrowthListData commonGrowthListData = new CommonGrowthListData(growth_record_id, userEnter_Date, userEnter_Height, calculatedHeight_diff, loopJ, category);
//                heightList.add(0, commonGrowthListData);
//                heightCursor.moveToNext();
//            }
//            growthHeightAdapter = new CommonGrowthAdapter(getActivity(), heightList);
//            userEnterGrowthDataListView.setAdapter(growthHeightAdapter);
//        } else {
//            noDataAvaiable.setVisibility(View.VISIBLE);
//        }

//        userEnterGrowthDataListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                CommonGrowthListData commonGrowthListData = heightList.get(position);
//                ListviewAlertDialog(position, commonGrowthListData);
//                System.out.println("List item click");
//            }
//        });

        userEnterGrowthDataListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CommonGrowthListData commonGrowthListData = heightList.get(position);
                System.out.println("position------" + position);

//                if (position == heightList.size()-1) {
//                    Toast.makeText(getActivity(), "Birth Values should not be changed", Toast.LENGTH_LONG).show();
//                } else {
//                    ListviewAlertDialog(position, commonGrowthListData);
//                    System.out.println("List item click");
//                }

                if (position != heightList.size() - 1) {
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

    public void HeightGrowthData() {

        heightCursor = databaseHandler.getUserEnteredGrowthData(parameter);
        System.out.println("!!!!!!!!!!!!!!!!!  !!!!!!!!  heightCursor " + heightCursor.getCount());
        heightList = new ArrayList<>();
        heightCursor.moveToFirst();
        System.out.println("heightCursor.moveToFirst()" + heightCursor.moveToFirst());

        if (heightCursor.moveToFirst()) {
            noDataAvaiable.setVisibility(View.INVISIBLE);
            try {
                String temps_height = heightCursor.getString(heightCursor.getColumnIndex("userBaby_1"));
                prv = Float.parseFloat(temps_height);
                System.out.println("prv" + prv);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Excetion caught in temps variable when stroing vale in variable.");
            }
            for (int loopJ = 0; loopJ < heightCursor.getCount(); loopJ++) {
                growth_record_id = heightCursor.getString(heightCursor.getColumnIndex("id"));
                System.out.println("growth_table_id@@@@@@@@@@@@ " + growth_record_id);
                userEnter_Date = heightCursor.getString(heightCursor.getColumnIndex("entry_date"));
                userEnter_Height = heightCursor.getString(heightCursor.getColumnIndex("userBaby_1"));
                category = heightCursor.getString(heightCursor.getColumnIndex("category"));
                ht = Float.parseFloat(userEnter_Height);
                calculatedHeight_diff = ht - prv;
                prv = ht;
                userEnter_Height = userEnter_Height + " Cm";
                System.out.println("!!!!!!!!!!!!!!!!!   calculatedHeight_diff " + calculatedHeight_diff);
                String temp_Entry_date = userEnter_Date;
                if (!temp_Entry_date.equals("null")) {
                    CommonGrowthListData commonGrowthListData = new CommonGrowthListData(growth_record_id, userEnter_Date, userEnter_Height, calculatedHeight_diff, loopJ, category);
                    heightList.add(0, commonGrowthListData);
                } else {
                    System.out.println("userEnter_Date is ZERO" + userEnter_Date);
                }
                heightCursor.moveToNext();
            }
            growthHeightAdapter = new CommonGrowthAdapter(getActivity(), heightList);
            userEnterGrowthDataListView.setAdapter(growthHeightAdapter);
        } else {
            noDataAvaiable.setVisibility(View.VISIBLE);
        }

    }

    public void ListviewAlertDialog(final int itemPostion, final CommonGrowthListData selectedGrowthHeightListData) {
        LayoutInflater flater = getActivity().getLayoutInflater();
        View view = flater.inflate(R.layout.growth_main_entry_edit_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        editEntrySpineer = (Spinner) view.findViewById(R.id.editEntry);
        editEntryRangesTextView = (TextView) view.findViewById(R.id.editEntryRanges);

        for (int looph = 45; looph <= 120; looph++) {
            String Height;
            int int_Height;

            int_Height = (int) looph;
            Height = Integer.toString(int_Height);
            editSpinnerValueList.add(Height + " Cm");
        }
        editSpinnerValueAdapter = new ArrayAdapter<String>(getActivity(), R.layout.range_spinner_item, editSpinnerValueList);
        editSpinnerValueAdapter.setDropDownViewResource(R.layout.range_dropdownspinner_item);
        editEntrySpineer.setAdapter(editSpinnerValueAdapter);
        editEntrySpineer.setSelection(editSpinnerValueList.indexOf(selectedGrowthHeightListData.getEnterData()));
        builder.setTitle("Add new ");
        System.out.println(" selectedGrowthHeightListData.getEnterDate()" + selectedGrowthHeightListData.getEnterDate());
        System.out.println("selectedGrowthHeightListData.getEnterHeight()" + selectedGrowthHeightListData.getEnterData());
        editEntryRangesTextView.setText(selectedGrowthHeightListData.getEnterDate());

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String updatedHeight = editEntrySpineer.getSelectedItem().toString();
                updatedHeight = updatedHeight.substring(0, updatedHeight.length() - 2).trim();
                System.out.println("selectedGrowthWeightListData.getEnterDate()" + selectedGrowthHeightListData.getEnterDate());
                System.out.println("updatedWieght" + updatedHeight);
                System.out.println(" selectedGrowthHeightListData.getRecord_id()  " + selectedGrowthHeightListData.getRecord_id());
                GrowthSqliteData userUpdateGrowthData = new GrowthSqliteData(null, updatedHeight, null, selectedGrowthHeightListData.getEnterDate(), null, selectedGrowthHeightListData.getRecord_id());
                int updtedrecord = database.userUpdatedGrowthRecord(userUpdateGrowthData);
                System.out.println("updated Record @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ " + updtedrecord);
                /*
                    After updating data HeightGrowthData Reccall method to refresh height list.
                 */

                HeightGrowthData();
            }
        });
        builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                System.out.println(" selectedGrowthHeightListData.getEnterDate()" + selectedGrowthHeightListData.getRecord_id());
                GrowthSqliteData userDeletedGrowthData = new GrowthSqliteData(null, "0", null, null, null, selectedGrowthHeightListData.getRecord_id());
                int updtedrecord = database.updateUserDeletedGrowth(userDeletedGrowthData);
                System.out.println("updated Record @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ " + updtedrecord);
                heightList.remove(itemPostion);
                growthHeightAdapter.notifyDataSetChanged();
//                System.out.println(heightList.size());
                HeightGrowthData();

            }
        });
        builder.create().show();
    }


}
