/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat; 
import java.util.Date; 
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class Nurses_Registration extends javax.swing.JPanel {

    JDBC db=new JDBC();
    int sr;
    SimpleDateFormat joinFormatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    DateFormat get_date1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    /**
     * Creates new form employee_Registration
     */
    public Nurses_Registration() {
        initComponents();
        LoadTable();
    }
    private  void auto_gen_id()
    {
       try {
            ResultSet r = db.getData("SELECT MAX(E_id)from employee  where E_id like '714380%' ");
            while (r.next()) {
            String id = r.getString(1);
            int maxid=Integer.parseInt(id);
            String maxval=Integer.toString(maxid+1);
            txtID.setText(maxval);
 
        }
        } catch (Exception ex) {
            Logger.getLogger(Nurses_Registration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    private void insertVal()
    {
        String id=txtID.getText();
        String fname=txtfname.getText();
        String lname=txtlname.getText();
        String age=txtage.getText();
        String sex=txtSex1.getSelectedItem().toString();
        DateFormat get_date1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String Jdate1 = get_date1.format(dateOB.getDate());
        String nic=txtNIC.getText();
        String email=txtEmail.getText();
        
        String address=txtaddress.getText();
        DateFormat get_date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String Jdate = get_date.format(date_join.getDate());
        
        String discrip=txtDiscription.getText();
        try {
            db.putData("insert into employee(E_id,Efname,Elname,age,gender,DOB,NIC,Email,address,joined_date,discription)values('"+id+"','"+fname+"','"+lname+"','"+age+"','"+sex+"','"+Jdate1+"','"+nic+"','"+email+"','"+address+"','"+Jdate+"','"+discrip+"')");
            insertdesination();
            JOptionPane.showMessageDialog(null,"Insert Successfully","",JOptionPane.PLAIN_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Something Wrong","",JOptionPane.ERROR_MESSAGE);
        }
        LoadTable();
        
        
   }
    
   private  void updateVal()
   {
        String id=txtID.getText();
        String fname=txtfname.getText();
        String lname=txtlname.getText();
        String age=txtage.getText();
        String sex=txtSex1.getSelectedItem().toString();
        
        String Jdate1 = get_date1.format(dateOB.getDate());
        String nic=txtNIC.getText();
        String email=txtEmail.getText();
        String address=txtaddress.getText();
        //DateFormat get_date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String Jdate = get_date1.format(date_join.getDate());
        String discrip=txtDiscription.getText();
        String desig=txt_combo_designation.getSelectedItem().toString();
        try {
            db.putData("update employee  INNER JOIN designation  ON (employee.E_id=designation.E_id) set employee.Efname='"+fname+"',employee.Elname='"+lname+"',employee.age='"+age+"',employee.gender='"+sex+"',employee.DOB='"+Jdate1+"',employee.NIC='"+nic+"',employee.Email='"+email+"',employee.address='"+address+"',employee.joined_date='"+Jdate+"',employee.discription='"+discrip+"',designation.desig='"+desig+"' where employee.E_id='"+id+"' and designation.E_id='"+id+"';");
            JOptionPane.showMessageDialog(null,"Update Successfully","",JOptionPane.PLAIN_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Something Wrong","",JOptionPane.ERROR_MESSAGE);
        }
        LoadTable(); 
   }
   
   private  void deleteVal()
   {
        String id=txtID.getText();
        try {
            db.putData("delete from employee where E_id='"+id+"'");
            JOptionPane.showMessageDialog(null,"Delete Successfully","",JOptionPane.PLAIN_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Something Wrong","",JOptionPane.ERROR_MESSAGE);
        }
        LoadTable(); 
   }
       
    private  void insertdesination()
    {
        String id=txtID.getText();
        String designa= txt_combo_designation.getSelectedItem().toString();
        
        try {
            db.putData("insert into designation (E_id,desig) values ('"+ id +"','"+ designa +"')");
        } catch (Exception e) {
        }
    }
    
   
    private  void LoadTable()
    {
     
        while (EdataTable.getRowCount() > 0) {
	((DefaultTableModel)EdataTable.getModel()).removeRow(0);
        }

        
        try {
            DefaultTableModel dtm = (DefaultTableModel) EdataTable.getModel();
            ResultSet r = db.getData("select employee.E_id,employee.Efname,employee.Elname,employee.age,employee.gender,employee.DOB,employee.NIC,employee.Email,employee.address,employee.joined_date,employee.discription,designation.desig from employee,designation  where employee.E_id like '714380%' and employee.E_id=designation.E_id");
            
            while (r.next()) {
            Vector v = new Vector();
            v.add(r.getString("E_id"));
            v.add(r.getString("Efname"));
            v.add(r.getString("Elname"));
            v.add(r.getString("age"));
            v.add(r.getString("gender"));
            v.add(r.getString("DOB"));
            v.add(r.getString("NIC"));
            v.add(r.getString("Email"));
            v.add(r.getString("address"));
            v.add(r.getString("joined_date"));
            v.add(r.getString("discription"));
            v.add(r.getString("desig"));
            

            dtm.addRow(v);

        }
        } catch (Exception e) {
        }
    }
    
    
    private  void FilterTable_by_date()
    {
     
        while (EdataTable.getRowCount() > 0) {
	((DefaultTableModel)EdataTable.getModel()).removeRow(0);
        }

        DateFormat get_date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String Jdate1 = get_date.format(joinedDate_1.getDate());
        String Jdate2 = get_date.format(JoinedDate_2.getDate());
        
        
        
        try {
            DefaultTableModel dtm = (DefaultTableModel) EdataTable.getModel();
            ResultSet r = db.getData("Select * from employee where joined_date >= '" + Jdate1 + "' and joined_date <= '" + Jdate2+ "' and E_id like '714380%'");
            
            while (r.next()) {
            Vector v = new Vector();
            v.add(r.getString("E_id"));
            v.add(r.getString("Efname"));
            v.add(r.getString("Elname"));
            v.add(r.getString("age"));
            v.add(r.getString("gender"));
            v.add(r.getString("DOB"));
            v.add(r.getString("NIC"));
            v.add(r.getString("Email"));
            v.add(r.getString("address"));
            v.add(r.getString("joined_date"));
            v.add(r.getString("discription"));

            dtm.addRow(v);

        }
        } catch (Exception e) {
        }
    }
    
    private  void filterTableValue_by_name()
    {
     
        while (EdataTable.getRowCount() > 0) {
	((DefaultTableModel)EdataTable.getModel()).removeRow(0);
        }

        
        try {
            DefaultTableModel dtm = (DefaultTableModel) EdataTable.getModel();
            ResultSet r = db.getData("select employee.E_id,employee.Efname,employee.Elname,employee.age,employee.gender,employee.DOB,employee.NIC,employee.Email,employee.address,employee.joined_date,employee.discription,designation.desig from employee join designation on employee.E_id = designation.E_id  where employee.E_id like '"+txtsearchbyname.getText()+"%' and employee.E_id like '714380%' or employee.Efname like '"+txtsearchbyname.getText()+"%' and employee.E_id like '714380%' or employee.Elname like '"+txtsearchbyname.getText()+"%' and employee.E_id like '714380%' or employee.NIC like '"+txtsearchbyname.getText()+"%' and employee.E_id like '714380%' or designation.desig like '"+txtsearchbyname.getText()+"%' and employee.E_id like '714380%' ");
            
            while (r.next()) {
            Vector v = new Vector();
            v.add(r.getString("E_id"));
            v.add(r.getString("Efname"));
            v.add(r.getString("Elname"));
            v.add(r.getString("age"));
            v.add(r.getString("gender"));
            v.add(r.getString("DOB"));
            v.add(r.getString("NIC"));
            v.add(r.getString("Email"));
            v.add(r.getString("address"));
            v.add(r.getString("joined_date"));
            v.add(r.getString("discription"));
            v.add(r.getString("desig"));

            dtm.addRow(v);

        }
        } catch (Exception e) {
        }
    }
    
//db.putData("insert into employee(E_id,Efname,Elname,age,gender,DOB,NIC,Email,address,joined_date,discription)values('"+txtID.getText()+"','"+txtfname.getText()+"','"+txtlname.getText()+"','"+txtage.getText()+"','"+date_join.getText()+"','"+dateOB.getText()+"','"+txtNIC.getText()+"','"+txtEmail.getText()+"','"+txtaddress.getText()+"','"+date_join.getText()+"','"+txtDiscription.getText()+"')");
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtfname = new javax.swing.JTextField();
        txtlname = new javax.swing.JTextField();
        txtage = new javax.swing.JTextField();
        txtNIC = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtaddress = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDiscription = new javax.swing.JTextArea();
        btn_insert = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        txtSex1 = new javax.swing.JComboBox();
        dateOB = new com.toedter.calendar.JDateChooser();
        date_join = new com.toedter.calendar.JDateChooser();
        jScrollPane2 = new javax.swing.JScrollPane();
        EdataTable = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        txt_combo_designation = new javax.swing.JComboBox();
        txtsearchbyname = new javax.swing.JTextField();
        joinedDate_1 = new com.toedter.calendar.JDateChooser();
        JoinedDate_2 = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        btnFilter = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        emp_bg_img = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txtID1 = new javax.swing.JTextField();
        txtfname1 = new javax.swing.JTextField();
        txtlname1 = new javax.swing.JTextField();
        txtage1 = new javax.swing.JTextField();
        txtNIC1 = new javax.swing.JTextField();
        txtEmail1 = new javax.swing.JTextField();
        txtaddress1 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDiscription1 = new javax.swing.JTextArea();
        btn_insert1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        txtSex2 = new javax.swing.JComboBox();
        dateOB1 = new com.toedter.calendar.JDateChooser();
        date_join1 = new com.toedter.calendar.JDateChooser();
        jScrollPane4 = new javax.swing.JScrollPane();
        EdataTable1 = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        txt_combo_designation1 = new javax.swing.JComboBox();
        txtsearchbyname1 = new javax.swing.JTextField();
        joinedDate_2 = new com.toedter.calendar.JDateChooser();
        JoinedDate_3 = new com.toedter.calendar.JDateChooser();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        btnFilter1 = new javax.swing.JButton();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        emp_bg_img1 = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(1111, 618));
        setPreferredSize(new java.awt.Dimension(1111, 618));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("Email");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, -1, -1));

        jLabel3.setText("Nurse ID");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        jLabel4.setText("Employee firstname");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        jLabel1.setFont(new java.awt.Font("CordiaUPC", 0, 36)); // NOI18N
        jLabel1.setText("Nurses Regitration");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 0, -1, -1));

        jLabel5.setText("Employee lastname");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, -1, -1));

        jLabel6.setText("Age");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, -1, -1));

        jLabel7.setText("Gender");
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, -1, -1));

        jLabel8.setText("Date of birth");
        add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, -1, -1));

        jLabel9.setText("NIC");
        add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, -1, -1));
        add(txtID, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 200, 30));
        add(txtfname, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, 200, 30));
        add(txtlname, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 130, 200, 30));
        add(txtage, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 170, 200, 30));

        txtNIC.setToolTipText("You can insert only 9 numbers and one character Like \"933533930V\"");
        add(txtNIC, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 290, 200, 30));
        add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 330, 200, 30));
        add(txtaddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 370, 200, 30));

        jLabel12.setText("Discription");
        add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 490, -1, -1));

        jLabel10.setText("Address");
        add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, -1, -1));

        jLabel11.setText("Joined Date");
        add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, -1, -1));

        txtDiscription.setColumns(20);
        txtDiscription.setRows(5);
        jScrollPane1.setViewportView(txtDiscription);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 490, 480, 90));

        btn_insert.setText("Insert");
        btn_insert.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_insertMouseClicked(evt);
            }
        });
        add(btn_insert, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 590, 100, -1));

        jButton2.setText("Update");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 590, 100, -1));

        jButton3.setText("Remove ");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 590, 110, -1));

        txtSex1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Gender", "Male", "Female", " " }));
        add(txtSex1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 210, 200, 30));

        dateOB.setDateFormatString("yyyy-MM-dd HH:mm:ss");
        add(dateOB, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 250, 200, 30));

        date_join.setDateFormatString("yyyy-MM-dd HH:mm:ss");
        add(date_join, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 410, 200, 30));

        EdataTable.setBackground(new java.awt.Color(52, 50, 50));
        EdataTable.setForeground(new java.awt.Color(255, 255, 255));
        EdataTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "First Name", "Last Name", "Age", "Gender", "Date Of Birth", "NIC", "Email", "Address", "Joined Date", "Discription", "Designation"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        EdataTable.setGridColor(new java.awt.Color(153, 153, 153));
        EdataTable.setSelectionBackground(new java.awt.Color(255, 0, 51));
        EdataTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EdataTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(EdataTable);

        add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 100, 780, 380));

        jButton1.setText("Register New Employee");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 590, 200, -1));

        jLabel13.setText("Designation");
        add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, -1, -1));

        txt_combo_designation.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nurse", " " }));
        txt_combo_designation.setToolTipText("");
        add(txt_combo_designation, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 450, 200, 30));

        txtsearchbyname.setBackground(new java.awt.Color(239, 237, 237));
        txtsearchbyname.setForeground(new java.awt.Color(243, 70, 6));
        txtsearchbyname.setText("Search nurses by name.........");
        txtsearchbyname.setBorder(null);
        txtsearchbyname.setOpaque(false);
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
        add(txtsearchbyname, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 60, 390, 32));

        joinedDate_1.setDateFormatString("yyyy-MM-dd HH:mm:ss");
        add(joinedDate_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 510, 190, 30));

        JoinedDate_2.setDateFormatString("yyyy-MM-dd HH:mm:ss");
        add(JoinedDate_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 510, 190, 30));

        jLabel14.setForeground(new java.awt.Color(0, 51, 255));
        jLabel14.setText("Filter Table values among joined date_____________________________________________________  ");
        add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 490, -1, -1));

        jLabel15.setText("To");
        add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 520, -1, -1));

        btnFilter.setText("Filter");
        btnFilter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnFilterMouseClicked(evt);
            }
        });
        add(btnFilter, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 510, -1, 30));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("     Search");
        jLabel17.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel17MouseClicked(evt);
            }
        });
        add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 60, 90, 30));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/search.png"))); // NOI18N
        add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 50, 530, 50));

        emp_bg_img.setBackground(new java.awt.Color(0, 0, 0));
        add(emp_bg_img, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1111, 618));

        jPanel1.setMinimumSize(new java.awt.Dimension(1111, 618));
        jPanel1.setPreferredSize(new java.awt.Dimension(1111, 618));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel18.setText("Email");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, -1, -1));

        jLabel19.setText("Nurse ID");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        jLabel20.setText("Employee firstname");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        jLabel21.setFont(new java.awt.Font("CordiaUPC", 0, 36)); // NOI18N
        jLabel21.setText("Nurses Regitration");
        jPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 0, -1, -1));

        jLabel22.setText("Employee lastname");
        jPanel1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, -1, -1));

        jLabel23.setText("Age");
        jPanel1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, -1, -1));

        jLabel24.setText("Gender");
        jPanel1.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, -1, -1));

        jLabel25.setText("Date of birth");
        jPanel1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, -1, -1));

        jLabel26.setText("NIC");
        jPanel1.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, -1, -1));
        jPanel1.add(txtID1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 200, 30));
        jPanel1.add(txtfname1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, 200, 30));
        jPanel1.add(txtlname1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 130, 200, 30));
        jPanel1.add(txtage1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 170, 200, 30));
        jPanel1.add(txtNIC1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 290, 200, 30));
        jPanel1.add(txtEmail1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 330, 200, 30));
        jPanel1.add(txtaddress1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 370, 200, 30));

        jLabel27.setText("Discription");
        jPanel1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 490, -1, -1));

        jLabel28.setText("Address");
        jPanel1.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, -1, -1));

        jLabel29.setText("Joined Date");
        jPanel1.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, -1, -1));

        txtDiscription1.setColumns(20);
        txtDiscription1.setRows(5);
        jScrollPane3.setViewportView(txtDiscription1);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 490, 480, 90));

        btn_insert1.setText("Insert");
        btn_insert1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_insert1MouseClicked(evt);
            }
        });
        jPanel1.add(btn_insert1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 590, 100, -1));

        jButton4.setText("Update");
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 590, 100, -1));

        jButton5.setText("Remove ");
        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton5MouseClicked(evt);
            }
        });
        jPanel1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 590, 110, -1));

        txtSex2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Gender", "Male", "Female", " " }));
        jPanel1.add(txtSex2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 210, 200, 30));

        dateOB1.setDateFormatString("yyyy-MM-dd HH:mm:ss");
        jPanel1.add(dateOB1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 250, 200, 30));

        date_join1.setDateFormatString("yyyy-MM-dd HH:mm:ss");
        jPanel1.add(date_join1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 410, 200, 30));

        EdataTable1.setBackground(new java.awt.Color(52, 50, 50));
        EdataTable1.setForeground(new java.awt.Color(255, 255, 255));
        EdataTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "First Name", "Last Name", "Age", "Gender", "Date Of Birth", "NIC", "Email", "Address", "Joined Date", "Discription", "Designation"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        EdataTable1.setGridColor(new java.awt.Color(153, 153, 153));
        EdataTable1.setSelectionBackground(new java.awt.Color(255, 0, 51));
        EdataTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EdataTable1MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(EdataTable1);

        jPanel1.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 100, 780, 380));

        jButton6.setText("Register New Employee");
        jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton6MouseClicked(evt);
            }
        });
        jPanel1.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 590, 200, -1));

        jLabel30.setText("Designation");
        jPanel1.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, -1, -1));

        txt_combo_designation1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Your Position", "Doctor", "Nurse", " " }));
        jPanel1.add(txt_combo_designation1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 450, 200, 30));

        txtsearchbyname1.setBackground(new java.awt.Color(239, 237, 237));
        txtsearchbyname1.setForeground(new java.awt.Color(243, 70, 6));
        txtsearchbyname1.setText("Search nurses by name.........");
        txtsearchbyname1.setBorder(null);
        txtsearchbyname1.setOpaque(false);
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
        jPanel1.add(txtsearchbyname1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 60, 390, 32));

        joinedDate_2.setDateFormatString("yyyy-MM-dd HH:mm:ss");
        jPanel1.add(joinedDate_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 510, 190, 30));

        JoinedDate_3.setDateFormatString("yyyy-MM-dd HH:mm:ss");
        jPanel1.add(JoinedDate_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 510, 190, 30));

        jLabel31.setForeground(new java.awt.Color(0, 51, 255));
        jLabel31.setText("Filter Table values among joined date_____________________________________________________  ");
        jPanel1.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 490, -1, -1));

        jLabel32.setText("To");
        jPanel1.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 520, -1, -1));

        btnFilter1.setText("Filter");
        btnFilter1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnFilter1MouseClicked(evt);
            }
        });
        jPanel1.add(btnFilter1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 510, -1, 30));

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setText("     Search");
        jLabel33.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel33MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 60, 90, 30));

        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/search.png"))); // NOI18N
        jPanel1.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 50, 530, 50));

        emp_bg_img1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.add(emp_bg_img1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1111, 618));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void btn_insertMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_insertMouseClicked
        int response = JOptionPane.showConfirmDialog(null, "Do you want to insert this details");
        if (response==0) {
        insertVal();
        cleartext();
        }
        else{
        cleartext();
        }
    }//GEN-LAST:event_btn_insertMouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        cleartext();
        auto_gen_id();
    }//GEN-LAST:event_jButton1MouseClicked

    private void EdataTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EdataTableMouseClicked
      
        retriveFromTable();
    }//GEN-LAST:event_EdataTableMouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
         int response = JOptionPane.showConfirmDialog(null, "Do you want to insert this details");
        if (response==0) {
         updateVal();
         cleartext();
         }
        else{
        cleartext();
        }
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
       
        int response = JOptionPane.showConfirmDialog(null, "Do you want to insert this details");
        if (response==0 ) {   
       deleteVal();
       }
        else{
          
        cleartext();
        }
    }//GEN-LAST:event_jButton3MouseClicked

    private void SearchForButton()
    {
        try{
        ResultSet rs=new JDBC().getData("select employee.E_id,employee.Efname,employee.Elname,employee.age,employee.gender,employee.DOB,employee.NIC,employee.Email,employee.address,employee.joined_date,employee.discription,designation.desig from employee join designation on employee.E_id = designation.E_id where employee.E_id='"+txtsearchbyname.getText()+"'");
        
        while(rs.next()){
        txtID.setText(rs.getString("E_id"));
        txtfname.setText(rs.getString("Efname"));
        txtlname.setText(rs.getString("Elname"));
        txtage.setText(rs.getString("age"));
        txtSex1.setSelectedItem(rs.getString("gender"));
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateInstring= (String) rs.getString("DOB");
        Date dateO_B=formatter.parse(dateInstring);
        dateOB.setDate(dateO_B);
        txtNIC.setText(rs.getString("NIC"));
        txtEmail.setText(rs.getString("Email"));
        txtaddress.setText(rs.getString("address"));
        SimpleDateFormat formatter1= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateInstring1= (String) rs.getString("joined_date");
        Date dateO_J=formatter1.parse(dateInstring1);
        date_join.setDate(dateO_J);
        txtDiscription.setText(rs.getString("discription"));
        txt_combo_designation.setSelectedItem(rs.getString("desig"));
        }
        
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    private void cleartext()
    {
        txtID.setText("");
        txtfname.setText("");
        txtlname.setText("");
        txtage.setText("");
        
        
        txtNIC.setText("");
        txtEmail.setText("");
        txtaddress.setText("");
        
        txtDiscription.setText("");
        
    }
    
    private void txtsearchbynameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtsearchbynameMouseClicked
        this.txtsearchbyname.setText("");
    }//GEN-LAST:event_txtsearchbynameMouseClicked

    private void txtsearchbynamePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtsearchbynamePropertyChange
        
    }//GEN-LAST:event_txtsearchbynamePropertyChange

    private void btnFilterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFilterMouseClicked
        
        FilterTable_by_date();
    }//GEN-LAST:event_btnFilterMouseClicked

    private void txtsearchbynameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtsearchbynameKeyTyped
        try {
           filterTableValue_by_name();

           SearchForButton();
           
            
            
        } catch (Exception e) {
        }
 
        
         
    }//GEN-LAST:event_txtsearchbynameKeyTyped

    private void jLabel17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel17MouseClicked
        try {
            if (true) {
                
            }
                SearchForButton();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,"There ","",JOptionPane.ERROR_MESSAGE);
            }
    }//GEN-LAST:event_jLabel17MouseClicked

    private void btn_insert1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_insert1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_insert1MouseClicked

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4MouseClicked

    private void jButton5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5MouseClicked

    private void EdataTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EdataTable1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_EdataTable1MouseClicked

    private void jButton6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6MouseClicked

    private void txtsearchbyname1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtsearchbyname1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtsearchbyname1MouseClicked

    private void txtsearchbyname1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtsearchbyname1PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtsearchbyname1PropertyChange

    private void txtsearchbyname1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtsearchbyname1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtsearchbyname1KeyTyped

    private void btnFilter1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFilter1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnFilter1MouseClicked

    private void jLabel33MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel33MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel33MouseClicked

    private  void insertposition()
    {
    
    }
    
    private  void retriveFromTable()

    {
        try {
            sr=EdataTable.getSelectedRow();
            
            DefaultTableModel dtm=(DefaultTableModel) EdataTable.getModel();
            String eID= (String) dtm.getValueAt(sr, 0);
            String fname=(String) dtm.getValueAt(sr, 1);
            String lnamne=(String) dtm.getValueAt(sr, 2);
            String age=(String) dtm.getValueAt(sr, 3);
            String gender=(String) dtm.getValueAt(sr, 4);
            //-------------convert string to date----------------------------------------
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateInstring= (String) dtm.getValueAt(sr, 5);
            Date dateO_B=formatter.parse(dateInstring);
            //end
            String nic= (String) dtm.getValueAt(sr, 6);
            String email=(String) dtm.getValueAt(sr, 7);
            String address=(String) dtm.getValueAt(sr, 8);
            //-------------convert string to date-------------------------------------------
            SimpleDateFormat joinFormatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String JoinDateInString=(String) dtm.getValueAt(sr, 9);
            Date dateJoin=joinFormatter.parse(JoinDateInString);
            //end
            String description=(String) dtm.getValueAt(sr, 10);
            String desig=(String) dtm.getValueAt(sr, 11);
            
            
            
            
            txtID.setText(eID);
            txtfname.setText(fname);
            txtlname.setText(lnamne);
            txtage.setText(age);
            txtSex1.setSelectedItem(gender);
            dateOB.setDate(dateO_B);
            txtNIC.setText(nic);
            txtEmail.setText(email);
            txtaddress.setText(address);
            date_join.setDate(dateJoin);
            txtDiscription.setText(description);
            txt_combo_designation.setSelectedItem(desig);
            
            
        } catch (ParseException ex) {
            Logger.getLogger(Nurses_Registration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable EdataTable;
    private javax.swing.JTable EdataTable1;
    private com.toedter.calendar.JDateChooser JoinedDate_2;
    private com.toedter.calendar.JDateChooser JoinedDate_3;
    private javax.swing.JButton btnFilter;
    private javax.swing.JButton btnFilter1;
    private javax.swing.JButton btn_insert;
    private javax.swing.JButton btn_insert1;
    private com.toedter.calendar.JDateChooser dateOB;
    private com.toedter.calendar.JDateChooser dateOB1;
    private com.toedter.calendar.JDateChooser date_join;
    private com.toedter.calendar.JDateChooser date_join1;
    private javax.swing.JLabel emp_bg_img;
    private javax.swing.JLabel emp_bg_img1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private com.toedter.calendar.JDateChooser joinedDate_1;
    private com.toedter.calendar.JDateChooser joinedDate_2;
    private javax.swing.JTextArea txtDiscription;
    private javax.swing.JTextArea txtDiscription1;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEmail1;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtID1;
    private javax.swing.JTextField txtNIC;
    private javax.swing.JTextField txtNIC1;
    private javax.swing.JComboBox txtSex1;
    private javax.swing.JComboBox txtSex2;
    private javax.swing.JComboBox txt_combo_designation;
    private javax.swing.JComboBox txt_combo_designation1;
    private javax.swing.JTextField txtaddress;
    private javax.swing.JTextField txtaddress1;
    private javax.swing.JTextField txtage;
    private javax.swing.JTextField txtage1;
    private javax.swing.JTextField txtfname;
    private javax.swing.JTextField txtfname1;
    private javax.swing.JTextField txtlname;
    private javax.swing.JTextField txtlname1;
    private javax.swing.JTextField txtsearchbyname;
    private javax.swing.JTextField txtsearchbyname1;
    // End of variables declaration//GEN-END:variables
}
