package com.maakservicess.beingparents.app_monitor;

import android.content.ContentValues;
import android.util.Log;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.Iterator;

/**
 * Created by amey on 2/25/2016.
 */
public class AppToExcelData {

//    public static final String id = "id";
    public static final String month = "month";
    public static final String range = "range";
    public static final String percentile3 = "percentile3";
    public static final String percentile15 = "percentile15";
    public static final String percentile30 = "percentile30";
    public static final String percentile50 = "percentile50";
    public static final String percentile70 = "percentile70";
    public static final String percentile85 = "percentile85";
    public static final String percentile97 = "percentile97";
    public static final String gender = "gender";
    public static final String categeroy = "category";
    public static final String userBaby_1 = "userBaby_1";
    public static final String userBaby_2 = "userBaby_2";
    public static final String Table_Insert = "growth";


    public static void insertExcelToSqlite(DatabaseHandler databaseHandler, Sheet sheet) {
        for (Iterator<Row> rit = sheet.rowIterator(); rit.hasNext(); ) {
            Row row = rit.next();
            ContentValues contentValues = new ContentValues();

            row.getCell(0, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(1, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(2, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(3, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(4, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(5, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(6, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(7, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(8, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(9, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(10, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(11, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(12, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(13, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);

//            contentValues.put(id, row.getCell(0, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put(month, row.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put(categeroy, row.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put(range, row.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put(percentile3, row.getCell(4, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put(percentile15, row.getCell(5, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put(percentile30, row.getCell(6, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put(percentile50, row.getCell(7, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put(percentile70, row.getCell(8, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put(percentile85, row.getCell(9, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put(percentile97, row.getCell(10, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put(gender, row.getCell(11, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put(userBaby_1, row.getCell(12, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put(userBaby_2, row.getCell(13, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            try {
                if (databaseHandler.insert(Table_Insert, contentValues) < 0) {
                    return;
                }
            } catch (Exception ex) {
                Log.d("Exception in importing", ex.getMessage().toString());
            }
        }
    }


}
