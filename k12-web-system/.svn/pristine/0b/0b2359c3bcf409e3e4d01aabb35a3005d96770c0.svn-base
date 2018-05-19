package com.wdcloud.poi.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddressList;

import com.wdcloud.jyx.web.system.model.ExportDefinition;
import com.wdcloud.jyx.web.system.model.RowCellIndex;

/**
 * 生成级联下拉选
 *
 * @描述
 * @作者 huan
 * @时间 2017年3月31日 - 下午9:14:43
 */
public class DropDownListTest {

	private static final String	DICT_SHEET	= "DICT_SHEET";

	public static void main(String[] args) throws IOException {

		// 1.准备需要生成excel模板的数据
		List<ExportDefinition> edList = new ArrayList<>();
		edList.add(new ExportDefinition("序号", "xh", null, null, null));
		edList.add(new ExportDefinition("姓名", "username", null, null, null));
		edList.add(new ExportDefinition("性别", "gender", null, null, null));
		edList.add(new ExportDefinition("民族", "nation", null, null, null));
		edList.add(new ExportDefinition("出生日期", "birthday", null, null, null));
		edList.add(new ExportDefinition("组织机构", "org", null, null, null));
		edList.add(new ExportDefinition("手机号", "phone", null, null, null));
		edList.add(new ExportDefinition("邮箱", "email", null, null, null));
		edList.add(new ExportDefinition("学段", "xd", "xd-dict", "xk-dict", "xk"));
		edList.add(new ExportDefinition("学科", "xk", "xk-dict", "", ""));

		// 2.生成导出模板
		// InputStream finput =
		// DropDownListTest.class.getClassLoader().getResourceAsStream("template/JYYGL_PLDRMB.xls");
		// POIFSFileSystem pss = new POIFSFileSystem(finput);
		// HSSFWorkbook wb = new HSSFWorkbook(pss);
		// // 读取Sheet
		// HSSFSheet sh = wb.getSheetAt(0);
		Workbook wb = new HSSFWorkbook();
		createExportSheet(edList, wb);
		// Sheet sheet = createImportSheet(edList, wb);
		//
		// // 3.创建数据字典sheet页，做级联下拉
		// createDictSheet(edList, wb);
		//
		// // 4.设置数据有效性
		// setDataValidation(edList, sheet);
		//
		// // 5.单独下拉
		// Map<Integer, List<String>> dataMap = new HashMap<Integer, List<String>>();
		// // 性别
		// List<String> xblist = new ArrayList<String>();
		// xblist.add("男");
		// xblist.add("女");
		// dataMap.put(1, xblist);
		//
		// // 民族
		// List<String> mzlist = new ArrayList<String>();
		// mzlist.add("汉族");
		// mzlist.add("白族");
		// mzlist.add("苗族");
		// mzlist.add("维吾尔族");
		// dataMap.put(2, mzlist);
		// createSelect(dataMap, sheet);

		// 6.保存excel到本地
		OutputStream os = new FileOutputStream("d:/5.xls");
		wb.write(os);

		WorkbookFactory workbookFactory = new WorkbookFactory();

		System.out.println("模板生成成功.");
	}

	public static void createDataValidateSubList(Sheet sheet, ExportDefinition ed) {

		int rowIndex = ed.getRowIndex();
		CellRangeAddressList cal;
		DVConstraint constraint;
		CellReference cr;
		DataValidation dataValidation;
		System.out.println(ed);
		for (int i = 0; i < 100; i++) {
			int tempRowIndex = ++rowIndex;
			cal = new CellRangeAddressList(tempRowIndex, tempRowIndex, ed.getCellIndex(), ed.getCellIndex());
			cr = new CellReference(rowIndex, ed.getCellIndex() - 1, true, true);
			constraint = DVConstraint.createFormulaListConstraint("INDIRECT(" + cr.formatAsString() + ")");
			dataValidation = new HSSFDataValidation(cal, constraint);
			dataValidation.setSuppressDropDownArrow(false);
			dataValidation.createPromptBox("操作提示", "请选择下拉选中的值");
			dataValidation.createErrorBox("错误提示", "请从下拉选中选择，不要随便输入");
			sheet.addValidationData(dataValidation);
		}
	}

	/**
	 * @param edList
	 * @param sheet
	 */
	private static void setDataValidation(List<ExportDefinition> edList, Sheet sheet) {

		for (ExportDefinition ed : edList) {
			if (ed.isValidate()) {// 说明是下拉选
				DVConstraint constraint = DVConstraint.createFormulaListConstraint(ed.getField());
				if (null == ed.getRefName()) {// 说明是一级下拉选
					createDataValidate(sheet, ed, constraint);
				} else {// 说明是二级下拉选
					createDataValidateSubList(sheet, ed);
				}
			}
		}
	}

