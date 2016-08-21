/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class OverTime extends javax.swing.JPanel {
JDBC db= new JDBC();
    /**
     * Creates new form OverTime
     */
    public OverTime() {
        initComponents();
        
        while (JT_Emp_detail.getRowCount() > 0) {
	((DefaultTableModel)JT_Emp_detail.getModel()).removeRow(0);
        }
        
        while (JT_salary.getRowCount() > 0) {
	((DefaultTableModel)JT_salary.getModel()).removeRow(0);
        }
    }
    int sr;
    
    
    private  void filterTableValue_by_name()
    {
     
        if ("".equals(txtsearchbyname.getText())) {
            while (JT_Emp_detail.getRowCount() > 0) {
            ((DefaultTableModel)JT_Emp_detail.getModel()).removeRow(0);
            }
        }
        else{
            while (JT_Emp_detail.getRowCount() > 0) {
            ((DefaultTableModel)JT_Emp_detail.getModel()).removeRow(0);
            }


            try {
                DefaultTableModel dtm = (DefaultTableModel) JT_Emp_detail.getModel();
                ResultSet r = db.getData("select employee.E_id,employee.Efname,employee.Elname,designation.desig from employee join designation on employee.E_id = designation.E_id where employee.Efname like '"+txtsearchbyname.getText()+"%' or  employee.NIC like '"+txtsearchbyname.getText()+"%' or employee.E_id like '"+txtsearchbyname.getText()+"%' or designation.desig like '"+txtsearchbyname.getText()+"%'");

                while (r.next()) {
                Vector v = new Vector();
                v.add(r.getString("E_id"));
                v.add(r.getString("Efname"));
                v.add(r.getString("Elname"));
                v.add(r.getString("desig"));


                dtm.addRow(v);

            }
            } catch (Exception e) {
            }
        }
    }
    
    
    private  void filterTableValue()
    {
     
        if ("".equals(txtsearchbyname.getText())) {
            while (JT_salary.getRowCount() > 0) {
            ((DefaultTableModel)JT_salary.getModel()).removeRow(0);
            }
        }
        else{
            while (JT_salary.getRowCount() > 0) {
            ((DefaultTableModel)JT_salary.getModel()).removeRow(0);
            }


            try {
                DefaultTableModel dtm = (DefaultTableModel) JT_salary.getModel();
                ResultSet r = db.getData("select overtime.E_id,overtime.basic_salary,overtime.normal_salary,overtime.holyday_salary,designation.desig from overtime LEFT JOIN designation on overtime.E_id = designation.E_id LEFT JOIN employee on employee.E_id = overtime.E_id where overtime.E_id like '"+txtsearchbyname1.getText()+"%'  or designation.desig like '"+txtsearchbyname1.getText()+"%' or employee.Efname like '"+txtsearchbyname.getText()+"%' or  employee.NIC like '"+txtsearchbyname.getText()+"%'");

                while (r.next()) {
                Vector v = new Vector();
                v.add(r.getString("E_id"));
                v.add(r.getString("basic_salary"));
                v.add(r.getString("normal_salary"));
                v.add(r.getString("holyday_salary"));
                v.add(r.getString("desig"));


                dtm.addRow(v);
                
                

            }
            } catch (Exception e) {
            }
        }
    }
    
    private void insertval()
    {
        String id=txt_emp_id.getText();
        String basic_sal=txt_basic_salary.getText();
        String normal_sal=txt_normal_OT.getText();
        String holyday_sal=txtx_holyday_OT.getText();
        
        try {
            db.putData("insert into overtime(E_id,basic_salary,normal_salary,holyday_salary)values('"+id+"','"+basic_sal+"','"+normal_sal+"','"+holyday_sal+"')");
            
            JOptionPane.showMessageDialog(null,"Insert Successfully","",JOptionPane.PLAIN_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Something Wrong","",JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    private void Updateval()
    {
        String id=txt_emp_id.getText();
        String basic_sal=txt_basic_salary.getText();
        String normal_sal=txt_normal_OT.getText();
        String holyday_sal=txtx_holyday_OT.getText();
        
        try {
            db.putData("update overtime set E_id = '"+id+"',basic_salary='"+basic_sal+"',normal_salary='"+normal_sal+"',holyday_salary='"+holyday_sal+"' where E_id= '"+id+"'");
            
            JOptionPane.showMessageDialog(null,"Update Successfully","",JOptionPane.PLAIN_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Something Wrong","",JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    
    private void retriveValFromTable()
    {
        try {
            sr=JT_Emp_detail.getSelectedRow();
            DefaultTableModel dtm=(DefaultTableModel)JT_Emp_detail.getModel();
            
            String Eid=(String) dtm.getValueAt(sr, 0);
            //String fname=(String) dtm.getValueAt(sr,1);
            //String lname=(String) dtm.getValueAt(sr, 2);
            String desig=(String) dtm.getValueAt(sr, 3);
            
            this.txt_emp_id.setText(Eid);
            this.txt_desig.setText(desig);
            
        } catch (Exception e) {
        }
        
    }
    
     private void retrive_salary_FromTable()
    {
        try {
            sr=JT_salary.getSelectedRow();
            DefaultTableModel dtm=(DefaultTableModel)JT_salary.getModel();
            
            String Eid=(String) dtm.getValueAt(sr, 0);
            String Bsal=(String) dtm.getValueAt(sr,1);
            String Nsal=(String) dtm.getValueAt(sr, 2);
            String Hsal=(String) dtm.getValueAt(sr, 3);
            String desig=(String) dtm.getValueAt(sr, 4);
            
            this.lbl_id.setText(Eid);
            this.lbl_desig.setText(desig);
            this.txt_B_sal.setText(Bsal+".00");
            this.txt_ND_sal.setText(Nsal+".00");
            this.txt_Hol_sal.setText(Hsal+".00");
            
            this.txt_emp_id.setText(Eid);
            this.txt_basic_salary.setText(Bsal);
            this.txt_normal_OT.setText(Nsal);
            this.txtx_holyday_OT.setText(Hsal);
            
            
        } catch (Exception e) {
        }
        
    }
     
     private void calculateSal()
     {
         
       if ("".equals(txt_ND_sal.getText())) {
                 
                 JOptionPane.showMessageDialog(null,"You must be select employee","",JOptionPane.ERROR_MESSAGE);
                 
             } else {
                 
                 if ("".equals(ND_OT_hour.getText())){
                     
                    JOptionPane.showMessageDialog(null,"Check your normal over time hour ","",JOptionPane.ERROR_MESSAGE); 
                 }
                 
                 else if("".equals(Holiday_OT_hour.getText()))
                 {
                     JOptionPane.showMessageDialog(null,"Check your holiday over time hour ","",JOptionPane.ERROR_MESSAGE); 
                 }
                 
                 else
                 { 
         
                    double basicSal=Double.parseDouble(txt_B_sal.getText());
                    double normalOT=Double.parseDouble(txt_ND_sal.getText());
                    double HolidayOT=Double.parseDouble(txt_Hol_sal.getText());
                    double NDhour=Double.parseDouble(ND_OT_hour.getText());
                    double HDhour=Double.parseDouble(Holiday_OT_hour.getText());
                    double ND_Sal;
                    double HD_Sal;
                    double Full_Sal;
//                    double Normal_day_OT=Double.parseDouble(txt_OT_sal_Normal_result.getText());
//                    double Holiday_day_OT=Double.parseDouble(txt_OT_sal_holiday_result.getText());
                    
       
        
                    ND_Sal=normalOT*NDhour;
                    HD_Sal=HolidayOT*HDhour;
                    Full_Sal=ND_Sal+ HD_Sal+basicSal;
             
             
                     txt_OT_sal_Normal_result.setText(String.valueOf(ND_Sal));
                     txt_OT_sal_holiday_result.setText(String.valueOf(HD_Sal));
                     txt_full_sal_result.setText(String.valueOf(Full_Sal));
                     
                     
                     
                     
                     
  
         }
       
       }
    }
                 
                 
     
    
    private void SearchForButton()
    {
        try{
        ResultSet rs=new JDBC().getData("select employee.E_id,employee.Efname,designation.desig from employee join designation on employee.E_id = designation.E_id where employee.E_id='"+ this.txtsearchbyname.getText()+"'");
        while(rs.next()){
        txt_emp_id.setText(rs.getString("E_id"));
        txt_desig.setText(rs.getString("Efname"));
        //txtlname.setText(rs.getString("Elname"));
        
        //txtdesig.setText(rs.getString("desig"));
        }
        }catch(Exception e){
            System.out.println(e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        JT_Emp_detail = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txt_desig = new javax.swing.JTextField();
        txt_basic_salary = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtx_holyday_OT = new javax.swing.JTextField();
        txt_normal_OT = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_emp_id = new javax.swing.JTextField();
        txtsearchbyname = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txt_B_sal = new javax.swing.JTextField();
        btn_searchOT = new javax.swing.JLabel();
        txtsearchbyname1 = new javax.swing.JTextField();
        txt_ND_sal = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        txt_Hol_sal = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        ND_OT_hour = new javax.swing.JTextField();
        lbl_id = new javax.swing.JLabel();
        lbl_desig = new javax.swing.JLabel();
        txt_OT_sal_Normal_result = new javax.swing.JTextField();
        txt_full_sal_result = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        Holiday_OT_hour = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_OT_sal_holiday_result = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        JT_salary = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Over Time Salary Calculating");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Set Salery", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Adobe Arabic", 0, 18), new java.awt.Color(0, 153, 255))); // NOI18N

        jButton1.setText("Set salery");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jLabel17.setBackground(new java.awt.Color(0, 204, 255));
        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setText("     Search");
        jLabel17.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel17.setOpaque(true);
        jLabel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel17MouseClicked(evt);
            }
        });

        jLabel2.setText("Employee ID");

        JT_Emp_detail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Employee ID", "First Name", "Last Name", "Designation"
            }
        ));
        JT_Emp_detail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JT_Emp_detailMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(JT_Emp_detail);

        jLabel6.setText("Holyday Overtime");

        txt_desig.setEditable(false);
        txt_desig.setBackground(new java.awt.Color(102, 102, 102));
        txt_desig.setForeground(new java.awt.Color(255, 255, 255));

        jLabel3.setText("Basic Salery");

        jLabel4.setText("Designation");

        jLabel5.setText("Normal Day OT salery");

        txt_emp_id.setEditable(false);
        txt_emp_id.setBackground(new java.awt.Color(102, 102, 102));
        txt_emp_id.setForeground(new java.awt.Color(255, 255, 255));

        txtsearchbyname.setForeground(new java.awt.Color(243, 70, 6));
        txtsearchbyname.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtsearchbyname.setText("Search nurses by name.........");
        txtsearchbyname.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        txtsearchbyname.setPreferredSize(new java.awt.Dimension(148, 15));
        txtsearchbyname.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtsearchbynameMouseClicked(evt);
            }
        });
        txtsearchbyname.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtsearchbynamePropertyChange(evt);
            }
        });
        txtsearchbyname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtsearchbynameKeyTyped(evt);
            }
        });

        jButton2.setText("Update set salery");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtsearchbyname, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txt_emp_id, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txt_desig, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGap(420, 420, 420)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addGap(18, 18, 18))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addGap(36, 36, 36)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(65, 65, 65)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_basic_salary, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtx_holyday_OT)
                                .addComponent(txt_normal_OT, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtsearchbyname, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_emp_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txt_desig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_basic_salary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_normal_OT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtx_holyday_OT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Calculate Salary", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Adobe Arabic", 0, 14), new java.awt.Color(153, 0, 255))); // NOI18N

        txt_B_sal.setEditable(false);
        txt_B_sal.setBackground(new java.awt.Color(12, 177, 188));

        btn_searchOT.setBackground(new java.awt.Color(0, 255, 102));
        btn_searchOT.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_searchOT.setText("     Search");
        btn_searchOT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_searchOT.setOpaque(true);
        btn_searchOT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_searchOTMouseClicked(evt);
            }
        });

        txtsearchbyname1.setBackground(new java.awt.Color(102, 102, 102));
        txtsearchbyname1.setForeground(new java.awt.Color(255, 255, 255));
        txtsearchbyname1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtsearchbyname1.setText("Search nurses by name.........");
        txtsearchbyname1.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        txtsearchbyname1.setPreferredSize(new java.awt.Dimension(148, 15));
        txtsearchbyname1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtsearchbyname1MouseClicked(evt);
            }
        });
        txtsearchbyname1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtsearchbyname1PropertyChange(evt);
            }
        });
        txtsearchbyname1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtsearchbyname1KeyTyped(evt);
            }
        });

        txt_ND_sal.setEditable(false);
        txt_ND_sal.setBackground(new java.awt.Color(12, 177, 188));

        jLabel10.setText("Number of OT Hours");

        jLabel7.setText("Basic Salary");

        jButton3.setText("Calculate salary");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Normal day OverTime Salary");

        txt_Hol_sal.setEditable(false);
        txt_Hol_sal.setBackground(new java.awt.Color(12, 177, 188));

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("Holiday OverTime  Salary");

        ND_OT_hour.setText("0.00");

        lbl_id.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_id.setForeground(new java.awt.Color(255, 0, 51));
        lbl_id.setText("ID");

        lbl_desig.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_desig.setForeground(new java.awt.Color(0, 102, 255));
        lbl_desig.setText("Designation");

        txt_OT_sal_Normal_result.setBackground(new java.awt.Color(225, 225, 225));
        txt_OT_sal_Normal_result.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_OT_sal_Normal_result.setForeground(new java.awt.Color(0, 102, 255));

        txt_full_sal_result.setBackground(new java.awt.Color(225, 225, 225));
        txt_full_sal_result.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_full_sal_result.setForeground(new java.awt.Color(255, 0, 0));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("Monthly Full Salary");

        Holiday_OT_hour.setText("0.00");

        jLabel9.setText("Holyday OT salary");

        jLabel8.setText("Normal Day OT salary");

        txt_OT_sal_holiday_result.setBackground(new java.awt.Color(225, 225, 225));
        txt_OT_sal_holiday_result.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_OT_sal_holiday_result.setForeground(new java.awt.Color(51, 153, 0));
        txt_OT_sal_holiday_result.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_OT_sal_holiday_resultActionPerformed(evt);
            }
        });

        JT_salary.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Employee ID", "Basic salary", "Normal day OT salary", "Holyday OT salary", "Designation"
            }
        ));
        JT_salary.setSelectionBackground(new java.awt.Color(255, 51, 0));
        JT_salary.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JT_salaryMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(JT_salary);

        jLabel11.setText("Number of OT Hours");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtsearchbyname1, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(420, 420, 420)
                        .addComponent(btn_searchOT, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(lbl_id))
                        .addGap(54, 54, 54)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_desig)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jButton3)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txt_B_sal)
                                        .addComponent(txt_ND_sal)
                                        .addComponent(txt_Hol_sal, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(jLabel11)
                                            .addGap(18, 18, 18)
                                            .addComponent(Holiday_OT_hour))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(jLabel10)
                                            .addGap(18, 18, 18)
                                            .addComponent(ND_OT_hour, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_OT_sal_Normal_result, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_full_sal_result, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(57, 57, 57)
                                .addComponent(txt_OT_sal_holiday_result, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(111, 111, 111)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtsearchbyname1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_searchOT, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_id)
                    .addComponent(lbl_desig))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txt_B_sal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(txt_ND_sal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ND_OT_hour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txt_Hol_sal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Holiday_OT_hour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txt_OT_sal_Normal_result, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txt_OT_sal_holiday_result, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txt_full_sal_result, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(414, 414, 414)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 553, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 8, Short.MAX_VALUE)))
                .addContainerGap(33, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel17MouseClicked
        SearchForButton();
    }//GEN-LAST:event_jLabel17MouseClicked

    private void txtsearchbynameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtsearchbynameMouseClicked
        this.txtsearchbyname.setText("");
    }//GEN-LAST:event_txtsearchbynameMouseClicked

    private void txtsearchbynamePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtsearchbynamePropertyChange

    }//GEN-LAST:event_txtsearchbynamePropertyChange

    private void txtsearchbynameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtsearchbynameKeyTyped
        try {
            filterTableValue_by_name();

        } catch (Exception e) {
        }

    }//GEN-LAST:event_txtsearchbynameKeyTyped

    private void btn_searchOTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_searchOTMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_searchOTMouseClicked

    private void txtsearchbyname1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtsearchbyname1MouseClicked
        this.txtsearchbyname1.setText("");
    }//GEN-LAST:event_txtsearchbyname1MouseClicked

    private void txtsearchbyname1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtsearchbyname1PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtsearchbyname1PropertyChange

    private void txtsearchbyname1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtsearchbyname1KeyTyped
       filterTableValue();
    }//GEN-LAST:event_txtsearchbyname1KeyTyped

    private void JT_Emp_detailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JT_Emp_detailMouseClicked
        retriveValFromTable();
    }//GEN-LAST:event_JT_Emp_detailMouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        int response = JOptionPane.showConfirmDialog(null, "Do you want to set salery");
        if (response==0) {
        insertval();
        
        }
        else{
        
        }
    }//GEN-LAST:event_jButton1MouseClicked

    private void JT_salaryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JT_salaryMouseClicked
        try {
            retrive_salary_FromTable();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_JT_salaryMouseClicked

    private void txt_OT_sal_holiday_resultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_OT_sal_holiday_resultActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_OT_sal_holiday_resultActionPerformed

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        calculateSal();
    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        int response = JOptionPane.showConfirmDialog(null, "Do you want to set salery");
        if (response==0) {
        Updateval();
        }
        else{
        
        }
    }//GEN-LAST:event_jButton2MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Holiday_OT_hour;
    private javax.swing.JTable JT_Emp_detail;
    private javax.swing.JTable JT_salary;
    private javax.swing.JTextField ND_OT_hour;
    private javax.swing.JLabel btn_searchOT;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbl_desig;
    private javax.swing.JLabel lbl_id;
    private javax.swing.JTextField txt_B_sal;
    private javax.swing.JTextField txt_Hol_sal;
    private javax.swing.JTextField txt_ND_sal;
    private javax.swing.JTextField txt_OT_sal_Normal_result;
    private javax.swing.JTextField txt_OT_sal_holiday_result;
    private javax.swing.JTextField txt_basic_salary;
    private javax.swing.JTextField txt_desig;
    private javax.swing.JTextField txt_emp_id;
    private javax.swing.JTextField txt_full_sal_result;
    private javax.swing.JTextField txt_normal_OT;
    private javax.swing.JTextField txtsearchbyname;
    private javax.swing.JTextField txtsearchbyname1;
    private javax.swing.JTextField txtx_holyday_OT;
    // End of variables declaration//GEN-END:variables
}
