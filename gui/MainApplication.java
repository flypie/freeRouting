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
 * MainApplication.java
 *
 * Created on 19. Oktober 2002, 17:58
 *-
 */
package gui;

import java.awt.*; 
import javax.swing.*;

import board.TestLevel;

/**
 *
 * Main application for creating frames with new or existing board designs.
 *
 * @author  Alfons Wirtz
 */
public class MainApplication extends javax.swing.JFrame
{

    /**
     * Main function of the Application
     */
    public static void main(String[] p_args)
    {
        boolean designFileAsParameter = false;
        boolean debugOption = false;
        boolean whiteBackground = false;
        boolean autoSaveSpectraSessionFileOnExit = false;
        boolean autoroutesaveexit = false;
        int     maxOptimiserIterrations = Integer.MAX_VALUE;
        boolean FanOut=false;
        String designFileName = null;
        String designDirName = null;
        java.util.Locale currentLocale = java.util.Locale.ENGLISH;        for (int i = 0; i < p_args.length; ++i)
        {
            if (p_args[i].startsWith("-de"))
            // the design file is provided
            {
                if (p_args.length > i + 1 && !p_args[i + 1].startsWith("-"))
                {
                    designFileAsParameter = true;
                    designFileName = p_args[i + 1];
                }
            }
            else if (p_args[i].startsWith("-di"))
            // the design directory is provided
            {
                if (p_args.length > i + 1 && !p_args[i + 1].startsWith("-"))
                {
                    designDirName = p_args[i + 1];
                }
            }
            else if (p_args[i].startsWith("-l"))
            // the locale is provided
            {
                if (p_args.length > i + 1 && p_args[i + 1].startsWith("d"))
                {
                    currentLocale = java.util.Locale.GERMAN;
                }
            }
            else if (p_args[i].startsWith("-s"))
            {
                autoSaveSpectraSessionFileOnExit = true;
            }
            else if (p_args[i].startsWith("-t"))
            {
                debugOption = true;
            }
            else if (p_args[i].startsWith("-debug"))
            {
                debugOption = true;
            }
            else if (p_args[i].startsWith("-white"))
            {
                whiteBackground = true;
            }
            else if (p_args[i].startsWith("-asx"))
            {
                autoroutesaveexit = true;
                autoSaveSpectraSessionFileOnExit = true;
            }
            else if (p_args[i].startsWith("-moi"))
            {
                if (p_args.length > i + 1 && !p_args[i + 1].startsWith("-"))
                {
                    maxOptimiserIterrations = Integer.valueOf(p_args[i + 1]);
                }
            }            
            else if (p_args[i].startsWith("-fan"))
            {
                FanOut=true;
            }            
            else if (p_args[i].startsWith("-h")||p_args[i].startsWith("--help"))
            {
                System.out.println("FreeRouting version "+VERSION_NUMBER_STRING);
                System.out.println("command line options are:");
                System.out.println("-asx  autoroute save exit");
                System.out.println("-de  provide design file");
                System.out.println("-di  design folder used in file dialog");
                System.out.println("-l   provide locale");
                System.out.println("-moi maxium optimisation interations");
                System.out.println("-fan");
                System.out.println("-s   spectra session file is automatic saved on exit");
                System.out.println("-t   debug option");
                System.out.println("-white   white background");
                System.out.println("-h   this help");
                return;
            }
        }

        java.util.ResourceBundle resources = java.util.ResourceBundle.getBundle("gui.resources.MainApplication", currentLocale);
        if (designFileAsParameter)
        {

            DesignFile designFile = DesignFile.get_instance(designFileName);
            if (designFile == null)
            {
                System.out.print(resources.getString("message_6") + " ");
                System.out.print(designFileName);
                System.out.println(" " + resources.getString("message_7"));
                return;
            }
            String message = resources.getString("loading_design") + " " + designFileName;
            WindowMessage welcomeWindow = WindowMessage.show(message);
            final BoardFrame newFrame = createBoardFrame(designFile, autoSaveSpectraSessionFileOnExit, debugOption, currentLocale, whiteBackground, autoroutesaveexit, maxOptimiserIterrations,FanOut);
            welcomeWindow.dispose();
            if (newFrame == null)
            {
                System.out.print(resources.getString("message_6") + " ");
                System.out.print(designFileName);
                System.out.println(" " + resources.getString("message_7"));
                Runtime.getRuntime().exit(1);
            }
            
            if(autoSaveSpectraSessionFileOnExit)
            {
                newFrame.board_panel.board_handling.start_batch_autorouter();
            }
            else
            {
                newFrame.addWindowListener(new java.awt.event.WindowAdapter()
                {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent evt)
                    {
                        Runtime.getRuntime().exit(0);
                    }
                });
            }
        }
        else
        {

            DesignFile designFile = DesignFile.open_dialog(designDirName);

            if (designFile == null)
            {
                String message1 = resources.getString("message_3")+" ";
                int option = javax.swing.JOptionPane.showConfirmDialog(null, message1 , "error", 0,javax.swing.JOptionPane.ERROR_MESSAGE);
                Runtime.getRuntime().exit(1);
            }

            String message = resources.getString("loading_design") + " " + designFile.get_name();
            WindowMessage welcomeWindow = WindowMessage.show(message);
            welcomeWindow.setTitle(message);
            BoardFrame newFrame = createBoardFrame(designFile, autoSaveSpectraSessionFileOnExit, debugOption, currentLocale, whiteBackground, autoroutesaveexit, maxOptimiserIterrations, FanOut);
            welcomeWindow.dispose();
            if (newFrame == null) Runtime.getRuntime().exit(1);

            newFrame.addWindowListener(new java.awt.event.WindowAdapter()
            {
                @Override
                public void windowClosed(java.awt.event.WindowEvent evt)
                {
                    Runtime.getRuntime().exit(0);
                }
            });

        }
    }



    /**
     * Creates a new board frame containing the data of the input design file.
     * Returns null, if an error occured.
     */
    static private BoardFrame createBoardFrame(DesignFile designFile,
            boolean autoSaveSpectraSessionFileOnExit, boolean debugOption, java.util.Locale currentLocale, boolean whiteBackground, boolean autoroutesaveexit, Integer maxOptimiserIterrations, boolean FanOut)
    {
        java.util.ResourceBundle resources = java.util.ResourceBundle.getBundle("gui.resources.MainApplication", currentLocale);

        java.io.InputStream inputStream = designFile.get_input_stream();
        if (inputStream == null) return null;

        TestLevel testLevel;
        if (debugOption) testLevel = TestLevel.CRITICAL_DEBUGGING_OUTPUT;
        else testLevel = TestLevel.RELEASE_VERSION;

        BoardFrame newFrame = new BoardFrame(designFile, autoSaveSpectraSessionFileOnExit, testLevel, currentLocale, whiteBackground, autoroutesaveexit,maxOptimiserIterrations, FanOut);
        boolean read_ok = newFrame.read(inputStream, designFile.is_created_from_text_file(), null);
        if (!read_ok)  return null;

        //new_frame.menubar.add_design_dependent_items();
        if (designFile.is_created_from_text_file())
        {
            // Read the file  with the saved rules, if it is existing.

            String file_name = designFile.get_name();
            String[] name_parts = file_name.split("\\.");
            String confirm_import_rules_message = resources.getString("confirm_import_rules");
            DesignFile.read_rules_file(name_parts[0], designFile.get_parent(), newFrame.board_panel.board_handling, confirm_import_rules_message);
            newFrame.refresh_windows();
        }
        
        newFrame.board_panel.board_handling.settings.autoroute_settings.set_with_fanout(FanOut); //Ontobus
        return newFrame;
    }


    public MainApplication(){
    }

    /**
     * Change this string when creating a new version
     */
    static final String VERSION_NUMBER_STRING = "1.3.2α";
}