	/**
	 * @param sheet
	 * @param ed
	 * @param constraint
	 */
	private static void createDataValidate(Sheet sheet, ExportDefinition ed, DVConstraint constraint) {

		CellRangeAddressList regions = new CellRangeAddressList(ed.getRowIndex() + 1, ed.getRowIndex() + 100,
				ed.getCellIndex(), ed.getCellIndex());
		DataValidation dataValidation = new HSSFDataValidation(regions, constraint);
		dataValidation.setSuppressDropDownArrow(false);
		// 设置提示信息
		dataValidation.createPromptBox("操作提示", "请选择下拉选中的值");
		// 设置输入错误信息
		dataValidation.createErrorBox("错误提示", "请从下拉选中选择，不要随便输入");
		sheet.addValidationData(dataValidation);
	}

	/**
	 * @param edList
	 * @param wb
	 */
	private static void createDictSheet(List<ExportDefinition> edList, Workbook wb) {

		Sheet sheet = wb.createSheet(DICT_SHEET);
		RowCellIndex rci = new RowCellIndex(0, 0);
		for (ExportDefinition ed : edList) {
			String mainDict = ed.getMainDict();
			if (null != mainDict && null == ed.getRefName()) {// 是第一个下拉选
				List<String> mainDictList = (List<String>) DictData.getDict(mainDict);
				String refersToFormula = createDictAndReturnRefFormula(sheet, rci, mainDictList);
				// 创建 命名管理
				createName(wb, ed.getField(), refersToFormula);
				ed.setValidate(true);
			}
			if (null != mainDict && null != ed.getSubDict() && null != ed.getSubField()) {// 联动时加载ed.getSubField()的数据
				ExportDefinition subEd = fiterByField(edList, ed.getSubField());// 获取需要级联的那个字段
				if (null == subEd) {
					continue;
				}
				subEd.setRefName(ed.getPoint());// 保存主下拉选的位置
				subEd.setValidate(true);
				Map<String, List<String>> subDictListMap = (Map<String, List<String>>) DictData
						.getDict(ed.getSubDict());
				for (Entry<String, List<String>> entry : subDictListMap.entrySet()) {
					String refersToFormula = createDictAndReturnRefFormula(sheet, rci, entry.getValue());
					// 创建 命名管理
					createName(wb, entry.getKey(), refersToFormula);
				}
			}
		}
	}

	/**
	 * @param sheet
	 * @param rci
	 * @param mainDict
	 * @return
	 */
	private static String createDictAndReturnRefFormula(Sheet sheet, RowCellIndex rci, List<String> datas) {

		Row row = sheet.createRow(rci.incrementRowIndexAndGet());
		rci.setCellIndex(0);
		int startRow = rci.getRowIndex();
		int startCell = rci.getCellIndex();
		for (String dict : datas) {
			row.createCell(rci.incrementCellIndexAndGet()).setCellValue(dict);
		}
		int endRow = rci.getRowIndex();
		int endCell = rci.getCellIndex();
		String startName = new CellReference(DICT_SHEET, startRow, startCell, true, true).formatAsString();
		String endName = new CellReference(endRow, endCell, true, true).formatAsString();
		String refersToFormula = startName + ":" + endName;
		System.out.println(refersToFormula);
		return refersToFormula;
	}

	/**
	 * @param wb
	 * @param nameName
	 *            表示命名管理的名字
	 * @param refersToFormula
	 */
	private static void createName(Workbook wb, String nameName, String refersToFormula) {

		Name name = wb.createName();
		name.setNameName(nameName);
		name.setRefersToFormula(refersToFormula);
	}

	private static ExportDefinition fiterByField(List<ExportDefinition> edList, String field) {

		for (ExportDefinition ed : edList) {
			if (Objects.equals(ed.getField(), field)) {
				return ed;
			}
		}
		return null;
	}

