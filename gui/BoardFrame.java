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
 */

package gui;

import interactive.ScreenMessages;

import java.io.File;

import datastructures.FileFilter;

import board.TestLevel;
import board.BoardObservers;

import designformats.specctra.DsnFile;
import java.io.IOException;

/**
 *
 * Graphical frame of for interactive editing of a routing board.
 *
 * @author  Alfons Wirtz
 */

public class BoardFrame extends javax.swing.JFrame
{
   private static final long serialVersionUID = 7524472765628777220L;
   
    /**
     *
     */
    public void WriteSpecctra()
    {
        design_file.write_specctra_session_file(menubar.file_menu.board_frame);
    }
    
    /**
     * Creates new form BoardFrame.
     * If p_test_level > RELEASE_VERSION, functionality not yet ready for release is included.
     * Also the warning output depends on p_test_level.
     */
    public BoardFrame(DesignFile p_design, boolean autoSaveSpectraSessionFileOnExit_, TestLevel p_test_level,
            java.util.Locale p_locale, boolean whiteBackground, boolean p_autoroutesaveexit, Integer maxOptimiserIterrations,boolean FanOut)
    {
        this(p_design, autoSaveSpectraSessionFileOnExit_ , p_test_level,
                new board.BoardObserverAdaptor(), new board.ItemIdNoGenerator(),
                p_locale, whiteBackground, p_autoroutesaveexit, maxOptimiserIterrations, FanOut);
    }
    
    /**
     * Creates new form BoardFrame.
     * The parameters p_item_observers and p_item_id_no_generator are used for syncronizing purposes,
     * if the frame is embedded into a host system,
     */
    BoardFrame(DesignFile p_design, boolean autoSaveSpectraSessionFileOnExit_, TestLevel p_test_level, BoardObservers p_observers,
            datastructures.IdNoGenerator p_item_id_no_generator, java.util.Locale p_locale, boolean whiteBackground, boolean p_autoroutesaveexit, int  p_maxOptimiserIterrations, boolean p_FanOut)
    {
        this.design_file = p_design;
        this.test_level = p_test_level;
        
        this.board_observers = p_observers;
        this.item_id_no_generator = p_item_id_no_generator;
        this.locale = p_locale;
        this.resources = java.util.ResourceBundle.getBundle("gui.resources.BoardFrame", p_locale);
        BoardMenuBar curr_menubar;
        this.autoSaveSpectraSessionFileOnExit = autoSaveSpectraSessionFileOnExit_;
        curr_menubar = BoardMenuBar.get_instance(this, autoSaveSpectraSessionFileOnExit);
        
        
        this.autoroutesaveexit=p_autoroutesaveexit;

        this.menubar = curr_menubar;
        setJMenuBar(this.menubar);
        
        this.toolbar_panel = new BoardToolbar(this);
        this.add(this.toolbar_panel, java.awt.BorderLayout.NORTH);
        
        this.message_panel = new BoardPanelStatus(this.locale);
        this.add(this.message_panel, java.awt.BorderLayout.SOUTH);
        
        this.select_toolbar = new BoardToolbarSelectedItem(this);
        
        this.screen_messages =
                new ScreenMessages(this.message_panel.status_message, this.message_panel.add_message,
                this.message_panel.current_layer, this.message_panel.mouse_position, this.locale);
        
        this.scroll_pane = new javax.swing.JScrollPane();
        this.scroll_pane.setPreferredSize(new java.awt.Dimension(1150, 800));
        this.scroll_pane.setVerifyInputWhenFocusTarget(false);
        this.add(scroll_pane, java.awt.BorderLayout.CENTER);
        
        this.board_panel = new BoardPanel(screen_messages, this, p_locale,whiteBackground, p_maxOptimiserIterrations, p_FanOut);
        this.scroll_pane.setViewportView(board_panel);
        
        this.setTitle(resources.getString("title")+" "+design_file.get_name());
        this.addWindowListener(new WindowStateListener());
        
        this.pack();


    }
    
