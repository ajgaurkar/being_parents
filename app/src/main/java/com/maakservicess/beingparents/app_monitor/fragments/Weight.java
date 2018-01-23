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

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Amey on 4/26/2016.
 */
public class Weight extends Fragment {

    DatabaseHandler databaseHandler;
    ListView userEnterGrowthDataListView;
    SessionManager sessionManager;
    HashMap<String, String> babyDetailsMap;
    String gender_of_user;
    String parameter;
    private Cursor weightCursor;
    String userEnter_Date;
    String userEnter_Weight;
    //    ArrayList<GrowthWeightListData> weightList;
    ArrayList<CommonGrowthListData> weightList;
    // private GrowthWeightAdapter growthWeightAdapter;
    private CommonGrowthAdapter growthWeightAdapter;
    private String variablefordiff;
    float prv = (float) 0.0;
    float calculatedWeight_diff;
    float wt;
    private TextView noDataAvaiable;
    private View mainViewForSanckBar;
    private Spinner editEntrySpineer;
    private TextView editEntryRangesTextView;
    List<String> editSpinnerValueList;
    private ArrayAdapter<String> editSpinnerValueAdapter;
    private String growth_record_id;
    private String category;
    DatabaseHandler database;
    private List<Float> userValuesList;
    private List<String> userValuesIDList;
    private ArrayList<String> queryParamsArrayList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseHandler = new DatabaseHandler(getActivity());
        sessionManager = new SessionManager(getActivity());
        babyDetailsMap = sessionManager.getBabyDetails();
        gender_of_user = babyDetailsMap.get(SessionManager.KEY_BABY_GENDER);
        database = new DatabaseHandler(getActivity());

        queryParamsArrayList = new ArrayList<>();
        editSpinnerValueList = new ArrayList<>();

        if (gender_of_user.equals("Baby Boy")) {
            gender_of_user = "b";
            parameter = "w" + gender_of_user;
            System.out.println("parameter that to be send to database" + parameter);
            queryParamsArrayList.add(parameter);

        } else if (gender_of_user.equals("Baby Girl")) {
            gender_of_user = "g";
            parameter = "w" + gender_of_user;
            System.out.println("parameter that to be send to database" + parameter);
            queryParamsArrayList.add(parameter);

        }


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_enter_data_fragment, container, false);
        userEnterGrowthDataListView = (ListView) rootView.findViewById(R.id.growth_user_entered_listView);
        noDataAvaiable = (TextView) rootView.findViewById(R.id.noDataAvailable_Textview);


        WeightGrowthData();

//        userEnterGrowthDataListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                CommonGrowthListData commonGrowthListData = weightList.get(position);
//                ListviewAlertDialog(position, commonGrowthListData);
//                System.out.println("List item click");
//            }
//        });

        userEnterGrowthDataListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CommonGrowthListData commonGrowthListData = weightList.get(position);
