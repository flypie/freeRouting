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
 * BoardFileMenu.java
 *
 * Created on 11. Februar 2005, 11:26
 */
package gui;

/**
 * Creates the file menu of a board frame.
 *
 * @author Alfons Wirtz
 */
public class BoardMenuFile extends javax.swing.JMenu
{

    /** Returns a new file menu for the board frame. */
    public static BoardMenuFile get_instance(BoardFrame p_board_frame, boolean p_session_file_option)
    {
        final BoardMenuFile file_menu = new BoardMenuFile(p_board_frame, p_session_file_option);


   /*     javax.swing.JMenuItem save_and_exit_item = new javax.swing.JMenuItem();
        save_and_exit_item.setText(file_menu.resources.getString("save_and_exit"));
        save_and_exit_item.setToolTipText(file_menu.resources.getString("save_and_exit_tooltip"));
        save_and_exit_item.addActionListener(new java.awt.event.ActionListener()
        {

            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                if (file_menu.session_file_option)
                {
                    file_menu.board_frame.design_file.write_specctra_session_file(file_menu.board_frame);
                }
                else
                {
                    file_menu.board_frame.save();
                }
                file_menu.board_frame.dispose();
            }
        });

        file_menu.add(save_and_exit_item);


        javax.swing.JMenuItem cancel_and_exit_item = new javax.swing.JMenuItem();
        cancel_and_exit_item.setText(file_menu.resources.getString("cancel_and_exit"));
        cancel_and_exit_item.setToolTipText(file_menu.resources.getString("cancel_and_exit_tooltip"));
        cancel_and_exit_item.addActionListener(new java.awt.event.ActionListener()
        {

            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                file_menu.board_frame.dispose();
            }
        });

        file_menu.add(cancel_and_exit_item);

        if (!file_menu.session_file_option)
        {




        }*/


        return file_menu;
    }

 /*   public void add_design_dependent_items()
    {
       if (this.session_file_option)
        {
            return;
        }
        board.BasicBoard routing_board = this.board_frame.board_panel.board_handling.get_routing_board();
        boolean host_cad_is_eagle = routing_board.communication.host_cad_is_eagle();

        javax.swing.JMenuItem write_session_file_item = new javax.swing.JMenuItem();
        write_session_file_item.setText(resources.getString("session_file"));
        write_session_file_item.setToolTipText(resources.getString("session_file_tooltip"));
        write_session_file_item.addActionListener(new java.awt.event.ActionListener()
        {

            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                board_frame.design_file.write_specctra_session_file(board_frame);
            }
        });

        if ((routing_board.get_test_level() != board.TestLevel.RELEASE_VERSION || !host_cad_is_eagle))
        {
            this.add(write_session_file_item);
        }

        javax.swing.JMenuItem write_eagle_session_script_item = new javax.swing.JMenuItem();
        write_eagle_session_script_item.setText(resources.getString("eagle_script"));
        write_eagle_session_script_item.setToolTipText(resources.getString("eagle_script_tooltip"));
        write_eagle_session_script_item.addActionListener(new java.awt.event.ActionListener()
        {

            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                board_frame.design_file.update_eagle(board_frame);
            }
        });

        if (routing_board.get_test_level() != board.TestLevel.RELEASE_VERSION || host_cad_is_eagle)
        {
            this.add(write_eagle_session_script_item);
        }
    }*/