    /**
     * Reads interactive actions from a logfile.
     */
    void read_logfile(java.io.InputStream p_input_stream)
    {
        board_panel.board_handling.read_logfile(p_input_stream);
    }
    
    
    /**
     * Reads an existing board design from file.
     * If p_is_import, the design is read from a scpecctra dsn file.
     * Returns false, if the file is invalid.
     */
    boolean read(java.io.InputStream p_input_stream, boolean p_is_import, javax.swing.JTextField p_message_field)
    {
        java.awt.Point viewport_position = null;
        if (p_is_import)
        {
            DsnFile.ReadResult read_result = board_panel.board_handling.import_design(p_input_stream, this.board_observers,
                    this.item_id_no_generator, this.test_level);
            if (read_result !=  DsnFile.ReadResult.OK)
            {
                if (p_message_field != null)
                {
                    if (read_result == DsnFile.ReadResult.OUTLINE_MISSING)
                    {
                        p_message_field.setText(resources.getString("error_7"));
                    }
                    else
                    {
                         p_message_field.setText(resources.getString("error_6"));
                    }
                }
                return false;
            }
            viewport_position = new java.awt.Point(0,0);
            initialize_windows();
        }
        else
        {
            java.io.ObjectInputStream object_stream;
            try
            {
                object_stream = new java.io.ObjectInputStream(p_input_stream);
            }
            catch (java.io.IOException e)
            {
                return false;
            }
            boolean read_ok = board_panel.board_handling.read_design(object_stream, this.test_level);
            if (!read_ok)
            {
                return false;
            }
            java.awt.Point frame_location;
            java.awt.Rectangle frame_bounds;
            try
            {
                viewport_position = (java.awt.Point) object_stream.readObject();
                frame_location = (java.awt.Point) object_stream.readObject();
                frame_bounds = (java.awt.Rectangle) object_stream.readObject();
            }
            catch (IOException e)
            {
                return false;
            }
            catch (ClassNotFoundException e)
            {
                return false;
            }
            this.setLocation(frame_location);
            this.setBounds(frame_bounds);
            
            allocate_permanent_subwindows();
            
            for (int i = 0; i < this.permanent_subwindows.length; ++i)
            {
                this.permanent_subwindows[i].read(object_stream);
            }
        }
        try
        {
            p_input_stream.close();
        }
        catch (java.io.IOException e)
        {
            return false;
        }
        
        java.awt.Dimension panel_size = board_panel.board_handling.graphics_context.get_panel_size();
        board_panel.setSize(panel_size);
        board_panel.setPreferredSize(panel_size);
        if (viewport_position != null)
        {
            board_panel.set_viewport_position(viewport_position);
        }
        board_panel.create_popup_menus();
        board_panel.init_colors();
        board_panel.board_handling.create_ratsnest();
        this.highlight_selected_button();
        this.toolbar_panel.unit_factor_field.setValue(board_panel.board_handling.coordinate_transform.user_unit_factor);
        this.toolbar_panel.unit_combo_box.setSelectedItem(board_panel.board_handling.coordinate_transform.user_unit);
        this.setVisible(true);
        if (p_is_import)
        {
            // Read the default gui settings, if gui default file exists.
            java.io.InputStream input_stream = null;
            boolean defaults_file_found;
            File defaults_file = new File(this.design_file.get_parent(), GUI_DEFAULTS_FILE_NAME);
            defaults_file_found = true;
            try
            {
                input_stream = new java.io.FileInputStream(defaults_file);
            }
            catch (java.io.FileNotFoundException e)
            {
                defaults_file_found = false;
            }
            if (defaults_file_found)
            {
                boolean read_ok = gui.GUIDefaultsFile.read(this, board_panel.board_handling, input_stream);
                if (!read_ok)
                {
                    screen_messages.set_status_message(resources.getString("error_1"));
                }
                try
                {
                    input_stream.close();
                }
                catch (java.io.IOException e)
                {
                    return false;
                }
            }
            this.zoom_all();
        }
        return true;
    }
    
    
    /**
     * Saves the interactive settings and the design file to disk.
     * Returns false, if the save failed.
     */
    boolean save()
    {
        if (this.design_file == null)
        {
            return false;
        }
        java.io.OutputStream output_stream;
        java.io.ObjectOutputStream object_stream;
        try
        {
            output_stream = new java.io.FileOutputStream(this.design_file.get_output_file());
            object_stream = new java.io.ObjectOutputStream(output_stream);
        }
        catch (java.io.IOException e)
        {
            screen_messages.set_status_message(resources.getString("error_2"));
            return false;
        }
        catch (java.security.AccessControlException e)
        {
            screen_messages.set_status_message(resources.getString("error_3"));
            return false;
        }
        boolean save_ok = board_panel.board_handling.save_design_file(object_stream);
        if (!save_ok)
        {
            return false;
        }
        try
        {
            object_stream.writeObject(board_panel.get_viewport_position());
            object_stream.writeObject(this.getLocation());
            object_stream.writeObject(this.getBounds());
        }
        catch (java.io.IOException e)
        {
            screen_messages.set_status_message(resources.getString("error_4"));
            return false;
        }
        for (int i = 0; i < this.permanent_subwindows.length; ++i)
        {
            this.permanent_subwindows[i].save(object_stream);
        }
        try
        {
            object_stream.flush();
            output_stream.close();
        }
        catch (java.io.IOException e)
        {
            screen_messages.set_status_message(resources.getString("error_5"));
            return false;
        }
        return true;
    }
    
