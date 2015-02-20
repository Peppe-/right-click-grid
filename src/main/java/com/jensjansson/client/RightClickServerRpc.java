package com.jensjansson.client;

import com.vaadin.shared.communication.ServerRpc;

public interface RightClickServerRpc extends ServerRpc {
    public void rightClicked(int rowIndex, int columnIndex, int clientX, int clientY);
}