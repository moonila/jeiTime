package org.jeinnov.jeitime.utils;

import java.io.PrintWriter;
import java.sql.Date;
import java.util.Calendar;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ow2.opensuit.core.session.OpenSuitSession;
import org.ow2.opensuit.xml.base.html.table.Table;
import org.ow2.opensuit.xml.base.html.table.TableRenderingContext;
import org.ow2.opensuit.xml.base.html.table.TableRenderingContext.ColumnDef;
import org.ow2.opensuit.xml.base.html.table.export.ITableExportFormat;
import org.ow2.opensuit.xmlmap.annotations.XmlAncestor;
import org.ow2.opensuit.xmlmap.annotations.XmlAttribute;
import org.ow2.opensuit.xmlmap.annotations.XmlDoc;
import org.ow2.opensuit.xmlmap.annotations.XmlElement;
import org.ow2.opensuit.xmlmap.interfaces.IInitializable;
import org.ow2.opensuit.xmlmap.interfaces.IInitializationSupport;
import org.ow2.opensuit.xmlmap.interfaces.IInstantiationContext;

@XmlDoc("This component is able to export table data to CSV format (Comma Separated Values).")
@XmlElement
public class CSVExport implements ITableExportFormat, IInitializable {
	@XmlDoc("Filename. By default, uses the Table ID.")
	@XmlAttribute(name = "FileName", required = false)
	private String filename = null;

	@XmlDoc("File extension. By default 'csv'.")
	@XmlAttribute(name = "FileExtension", required = false)
	private String fileExtension = "csv";

	@XmlDoc("Separator to use. Default: 'text/cvs'.")
	@XmlAttribute(name = "MimeType", required = false)
	private String mimeType = "text/csv";

	@XmlDoc("Separator to use. Default: ','.")
	@XmlAttribute(name = "Separator", required = false)
	private String separator = ",";

	@XmlAncestor
	private Table table;

//	public void initialize(IInitializationSupport initSupport,
//			Object additionalSupport) {
//		if (fileExtension.startsWith(".")) {
//			fileExtension = fileExtension.substring(1);
//		}
//
//	}
	
	public void initialize(IInitializationSupport initSupport,
			IInstantiationContext instContext) {
		if(fileExtension.startsWith("."))
			fileExtension = fileExtension.substring(1);
		
	}

	public String getFileName() {
		if (filename == null) {
			return table.getId() + "." + fileExtension;
		}

		return filename + "." + fileExtension;
	}

	public void exportData(HttpServletRequest iRequest,
			HttpServletResponse iResponse, TableRenderingContext context,
			Collection<?> data) throws Exception {
		OpenSuitSession session = OpenSuitSession.getSession(iRequest);

		// --- render response (CSV)
		iResponse.setContentType(mimeType + ";charset="
				+ session.getLocaleConfig().getCharSet());
		iResponse.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
		iResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0
		iResponse.setDateHeader("Expires", 0); // prevents caching at the proxy
												// server

		// --- place rendering context in request
		iRequest.setAttribute("$context", context);

		// --- Render
		PrintWriter writer = iResponse.getWriter();

		// --- render columns headers
		int iDisplayedCol = 0;
		for (ColumnDef col : context.getColumns()) {
			if (!col.isVisible()) {
				continue;
			}

			if (iDisplayedCol > 0) {
				writer.print(separator);
			}

			write2CSV(writer, col.getProvider().getColumnTitle(iRequest, col));
			iDisplayedCol++;
		}
		writer.print('\n');

		// --- render table body (rows)
		if (data != null) {
			for (Object rowObj : data) {
				// --- place item in request
				iRequest.setAttribute(context.getTable().getRowBeanName(),
						rowObj);

				iDisplayedCol = 0;
				for (ColumnDef col : context.getColumns()) {
					if (!col.isVisible()) {
						continue;
					}

					// --- render cell
					if (iDisplayedCol > 0) {
						writer.print(separator);
					}

					Object cellData = col.getProvider()
							.getCellDisplayedContent(iRequest, col, rowObj);

					String cellTest = String.valueOf(cellData);
					String[] params = cellTest.split("<");

					String[] myCurrParam = null;
					for (int i = 0; i < params.length; i++) {
						myCurrParam = params[i].split(">");
						for (int j = 0; j < myCurrParam.length; j++) {
							if (j == 1 && i == 1) {
								cellData = myCurrParam[1];
							}
						}
					}

					write2CSV(writer, cellData);
					iDisplayedCol++;
				}
				writer.print('\n');
			}
		}

		writer.flush();
	}

	private static void write2CSV(PrintWriter writer, Object value) {
		if (value == null) {
			// write nothing
			return;
		}

		if (value.getClass() == Boolean.class
				|| value.getClass() == Boolean.TYPE) {
			writer.print(String.valueOf(value));
		} else if (Number.class.isInstance(value)) {
			writer.print(String.valueOf(value));
		} else if (value.getClass().isPrimitive()) {
			writer.print(String.valueOf(value));
		} else if (Date.class.isInstance(value)
				|| Calendar.class.isInstance(value)) {
			// TODO
			writer.print(String.valueOf(value));
		} else {
			// --- output to string (surround with double-quotes)
			String text = String.valueOf(value);
			int nbChars = text.length();
			writer.print('\"');
			for (int i = 0; i < nbChars; i++) {
				char c = text.charAt(i);
				switch (c) {
				case '"': // escape double quotes
					writer.print("\"\"");
					break;
				default:
					writer.print(c);
					break;
				}
			}
			writer.print('\"');
		}
	}


	
}