	/**
	 * @param edList
	 * @param wb
	 */
	private static Sheet createImportSheet(List<ExportDefinition> edList, Workbook wb) {

		Sheet sheet = wb.createSheet("导出模板");
		RowCellIndex rci = new RowCellIndex(0, -1);
		Row row = sheet.createRow(rci.getRowIndex());
		// 行高
		row.setHeightInPoints(30);
		CellReference cr = null;
		int n = 0;
		for (ExportDefinition ed : edList) {
			Cell cell = row.createCell(rci.incrementCellIndexAndGet());
			cell.setCellValue(ed.getTitle());

			CellStyle cellStyle = wb.createCellStyle();

			// 背景色
			cellStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			// 居中
			cellStyle.setAlignment(HorizontalAlignment.CENTER); // 水平居中
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中

			// 字体
			Font font = wb.createFont();
			font.setFontName("宋体");// 字体
			font.setFontHeightInPoints((short) 11);// 设置字体大小
			font.setBold(true);// 粗体显示
			if ("username".equals(ed.getField()) || "org".equals(ed.getField())
					|| "xd".equals(ed.getField()) || "xk".equals(ed.getField())) {
				font.setColor(IndexedColors.RED.getIndex()); // 字体颜色
			}
			cellStyle.setFont(font);// 选择需要用到的字体格式

			// 设置边框
			cellStyle.setBorderBottom(BorderStyle.THIN); // 下边框
			cellStyle.setBorderLeft(BorderStyle.THIN);// 左边框
			cellStyle.setBorderTop(BorderStyle.THIN);// 上边框
			cellStyle.setBorderRight(BorderStyle.THIN);// 右边框

			sheet.setColumnWidth(n++, 4370); // 列宽

			// 设置文本格式
			DataFormat format = wb.createDataFormat();
			cellStyle.setDataFormat(format.getFormat("@"));
			cell.setCellType(CellType.STRING);

			cell.setCellStyle(cellStyle);

			ed.setRowIndex(rci.getRowIndex());
			ed.setCellIndex(rci.getCellIndex());
			cr = new CellReference(ed.getRowIndex() + 1, ed.getCellIndex(), true, true);
			ed.setPoint(cr.formatAsString());
		}

		// 注意事项
		Cell cell = row.createCell(rci.incrementCellIndexAndGet());
		cell.setCellValue("*注：数据行间不能有空行");

		CellStyle cellStyle = wb.createCellStyle();

		// 居中
		cellStyle.setAlignment(HorizontalAlignment.CENTER); // 水平居中
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中

		// 字体
		Font font = wb.createFont();
		font.setFontName("宋体");// 字体
		font.setFontHeightInPoints((short) 11);// 设置字体大小
		font.setBold(true);// 粗体显示
		font.setColor(IndexedColors.RED.getIndex()); // 字体颜色
		cellStyle.setFont(font);// 选择需要用到的字体格式

		sheet.setColumnWidth(n++, 9000); // 列宽

		cell.setCellStyle(cellStyle);

		for (int i = 1; i < 50; i++) {
			Row rowID = sheet.createRow(i);
			// 行高
			rowID.setHeightInPoints(20);
			for (int j = 0; j < 9; j++) {
				CellStyle style = wb.createCellStyle();

				Font font1 = wb.createFont();
				font1.setFontName("宋体");// 字体
				font1.setFontHeightInPoints((short) 12);// 设置字体大小
				style.setFont(font1);// 选择需要用到的字体格式

				// 居中
				style.setAlignment(HorizontalAlignment.GENERAL); // 水平居中
				style.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中

				// 设置边框
				style.setBorderBottom(BorderStyle.THIN); // 下边框
				style.setBorderLeft(BorderStyle.THIN);// 左边框
				style.setBorderTop(BorderStyle.THIN);// 上边框
				style.setBorderRight(BorderStyle.THIN);// 右边框
				// 设置为文本格式，防止出生日期变成科学计数法
				DataFormat format = wb.createDataFormat();
				style.setDataFormat(format.getFormat("@"));
				Cell cell1 = rowID.createCell((short) j);
				cell1.setCellStyle(style);
			}
		}

		return sheet;
	}

	public static void createSelect(Map<Integer, List<String>> dataMap, Sheet sheet) throws IOException {

		// excel下拉菜单作用域，例子中是第12列(从0开始)
		for (Map.Entry<Integer, List<String>> entry : dataMap.entrySet()) {
			List<String> dataList = entry.getValue();
			CellRangeAddressList regions = new CellRangeAddressList(1, 65535, entry.getKey(), entry.getKey());
			String[] list = new String[dataList.size()];
			;
			for (int i = 0; i < dataList.size(); i++) {
				list[i] = dataList.get(i); // 加载下拉菜单中的内容
			}
			// 将list中的内容写入到excel中
			DVConstraint constraint = DVConstraint.createExplicitListConstraint(list);
			// 绑定下拉菜单和作用域
			HSSFDataValidation data_validation = new HSSFDataValidation(regions, constraint);
			// 对sheet生效
			sheet.addValidationData(data_validation);
		}
	}

