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
 * BoardMenuBar.java
 *
 * Created on 11. Februar 2005, 10:17
 */

package gui;

/**
 * Creates the menu bar of a board frame together with its menu items.
 *
 * @author Alfons Wirtz
 */
class BoardMenuBar extends javax.swing.JMenuBar
{
    private static final long serialVersionUID = 7524472765628777254L;
    /** Creates a new BoardMenuBar together with its menus */
    static BoardMenuBar get_instance(BoardFrame p_board_frame, boolean p_session_file_option)
    {
        BoardMenuBar menubar = new BoardMenuBar();
        menubar.file_menu = BoardMenuFile.get_instance(p_board_frame, p_session_file_option);
        menubar.add(menubar.file_menu);
        javax.swing.JMenu routingMenu = new BoardMenuRouting(p_board_frame);
        menubar.add(routingMenu);
        javax.swing.JMenu zoomMenu = new BoardMenuZoom(p_board_frame);
        menubar.add(zoomMenu);

        javax.swing.JMenu utilities_menu = BoardMenuUtilities.get_instance(p_board_frame);
        menubar.add(utilities_menu);
        javax.swing.JMenu help_menu = new BoardMenuHelpReduced(p_board_frame);
        menubar.add(help_menu);

        return menubar;
    }
    
  /*  void add_design_dependent_items()
    {
        this.file_menu.add_design_dependent_items();
    }*/
    
    public BoardMenuFile file_menu;
}