//                if (position == weightList.size() - 1) {
//                    Toast.makeText(getActivity(), "Birth Values should not be changed", Toast.LENGTH_LONG).show();
//                } else {
//                    ListviewAlertDialog(position, commonGrowthListData);
//                    System.out.println("List item click");
//                }

                if (position != weightList.size() - 1) {
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

    public void WeightGrowthData() {
        weightCursor = databaseHandler.getUserEnteredGrowthData(parameter);
        System.out.println("weightCursor.getCount()" + weightCursor.getCount());
        weightList = new ArrayList();

        if (weightCursor.moveToFirst()) {
            noDataAvaiable.setVisibility(View.INVISIBLE);

            try {
                String temps = weightCursor.getString(weightCursor.getColumnIndex("userBaby_1"));
                prv = Float.parseFloat(temps);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Excetion caught in temps variable when stroing vale in variable.");
            }
            System.out.println("prv" + prv);
            for (int loopI = 0; loopI < weightCursor.getCount(); loopI++) {
                userEnter_Date = weightCursor.getString(weightCursor.getColumnIndex("entry_date"));
                userEnter_Weight = weightCursor.getString(weightCursor.getColumnIndex("userBaby_1"));
                growth_record_id = weightCursor.getString(weightCursor.getColumnIndex("id"));
                category = weightCursor.getString(weightCursor.getColumnIndex("category"));

//                if (userEnter_Date.equals("null")) {
//                    System.out.println(loopI + "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
//                }
//                if (userEnter_Date == null) {
//                    System.out.println(loopI + "*******************************************************************************************************************");
//                }


                wt = Float.parseFloat(userEnter_Weight);
                calculatedWeight_diff = wt - prv;
                prv = wt;
                userEnter_Weight = userEnter_Weight + " Kg";
                System.out.println("!!!!!!!!!!!!!!!!!  !!!!!!!!  calculatedWeight_diff " + calculatedWeight_diff);
                // GrowthWeightListData weightListData = new GrowthWeightListData(userEnter_Date, userEnter_Weight, calculatedWeight_diff, loopI);
                String temp_Entry_date = userEnter_Date;
                if (!temp_Entry_date.equals("null")) {
                    CommonGrowthListData commonGrowthListData = new CommonGrowthListData(growth_record_id, userEnter_Date, userEnter_Weight, calculatedWeight_diff, loopI, category);
                    weightList.add(0, commonGrowthListData);
                } else {
                    System.out.println("userEnter_Date is ZERO" + userEnter_Date);
                }
                weightCursor.moveToNext();
            }
            growthWeightAdapter = new CommonGrowthAdapter(getActivity(), weightList);
            userEnterGrowthDataListView.setAdapter(growthWeightAdapter);
            System.out.println("!!!!!!!!!!!!!!!!!  !!!!!!!!  weightCursor " + weightCursor.getCount());

        } else {
            noDataAvaiable.setVisibility(View.VISIBLE);
        }
    }

    public void ListviewAlertDialog(final int itemPostion, final CommonGrowthListData selectedGrowthWeightListData) {
        LayoutInflater flater = getActivity().getLayoutInflater();
        View view = flater.inflate(R.layout.growth_main_entry_edit_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        editEntrySpineer = (Spinner) view.findViewById(R.id.editEntry);
        editEntryRangesTextView = (TextView) view.findViewById(R.id.editEntryRanges);

        for (double loopw = 1; loopw <= 30; loopw = loopw + 0.5) {
            String Weight;
            float float_Weight;
            float_Weight = (float) (loopw);
            Weight = Float.toString(float_Weight);
            editSpinnerValueList.add(Weight + " Kg");
        }
        editSpinnerValueAdapter = new ArrayAdapter<String>(getActivity(), R.layout.range_spinner_item, editSpinnerValueList);
        editSpinnerValueAdapter.setDropDownViewResource(R.layout.range_dropdownspinner_item);
        editEntrySpineer.setAdapter(editSpinnerValueAdapter);
        editEntrySpineer.setSelection(editSpinnerValueList.indexOf(selectedGrowthWeightListData.getEnterData()));
        builder.setTitle("Add new ");
        System.out.println(" selectedGrowthHeightListData.getEnterDate()" + selectedGrowthWeightListData.getEnterDate());
        System.out.println("selectedGrowthHeightListData.getEnterHeight()" + selectedGrowthWeightListData.getEnterData());
        editEntryRangesTextView.setText(selectedGrowthWeightListData.getEnterDate());

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String updatedWieght = editEntrySpineer.getSelectedItem().toString();
                updatedWieght = updatedWieght.substring(0, updatedWieght.length() - 2).trim();
                System.out.println("selectedGrowthWeightListData.getEnterDate()" + selectedGrowthWeightListData.getEnterDate());
                System.out.println("updatedWieght" + updatedWieght);
                GrowthSqliteData userUpdateGrowthData = new GrowthSqliteData(null, updatedWieght, null, selectedGrowthWeightListData.getEnterDate(), null, selectedGrowthWeightListData.getRecord_id());
                int updtedrecord = database.updateUserDeletedGrowth(userUpdateGrowthData);
                System.out.println("updated Record @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ " + updtedrecord);
//                growthWeightAdapter.notifyDataSetChanged();
//                System.out.println(weightList.size());
                WeightGrowthData();
//                setAverageOnMissingValues();
            }
        });
        builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println(" selectedGrowthHeightListData.getRecord_id()  " + selectedGrowthWeightListData.getRecord_id());

                GrowthSqliteData userDeletedGrowthData = new GrowthSqliteData(null, "0", null, null, null, selectedGrowthWeightListData.getRecord_id());
                int updtedrecord = database.updateUserDeletedGrowth(userDeletedGrowthData);
                System.out.println("updated Record @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ " + updtedrecord);
                weightList.remove(itemPostion);
                growthWeightAdapter.notifyDataSetChanged();
//                System.out.println(weightList.size());

                WeightGrowthData();
