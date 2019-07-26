/*
 *  Copyright (C) 2017  Jürgen Thies
 *   website www.freerouting.net
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License at <http://www.gnu.org/licenses/> 
 *   for more details.
 *
 * BoardMenuRouting.java
 *
 * Created on 3. Feb 2017
 *
 */

package gui;

/**
 *
 * @author Jürgen Thies
 */
public class BoardMenuRouting extends javax.swing.JMenu
{

    public BoardMenuRouting(BoardFrame boardFrame_)
    {
        this.boardFrame = boardFrame_;
        this.resources = java.util.ResourceBundle.getBundle("gui.resources.BoardMenuRouting", boardFrame_.get_locale());
        this.setText("Routing");

        javax.swing.ImageIcon autoroutingIcon = new javax.swing.ImageIcon(getClass().getResource("/gui/resources/netautorouteicon.png"));
        javax.swing.JMenuItem autoroutingItem = new javax.swing.JMenuItem(autoroutingIcon);
        autoroutingItem.setText("Autorouting");
        autoroutingItem.setToolTipText("start autorouting of the complete PCB");
        autoroutingItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                 boardFrame.board_panel.board_handling.start_batch_autorouter();
            }
        });
        this.add(autoroutingItem);

        javax.swing.ImageIcon routingIcon = new javax.swing.ImageIcon(getClass().getResource("/gui/resources/netrouteicon.png"));
        javax.swing.JMenuItem routingItem = new javax.swing.JMenuItem(routingIcon);
        routingItem.setText("Routing (manual)");
        routingItem.setToolTipText("set manual routing mode");
        routingItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                 boardFrame.board_panel.board_handling.set_route_menu_state();
            }
        });
        this.add(routingItem);

        javax.swing.ImageIcon selectIcon = new javax.swing.ImageIcon(getClass().getResource("/gui/resources/selectediticon.png"));
        javax.swing.JMenuItem selectItem = new javax.swing.JMenuItem(selectIcon);
        selectItem.setText("Select/Edit");
        selectItem.setToolTipText("set select/edit mode");
        selectItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                 boardFrame.board_panel.board_handling.set_select_menu_state();
            }
        });
        this.add(selectItem);

        javax.swing.ImageIcon dragIcon = new javax.swing.ImageIcon(getClass().getResource("/gui/resources/dragicon.png"));
        javax.swing.JMenuItem dragItem = new javax.swing.JMenuItem(dragIcon);
        dragItem.setText("Drag");
        dragItem.setToolTipText("set drag mode to move components/nets");
        dragItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                 boardFrame.board_panel.board_handling.set_drag_menu_state();
            }
        });
        this.add(dragItem);

        this.addSeparator();

        javax.swing.ImageIcon undoIcon = new javax.swing.ImageIcon(getClass().getResource("/gui/resources/undoicon.png"));
        javax.swing.JMenuItem undoItem = new javax.swing.JMenuItem(undoIcon);
        undoItem.setText("Undo");
        undoItem.setToolTipText("undo last step");
        undoItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                boardFrame.board_panel.board_handling.cancel_state();
                boardFrame.board_panel.board_handling.undo();
                boardFrame.refresh_windows();
            }
        });
        this.add(undoItem);


        javax.swing.ImageIcon redoIcon = new javax.swing.ImageIcon(getClass().getResource("/gui/resources/redoicon.png"));
        javax.swing.JMenuItem redoItem = new javax.swing.JMenuItem(redoIcon);
        redoItem.setText("Redo");
        redoItem.setToolTipText("redo last undo step");
        redoItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                boardFrame.board_panel.board_handling.redo();
            }
        });
        this.add(redoItem);



    }
    


    protected final BoardFrame boardFrame;
    protected final java.util.ResourceBundle resources;
}
