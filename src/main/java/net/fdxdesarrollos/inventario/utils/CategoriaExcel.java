package net.fdxdesarrollos.inventario.utils;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
//import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import net.fdxdesarrollos.inventario.model.Categoria;

public class CategoriaExcel {
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private List<Categoria> categorias;
	
	public CategoriaExcel(List<Categoria> categorias) {
		this.categorias = categorias;
		workbook = new XSSFWorkbook();
	}
	
	private void writeHeaderLine() {
		sheet = workbook.createSheet("Resultado");
		Row row = sheet.createRow(0);
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		
		font.setBold(true);
		font.setFontHeight(14);
		style.setFont(font);
		
		style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);		
		
		createCell(row, 0, "ID", style);
		createCell(row, 1, "Nombre", style);
		createCell(row, 2, "Descripción", style);
	}
	
	private void writeDetailLines() {
		int rowCount = 1;
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		
		font.setFontHeight(14);
		style.setFont(font);		
		
		for(Categoria result: categorias) {
			Row row = sheet.createRow(rowCount ++);
			int columnCount = 0;
			createCell(row, columnCount++, String.valueOf(result.getId()), style);
			createCell(row, columnCount++, result.getNombre(), style);
			createCell(row, columnCount++, result.getDescripcion(), style);
		}
	}
	
	private void createCell(Row row, int columnCount, Object value, CellStyle style) {
		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);
		
		if(value instanceof Integer) {
			cell.setCellValue((Integer) value);
		}
		else if(value instanceof Boolean){
			cell.setCellValue((Boolean) value);
		}
		else {
			cell.setCellValue((String) value);
		}
		
		cell.setCellStyle(style);
	}
	
	public void export(HttpServletResponse response) throws IOException{
		writeHeaderLine();
		writeDetailLines();
		
		ServletOutputStream servletOutput = response.getOutputStream();
		workbook.write(servletOutput);
		workbook.close();
		servletOutput.close();
	}
	
}
