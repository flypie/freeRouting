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
public class BoardMenuZoom extends javax.swing.JMenu
{

    public BoardMenuZoom(BoardFrame boardFrame_)
    {
        this.boardFrame = boardFrame_;
        this.resources = java.util.ResourceBundle.getBundle("gui.resources.BoardMenuZoom", boardFrame_.get_locale());
        this.setText("Zoom");

        javax.swing.ImageIcon zoomMouseIcon = new javax.swing.ImageIcon(getClass().getResource("/gui/resources/zoomicon.png"));
        javax.swing.JMenuItem zoomMouseItem = new javax.swing.JMenuItem(zoomMouseIcon);
        zoomMouseItem.setText("Zoom Mouse");
        zoomMouseItem.setToolTipText("zoom into a region defines by mouse");
        zoomMouseItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                 boardFrame.board_panel.board_handling.zoom_region();
            }
        });
        this.add(zoomMouseItem);

        javax.swing.ImageIcon zoomFullIcon = new javax.swing.ImageIcon(getClass().getResource("/gui/resources/zoomfullicon.png"));
        javax.swing.JMenuItem zoomFullItem = new javax.swing.JMenuItem(zoomFullIcon);
        zoomFullItem.setText("Zoom Fit All");
        zoomFullItem.setToolTipText("zoom in a way that the hole board is displayed");
        zoomFullItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                 boardFrame.zoom_all();
            }
        });
        this.add(zoomFullItem);

        javax.swing.ImageIcon zoomSelectionIcon = new javax.swing.ImageIcon(getClass().getResource("/gui/resources/zoomselecticon.png"));
        javax.swing.JMenuItem zoomSelectionItem = new javax.swing.JMenuItem(zoomSelectionIcon);
        zoomSelectionItem.setText("Zoom Fit Selection");
        zoomSelectionItem.setToolTipText("zoom on the selcetion");
        zoomSelectionItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                  boardFrame.board_panel.board_handling.zoom_selection();
            }
        });
        this.add(zoomSelectionItem);


    }
    


    protected final BoardFrame boardFrame;
    protected final java.util.ResourceBundle resources;
}
