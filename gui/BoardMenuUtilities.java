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
 * BoardMenuOther.java
 *
 * Created on 19. Oktober 2005, 08:34
 *
 */

package gui;


public class BoardMenuUtilities extends javax.swing.JMenu
{
    /** Returns a new utilities menu for the board frame. */
    public static BoardMenuUtilities get_instance(BoardFrame p_board_frame)
    {
        final BoardMenuUtilities utilities_menu = new BoardMenuUtilities(p_board_frame);
        
        return utilities_menu;
    }
    
    /** Creates a new instance of BoardMenuUtilities */
    private BoardMenuUtilities(BoardFrame p_board_frame)
    {
        board_frame = p_board_frame;
        resources = java.util.ResourceBundle.getBundle("gui.resources.BoardMenuUtilities", p_board_frame.get_locale());

        this.setText(this.resources.getString("Utilities"));

        javax.swing.JMenu displayMenu = BoardMenuDisplay.get_instance(p_board_frame);
        this.add(displayMenu);

        javax.swing.JMenu parameter_menu = BoardMenuParameter.get_instance(p_board_frame);
        this.add(parameter_menu);
        javax.swing.JMenu rules_menu = BoardMenuRules.get_instance(p_board_frame);
        this.add(rules_menu);
        javax.swing.JMenu info_menu = BoardMenuInfo.get_instance(p_board_frame);
        this.add(info_menu);

        javax.swing.JMenu logfileMenu = new javax.swing.JMenu(this.resources.getString("logfile"));
        //logsubmenu.setMnemonic(KeyEvent.VK_S);
            javax.swing.JMenuItem write_logfile_item = new javax.swing.JMenuItem();
            write_logfile_item.setText(this.resources.getString("generate_logfile"));
            write_logfile_item.setToolTipText(this.resources.getString("generate_logfile_tooltip"));
            write_logfile_item.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    write_logfile_action();
                }
            });

            logfileMenu.add(write_logfile_item);

            javax.swing.JMenuItem replay_logfile_item = new javax.swing.JMenuItem();
            replay_logfile_item.setText(this.resources.getString("replay_logfile"));
            replay_logfile_item.setToolTipText(this.resources.getString("replay_logfile_tooltip"));
            replay_logfile_item.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    read_logfile_action();
                }
            });

            logfileMenu.add(replay_logfile_item);
        this.add(logfileMenu);

        javax.swing.JMenuItem snapshots = new javax.swing.JMenuItem();
        snapshots.setText(this.resources.getString("snapshots"));
        snapshots.setToolTipText(this.resources.getString("snapshots_tooltip"));
        snapshots.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                board_frame.snapshot_window.setVisible(true);
            }
        });
        this.add(snapshots);

    }
    

    
    private void write_logfile_action()
    {
        javax.swing.JFileChooser file_chooser = new javax.swing.JFileChooser();
        java.io.File logfile_dir = board_frame.design_file.get_parent_file();
        file_chooser.setCurrentDirectory(logfile_dir);
        file_chooser.setFileFilter(BoardFrame.logfile_filter);
        file_chooser.showOpenDialog(this);
        java.io.File filename = file_chooser.getSelectedFile();
        if (filename == null)
        {
            board_frame.screen_messages.set_status_message(resources.getString("message_8"));
        }
        else
        {
            board_frame.screen_messages.set_status_message(resources.getString("message_9"));
            board_frame.board_panel.board_handling.start_logfile(filename);
        }
    }

    private void read_logfile_action()
    {
        javax.swing.JFileChooser file_chooser = new javax.swing.JFileChooser();
        java.io.File logfile_dir = board_frame.design_file.get_parent_file();
        file_chooser.setCurrentDirectory(logfile_dir);
        file_chooser.setFileFilter(BoardFrame.logfile_filter);
        file_chooser.showOpenDialog(this);
        java.io.File filename = file_chooser.getSelectedFile();
        if (filename == null)
        {
            board_frame.screen_messages.set_status_message(resources.getString("message_10"));
        }
        else
        {
            java.io.InputStream input_stream = null;
            try
            {
                input_stream = new java.io.FileInputStream(filename);
            } catch (java.io.FileNotFoundException e)
            {
                return;
            }
            board_frame.read_logfile(input_stream);
        }
    }

    private final BoardFrame board_frame;
    private final java.util.ResourceBundle resources;

}
