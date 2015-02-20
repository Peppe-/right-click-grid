package com.jensjansson;

import org.vaadin.peter.contextmenu.ContextMenu;
import org.vaadin.peter.contextmenu.ContextMenu.ContextMenuItem;
import org.vaadin.peter.contextmenu.ContextMenu.ContextMenuItemClickEvent;
import org.vaadin.peter.contextmenu.ContextMenu.ContextMenuItemClickListener;
import org.vaadin.peter.contextmenu.ContextMenu.ContextMenuOpenedListener.ComponentListener;
import org.vaadin.peter.contextmenu.ContextMenu.ContextMenuOpenedOnComponentEvent;

import com.jensjansson.RightClickExtension.RightClickListener;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;

public class MyGridView extends VerticalLayout {
	private static final long serialVersionUID = -5193406304540425820L;
	private Object lastClickedItemId = null;
	@SuppressWarnings("unchecked")
	public MyGridView() {
		final Grid grid = new Grid();
		grid.setSizeFull();
		
		final IndexedContainer container = new IndexedContainer();
		container.addContainerProperty("column 1", String.class, "");
		container.addContainerProperty("column 2", String.class, "");
		container.addContainerProperty("column 3", String.class, "");
		container.addContainerProperty("column 4", String.class, "");
		
		for(int i = 1; i < 100; i++){
			Item item = container.addItem(i);
			item.getItemProperty("column 1").setValue("row " + i +", column 1");
			item.getItemProperty("column 2").setValue("row " + i +", column 2");
			item.getItemProperty("column 3").setValue("row " + i +", column 3");
			item.getItemProperty("column 4").setValue("row " + i +", column 4");
		}
		final ContextMenu context = new ContextMenu();
		
		ContextMenuItem clear = context.addItem("clear selection", FontAwesome.MINUS);
		ContextMenuItem delete = context.addItem("delete", FontAwesome.TRASH_O);
		ContextMenuItem sub = context.addItem("sub menu", FontAwesome.INFO);
		sub.addItem("You can do sub menus", FontAwesome.EXCLAMATION_TRIANGLE);
		context.setAsContextMenuOf(grid);
		context.setOpenAutomatically(false);
		clear.addItemClickListener(new ContextMenuItemClickListener() {
			
			@Override
			public void contextMenuItemClicked(ContextMenuItemClickEvent event) {
				grid.select(null);
				
			}
		});
		delete.addItemClickListener(new ContextMenuItemClickListener() {
			
			@Override
			public void contextMenuItemClicked(ContextMenuItemClickEvent event) {
				boolean success = container.removeItem(lastClickedItemId);
				System.out.println("deleted: " + success);
			}
		});
		
		grid.setContainerDataSource(container);
		RightClickListener listener = new RightClickListener() {
			
			@Override
			public void rightClicked(int rowIndex, int columnIndex, int clientX,
					int clientY) {
				String info = "row: " + rowIndex + ", column: " + columnIndex + ", x: " + clientX + ", y: " + clientY;
				System.out.println(info);
				lastClickedItemId = container.getIdByIndex(rowIndex);
				Item item = container.getItem(lastClickedItemId);
				Object columnProperty = container.getContainerPropertyIds().toArray()[columnIndex];
				String value = String.valueOf(item.getItemProperty(columnProperty).getValue());
				context.open(clientX, clientY);
				Notification.show("Clicked on " + value + " located at " + clientX + ":" + clientY, Type.TRAY_NOTIFICATION);
			}
		};
		new RightClickExtension(grid, listener);
		
		// Set the selection mode
		grid.setSelectionMode(SelectionMode.SINGLE);
		grid.setWidth("800px");
		grid.setHeight("500px");
		addComponent(grid);
		setSizeFull();
		setComponentAlignment(grid, Alignment.MIDDLE_CENTER);
	}
}

