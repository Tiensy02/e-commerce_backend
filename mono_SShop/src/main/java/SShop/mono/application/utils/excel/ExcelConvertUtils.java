package SShop.mono.application.utils.excel;

import SShop.mono.application.utils.json.JacksonUtils;
import com.monitorjbl.xlsx.StreamingReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

@Slf4j
public class ExcelConvertUtils<T> {

  private final ExcelConvertConfig config;

  public ExcelConvertUtils(ExcelConvertConfig excelConvertConfig) {
    this.config = excelConvertConfig;
  }

  public void convert(Class<T> clazz, int batchSize, Consumer<List<T>> consumer) {
    StopWatch stopWatch = StopWatch.create();

    Workbook wb = StreamingReader.builder()
        .rowCacheSize(100)
        .bufferSize(4096)
        .open(config.getSourceFile());

    List<T> data = new ArrayList<>();

    for (Sheet sheet : wb){
      int rowIndex = 0;
      List<String> headers = new ArrayList<>();

      for (Row row : sheet) {
        if(rowIndex == 0) {
          headers = readHeader(row);
        } else {
          Map<String, Object> rowData = readData(row, headers);
          if(!isRowEmpty(rowData)) {
            String json = JacksonUtils.getJson(rowData);
            try {
              T t = JacksonUtils.getObject(json, clazz);
//              IReadExcelValidator validT =validatorFactory.getReadValidator(t);
//              if(validT != null ) {
//                t = (T) validT.validate(rowData,t);
//              }
              if(t != null ) data.add(t);
            } catch (Exception e) {
              log.error("Can not parser item at row {}: {}", rowIndex, e.getLocalizedMessage());
            }
          }
        }

        rowIndex++;

        if (rowIndex % batchSize == 0) {
          consumer.accept(data);
          data = new ArrayList<>();
        }
      }

      if(!data.isEmpty()) {
        consumer.accept(data);
      }
    }
    stopWatch.stop();

  }

  private boolean isRowEmpty(Map<String, Object> rowData) {
    if (null != rowData) {
      for (Map.Entry<String, Object> entry : rowData.entrySet()) {
        if (entry.getValue() != null && !StringUtils.isEmpty(entry.getValue().toString())) {
          return false;
        }
      }
    }
    return true;
  }

  private List<String> readHeader(Row row) {
    List<String> headers = new ArrayList<>();

    int cellIndex = 0;
    for (Cell cell : row) {
      Object value = cellToObject(cell);
      headers.add(value == null ? "CELL_" + cellIndex : value.toString());
      cellIndex++;
    }

    return headers;
  }

  private Map<String, Object> readData(Row row, List<String> headers) {
    Map<String, Object> rowData = new HashMap<>();

    for (int i = 0; i < headers.size(); i++) {
      Cell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
      Object value = cellToObject1(cell);
      rowData.put(headers.get(i), value);
    }
    return rowData;
  }

  private Object cellToObject1(Cell cell) {
    if (null == cell) {
      return null;
    }

    CellType type = cell.getCellType();
    if (type == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
      if (config.getFormatDate() != null) {
        return config.getFormatDate().format(cell.getDateCellValue());
      }
      return cell.getDateCellValue();
    }
    String text = cleanString(cell.getStringCellValue());
    return StringUtils.isEmpty(text) ? null : text;
  }

  private Object cellToObject(Cell cell) {
    if (null == cell) {
      return null;
    }

    CellType type = cell.getCellType();
    if (type == CellType.STRING) {
      return cleanString(cell.getStringCellValue());
    }
    if (type == CellType.BOOLEAN) {
      return cell.getBooleanCellValue();
    }
    if (type == CellType.NUMERIC) {
      if (cell.getCellStyle().getDataFormatString().contains("%")) {
        return cell.getNumericCellValue() * 100;
      }
      return numeric(cell);
    }
    if (type == CellType.FORMULA) {
      switch (cell.getCachedFormulaResultType()) {
        case NUMERIC:
          return numeric(cell);
        case STRING:
          return cleanString(cell.getRichStringCellValue().toString());
      }
    }
    return null;
  }

  /**
   * Loại bỏ ký tự xuống dòng
   */
  private String cleanString(String str) {
    String text = str.replace("\n", "").replace("\r", "");
    if (!StringUtils.isEmpty(config.getDateInputFormat()) && !StringUtils.isEmpty(text)) {
      try {
        Date date = new SimpleDateFormat(config.getDateInputFormat()).parse(text);
        text = config.getFormatDate().format(date);
      } catch (Exception e) {
      }
    }

    return text;
  }

  /**
   * Lấy giá trị số trong cell
   */
  private Object numeric(Cell cell) {
    return cell.getNumericCellValue();
  }
}
