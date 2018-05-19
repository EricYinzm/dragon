package com.wdcloud.jyx.web.system.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class ExcelUtil {

	// 码表保存sheet编号
	public static final int	CODE_SHEET_INDEX		= 0;
	// 列头显示行
	public static final int	COL_TITLE_ROW			= 0;
	// 数据起始行
	public static final int	DATA_BEGIN_ROW			= 1;										;
	// sheet编号
	public static final int	TABLE_SHEET_IDNEX		= 1;
	// 表字段起始列
	public static final int	FIELD_BEGIN_COL			= 0;

	/** 导出EXCEL列表动作 */
	public static String	EXCEL_EXPORT_STATIC		= "STATIC";								// 静态的
	public static String	EXCEL_EXPORT_DYNAMIC	= "DYNAMIC";								// 动态的

	private static Logger	logger					= LoggerFactory.getLogger(ExcelUtil.class);

	// ==========================================================================================================//
	// ==========================================================================================================//
	// ==========================================================================================================//
	// ==========================================================================================================//

	/**
	 * 按行读取Excel中数据，将每行组装成Object数组(Object[])放入list中返回。
	 *
	 * @param file
	 *            Excel文件。
	 * @param list
	 *            该list用来装从Excel中读取的Object[]。
	 * @param tableSheetIndex
	 *            sheet页码。
	 * @param rowStart
	 *            (行数从0开始,0对应excel中的第1行)
	 *            从第几行开始
	 * @param colStart
	 *            从第几列开始(列数从1开始,1对应excel中的第1列)
	 * @param rank
	 *            从第一列算起，需要读取的列数。
	 * @return 成功返回true;失败返回false。
	 */
	public static boolean readExcel2Obj(InputStream fis, List<Object[]> list, int tableSheetIndex, int rowStart,
			int colStart, int rank) {

		logger.info("开始读取excel，将其转成Object数组");
		// 定义excel工作簿
		HSSFWorkbook wb = null;
		// FileInputStream fis = null;
		try {
			// fis = new FileInputStream(file);
			wb = new HSSFWorkbook(fis);
		} catch (Exception e) {
			logger.error("解析excel文档流有误。", e);
			return false;
		} finally {
			close(fis);
		}

		try {
			// 服务信息sheet页。
			HSSFSheet xlsSheet = wb.getSheetAt(tableSheetIndex);
			int index = rowStart; // 读取的行数记录,因为第0行是标题
			while (true) {
				HSSFRow row = xlsSheet.getRow(index++);
				if (row == null) {
					break;
				}
				Object[] obj = new Object[rank];
				int blank = 0;
				for (int i = 0; i < rank; i++) {
					HSSFCell cell = row.getCell(i + colStart);
					// 判断每个表格的数据类型并提取。
					if (cell != null) {
						int cellType = cell.getCellType();
						if (cellType == cell.CELL_TYPE_STRING) {
							obj[i] = cell.getRichStringCellValue().toString();
						} else if (cellType == cell.CELL_TYPE_NUMERIC) {
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								// Date d = cell.getDateCellValue();
								// DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
								// obj[i] = String.valueOf(formater.format(d));
								obj[i] = cell.getDateCellValue();
							} else {
								obj[i] = formatRate(cell.getNumericCellValue());
							}
						} else if (cellType == cell.CELL_TYPE_FORMULA) {
							obj[i] = cell.getCellFormula();
						} else if (cellType == cell.CELL_TYPE_BOOLEAN) {
							obj[i] = cell.getBooleanCellValue();
						} else {
							obj[i] = "";
							blank++;
						}
					} else {
						obj[i] = "";
						blank++;
					}
				}
				if (blank == rank) {
					break;
				} else {
					list.add(obj);
				}
			}
			logger.info("读取excel，成功将其转成Object数组");
			return true;
		} catch (Exception e) {
			logger.error("解析excel数据有误", e);
			return false;
		}
	}

	// ==========================================================================================================//
	// ==========================================================================================================//
	// ==========================================================================================================//
	// ==========================================================================================================//

	/**
	 * Discription:多模版多sheet页导出
	 *
	 * @param templatePath
	 *            模板文件
	 * @param beans
	 *            需要导出的实体信息
	 * @param os
	 *            导出文件需要的输出流
	 * @author
	 * @update [日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public static void export2Excel(String templatePath, Map beans, OutputStream os) {

		logger.debug("开始导出..." + JSON.toJSONString(beans));

		// 导出模板解析器
		XLSTransformer transformer = new XLSTransformer();

		InputStream is = null;
		is = ExcelUtil.class.getClassLoader().getResourceAsStream(templatePath);

		try {
			Workbook workbook = transformer.transformXLS(is, beans);
			// JxlsOutTag.localThd.remove();
			workbook.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ==========================================================================================================//
	// ==========================================================================================================//
	// ==========================================================================================================//
	// ==========================================================================================================//

	/**
	 * Discription:多模版分别克隆多sheet页导出
	 *
	 * @param templatePath
	 *            模板文件
	 * @param os
	 *            导出文件需要的输出流
	 * @param templateSheetNameList
	 *            模版sheet名称集合==注：模版集合应与要生成的sheet集合
	 * @param sheetNameList
	 *            要生成的sheet名称集合
	 * @param beanParamsList
	 *            要生成的sheet对应的数据集合
	 * @author
	 * @update [日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public static void exportMoreTemplate2Excel(String templatePath, OutputStream os,
			List<String> templateSheetNameList, List<String> sheetNameList,
			List<Map<String, Object>> beanParamsList) {

		// 导出模板解析器
		XLSTransformer transformer = new XLSTransformer();

		InputStream is = null;
		try {
			is = new FileInputStream(templatePath);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		try {
			Workbook workbook = transformer.transformXLS(is, templateSheetNameList, sheetNameList, beanParamsList);
			// JxlsOutTag.localThd.remove();
			workbook.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ===========================================================================================================//
	// ===========================================================================================================//
	// ===========================================================================================================//
	// ===========================================================================================================//

	public static void exportMultiple2Excel() {

	}

	// ===========================================================================================================//
	// ===========================================================================================================//
	// ===========================================================================================================//
	// ===========================================================================================================//
	/**
	 * 关闭输入流。
	 *
	 * @param is
	 *            待关闭的输入流。
	 */
	private static void close(InputStream is) {

		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	// ==========================================================================================================//
	// ==========================================================================================================//
	// ==========================================================================================================//
	// ==========================================================================================================//

	public static String formatRate(double v) {

		DecimalFormat fmt = new DecimalFormat("####0.00000000");
		return fmt.format(v);
	}

	/**
	 * @Method: com.wdcloud.jyx.common.util.ExcelUtil.downLoadModle
	 * @Description: 模板下载
	 * @author: zhangX
	 * @date: 2015年7月22日
	 * @version: 1.0
	 * @param response
	 * @param filePath
	 * @param fileName
	 * @param dataMap
	 * @throws IOException
	 *             void
	 * @update [日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public static void downLoadModle(String filePath, ServletOutputStream out,
			Map<Integer, List<String>> dataMap) throws IOException {

		// 从工程目录下下载模板
		// FileInputStream finput = new FileInputStream(filePath);
		InputStream finput = ExcelUtil.class.getClassLoader().getResourceAsStream(filePath);
		POIFSFileSystem pss = new POIFSFileSystem(finput);
		HSSFWorkbook workbook = new HSSFWorkbook(pss);
		// 读取Sheet
		HSSFSheet sheet = workbook.getSheetAt(0);
		// HSSFSheet sheet2 = workbook.getSheetAt(1);
		// 索引
		int index = 0;
		// excel下拉菜单作用域，例子中是第12列(从0开始)
		for (Map.Entry<Integer, List<String>> entry : dataMap.entrySet()) {
			index++;
			List<String> dataList = entry.getValue();
			CellRangeAddressList regions = new CellRangeAddressList(1, 65535, entry.getKey(), entry.getKey());
			String[] list = new String[dataList.size()];
			;
			for (int i = 0; i < dataList.size(); i++) {
				list[i] = dataList.get(i); // 加载下拉菜单中的内容
			}
			try {
				// 将list中的内容写入到excel中
				DVConstraint constraint = DVConstraint.createExplicitListConstraint(list);
				// 绑定下拉菜单和作用域
				HSSFDataValidation data_validation = new HSSFDataValidation(regions, constraint);
				// 对sheet生效
				sheet.addValidationData(data_validation);
			} catch (IllegalArgumentException e) {
				// HSSFRow row = sheet2.createRow(index);
				// for (int i = 0; i < dataList.size(); i++) {
				// HSSFCell cell = row.createCell(i + 1);
				// cell.setCellValue(dataList.get(i));
				// }
				// CellRangeAddress address = new CellRangeAddress(index, index, 1, dataList.size());
				// String formuler = creatFormuler(address.formatAsString());
				// DVConstraint constraint = DVConstraint.createFormulaListConstraint(formuler);
				// HSSFDataValidation data_validation = new HSSFDataValidation(regions, constraint);
				// sheet.addValidationData(data_validation);
			}
		}
		workbook.write(out);
	}

	/**
	 * Discription:按照需要的格式构造formuler；
	 *
	 * @param formatAsString
	 *            利用address.formatAsString()方法生成的字符串
	 * @return
	 * @author lifeng
	 * @update [日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	private static String creatFormuler(String formatAsString) {

		String formuler = "";
		String s[] = formatAsString.split(":");
		String range = "";
		for (int i = 0; i < s.length; i++) {
			if (i == s.length - 1) {
				range = range + "$" + s[i];
			} else {
				range = range + "$" + s[i] + ":";
			}
		}
		formuler = "INDIRECT(\"sheet2!" + range + "\")";
		return formuler;
	}
}
