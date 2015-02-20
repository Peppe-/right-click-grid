package com.jensjansson.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.user.client.ui.Widget;
import com.jensjansson.RightClickExtension;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ServerConnector;
import com.vaadin.client.extensions.AbstractExtensionConnector;
import com.vaadin.client.widget.grid.events.BodyClickHandler;
import com.vaadin.client.widget.grid.events.GridClickEvent;
import com.vaadin.client.widget.grid.events.HeaderClickHandler;
import com.vaadin.client.widgets.Grid;
import com.vaadin.shared.ui.Connect;

@Connect(RightClickExtension.class)
public class RightClickExtensionConnector extends AbstractExtensionConnector {
	Logger logger = Logger.getLogger(RightClickExtensionConnector.class.getName());
	
    RightClickServerRpc rpc =
            getRpcProxy(RightClickServerRpc.class);

	 @Override
	    protected void extend(ServerConnector target) {
		 	logger.log(Level.WARNING, "extending grid with right click");
	        // Get the extended widget
	        final Widget widget =
	                ((ComponentConnector) target).getWidget();
	        Grid grid = (Grid) widget;
	        grid.addHeaderClickHandler(new HeaderClickHandler() {
				
				@Override
				public void onClick(GridClickEvent event) {
					logger.log(Level.WARNING, "header click");					
				}
			});
	        grid.addBodyClickHandler(new BodyClickHandler() {
				
				@Override
				public void onClick(GridClickEvent event) {
					// TODO Auto-generated method stub
					logger.log(Level.WARNING, "right click issued, row: " + event.getTargetCell().getRowIndex() + event.getTargetCell().getColumnIndex());
					rpc.rightClicked(event.getTargetCell().getRowIndex(), event.getTargetCell().getColumnIndex(), event.getClientX(), event.getClientY());
				}
			});
	    }
}