    /**
     * Sets contexts sensitive help for the input component, if the help system is used.
     */
    public void set_context_sensitive_help(java.awt.Component p_component, String p_help_id)
    {

           java.awt.Component curr_component;
            if (p_component instanceof javax.swing.JFrame)
            {
                curr_component = ((javax.swing.JFrame) p_component).getRootPane();
            }
            else
            {
                curr_component = p_component;
            }
            String help_id = "html_files." + p_help_id;
            //System.out.println(help_id);
            //javax.help.CSH.setHelpIDString(curr_component, help_id);
            // if (!this.is_web_start)
            {
                /*help_broker.enableHelpKey(curr_component, help_id, help_set);  cases error disabled in 2015.1.25*/
            }

    }
    
    /** Sets the toolbar to the buttons of the selected item state. */
    public void set_select_toolbar()
    {
        getContentPane().remove(toolbar_panel);
        getContentPane().add(select_toolbar, java.awt.BorderLayout.NORTH);
        repaint();
    }
    
    /** Sets the toolbar buttons to the select. route and drag menu buttons of the main menu. */
    public void set_menu_toolbar()
    {
        getContentPane().remove(select_toolbar);
        getContentPane().add(toolbar_panel, java.awt.BorderLayout.NORTH);
        repaint();
    }
    
    /**
     * Calculates the absolute location of the board frame in his outmost parent frame.
     */
    java.awt.Point absolute_panel_location()
    {
        int x = this.scroll_pane.getX();
        int y = this.scroll_pane.getY();
        java.awt.Container curr_parent = this.scroll_pane.getParent();
        while (curr_parent != null)
        {
            x += curr_parent.getX();
            y += curr_parent.getY();
            curr_parent = curr_parent.getParent();
        }
        return new java.awt.Point(x, y);
    }
    
    /** Sets the displayed region to the whole board. */
    public void zoom_all()
    {
        board_panel.board_handling.adjust_design_bounds();
        java.awt.Rectangle display_rect = board_panel.get_viewport_bounds();
        java.awt.Rectangle design_bounds = board_panel.board_handling.graphics_context.get_design_bounds();
        double width_factor = display_rect.getWidth() /design_bounds.getWidth();
        double height_factor = display_rect.getHeight() /design_bounds.getHeight();
        double zoom_factor =  Math.min(width_factor, height_factor);
        java.awt.geom.Point2D zoom_center = board_panel.board_handling.graphics_context.get_design_center();
        board_panel.zoom(zoom_factor, zoom_center);
        java.awt.geom.Point2D new_vieport_center = board_panel.board_handling.graphics_context.get_design_center();
        board_panel.set_viewport_center(new_vieport_center);
        
    }
    
    /**
     * Actions to be taken when this frame vanishes.
     */
   @Override
    public void dispose()
    {
        for (int i = 0; i < this.permanent_subwindows.length; ++i)
        {
            if (this.permanent_subwindows[i] != null)
            {
                this.permanent_subwindows[i].dispose();
                this.permanent_subwindows[i] = null;
            }
        }
        for (BoardTemporarySubWindow curr_subwindow : this.temporary_subwindows)
        {
            if (curr_subwindow != null)
            {
                curr_subwindow.board_frame_disposed();
            }
        }
        if (board_panel.board_handling != null)
        {
            board_panel.board_handling.dispose();
            board_panel.board_handling = null;
        }
        super.dispose();
    }
    
