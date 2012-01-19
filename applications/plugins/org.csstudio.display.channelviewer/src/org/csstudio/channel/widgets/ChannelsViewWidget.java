package org.csstudio.channel.widgets;

import gov.bnl.channelfinder.api.Channel;
import gov.bnl.channelfinder.api.ChannelQuery.Result;
import gov.bnl.channelfinder.api.ChannelUtil;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.csstudio.channelviewer.views.ChannelContentProvider;
import org.csstudio.channelviewer.views.PropertyCellLabelProvider;
import org.csstudio.channelviewer.views.TableViewerChannelPropertySorter;
import org.csstudio.channelviewer.views.TableViewerChannelTagSorter;
import org.csstudio.channelviewer.views.TagCellLabelProvider;
import org.csstudio.ui.util.widgets.ErrorBar;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.swtdesigner.TableViewerColumnSorter;

public class ChannelsViewWidget extends AbstractChannelQueryResultWidget
		implements ISelectionProvider {

	private Table table;
	private TableViewer tableViewer;
	private ErrorBar errorBar;

	// Simple Model
	private Collection<Channel> channels = new ArrayList<Channel>();
	private AbstractSelectionProviderWrapper selectionProvider;

	public Collection<Channel> getChannels() {
		return channels;
	}

	private void setChannels(Collection<Channel> channels) {
		Collection<Channel> oldChannels = this.channels;
		this.channels = channels;
		changeSupport.firePropertyChange("channels", oldChannels, channels);
	}

	private void updateTable(){
		// Clear the channel list;
		tableViewer.setInput(channels.toArray());
		tableViewer.setItemCount(channels.size());
		// Remove all old columns
		// TODO add the additional columns in the correct sorted order.
		while (tableViewer.getTable().getColumnCount() > 2) {
			tableViewer.getTable().getColumn(table.getColumnCount() - 1)
					.dispose();
		}
		// Add a new column for each property
		for (String propertyName : ChannelUtil.getPropertyNames(channels)) {
			// Property Column
			TableViewerColumn channelPropertyColumn = new TableViewerColumn(
					tableViewer, SWT.NONE);
			channelPropertyColumn
					.setLabelProvider(new PropertyCellLabelProvider(
							propertyName));
			new TableViewerChannelPropertySorter(channelPropertyColumn,
					propertyName);
			TableColumn tblclmnNumericprop = channelPropertyColumn.getColumn();
			// tcl_composite.setColumnData(tblclmnNumericprop, new
			// ColumnPixelData(
			// 100, true, true));

			tblclmnNumericprop.setText(propertyName);
			tblclmnNumericprop.setWidth(100);
		}
		// Add a new column for each Tag
		for (String tagName : ChannelUtil.getAllTagNames(channels)) {
			// Tag Column
			TableViewerColumn channelTagColumn = new TableViewerColumn(
					tableViewer, SWT.NONE);
			channelTagColumn
					.setLabelProvider(new TagCellLabelProvider(tagName));
			new TableViewerChannelTagSorter(channelTagColumn, tagName);
			TableColumn tblclmnNumericprop = channelTagColumn.getColumn();
			// tcl_composite.setColumnData(tblclmnNumericprop, new
			// ColumnPixelData(
			// 100, true, true));
			tblclmnNumericprop.setText(tagName);
			tblclmnNumericprop.setWidth(100);
		}
		// calculate column size since adding removing colums does not trigger a
		// control resize event.
		Rectangle area = table.getClientArea();
		Point size = table.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		ScrollBar vBar = table.getVerticalBar();
		int width = area.width - table.computeTrim(0, 0, 0, 0).width
				- vBar.getSize().x;
		if (size.y > area.height + table.getHeaderHeight()) {
			// Subtract the scrollbar width from the total column width
			// if a vertical scrollbar will be required
			Point vBarSize = vBar.getSize();
			width -= vBarSize.x;
		}
		Point oldSize = table.getSize();
		TableColumn[] columns;
		if (oldSize.x > area.width) {
			// table is getting smaller so make the columns
			// smaller first and then resize the table to
			// match the client area width
			columns = table.getColumns();
			int newWidth = area.width / columns.length >= 100 ? area.width
					/ columns.length : 100;
			for (TableColumn tableColumn : columns) {
				tableColumn.setWidth(newWidth);
			}
		} else {
			// table is getting bigger so make the table
			// bigger first and then make the columns wider
			// to match the client area width
			columns = table.getColumns();
			int newWidth = area.width / columns.length >= 100 ? area.width
					/ columns.length : 100;
			for (TableColumn tableColumn : columns) {
				tableColumn.setWidth(newWidth);
			}
		}
		tableViewer.refresh();
		// table.notifyListeners(0, //new Event()));
	}
	
	public ChannelsViewWidget(Composite parent, int style) {
		super(parent, style);

		GridLayout gridLayout = new GridLayout(1, false);
		setLayout(gridLayout);
		
		errorBar = new ErrorBar(this, SWT.NONE);
		errorBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		tableViewer = new TableViewer(this, SWT.BORDER | SWT.FULL_SELECTION
				| SWT.MULTI | SWT.VIRTUAL);
		table = tableViewer.getTable();
		table.addMenuDetectListener(new MenuDetectListener() {
			public void menuDetected(MenuDetectEvent e) {
			}
		});
		// Make the Columns stretch with the table
		table.addControlListener(new ControlAdapter() {

			@Override
			public void controlResized(ControlEvent e) {
				Rectangle area = table.getClientArea();
				Point size = table.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				ScrollBar vBar = table.getVerticalBar();
				int width = area.width - table.computeTrim(0, 0, 0, 0).width
						- vBar.getSize().x;
				if (size.y > area.height + table.getHeaderHeight()) {
					// Subtract the scrollbar width from the total column width
					// if a vertical scrollbar will be required
					Point vBarSize = vBar.getSize();
					width -= vBarSize.x;
				}
				Point oldSize = table.getSize();
				TableColumn[] columns;
				if (oldSize.x > area.width) {
					// table is getting smaller so make the columns
					// smaller first and then resize the table to
					// match the client area width
					columns = table.getColumns();
					int newWidth = area.width / columns.length >= 100 ? area.width
							/ columns.length
							: 100;
					for (TableColumn tableColumn : columns) {
						tableColumn.setWidth(newWidth);
					}
				} else {
					// table is getting bigger so make the table
					// bigger first and then make the columns wider
					// to match the client area width
					columns = table.getColumns();
					int newWidth = area.width / columns.length >= 100 ? area.width
							/ columns.length
							: 100;
					for (TableColumn tableColumn : columns) {
						tableColumn.setWidth(newWidth);
					}
				}
			}
		});
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		TableViewerColumn channelNameColumn = new TableViewerColumn(
				tableViewer, SWT.NONE);
		channelNameColumn.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(((Channel) cell.getElement()).getName());
			}
		});
		new TableViewerColumnSorter(channelNameColumn) {
			@Override
			protected Object getValue(Object o) {
				return ((Channel) o).getName();
			}
		};
		TableColumn tblclmnChannelName = channelNameColumn.getColumn();
		tblclmnChannelName.setWidth(100);
		tblclmnChannelName.setText("Channel Name");

		TableViewerColumn channelOwnerColumn = new TableViewerColumn(
				tableViewer, SWT.NONE);
		channelOwnerColumn.setLabelProvider(new CellLabelProvider() {

			@Override
			public void update(ViewerCell cell) {
				cell.setText(((Channel) cell.getElement()).getOwner());
			}
		});
		new TableViewerColumnSorter(channelOwnerColumn) {
			@Override
			protected Object getValue(Object o) {
				return ((Channel) o).getOwner();
			}
		};
		TableColumn tblclmnOwner = channelOwnerColumn.getColumn();
		tblclmnOwner.setWidth(100);
		tblclmnOwner.setText("Owner");
		tableViewer.setContentProvider(new IStructuredContentProvider() {
			
			@Override
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
				
			}
			
			@Override
			public void dispose() {
				
			}
			
			@Override
			public Object[] getElements(Object inputElement) {
				return (Object[]) inputElement;			}
		});

		this.selectionProvider = new AbstractSelectionProviderWrapper(
				tableViewer, this) {
			@Override
			protected ISelection transform(IStructuredSelection selection) {
				if(selection!=null)
					return selection;
				else
					return new StructuredSelection();
			}
		};

		addPropertyChangeListener(new PropertyChangeListener() {

			List<String> properties = Arrays.asList("channels");

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (properties.contains(evt.getPropertyName())) {
					updateTable();
				}
			}

		});
	}

	@Override
	public void setMenu(Menu menu) {
		super.setMenu(menu);
		table.setMenu(menu);
	}
	
	@Override
	protected void queryCleared() {
		this.channels = null;
		this.errorBar.setException(null);
	}

	@Override
	protected void queryExecuted(Result result) {
		Exception e = result.exception;
		errorBar.setException(e);
		if (e == null) {
			this.setChannels(result.channels);
		}
	}

	@Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		selectionProvider.addSelectionChangedListener(listener);

	}

	@Override
	public ISelection getSelection() {
		return selectionProvider.getSelection();
	}

	@Override
	public void removeSelectionChangedListener(
			ISelectionChangedListener listener) {
		selectionProvider.removeSelectionChangedListener(listener);
	}

	@Override
	public void setSelection(ISelection selection) {
		selectionProvider.setSelection(selection);
	}

}