    /** Creates a new instance of BoardFileMenu */
    private BoardMenuFile(BoardFrame p_board_frame, boolean p_session_file_option)
    {
        session_file_option = p_session_file_option;
        board_frame = p_board_frame;
        resources = java.util.ResourceBundle.getBundle("gui.resources.BoardMenuFile", p_board_frame.get_locale());

        this.setText(this.resources.getString("file"));

        // Create the menu items.

        javax.swing.ImageIcon saveIcon = new javax.swing.ImageIcon(getClass().getResource("/gui/resources/filesave.png"));
        javax.swing.JMenuItem save_item = new javax.swing.JMenuItem(saveIcon);
        save_item.setText(this.resources.getString("save"));
        save_item.setToolTipText(this.resources.getString("save_tooltip"));
        save_item.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                saveAction();
            }
        });
        this.add(save_item);

        javax.swing.ImageIcon saveAsIcon = new javax.swing.ImageIcon(getClass().getResource("/gui/resources/filesaveas.png"));
        javax.swing.JMenuItem save_as_item = new javax.swing.JMenuItem(saveAsIcon);
        save_as_item.setText(this.resources.getString("save_as"));
        save_as_item.setToolTipText(this.resources.getString("save_as_tooltip"));
        save_as_item.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                saveAsAction();
            }
        });
        this.add(save_as_item);


        javax.swing.JMenuItem save_settings_item = new javax.swing.JMenuItem();
        save_settings_item.setText(resources.getString("settings"));
        save_settings_item.setToolTipText(resources.getString("settings_tooltip"));
        save_settings_item.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                saveDefaultsAction();
            }
        });
        add(save_settings_item);



        javax.swing.JMenu exportMenu = new javax.swing.JMenu(this.resources.getString("export"));

            javax.swing.JMenuItem write_session_file_item = new javax.swing.JMenuItem();
            write_session_file_item.setText(resources.getString("session_file"));
            write_session_file_item.setToolTipText(resources.getString("session_file_tooltip"));
            write_session_file_item.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    board_frame.design_file.write_specctra_session_file(board_frame);
                }
            });
            exportMenu.add(write_session_file_item);

            javax.swing.JMenuItem write_eagle_session_script_item = new javax.swing.JMenuItem();
            write_eagle_session_script_item.setText(resources.getString("eagle_script"));
            write_eagle_session_script_item.setToolTipText(resources.getString("eagle_script_tooltip"));
            write_eagle_session_script_item.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    board_frame.design_file.update_eagle(board_frame);
                }
            });
            exportMenu.add(write_eagle_session_script_item);

        this.add(exportMenu);


        javax.swing.ImageIcon quitIcon = new javax.swing.ImageIcon(getClass().getResource("/gui/resources/exiticon.png"));
        javax.swing.JMenuItem quit_item = new javax.swing.JMenuItem(quitIcon);
        quit_item.setText(this.resources.getString("quit"));
        quit_item.setToolTipText(this.resources.getString("quit_tooltip"));
        quit_item.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                quitAction();
            }
        });
        this.add(quit_item);

    }

    private void saveAction(){
        boolean save_ok = this.board_frame.save();
        this.board_frame.board_panel.board_handling.close_files();
        if (save_ok)
        {
            this.board_frame.screen_messages.set_status_message(this.resources.getString("save_message"));
        }
    }

    private void saveAsAction()
    {
        if (this.board_frame.design_file != null)
        {
            this.board_frame.design_file.save_as_dialog(this, this.board_frame);
        }
    }


    private void quitAction()
    {
        if (this.session_file_option)
        {
            this.board_frame.design_file.write_specctra_session_file(this.board_frame);
        }
        this.board_frame.dispose();
    }


    private void saveDefaultsAction()
    {
        java.io.OutputStream output_stream = null;
        java.io.File defaults_file = new java.io.File(board_frame.design_file.get_parent(), BoardFrame.GUI_DEFAULTS_FILE_NAME);
        if (defaults_file.exists())
        {
            // Make a backup copy of the old defaulds file.
            java.io.File defaults_file_backup = new java.io.File(board_frame.design_file.get_parent(), BoardFrame.GUI_DEFAULTS_FILE_BACKUP_NAME);
            if (defaults_file_backup.exists())
            {
                defaults_file_backup.delete();
            }
            defaults_file.renameTo(defaults_file_backup);
        }
        try
        {
            output_stream = new java.io.FileOutputStream(defaults_file);
        } catch (Exception e)
        {
            output_stream = null;
        }
        boolean write_ok;
        if (output_stream == null)
        {
            write_ok = false;
        }
        else
        {
            write_ok = gui.GUIDefaultsFile.write(board_frame, board_frame.board_panel.board_handling, output_stream);
        }
        if (write_ok)
        {
            board_frame.screen_messages.set_status_message(resources.getString("message_17"));
        }
        else
        {
            board_frame.screen_messages.set_status_message(resources.getString("message_18"));
        }

    }

    public final BoardFrame board_frame;
    private final boolean session_file_option;
    private final java.util.ResourceBundle resources;
}