    private void allocate_permanent_subwindows()
    {
        this.color_manager = new ColorManager(this);
        this.permanent_subwindows[0] = this.color_manager;
        this.object_visibility_window = WindowObjectVisibility.get_instance(this);
        this.permanent_subwindows[1] = this.object_visibility_window;
        this.layer_visibility_window = WindowLayerVisibility.get_instance(this);
        this.permanent_subwindows[2] = this.layer_visibility_window;
        this.display_misc_window = new WindowDisplayMisc(this);
        this.permanent_subwindows[3] = this.display_misc_window;
        this.snapshot_window = new WindowSnapshot(this);
        this.permanent_subwindows[4] = this.snapshot_window;
        this.route_parameter_window = new WindowRouteParameter(this);
        this.permanent_subwindows[5] = this.route_parameter_window;
        this.select_parameter_window = new WindowSelectParameter(this);
        this.permanent_subwindows[6] = this.select_parameter_window;
        this.clearance_matrix_window = new WindowClearanceMatrix(this);
        this.permanent_subwindows[7] = this.clearance_matrix_window;
        this.padstacks_window = new WindowPadstacks(this);
        this.permanent_subwindows[8] = this.padstacks_window;
        this.packages_window = new WindowPackages(this);
        this.permanent_subwindows[9] = this.packages_window;
        this.components_window = new WindowComponents(this);
        this.permanent_subwindows[10] = this.components_window;
        this.incompletes_window = new WindowIncompletes(this);
        this.permanent_subwindows[11] = this.incompletes_window;
        this.clearance_violations_window = new WindowClearanceViolations(this);
        this.permanent_subwindows[12] = this.clearance_violations_window;
        this.net_info_window = new WindowNets(this);
        this.permanent_subwindows[13] = this.net_info_window;
        this.via_window = new WindowVia(this);
        this.permanent_subwindows[14] = this.via_window;
        this.edit_vias_window = new WindowEditVias(this);
        this.permanent_subwindows[15] = this.edit_vias_window;
        this.edit_net_rules_window = new WindowNetClasses(this);
        this.permanent_subwindows[16] = this.edit_net_rules_window;
        this.assign_net_classes_window = new WindowAssignNetClass(this);
        this.permanent_subwindows[17] = this.assign_net_classes_window;
        this.length_violations_window = new WindowLengthViolations(this);
        this.permanent_subwindows[18] = this.length_violations_window;
        this.about_window = new WindowAbout(this.locale);
        this.permanent_subwindows[19] = this.about_window;
        this.move_parameter_window = new WindowMoveParameter(this);
        this.permanent_subwindows[20] = this.move_parameter_window;
        this.unconnected_route_window = new WindowUnconnectedRoute(this);
        this.permanent_subwindows[21] = this.unconnected_route_window;
        this.route_stubs_window = new WindowRouteStubs(this);
        this.permanent_subwindows[22] = this.route_stubs_window;
        this.autoroute_parameter_window = new WindowAutorouteParameter(this);
        this.permanent_subwindows[23] = this.autoroute_parameter_window;
    }
    
    /**
     * Creates the additional frames of the board frame.
     */
    private void initialize_windows()
    {
        allocate_permanent_subwindows();
        
        this.setLocation(120, 0);
        
        this.select_parameter_window.setLocation(0, 0);
        //this.select_parameter_window.setVisible(true);
        
        this.route_parameter_window.setLocation(0, 100);
        this.autoroute_parameter_window.setLocation(0, 200);
        this.move_parameter_window.setLocation(0, 50);
        this.clearance_matrix_window.setLocation(0, 150);
        this.via_window.setLocation(50, 150);
        this.edit_vias_window.setLocation(100, 150);
        this.edit_net_rules_window.setLocation(100, 200);
        this.assign_net_classes_window.setLocation(100, 250);
        this.padstacks_window.setLocation(100, 30);
        this.packages_window.setLocation(200, 30);
        this.components_window.setLocation(300, 30);
        this.incompletes_window.setLocation(400, 30);
        this.clearance_violations_window.setLocation(500, 30);
        this.length_violations_window.setLocation(550, 30);
        this.net_info_window.setLocation(350, 30);
        this.unconnected_route_window.setLocation(650, 30);
        this.route_stubs_window.setLocation(600, 30);
        this.snapshot_window.setLocation(0, 250);
        this.layer_visibility_window.setLocation(0, 450);
        this.object_visibility_window.setLocation(0, 550);
        this.display_misc_window.setLocation(0, 350);
        this.color_manager.setLocation(0, 600);
        this.about_window.setLocation(200, 200);
    }
    
