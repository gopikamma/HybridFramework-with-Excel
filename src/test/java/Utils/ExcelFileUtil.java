package Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFileUtil 
{
	Workbook wb;
	
	public ExcelFileUtil() throws EncryptedDocumentException, InvalidFormatException, IOException
	{
		FileInputStream fis = new FileInputStream(".\\TestInput\\InputSheet.xlsx");
		wb = WorkbookFactory.create(fis);
	}
	
	public int rowCount(String Sheetname)
	{
		return wb.getSheet(Sheetname).getLastRowNum();
	}
	
	public int colCount(String sheetname,int rowno)
	{
		return wb.getSheet(sheetname).getRow(rowno).getLastCellNum();
	}
	
	public String getData(String sheetname,int row,int column)
	{
		String data="";
		if (wb.getSheet(sheetname).getRow(row).getCell(column).getCellType()==Cell.CELL_TYPE_NUMERIC) 
		{
			int celldata = (int)wb.getSheet(sheetname).getRow(row).getCell(column).getNumericCellValue();
			data = String.valueOf(celldata);
		}
		else
		{
			data = wb.getSheet(sheetname).getRow(row).getCell(column).getStringCellValue();
		}
		
		return data;
	}

	public void setData(String sheetname,int row,int column,String str) throws IOException
	{
		Sheet sh = wb.getSheet(sheetname);
		Row rownum = sh.getRow(row);
		Cell cell = rownum.createCell(column);
		cell.setCellValue(str);
		if (str.equalsIgnoreCase("PASS"))
		{
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setColor(IndexedColors.GREEN.getIndex());
			font.setBoldweight(font.BOLDWEIGHT_BOLD);
			style.setFont(font);
			rownum.getCell(column).setCellStyle(style);
		} 
				else if(str.equalsIgnoreCase("FAIL"))
				{
					CellStyle style = wb.createCellStyle();
					Font font = wb.createFont();
					font.setColor(IndexedColors.RED.getIndex());
					font.setBoldweight(font.BOLDWEIGHT_BOLD);
					style.setFont(font);
					rownum.getCell(column).setCellStyle(style);
				}
		else if(str.equalsIgnoreCase("Not Executed"))
		{
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setColor(IndexedColors.BLUE.getIndex());
			font.setBoldweight(font.BOLDWEIGHT_BOLD);
			style.setFont(font);
			rownum.getCell(column).setCellStyle(style);
		}
		
		FileOutputStream fos = new FileOutputStream(".\\TestOutput\\OutputSheet.xlsx");
		wb.write(fos);
		fos.close();
	}
	



}