//                setAverageOnMissingValues();

            }
        });
        builder.create().show();
    }

    private void setAverageOnMissingValues() {
        Cursor averageCursorData = database.getAllGrowthDataByCursor(queryParamsArrayList);

        Boolean upperLimitOfListFoundStatus = false;
        userValuesList = new ArrayList<>();
        userValuesIDList = new ArrayList<>();
        if (averageCursorData.moveToLast()) {
            do {
                String userPercentileIdForUpdate = averageCursorData.getString(averageCursorData.getColumnIndex("id"));
                String userPercentilesForUpdate = averageCursorData.getString(averageCursorData.getColumnIndex("userBaby_1"));
                System.out.println("userPercentilesForUpdate : " + userPercentileIdForUpdate + " " + userPercentilesForUpdate);

                if (!upperLimitOfListFoundStatus) {
                    if (averageCursorData.getString(averageCursorData.getColumnIndex("userBaby_1")).equals("0")) {

                        upperLimitOfListFoundStatus = true;
                    }
                }
                if (upperLimitOfListFoundStatus) {
                    if ((averageCursorData.getString(averageCursorData.getColumnIndex("entry_date")) == null) ||
                            (averageCursorData.getString(averageCursorData.getColumnIndex("entry_date")).equals("0"))) {
                        //                            || (!averageCursorData.getString(averageCursorData.getColumnIndex("entry_date")).equals("0"))) {

                        System.out.println("INSIDE ENRTY DATE NULL");
                        userValuesList.add(0, 0.0f);
                    } else {
                        System.out.println("INSIDE user value");
                        userValuesList.add(0, Float.valueOf(averageCursorData.getString(averageCursorData.getColumnIndex("userBaby_1")) + "f"));
                    }
                    userValuesIDList.add(0, userPercentileIdForUpdate);
                }

            } while (averageCursorData.moveToPrevious());
        }
        System.out.println("------------------------------------------------------------------");
        System.out.println("userValuesList.size() : " + userValuesList.size());
        System.out.println("userValuesList : " + userValuesList);
//        userValuesList.set(0, Float.valueOf("5.0f"));
        System.out.println("userValuesList after 5.0f add : " + userValuesList);

        int lowerLimit = 0;
        int upperLimit = 0;

        for (int avgLoop = 0; avgLoop < userValuesList.size(); avgLoop++) {
            System.out.println("looping uservalue" + userValuesList.get(avgLoop));

            if (userValuesList.get(avgLoop) == 0.0) {
                lowerLimit = avgLoop;
                System.out.println("IF");

                for (int secondAvgLoop = avgLoop; secondAvgLoop <= userValuesList.size(); secondAvgLoop++) {
                    if (userValuesList.get(secondAvgLoop) != 0.0) {
                        System.out.println("IF FOR");
                        upperLimit = secondAvgLoop;
                        setMissingValues(--lowerLimit, upperLimit);
                        break;
                    }
                    avgLoop++;
                }
            }
        }
    }

    private void setMissingValues(int lowerLimit, int upperLimit) {
        System.out.println("lowerLimit : " + lowerLimit + " upperLimit : " + upperLimit);
        float valueDiff;
        float stepValue;
        int positionDiff = 0;
        positionDiff = upperLimit - lowerLimit;
        valueDiff = userValuesList.get(upperLimit) - userValuesList.get(lowerLimit);
        stepValue = valueDiff / positionDiff;
        System.out.println("stepValue : " + stepValue);

        //condition to check if upper and lower values diff 0.0(if yes, then values to be added becomes zero which is not desired)
        //instead add the same (upper OE lower) value as average
        if (stepValue == 0.0) {
            float valueToAdd = userValuesList.get(lowerLimit);
            for (int i = ++lowerLimit; i < upperLimit; i++) {
                userValuesList.set(i, valueToAdd);
            }
        } else {
            //decimal precision to get values upto 2 decimal only
            //because missing values calculation gives #.########## formatted values
            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.CEILING);

            float previousValue = userValuesList.get(lowerLimit);
            for (int i = ++lowerLimit; i < upperLimit; i++) {
                float valueToAdd = previousValue + stepValue;
                System.out.println("valueToAdd : " + valueToAdd);

                userValuesList.set(i, Float.parseFloat(df.format(valueToAdd)));
                previousValue = userValuesList.get(i);
            }
        }
        System.out.println("userValuesList AFTER MISSING VALUE ADDITION : " + userValuesList);
        System.out.println("userValuesIDList AFTER MISSING VALUE ADDITION : " + userValuesIDList);

        //Logic to update average values in database
        for (int insertNo = 0; insertNo < userValuesList.size(); insertNo++) {
            GrowthSqliteData userDeletedGrowthData = new GrowthSqliteData(null, String.valueOf(userValuesList.get(insertNo)), null, null, null, userValuesIDList.get(insertNo));
            int misssingValuesUpdatedRecord = database.userUpdatedGrowthRecord(userDeletedGrowthData);
            System.out.println("misssingValuesUpdatedRecord : " + misssingValuesUpdatedRecord);
        }
    }

}