	/**
	 * @param edList
	 * @param wb
	 */
	private static Sheet createExportSheet(List<ExportDefinition> edList, Workbook wb) {

		Sheet sheet = wb.createSheet("导出模板");
		RowCellIndex rci = new RowCellIndex(0, -1);
		Row row = sheet.createRow(rci.getRowIndex());
		// 行高
		row.setHeightInPoints(30);
		CellReference cr = null;
		int n = 0;
		for (ExportDefinition ed : edList) {
			Cell cell = row.createCell(rci.incrementCellIndexAndGet());
			cell.setCellValue(ed.getTitle());

			CellStyle cellStyle = wb.createCellStyle();

			// 背景色
			cellStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			// 居中
			cellStyle.setAlignment(HorizontalAlignment.CENTER); // 水平居中
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中

			// 字体
			Font font = wb.createFont();
			font.setFontName("宋体");// 字体
			font.setFontHeightInPoints((short) 11);// 设置字体大小
			font.setBold(true);// 粗体显示
			cellStyle.setFont(font);// 选择需要用到的字体格式

			// 设置边框
			cellStyle.setBorderBottom(BorderStyle.THIN); // 下边框
			cellStyle.setBorderLeft(BorderStyle.THIN);// 左边框
			cellStyle.setBorderTop(BorderStyle.THIN);// 上边框
			cellStyle.setBorderRight(BorderStyle.THIN);// 右边框

			// 设置文本格式
			DataFormat format = wb.createDataFormat();
			cellStyle.setDataFormat(format.getFormat("@"));
			cell.setCellType(CellType.STRING);

			cell.setCellStyle(cellStyle);

			// 列宽
			sheet.setColumnWidth(n++, 4370);

			ed.setRowIndex(rci.getRowIndex());
			ed.setCellIndex(rci.getCellIndex());
			cr = new CellReference(ed.getRowIndex() + 1, ed.getCellIndex(), true, true);
			ed.setPoint(cr.formatAsString());
		}

		for (int i = 1; i < 4; i++) {
			Row rowID = sheet.createRow(i);
			// 行高
			rowID.setHeightInPoints(20);
			for (int j = 0; j < 10; j++) {
				CellStyle style = wb.createCellStyle();

				Font font1 = wb.createFont();
				font1.setFontName("宋体");// 字体
				font1.setFontHeightInPoints((short) 12);// 设置字体大小
				style.setFont(font1);// 选择需要用到的字体格式

				// 居中
				style.setAlignment(HorizontalAlignment.GENERAL); // 水平居中
				style.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中

				// 设置边框
				style.setBorderBottom(BorderStyle.THIN); // 下边框
				style.setBorderLeft(BorderStyle.THIN);// 左边框
				style.setBorderTop(BorderStyle.THIN);// 上边框
				style.setBorderRight(BorderStyle.THIN);// 右边框

				// 设置为文本格式，防止出生日期变成科学计数法
				DataFormat format = wb.createDataFormat();
				style.setDataFormat(format.getFormat("@"));
				Cell cell1 = rowID.createCell((short) j);
				cell1.setCellStyle(style);

				if (i == 1 && j == 0) {
					cell1.setCellValue("<jx:forEach items=\"${list}\" var=\"entity\" varStatus=\"obj\">");
				}
				if (i == 2) {
					if (j == 0) {
						cell1.setCellValue("${obj.index+1}");
					}
					if (j == 1) {
						cell1.setCellValue("${entity.username}");
					}
					if (j == 2) {
						cell1.setCellValue("${entity.account}");
					}
					if (j == 3) {
						cell1.setCellValue("${entity.gender}");
					}
					if (j == 4) {
						cell1.setCellValue("${entity.nation}");
					}
					if (j == 5) {
						cell1.setCellValue("${entity.birthday}");
					}
					if (j == 6) {
						cell1.setCellValue("${entity.orgname}");
					}
					if (j == 7) {
						cell1.setCellValue("${entity.mobilephone}");
					}
					if (j == 8) {
						cell1.setCellValue("${entity.email}");
					}
					if (j == 9) {
						cell1.setCellValue("${entity.xdkm}");
					}
				}
				if (i == 3 && j == 0) {
					cell1.setCellValue("</jx:forEach>");
				}
			}
		}

		return sheet;
	}
}
