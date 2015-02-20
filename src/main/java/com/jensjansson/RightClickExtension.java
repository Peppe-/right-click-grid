package com.jensjansson;

import com.jensjansson.client.RightClickServerRpc;
import com.vaadin.server.AbstractExtension;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

public class RightClickExtension extends AbstractExtension {

	interface RightClickListener {
		public void rightClicked(int rowIndex, int columnIndex, int clientX, int clientY);
	}
	
	private final RightClickListener listener;
	
	private RightClickServerRpc rpc = new RightClickServerRpc() {

		@Override
		public void rightClicked(int rowIndex, int columnIndex, int clientX, int clientY) {
			if(listener != null){
				listener.rightClicked(rowIndex, columnIndex, clientX, clientY);
			}
		}
	};

	public RightClickExtension(Grid grid, RightClickListener listener) {
		super.extend(grid);
		registerRpc(rpc);
		this.listener = listener;
	}
}
