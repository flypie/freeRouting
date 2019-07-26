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
 * BoardSelectedItemToolbar.java
 *
 * Created on 16. Februar 2005, 05:59
 */

package gui;

/**
 * Describes the toolbar of the board frame, when it is in the selected item state.
 *
 * @author Alfons Wirtz
 */
class BoardToolbarSelectedItem extends javax.swing.JPanel
{
    
    /**
     * Creates a new instance of BoardSelectedItemToolbar.
     */
    BoardToolbarSelectedItem(BoardFrame p_board_frame)
    {
        this.board_frame = p_board_frame;
        
        this.resources = 
                java.util.ResourceBundle.getBundle("gui.resources.BoardToolbarSelectedItem", p_board_frame.get_locale());
        

        this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.X_AXIS));

        final javax.swing.JToolBar optionToolbar = new javax.swing.JToolBar();
        optionToolbar.setLayout( new javax.swing.BoxLayout(optionToolbar, javax.swing.BoxLayout.Y_AXIS ) );

        javax.swing.JLabel optionLabel=new javax.swing.JLabel();
        optionLabel.setText("Select Options");
        optionToolbar.add(optionLabel);
        optionLabel.setAlignmentX( java.awt.Component.LEFT_ALIGNMENT );

        javax.swing.JPanel optionPanel= new javax.swing.JPanel();
        optionPanel.setLayout(new javax.swing.BoxLayout(optionPanel, javax.swing.BoxLayout.X_AXIS ));
        optionToolbar.add(optionPanel);
        optionPanel.setAlignmentX( java.awt.Component.LEFT_ALIGNMENT );

        javax.swing.JButton cancelButton = new javax.swing.JButton();
        cancelButton.setText(resources.getString("cancel"));
        cancelButton.setToolTipText(resources.getString("cancel_tooltip"));
        cancelButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                board_frame.board_panel.board_handling.cancel_state();
            }
        });
        cancelButton.setAlignmentX( java.awt.Component.LEFT_ALIGNMENT );
        optionPanel.add(cancelButton);

        javax.swing.ImageIcon infoIcon = new javax.swing.ImageIcon(getClass().getResource("/gui/resources/componentinfoicon.png"));
        javax.swing.JButton info_button = new javax.swing.JButton(infoIcon);
        //info_button.setText(resources.getString("info"));
        info_button.setToolTipText(resources.getString("info_tooltip"));
        info_button.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                board_frame.board_panel.board_handling.display_selected_item_info();
            }
        });

        optionPanel.add(info_button);

        this.add(optionToolbar);

        final javax.swing.JToolBar editToolbar = new javax.swing.JToolBar();
        editToolbar.setLayout( new javax.swing.BoxLayout(editToolbar, javax.swing.BoxLayout.Y_AXIS ) );

        javax.swing.JLabel editLabel=new javax.swing.JLabel();
        editLabel.setText("Edit Selection");
        editToolbar.add(editLabel);
        editLabel.setAlignmentX( java.awt.Component.LEFT_ALIGNMENT );

        javax.swing.JPanel editPanel= new javax.swing.JPanel();
        editPanel.setLayout(new javax.swing.BoxLayout(editPanel, javax.swing.BoxLayout.X_AXIS ));
        editToolbar.add(editPanel);
        editPanel.setAlignmentX( java.awt.Component.LEFT_ALIGNMENT );

        javax.swing.ImageIcon deleteIcon = new javax.swing.ImageIcon(getClass().getResource("/gui/resources/deleteicon.png"));
        javax.swing.JButton delete_button = new javax.swing.JButton(deleteIcon);
        //delete_button.setText(resources.getString("delete"));
        delete_button.setToolTipText(resources.getString("delete_tooltip"));
        delete_button.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                board_frame.board_panel.board_handling.delete_selected_items();
            }
        });

        editPanel.add(delete_button);

        javax.swing.ImageIcon autorouteIcon = new javax.swing.ImageIcon(getClass().getResource("/gui/resources/netautorouteicon.png"));
        javax.swing.JButton autoroute_button = new javax.swing.JButton(autorouteIcon);
        //autoroute_button.setText(resources.getString("autoroute"));
        autoroute_button.setToolTipText(resources.getString("autoroute_tooltip"));
        autoroute_button.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                board_frame.board_panel.board_handling.autoroute_selected_items();
            }
        });
        editPanel.add(autoroute_button);

        javax.swing.ImageIcon lockIcon = new javax.swing.ImageIcon(getClass().getResource("/gui/resources/lock.png"));
        javax.swing.JButton fix_button = new javax.swing.JButton(lockIcon);
        //fix_button.setText(resources.getString("fix"));
        fix_button.setToolTipText(resources.getString("fix_tooltip"));
        fix_button.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                board_frame.board_panel.board_handling.fix_selected_items();
            }
        });

        editPanel.add(fix_button);

        javax.swing.ImageIcon unlockIcon = new javax.swing.ImageIcon(getClass().getResource("/gui/resources/unlock.png"));
        javax.swing.JButton unfix_button = new javax.swing.JButton(unlockIcon);
        //unfix_button.setText(resources.getString("unfix"));
        unfix_button.setToolTipText(resources.getString("unfix_tooltip"));
        unfix_button.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                board_frame.board_panel.board_handling.unfix_selected_items();
            }
        });

        editPanel.add(unfix_button);

        this.add(editToolbar);


        final javax.swing.JToolBar extendToolbar = new javax.swing.JToolBar();
        extendToolbar.setLayout( new javax.swing.BoxLayout(extendToolbar, javax.swing.BoxLayout.Y_AXIS ) );

        javax.swing.JLabel extendLabel=new javax.swing.JLabel();
        extendLabel.setText("Extend Selection");
        extendToolbar.add(extendLabel);
        extendLabel.setAlignmentX( java.awt.Component.LEFT_ALIGNMENT );

        javax.swing.JPanel extendPanel= new javax.swing.JPanel();
        extendPanel.setLayout(new javax.swing.BoxLayout(extendPanel, javax.swing.BoxLayout.X_AXIS ));
        extendToolbar.add(extendPanel);
        extendPanel.setAlignmentX( java.awt.Component.LEFT_ALIGNMENT );

        javax.swing.ImageIcon dragIcon = new javax.swing.ImageIcon(getClass().getResource("/gui/resources/nodeicon.png"));
        javax.swing.JButton extendNetButton = new javax.swing.JButton(dragIcon);
        //extendNetButton.setText(resources.getString("nets")");
        extendNetButton.setToolTipText(resources.getString("nets_tooltip"));
        extendNetButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                board_frame.board_panel.board_handling.extend_selection_to_whole_nets();
            }
        });
        extendNetButton.setAlignmentX( java.awt.Component.LEFT_ALIGNMENT );
        extendPanel.add(extendNetButton);

        javax.swing.ImageIcon extendSetIcon = new javax.swing.ImageIcon(getClass().getResource("/gui/resources/netlisticon.png"));
        javax.swing.JButton extendSetButton = new javax.swing.JButton(extendSetIcon);
        //extendSetButton.setText(resources.getString("conn_sets"));
        extendSetButton.setToolTipText(resources.getString("conn_sets_tooltip"));
        extendSetButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                board_frame.board_panel.board_handling.extend_selection_to_whole_connected_sets();
            }
        });
        extendSetButton.setAlignmentX( java.awt.Component.LEFT_ALIGNMENT );
        extendPanel.add(extendSetButton);

        javax.swing.ImageIcon connIcon = new javax.swing.ImageIcon(getClass().getResource("/gui/resources/snodeicon.png"));
        javax.swing.JButton extendConnectionButton = new javax.swing.JButton(connIcon);
        //extendConnectionButton.setText(resources.getString("connections"));
        extendConnectionButton.setToolTipText(resources.getString("connections_tooltip"));
        extendConnectionButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                board_frame.board_panel.board_handling.extend_selection_to_whole_connections();
            }
        });
        extendConnectionButton.setAlignmentX( java.awt.Component.LEFT_ALIGNMENT );
        extendPanel.add(extendConnectionButton);

        javax.swing.ImageIcon compIcon = new javax.swing.ImageIcon(getClass().getResource("/gui/resources/componenticon.png"));
        javax.swing.JButton extendCompButton = new javax.swing.JButton(compIcon);
        //extendCompButton.setText(resources.getString("components"));
        extendCompButton.setToolTipText(resources.getString("components_tooltip"));
        extendCompButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                board_frame.board_panel.board_handling.extend_selection_to_whole_components();
            }
        });
        extendCompButton.setAlignmentX( java.awt.Component.LEFT_ALIGNMENT );
        extendPanel.add(extendCompButton);

        this.add(extendToolbar);



        

        
        
        javax.swing.JButton cutout_button = new javax.swing.JButton();
        cutout_button.setText(resources.getString("cutout"));
        cutout_button.setToolTipText(resources.getString("cutout_tooltip"));
        cutout_button.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                board_frame.board_panel.board_handling.cutout_selected_items();
            }
        });
        
        editPanel.add(cutout_button);
        

             

        
        javax.swing.JButton tidy_button = new javax.swing.JButton();
        tidy_button.setText(resources.getString("pull_tight"));
        tidy_button.setToolTipText(resources.getString("pull_tight_tooltip"));
        tidy_button.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                board_frame.board_panel.board_handling.optimize_selected_items();
            }
        });
        
        editPanel.add(tidy_button);
                       
        javax.swing.JButton clearance_class_button = new javax.swing.JButton();
        clearance_class_button.setText(resources.getString("spacing"));
        clearance_class_button.setToolTipText(resources.getString("spacing_tooltip"));
        clearance_class_button.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                assign_clearance_class();
            }
        });
        
        javax.swing.JButton fanout_button = new javax.swing.JButton();
        fanout_button.setText(resources.getString("fanout"));
        fanout_button.setToolTipText(resources.getString("fanout_tooltip"));
        fanout_button.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                board_frame.board_panel.board_handling.fanout_selected_items();
            }
        });
        editPanel.add(fanout_button);
        
        editPanel.add(clearance_class_button);
        



        



    }
    
    private void assign_clearance_class()
    {
        if (board_frame.board_panel.board_handling.is_board_read_only())
        {
            return;
        }
        rules.ClearanceMatrix clearance_matrix = board_frame.board_panel.board_handling.get_routing_board().rules.clearance_matrix;
        Object [] class_name_arr = new Object[clearance_matrix.get_class_count()];
        for (int i = 0; i <  class_name_arr.length; ++i)
        {
            class_name_arr[i] =  clearance_matrix.get_name(i);
        }
        Object selected_value = javax.swing.JOptionPane.showInputDialog(null,
                resources.getString("select_clearance_class"), resources.getString("assign_clearance_class"),
                javax.swing.JOptionPane.INFORMATION_MESSAGE, null, class_name_arr, class_name_arr[0]);
        if(selected_value == null || !(selected_value instanceof String))
        {
            return;
        }
        int class_index = clearance_matrix.get_no((String) selected_value);
        if (class_index <  0)
        {
            return;
        }
        board_frame.board_panel.board_handling.assign_clearance_classs_to_selected_items(class_index);
    }
    
    private final BoardFrame board_frame;
    private final java.util.ResourceBundle resources;
}
