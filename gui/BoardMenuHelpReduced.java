/*
 *  Copyright (C) 2014  Alfons Wirtz  
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
 * BoardMenuHelpReduced.java
 *
 * Created on 21. Oktober 2005, 09:06
 *
 */

package gui;

/**
 *
 * @author Alfons Wirtz
 */
public class BoardMenuHelpReduced extends javax.swing.JMenu
{
    /**
     * Creates a new instance of BoardMenuHelpReduced
     * Separated from BoardMenuHelp to avoid ClassNotFound exception when the library
     * jh.jar is not found, which is only used in the  extended help menu.
     */
    public BoardMenuHelpReduced(BoardFrame p_board_frame)
    {
        this.board_frame = p_board_frame;
        this.resources = java.util.ResourceBundle.getBundle("gui.resources.BoardMenuHelp", p_board_frame.get_locale());
        this.setText(this.resources.getString("help"));

        javax.swing.ImageIcon helpIcon = new javax.swing.ImageIcon(getClass().getResource("/gui/resources/helpicon.png"));
        javax.swing.JMenuItem browerHelp = new javax.swing.JMenuItem(helpIcon);
        browerHelp.setText(this.resources.getString("help"));
        browerHelp.setToolTipText(this.resources.getString("help"));
        browerHelp.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                 openBrowser("https://freerouting.org");
            }
        });
        this.add(browerHelp);

        javax.swing.JMenuItem about_window = new javax.swing.JMenuItem();
        about_window.setText(this.resources.getString("about"));
        about_window.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                board_frame.about_window.setVisible(true);
            }
        });
        this.add(about_window);
    }
    
    public void openBrowser(String url)
    {
        java.awt.Desktop desktop = java.awt.Desktop.isDesktopSupported() ? java.awt.Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {
            try {
                desktop.browse(new java.net.URL(url).toURI());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            board_frame.about_window.setVisible(true);
        }

    }

    protected final BoardFrame board_frame;
    protected final java.util.ResourceBundle resources;
}