    /**
     * Returns the currently used locale for the language dependent output.
     */
    public java.util.Locale get_locale()
    {
        return this.locale;
    }
    
    /**
     * Sets the background of the board panel
     */
    public void set_board_background(java.awt.Color p_color)
    {
        this.board_panel.setBackground(p_color);
    }
    
    /**
     * Refreshs all displayed coordinates after the user unit has changed.
     */
    public void refresh_windows()
    {
        for (int i = 0; i < this.permanent_subwindows.length; ++i)
        {
            if (permanent_subwindows[i] != null)
            {
                permanent_subwindows[i].refresh();
            }
        }
    }
    
    /**
     * Sets the selected button in the menu button button group
     */
    public void highlight_selected_button()
    {
        this.toolbar_panel.highlight_selected_button();
    }
    

    public void highLightAutoroute(){
        this.toolbar_panel.highLightAutoroute();
    }


    /**
     * Restore the selected snapshot in the snapshot window.
     */
    public void goto_selected_snapshot()
    {
        if (this.snapshot_window != null)
        {
            this.snapshot_window.goto_selected();
        }
    }
    
    /**
     * Selects  the snapshot, which is previous to the current selected snapshot.
     * Thecurent selected snapshot will be no more selected.
     */
    public void select_previous_snapshot()
    {
        if (this.snapshot_window != null)
        {
            this.snapshot_window.select_previous_item();
        }
    }
    
    /**
     * Selects  the snapshot, which is next to the current selected snapshot.
     * Thecurent selected snapshot will be no more selected.
     */
    public void select_next_snapshot()
    {
        if (this.snapshot_window != null)
        {
            this.snapshot_window.select_next_item();
        }
    }
    
    /**
     * Used for storing the subwindowfilters in a snapshot.
     */
    public SubwindowSelections get_snapshot_subwindow_selections()
    {
        SubwindowSelections result = new SubwindowSelections();
        result.incompletes_selection = this.incompletes_window.get_snapshot_info();
        result.packages_selection = this.packages_window.get_snapshot_info();
        result.nets_selection = this.net_info_window.get_snapshot_info();
        result.components_selection = this.components_window.get_snapshot_info();
        result.padstacks_selection = this.padstacks_window.get_snapshot_info();
        return result;
    }
    
    /**
     * Used for restoring the subwindowfilters from a snapshot.
     */
    public void set_snapshot_subwindow_selections(SubwindowSelections p_filters)
    {
        this.incompletes_window.set_snapshot_info(p_filters.incompletes_selection);
        this.packages_window.set_snapshot_info(p_filters.packages_selection);
        this.net_info_window.set_snapshot_info(p_filters.nets_selection);
        this.components_window.set_snapshot_info(p_filters.components_selection);
        this.padstacks_window.set_snapshot_info(p_filters.padstacks_selection);
    }
    
    /**
     * Repaints this board frame and all the subwindows of the board.
     */
    public void repaint_all()
    {
        this.repaint();
        for (int i = 0; i < permanent_subwindows.length; ++i)
        {
            permanent_subwindows[i].repaint();
        }
    }
    
    /**
     *
     * @return
     */
    public boolean Getautoroutesaveexit()
    {
        return autoroutesaveexit;
    }
    
    
    /** The scroll pane for the panel of the routing board. */
    final javax.swing.JScrollPane scroll_pane;
    
    /** The menubar of this frame */
    final BoardMenuBar menubar;
    
    /** The panel with the graphical representation of the board. */
    final BoardPanel board_panel;
    
    /** The panel with the toolbars */
    private final BoardToolbar toolbar_panel;
    
    /** The toolbar used in the selected item state. */
    private final javax.swing.JPanel select_toolbar;
    
    /** The panel with the message line */
    private final BoardPanelStatus message_panel;
    
    final ScreenMessages screen_messages;
    
    private final TestLevel test_level;
    
    /** true, if the frame is created by an application running under Java Web Start */
    
    
    private final java.util.ResourceBundle resources;
    private java.util.Locale locale;
    
