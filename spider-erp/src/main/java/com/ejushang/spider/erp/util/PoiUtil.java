package com.ejushang.spider.erp.util;

import com.ejushang.spider.util.DateUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;

import java.util.Calendar;
import java.util.Date;

/**
 * User: Sed.Li(李朝)
 * Date: 14-4-12
 * Time: 下午4:12
 */
public class PoiUtil {


    public static Cell createCell(Row row, int index, Date value) {
        Cell cell = row.createCell(index);
        if (value == null) {
            cell.setCellValue("");
            return cell;
        }
        cell.setCellValue(DateUtils.formatDate(value, DateUtils.DateFormatType.DATE_FORMAT_STR_CHINA));
        return cell;
    }

    public static Cell createCell(Row row, int index, Calendar value) {
        Cell cell = row.createCell(index);
        if (value == null) {
            cell.setCellValue("");
            return cell;
        }
        cell.setCellValue(DateUtils.formatDate(value.getTime(), DateUtils.DateFormatType.DATE_FORMAT_STR_CHINA));
        return cell;
    }

    public static Cell createCell(Row row, int index, RichTextString value) {
        Cell cell = row.createCell(index);
        if (value == null) {
            cell.setCellValue("");
            return cell;
        }
        cell.setCellValue(value);
        return cell;
    }

    public static Cell createCell(Row row, int index, String value) {
        Cell cell = row.createCell(index);
        if (value == null) {
            cell.setCellValue("");
            return cell;
        }
        cell.setCellValue(value);
        return cell;
    }

    public static Cell createCell(Row row, int index, Boolean value) {
        Cell cell = row.createCell(index);
        if (value == null) {
            cell.setCellValue("");
        }
        cell.setCellValue(value);
        return cell;
    }

    public static Cell createCell(Row row, int index, Double value) {
        Cell cell = row.createCell(index);
        if (value == null) {
            cell.setCellValue("");
            return cell;
        }
        cell.setCellValue(value);
        return cell;
    }

    public static Cell createCell(Row row, int index, Integer value) {
        Cell cell = row.createCell(index);
        if (value == null) {
            cell.setCellValue("");
            return cell;
        }
        cell.setCellValue(value);
        return cell;
    }
}