    private boolean autoSaveSpectraSessionFileOnExit;

    private boolean autoroutesaveexit;
    
    private final BoardObservers  board_observers;
    private final datastructures.IdNoGenerator item_id_no_generator;
    
    WindowAbout about_window = null;
    WindowRouteParameter route_parameter_window = null;
    WindowAutorouteParameter autoroute_parameter_window = null;
    WindowSelectParameter select_parameter_window = null;
    WindowMoveParameter move_parameter_window = null;
    WindowClearanceMatrix clearance_matrix_window = null;
    WindowVia via_window = null;
    WindowEditVias edit_vias_window = null;
    WindowNetClasses edit_net_rules_window = null;
    WindowAssignNetClass assign_net_classes_window = null;
    WindowPadstacks padstacks_window = null;
    WindowPackages packages_window = null;
    WindowIncompletes incompletes_window = null;
    WindowNets net_info_window = null;
    WindowClearanceViolations clearance_violations_window = null;
    WindowLengthViolations length_violations_window = null;
    WindowUnconnectedRoute unconnected_route_window = null;
    WindowRouteStubs route_stubs_window  = null;
    WindowComponents components_window = null;
    WindowLayerVisibility layer_visibility_window = null;
    WindowObjectVisibility object_visibility_window = null;
    WindowDisplayMisc display_misc_window = null;
    WindowSnapshot snapshot_window = null;
    ColorManager color_manager = null;
    
    /** The windows above stored in an array */
    static final int SUBWINDOW_COUNT = 24;
    BoardSavableSubWindow[] permanent_subwindows = new BoardSavableSubWindow[SUBWINDOW_COUNT];
    
    java.util.Collection<BoardTemporarySubWindow> temporary_subwindows = new java.util.LinkedList<>();
    
    
    DesignFile design_file = null;
        
    
    static final String [] log_file_extensions = { "log" };
    
    static final String GUI_DEFAULTS_FILE_NAME = "gui_defaults.par";
    static final String GUI_DEFAULTS_FILE_BACKUP_NAME = "gui_defaults.par.bak";
    
    static final FileFilter logfile_filter = new FileFilter(log_file_extensions);
    
    private class WindowStateListener extends java.awt.event.WindowAdapter
    {
        @Override
        public void windowClosing(java.awt.event.WindowEvent evt)
        {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE );
            if (!autoSaveSpectraSessionFileOnExit)
            {
                int option = javax.swing.JOptionPane.showConfirmDialog(null, resources.getString("confirm_cancel"),
                        null, javax.swing.JOptionPane.YES_NO_OPTION);
                if (option == javax.swing.JOptionPane.NO_OPTION)
                {
                    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                }
            }
            else {
                design_file.write_specctra_session_file(menubar.file_menu.board_frame);
            }
        }
        
        @Override
        public void windowIconified(java.awt.event.WindowEvent evt)
        {
            for (int i = 0; i < permanent_subwindows.length; ++i)
            {
                permanent_subwindows[i].parent_iconified();
            }
            for (BoardSubWindow curr_subwindow : temporary_subwindows)
            {
                if (curr_subwindow != null)
                {
                    curr_subwindow.parent_iconified();
                }
            }
        }
        
        @Override
        public void windowDeiconified(java.awt.event.WindowEvent evt)
        {
            for (int i = 0; i < permanent_subwindows.length; ++i)
            {
                if (permanent_subwindows[i] != null)
                {
                    permanent_subwindows[i].parent_deiconified();
                }
            }
            for (BoardSubWindow curr_subwindow : temporary_subwindows)
            {
                if (curr_subwindow != null)
                {
                    curr_subwindow.parent_deiconified();
                }
            }
        }
    }
    
    /**
     * Used for storing the subwindow filters in a snapshot.
     */
    public static class SubwindowSelections implements java.io.Serializable
    {
        private static final long serialVersionUID = 7524472765628777221L;
        private WindowObjectListWithFilter.SnapshotInfo incompletes_selection;
        private WindowObjectListWithFilter.SnapshotInfo packages_selection;
        private WindowObjectListWithFilter.SnapshotInfo nets_selection;
        private WindowObjectListWithFilter.SnapshotInfo components_selection;
        private WindowObjectListWithFilter.SnapshotInfo padstacks_selection;
    }
}

