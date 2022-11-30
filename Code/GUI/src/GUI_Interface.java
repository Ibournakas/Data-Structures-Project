import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GUI_Interface extends Frame {
  static void customer_menu(ResultSet result, String email) throws SQLException {
    Frame f = new Frame("Customer Menu");
    f.setSize(300, 200);
    f.setLayout(new FlowLayout());
    f.setVisible(true);
    while (result.next()) {
      if (result.getString("email").equals(email)) {
        break;
      }
    }

    String dbURL = "jdbc:mysql://localhost/tvondemand";
    Connection conn = DriverManager.getConnection(dbURL, "root", "");

    Label label = new Label("Welcome: " + result.getString("first_name") + " " + result.getString("last_name"));
    f.add(label);
    Button b1 = new Button("View customer info");
    Button b2 = new Button("Change subscription");
    Button b3 = new Button("View rental info");
    Button b4 = new Button("View Inventory and Rent");
    Button b5 = new Button("Logout");
    f.add(b1);
    f.add(b2);
    f.add(b3);
    f.add(b4);
    f.add(b5);
    b1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // to fix the position that we read in the table
        int[] addresses = new int[100];
        int cnt = 0;
        try {
          result.beforeFirst();
          while (result.next()) {

            addresses[cnt] = result.getInt("address_id");
            cnt++;
          }
        } catch (SQLException ex) {
          Logger.getLogger(GUI_Interface.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
          result.beforeFirst();
        } catch (SQLException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
        try {

          while (result.next()) {
            if (result.getString("email").equals(email)) {
              break;
            }
          }

          Frame info = new Frame("Customer Info");
          info.setSize(500, 150);
          info.setLayout(new FlowLayout());
          info.setVisible(true);
          Label label = new Label("Customer Info: ");
          info.add(label);
          info.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
              info.dispose();
            }
          });
          Label label1 = new Label("First Name: ");
          Label label2 = new Label(", Last Name: ");
          Label label3 = new Label(", Email: ");
          Label label4 = new Label(", Address_id: ");
          // final Label updateLabel = new Label("");

          Button b1 = new Button("Update");
          Button b2 = new Button("Update");
          Button b4 = new Button("Update");

          TextField first_name = new TextField();
          TextField last_name = new TextField();
          Label email = new Label();

          first_name.setText(result.getString("first_name"));
          last_name.setText(result.getString("last_name"));
          email.setText(result.getString("email"));

          info.add(label1);
          info.add(first_name);
          info.add(b1);
          info.add(label2);
          info.add(last_name);
          info.add(b2);
          info.add(label3);
          info.add(email);
          info.add(label4);

          b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              try {

                String SQL = "UPDATE customer SET first_name = ? WHERE customer_id = ?";
                PreparedStatement pstmt = conn.prepareStatement(SQL);
                pstmt.setString(1, first_name.getText());
                pstmt.setInt(2, result.getInt("customer_id"));
                pstmt.executeUpdate();

                // pstmt.close();

              } catch (SQLException ex) {
                Logger.getLogger(GUI_Interface.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("SQL is bad " + ex.getMessage());
              } catch (Exception ex) {
                Logger.getLogger(GUI_Interface.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Exception");
              }
            }
          });
          b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              try {

                String SQL = "UPDATE customer SET last_name = ? WHERE customer_id = ?";
                PreparedStatement pstmt = conn.prepareStatement(SQL);
                pstmt.setString(1, last_name.getText());
                pstmt.setInt(2, result.getInt("customer_id"));
                pstmt.executeUpdate();
                // pstmt.close();
              } catch (SQLException ex) {
                Logger.getLogger(GUI_Interface.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("SQL is bad " + ex.getMessage());
              } catch (Exception ex) {
                Logger.getLogger(GUI_Interface.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Exception");
              }
            }
          });
          
        final Choice address_id_dropdown = new Choice();
          String[] tempp = new String[100];

          for (int i = 0; i < cnt; i++) {
            String SQL1 = "SELECT * FROM address WHERE address_id = ?";
            PreparedStatement pstmt1 = conn.prepareStatement(SQL1);
            pstmt1.setInt(1, addresses[i]);
            final ResultSet result1 = pstmt1.executeQuery();
            while (result1.next()) {
              address_id_dropdown.add(Integer.toString(addresses[i]) + "-" + result1.getString("address"));
              tempp[i] = result1.getString("address");

            }
          }
          int cntt = 0;

          String SQL1 = "SELECT * FROM address WHERE address_id = ?";
          PreparedStatement pstmt1 = conn.prepareStatement(SQL1);
          pstmt1.setInt(1, result.getInt("address_id"));
          final ResultSet result2 = pstmt1.executeQuery();
          while (result2.next()) {
            for (int i = 0; i < cnt; i++) {
              if (tempp[i].equals(result2.getString("address"))) {
                cntt = i;
                break;
              }
            }
          }

          // address_dropdown.select(Integer.toString(temp1.getInt("address_id")) + " -" +
          // tempp[cntt]);
          address_id_dropdown.select(Integer.toString(result.getInt("address_id")) + "-" + tempp[cntt]);
          info.add(address_id_dropdown);
          info.add(b4);

          // get address from address table and show it in a TextField

          b4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              try {
                String SQL = "UPDATE customer SET address_id = ? WHERE customer_id = ?";
                PreparedStatement pstmt = conn.prepareStatement(SQL);
                pstmt.setInt(1, Integer.parseInt(address_id_dropdown.getSelectedItem().split("-")[0]));
                pstmt.setInt(2, result.getInt("customer_id"));
                pstmt.executeUpdate();
                // pstmt.close();
              } catch (SQLException ex) {
                Logger.getLogger(GUI_Interface.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("SQL is bad " + ex.getMessage());
              } catch (Exception ex) {
                Logger.getLogger(GUI_Interface.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Exception");
              }
            }
          });

        } catch (SQLException exe) {
          Logger.getLogger(GUI_Interface.class.getName()).log(Level.SEVERE, null, exe);
        }
      }
    });
    b2.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          result.beforeFirst();
        } catch (SQLException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
        try {

          while (result.next()) {
            if (result.getString("email").equals(email)) {
              break;
            }
          }
          Frame sub = new Frame("Subscription Info");
          sub.setSize(500, 100);
          sub.setLayout(new FlowLayout());
          sub.setVisible(true);
          Label label1 = new Label("Subscription Type: ");
          sub.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
              sub.dispose();
            }
          });
          // TextField subscription_type = new TextField();
          // get the subscription_type from subscription table and show it in a TextField
          Button subscription_type_button = new Button("Update");
          sub.add(label1);
          // sub.add(subscription_type);

          final Choice subscription_type_dropdown = new Choice();

          String SQL1 = "SELECT * FROM subscription WHERE customer_id = ?";
          PreparedStatement pstmt1 = conn.prepareStatement(SQL1);
          pstmt1.setInt(1, result.getInt("customer_id"));
          ResultSet result1 = pstmt1.executeQuery();
          while (result1.next()) {
            if (result1.getInt("subscription_type") == 0) {
              subscription_type_dropdown.add(result1.getString("subscription_type") + " - Movies Only");
              subscription_type_dropdown.add("1" + " - TV Series Only");
              subscription_type_dropdown.add("2" + " - Movies and TV Series");

            } else if (result1.getInt("subscription_type") == 1) {
              subscription_type_dropdown.add(result1.getString("subscription_type") + " - TV Series Only");
              subscription_type_dropdown.add("0" + " - Movies Only");
              subscription_type_dropdown.add("2" + " - Movies and TV Series");

            }

            else if (result1.getInt("subscription_type") == 2) {
              subscription_type_dropdown.add(result1.getString("subscription_type") + " - Movies and TV Series");
              subscription_type_dropdown.add("0" + " - Movies Only");
              subscription_type_dropdown.add("1" + " - TV Series Only");

            }

          }
          while (result.next()) {
            if (result.getString("email").equals(email)) {
              int tmp = result.getInt("customer_id");
              while (result1.next()) {
                if (result1.getInt("customer_id") == tmp) {
                  subscription_type_dropdown.select(result1.getString("subscription_type"));
                  break;
                }
              }
              break;
            }
          }

          sub.add(subscription_type_dropdown);
          sub.add(subscription_type_button);
          subscription_type_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              try {
                result.beforeFirst();
              } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
              }
              try {
                while (result.next()) {
                  if (result.getString("email").equals(email)) {
                    break;
                  }
                }

                String SQL = "UPDATE subscription SET subscription_type = ? WHERE customer_id = ?";
                PreparedStatement pstmt = conn.prepareStatement(SQL);
                // convert the string to int
                pstmt.setInt(1, Integer.parseInt(subscription_type_dropdown.getSelectedItem().split(" ")[0]));

                pstmt.setInt(2, result.getInt("customer_id"));
                pstmt.executeUpdate();
                // pstmt.close();
              } catch (SQLException ex) {
                Logger.getLogger(GUI_Interface.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("SQL is bad " + ex.getMessage());
              } catch (Exception ex) {
                Logger.getLogger(GUI_Interface.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Exception");
              }
            }
          });

        } catch (SQLException ex) {
          Logger.getLogger(GUI_Interface.class.getName()).log(Level.SEVERE, null, ex);
          System.out.println("SQL is bad " + ex.getMessage());
        } catch (Exception ex) {
          Logger.getLogger(GUI_Interface.class.getName()).log(Level.SEVERE, null, ex);
          System.out.println("Exception");
        }
      }
    });
    b3.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          result.beforeFirst();
        } catch (SQLException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
        Frame ren = new Frame("Rental Info");
          ren.setSize(350, 200);
          ren.setLayout(new FlowLayout());
          ren.setVisible(true);
          ren.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent we) {
              ren.dispose();
            }

          });
          
          Label spaceLabel = new Label("                           ");
          Label l1 = new Label("Customer: ");
          ren.add(l1);
          Choice customer_dropdown = new Choice();
          ren.add(customer_dropdown);
          ren.add(spaceLabel);
          Label l2 = new Label("Rental id: ");
          ren.add(l2);
          Choice rental_dropdown = new Choice();
          ren.add(rental_dropdown);
          
          Label l3 = new Label("Rental Date: ");
          ren.add(l3);
          TextField rental_date_text = new TextField(20);
          ren.add(rental_date_text);
          Label l4 = new Label("film_inventory_id: ");
          ren.add(l4);
          TextField film_inventory_id_text = new TextField(20);
          ren.add(film_inventory_id_text);
          Label l5 = new Label("series_inventory_id: ");
          ren.add(l5);
          TextField series_inventory_id_text = new TextField(20);
          ren.add(series_inventory_id_text);
          try{
            while (result.next()) {
              if (result.getString("email").equals(email)) {
                customer_dropdown.add(result.getString("customer_id")+" - "+
                result.getString("first_name")+" "+result.getString("last_name"));
              }
            }
          
          String SQL1 = "SELECT * FROM rental WHERE customer_id = ?";
          PreparedStatement pstmt1 = conn.prepareStatement(SQL1, ResultSet.TYPE_SCROLL_INSENSITIVE,
              ResultSet.CONCUR_READ_ONLY);
          pstmt1.setInt(1, Integer.parseInt(customer_dropdown.getSelectedItem().split(" -")[0]));
          ResultSet rs1 = pstmt1.executeQuery();
          while (rs1.next()) {
            rental_dropdown.add(rs1.getString("rental_id"));
          }
          String SQL2 = "SELECT * FROM rental WHERE rental_id = ?";
          PreparedStatement pstmt2;
          pstmt2 = conn.prepareStatement(SQL2, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
          pstmt2.setInt(1, Integer.parseInt(rental_dropdown.getSelectedItem()));
          ResultSet rs2 = pstmt2.executeQuery();
          while (rs2.next()) {
            rental_date_text.setText(rs2.getString("rental_date"));
            film_inventory_id_text.setText(rs2.getString("film_inventory_id"));
            series_inventory_id_text.setText(rs2.getString("series_inventory_id"));
          }
          rental_dropdown.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
              try {
                String SQL = "SELECT * FROM rental WHERE rental_id = ?";
                PreparedStatement pstmt;
                pstmt = conn.prepareStatement(SQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                pstmt.setInt(1, Integer.parseInt(rental_dropdown.getSelectedItem()));
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                  rental_date_text.setText(rs.getString("rental_date"));
                  film_inventory_id_text.setText(rs.getString("film_inventory_id"));
                  series_inventory_id_text.setText(rs.getString("series_inventory_id"));
                }
              } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
              }
            }
          });



        } catch (SQLException ex) {
          Logger.getLogger(GUI_Interface.class.getName()).log(Level.SEVERE, null, ex);
          System.out.println("SQL is bad " + ex.getMessage());
        } catch (Exception ex) {
          Logger.getLogger(GUI_Interface.class.getName()).log(Level.SEVERE, null, ex);
          System.out.println("Exception");
        }
      
      
      
      }
    });
    b4.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          result.beforeFirst();
        } catch (SQLException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
        try {

          while (result.next()) {
            if (result.getString("email").equals(email)) {
              break;
            }
          }
          Frame rent = new Frame("View Inventory and Rent");
          rent.setSize(500, 150);
          rent.setLayout(new FlowLayout());
          rent.setVisible(true);
          Label label1 = new Label("Available Movies: ");
          Label label2 = new Label("Available TV Series: ");
          Button movie_button = new Button("Rent Movie");
          Button tv_button = new Button("Rent TV Episode");
          Choice movie_dropdown = new Choice();
          Choice tv_dropdown = new Choice();
          rent.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
              rent.dispose();
            }
          });
          rent.add(label1);
          rent.add(movie_dropdown);
          rent.add(movie_button);
          rent.add(label2);
          rent.add(tv_dropdown);
          rent.add(tv_button);

          
          // check the subscription_type
          String SQL2 = "SELECT * FROM subscription WHERE customer_id = ?";
          PreparedStatement pstmt2 = conn.prepareStatement(SQL2);
          pstmt2.setInt(1, result.getInt("customer_id"));
          ResultSet result2 = pstmt2.executeQuery();

          while (result2.next()) {
            if (result2.getInt("customer_id") == result.getInt("customer_id"))
              break;

          }
          if (result2.getInt("subscription_type") == 0 ||
              result2.getInt("subscription_type") == 2) {
          String SQL1 = "SELECT * FROM film";
          PreparedStatement pstmt1 = conn.prepareStatement(SQL1);
          ResultSet result1 = pstmt1.executeQuery();
          while (result1.next()) {
            movie_dropdown.add(result1.getString("film_id") + " - " + result1.getString("title"));
          }
        } else {
          movie_dropdown.add("Unavailable because of subscription type");
          movie_button.setEnabled(false);
        }
        

          if (result2.getInt("subscription_type") == 1 ||
              result2.getInt("subscription_type") == 2) {
            String SQL3 = "SELECT * FROM series";
            PreparedStatement pstmt3 = conn.prepareStatement(SQL3);
            ResultSet result3 = pstmt3.executeQuery();
            while (result3.next()) {
              tv_dropdown.add(result3.getString("series_id") + " - " +
                  result3.getString("title"));
            }
          } else {
            tv_dropdown.add("Unavailable because of subscription type");
            tv_button.setEnabled(false);
          }

          movie_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              try {
                // sttore into a variable the film_inventory_id from film_inventory
                String SQL1 = "SELECT * FROM film_inventory WHERE film_id = ?";
                PreparedStatement pstmt1 = conn.prepareStatement(SQL1);
                pstmt1.setInt(1, Integer.parseInt(movie_dropdown.getSelectedItem().split(" -")[0]));
                ResultSet result1 = pstmt1.executeQuery();
                while (result1.next()) {
                  if (result1.getInt("film_id") == Integer.parseInt(movie_dropdown.getSelectedItem().split(" -")[0])) {
                    break;
                  }
                }

                // INSERT INTO `rental` ( `rental_date`, `film_inventory_id`, `customer_id`)
                // VALUES ('2005-12-30 19:20:38', '6', '3');
                String SQL = "INSERT INTO rental (rental_date, film_inventory_id, customer_id) VALUES ( ?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(SQL);
                pstmt.setString(1, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                pstmt.setInt(2, result1.getInt("inventory_id"));
                pstmt.setInt(3, result.getInt("customer_id"));
                pstmt.executeUpdate();
                // pstmt.close();
              } catch (SQLException ex) {
                Logger.getLogger(GUI_Interface.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("SQL is bad " + ex.getMessage());
              } catch (Exception ex) {
                Logger.getLogger(GUI_Interface.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Exception");
              }
            }
          });

          tv_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              try {

               
                  // sttore into a variable the film_inventory_id from film_inventory
                  String SQL1 = "SELECT * FROM series_inventory WHERE series_id = ?";
                  PreparedStatement pstmt1 = conn.prepareStatement(SQL1);
                  pstmt1.setInt(1, Integer.parseInt(tv_dropdown.getSelectedItem().split(" -")[0]));
                  ResultSet result1 = pstmt1.executeQuery();
                  while (result1.next()) {
                    if (result1.getInt("series_id") == Integer.parseInt(tv_dropdown.getSelectedItem().split(" -")[0])) {
                      break;
                    }
                  }
                  // INSERT INTO `rental` ( `rental_date`, `film_inventory_id`, `customer_id`)
                  // VALUES ('2005-12-30 19:20:38', '6', '3');
                  String SQL = "INSERT INTO rental (rental_date, series_inventory_id, customer_id) VALUES ( ?, ?, ?)";
                  PreparedStatement pstmt = conn.prepareStatement(SQL);
                  pstmt.setString(1, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                  pstmt.setInt(2, result1.getInt("inventory_id"));
                  pstmt.setInt(3, result.getInt("customer_id"));
                  pstmt.executeUpdate();

                

                // pstmt.close();
              } catch (SQLException exe) {
                Logger.getLogger(GUI_Interface.class.getName()).log(Level.SEVERE, null, exe);
                System.out.println("SQL is bad " + exe.getMessage());
              } catch (Exception exee) {
                Logger.getLogger(GUI_Interface.class.getName()).log(Level.SEVERE, null, exee);
                System.out.println("Exception");
              }

            }
          });

          f.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent we) {
              f.dispose();
            }

          });
        } catch (SQLException ex) {
          Logger.getLogger(GUI_Interface.class.getName()).log(Level.SEVERE, null, ex);
          System.out.println("SQL is bad " + ex.getMessage());
        } catch (Exception ex) {
          Logger.getLogger(GUI_Interface.class.getName()).log(Level.SEVERE, null, ex);
          System.out.println("Exception");
        }

      }
    });
    b5.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        f.dispose();
      }
    });
    f.addWindowListener(new WindowAdapter() {

      public void windowClosing(WindowEvent we) {
        f.dispose();
      }

    });

  }

  static void employee_menu(ResultSet result1, String email) throws SQLException {
    Frame f = new Frame("Employee Menu");
    f.setSize(300, 200);
    f.setLayout(new FlowLayout());
    f.setVisible(true);

    try {
      result1.beforeFirst();
    } catch (SQLException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    while (result1.next()) {
      if (result1.getString("email").equals(email)) {
        break;
      }
    }
    f.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent we) {
        f.dispose();
      }

    });

    String dbURL = "jdbc:mysql://localhost/tvondemand";
    Connection conn = DriverManager.getConnection(dbURL, "root", "");
    Statement sqlSt3 = conn.createStatement(); // allows SQL

    sqlSt3.execute("DROP TRIGGER IF EXISTS customer_update");

    Label label = new Label("Welcome: " + result1.getString("first_name") + " " + result1.getString("last_name"));
    f.add(label);
    Button b1 = new Button("View Customer Details");
    f.add(b1);
    Button b2 = new Button("View Customer Rentals");
    f.add(b2);
    Button b3 = new Button("View Most Rented Last Month");
    f.add(b3);
    Button b4 = new Button("Logout");
    f.add(b4);
    b1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          // f.dispose();
          int[] addresses = new int[100];
          int cnt = 0;
          try {
            result1.beforeFirst();
          } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
          }
          while (result1.next()) {
            if (result1.getString("email").equals(email)) {
              break;
            }
          }

          Frame cus = new Frame("Customer Details");
          cus.setSize(400, 350);
          cus.setLayout(new FlowLayout());
          cus.setVisible(true);
          cus.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent we) {
              cus.dispose();
            }

          });
          String SQL = "SELECT * FROM customer";
          PreparedStatement pstmt = conn.prepareStatement(SQL, ResultSet.TYPE_SCROLL_INSENSITIVE,
              ResultSet.CONCUR_READ_ONLY);
          ResultSet temp = pstmt.executeQuery();

          try {
            temp.beforeFirst();
            while (temp.next()) {

              addresses[cnt] = temp.getInt("address_id");
              cnt++;
            }
          } catch (SQLException ex) {
            Logger.getLogger(GUI_Interface.class.getName()).log(Level.SEVERE, null, ex);
          }

          temp.beforeFirst();

          Choice customer_dropdown = new Choice();
          while (temp.next()) {
            customer_dropdown.add(temp.getString("customer_id") + " - " + temp.getString("first_name") + " "
                + temp.getString("last_name"));
          }
          Label spaceLabel = new Label("                           ");
          Label spaceLabel2 = new Label("            ");
          Label spaceLabel3 = new Label("                                      ");
          Label spaceLabel4 = new Label("                     ");
          Label spaceLabel5 = new Label("                         ");
          cus.add(customer_dropdown);
          Label first_name = new Label("First Name: ");
          TextField first_name_text = new TextField(20);
          Label last_name = new Label("Last Name: ");
          TextField last_name_text = new TextField(20);
          Label email = new Label("Email: ");
          TextField email_text = new TextField(20);
          Label address = new Label("Address: ");
          Choice address_dropdown = new Choice();
          Label active = new Label("Active: ");
          Choice active_dropdown = new Choice();
          Label create_date = new Label("Create Date: ");
          TextField create_date_text = new TextField(20);
          Button b1 = new Button("Change");
          Button b2 = new Button("Change");
          Button b3 = new Button("Change");
          Button b4 = new Button("Change");
          Button b5 = new Button("Change");
          Button b6 = new Button("Change");
          cus.add(spaceLabel);
          cus.add(first_name);
          cus.add(first_name_text);
          cus.add(b1);
          cus.add(last_name);
          cus.add(last_name_text);
          cus.add(b2);
          cus.add(email);
          cus.add(email_text);
          cus.add(b6);
          cus.add(spaceLabel2);
          cus.add(address);
          cus.add(address_dropdown);
          cus.add(spaceLabel5);
          cus.add(b3);
          cus.add(spaceLabel3);
          cus.add(active);
          cus.add(active_dropdown);
          cus.add(b4);
          cus.add(spaceLabel4);
          cus.add(create_date);
          cus.add(create_date_text);
          cus.add(b5);

          b1.setVisible(true);
          b2.setVisible(true);
          b3.setVisible(true);
          b4.setVisible(true);
          // b4.setEnabled(false);
          b5.setVisible(true);
          b6.setVisible(true);
          b6.setEnabled(false);
          ResultSet result4 = null;
          String tempp[] = new String[100];
          int tempint[] = new int[100];

          // fill in the TextrFields with the elements form
          // customer_dropdown.getselectedItem
          for (int i = 0; i < cnt; i++) {
            String SQL1 = "SELECT * FROM address WHERE address_id = ?";
            PreparedStatement pstmt1 = conn.prepareStatement(SQL1, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
            pstmt1.setInt(1, addresses[i]);
            result4 = pstmt1.executeQuery();

            while (result4.next()) {
              tempp[i] = result4.getString("address");
              tempint[i] = result4.getInt("address_id");
              address_dropdown.add(Integer.toString(addresses[i]) + " -" + result4.getString("address"));
            }
          }

          temp.beforeFirst();

          while (temp.next()) {
            if (temp.getString("customer_id").equals(customer_dropdown.getSelectedItem().split(" -")[0])) {
              break;
            }
          }
          first_name_text.setText(temp.getString("first_name"));
          last_name_text.setText(temp.getString("last_name"));
          email_text.setText(temp.getString("email"));
          address_dropdown.select(Integer.toString(temp.getInt("address_id")));
          if (temp.getString("active").equals("1")) {
            active_dropdown.add("1 - Active");
            active_dropdown.add("0 - Inactive");
          } else {
            active_dropdown.add("0 - Inactive");
            active_dropdown.add("1 - Active");
          }
          create_date_text.setText(temp.getString("create_date"));

          // determine if the selected item in customer_dropdown is changed
          customer_dropdown.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
              if (e.getStateChange() == ItemEvent.SELECTED) {
                int cnt = 0;
                try {
                  String SQL = "SELECT * FROM customer";
                  PreparedStatement pstmt = conn.prepareStatement(SQL, ResultSet.TYPE_SCROLL_INSENSITIVE,
                      ResultSet.CONCUR_READ_ONLY);
                  ResultSet temp1 = pstmt.executeQuery();
                  // temp1.beforeFirst();
                  while (temp1.next()) {
                    addresses[cnt] = temp1.getInt("address_id");
                    cnt++;
                  }
                  temp1.beforeFirst();
                  while (temp1.next()) {
                    if (temp1.getString("customer_id").equals(customer_dropdown.getSelectedItem().split(" -")[0])) {
                      break;
                    }
                  }
                  int cntt = 0;
                  for (int i = 0; i < 100; i++) {
                    if (tempint[i] == temp1.getInt("address_id")) {
                      cntt = i;
                      break;
                    }
                  }
                  first_name_text.setText(temp1.getString("first_name"));
                  last_name_text.setText(temp1.getString("last_name"));
                  email_text.setText(temp1.getString("email"));
                  address_dropdown.select(Integer.toString(temp1.getInt("address_id")) + " -" + tempp[cntt]);
                  if (temp1.getString("active").equals("1")) {
                    active_dropdown.select("1 - Active");
                  } else {
                    active_dropdown.select("0 - Inactive");
                  }
                  create_date_text.setText(temp1.getString("create_date"));
                } catch (SQLException e1) {
                  // TODO Auto-generated catch block
                  e1.printStackTrace();
                }
              }
            }
          });
          b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              String SQL1 = "UPDATE customer SET first_name = ? WHERE customer_id = ?";
              PreparedStatement pstmt1;
              try {
                pstmt1 = conn.prepareStatement(SQL1, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                pstmt1.setString(1, first_name_text.getText());
                pstmt1.setInt(2, Integer.parseInt(customer_dropdown.getSelectedItem().split(" -")[0]));
                pstmt1.executeUpdate();
              } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
              }
            }
          });
          b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              String SQL1 = "UPDATE customer SET last_name = ? WHERE customer_id = ?";
              PreparedStatement pstmt1;
              try {
                pstmt1 = conn.prepareStatement(SQL1, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                pstmt1.setString(1, last_name_text.getText());
                pstmt1.setInt(2, Integer.parseInt(customer_dropdown.getSelectedItem().split(" -")[0]));
                pstmt1.executeUpdate();
              } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
              }
            }
          });
          b3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              String SQL1 = "UPDATE customer SET address_id = ? WHERE customer_id = ?";
              PreparedStatement pstmt1;
              try {
                pstmt1 = conn.prepareStatement(SQL1, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                pstmt1.setInt(1, Integer.parseInt(address_dropdown.getSelectedItem().split(" -")[0]));
                pstmt1.setInt(2, Integer.parseInt(customer_dropdown.getSelectedItem().split(" -")[0]));
                pstmt1.executeUpdate();
              } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
              }
            }
          });
          b4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              String SQL1 = "UPDATE customer SET active = ? WHERE customer_id = ?";
              PreparedStatement pstmt1;
              try {
                pstmt1 = conn.prepareStatement(SQL1, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                if (active_dropdown.getSelectedItem().split(" -")[0].equals("1")) {
                  pstmt1.setBoolean(1, true);
                } else {
                  pstmt1.setBoolean(1, false);
                }
                pstmt1.setInt(2, Integer.parseInt(customer_dropdown.getSelectedItem().split(" -")[0]));
                pstmt1.executeUpdate();
              } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
              }
            }
          });
          b5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              String SQL1 = "UPDATE customer SET create_date = ? WHERE customer_id = ?";
              PreparedStatement pstmt1;
              try {
                pstmt1 = conn.prepareStatement(SQL1, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                pstmt1.setString(1, create_date_text.getText());
                pstmt1.setInt(2, Integer.parseInt(customer_dropdown.getSelectedItem().split(" -")[0]));
                pstmt1.executeUpdate();
              } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
              }
            }
          });
        } catch (SQLException ex) {
          Logger.getLogger(GUI_Interface.class.getName()).log(Level.SEVERE, null, ex);
          System.out.println("SQL is bad " + ex.getMessage());
        } catch (Exception ex) {
          Logger.getLogger(GUI_Interface.class.getName()).log(Level.SEVERE, null, ex);
          System.out.println("Exception");
        }
      }
    });
    b2.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          // f.dispose();
          Frame ren = new Frame("Customer Rentals");
          ren.setSize(350, 200);
          ren.setLayout(new FlowLayout());
          ren.setVisible(true);
          ren.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent we) {
              ren.dispose();
            }

          });
          Label spaceLabel = new Label("                           ");
          Label l1 = new Label("Customer: ");
          ren.add(l1);
          Choice customer_dropdown = new Choice();
          ren.add(customer_dropdown);
          Label l2 = new Label("Rental id: ");
          ren.add(l2);
          Choice rental_dropdown = new Choice();
          ren.add(rental_dropdown);
          ren.add(spaceLabel);
          Label l3 = new Label("Rental Date: ");
          ren.add(l3);
          TextField rental_date_text = new TextField(20);
          ren.add(rental_date_text);
          Label l4 = new Label("film_inventory_id: ");
          ren.add(l4);
          TextField film_inventory_id_text = new TextField(20);
          ren.add(film_inventory_id_text);
          Label l5 = new Label("series_inventory_id: ");
          ren.add(l5);
          TextField series_inventory_id_text = new TextField(20);
          ren.add(series_inventory_id_text);

          String SQL = "SELECT * FROM customer";
          PreparedStatement pstmt = conn.prepareStatement(SQL, ResultSet.TYPE_SCROLL_INSENSITIVE,
              ResultSet.CONCUR_READ_ONLY);
          ResultSet rs = pstmt.executeQuery();
          while (rs.next()) {
            customer_dropdown.add(rs.getString("customer_id") + " - " + rs.getString("first_name") + " "
                + rs.getString("last_name"));
          }
          String SQL1 = "SELECT * FROM rental WHERE customer_id = ?";
          PreparedStatement pstmt1 = conn.prepareStatement(SQL1, ResultSet.TYPE_SCROLL_INSENSITIVE,
              ResultSet.CONCUR_READ_ONLY);
          pstmt1.setInt(1, Integer.parseInt(customer_dropdown.getSelectedItem().split(" -")[0]));
          ResultSet rs1 = pstmt1.executeQuery();
          while (rs1.next()) {
            rental_dropdown.add(rs1.getString("rental_id"));
          }
          String SQL2 = "SELECT * FROM rental WHERE rental_id = ?";
          PreparedStatement pstmt2;
          pstmt2 = conn.prepareStatement(SQL2, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
          pstmt2.setInt(1, Integer.parseInt(rental_dropdown.getSelectedItem()));
          ResultSet rs2 = pstmt2.executeQuery();
          while (rs2.next()) {
            rental_date_text.setText(rs2.getString("rental_date"));
            film_inventory_id_text.setText(rs2.getString("film_inventory_id"));
            series_inventory_id_text.setText(rs2.getString("series_inventory_id"));
          }

          customer_dropdown.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
              try {
                String SQL = "SELECT * FROM rental WHERE customer_id = ?";
                PreparedStatement pstmt;
                pstmt = conn.prepareStatement(SQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                pstmt.setInt(1, Integer.parseInt(customer_dropdown.getSelectedItem().split(" -")[0]));
                ResultSet rs = pstmt.executeQuery();
                rental_dropdown.removeAll();
                while (rs.next()) {
                  rental_dropdown.add(rs.getString("rental_id"));
                }
                rental_dropdown.addItemListener(new ItemListener() {
                  public void itemStateChanged(ItemEvent e) {
                    try {
                      String SQL = "SELECT * FROM rental WHERE rental_id = ?";
                      PreparedStatement pstmt;
                      pstmt = conn.prepareStatement(SQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                      pstmt.setInt(1, Integer.parseInt(rental_dropdown.getSelectedItem()));
                      ResultSet rs = pstmt.executeQuery();
                      while (rs.next()) {
                        rental_date_text.setText(rs.getString("rental_date"));
                        film_inventory_id_text.setText(rs.getString("film_inventory_id"));
                        series_inventory_id_text.setText(rs.getString("series_inventory_id"));
                      }
                    } catch (SQLException e1) {
                      // TODO Auto-generated catch block
                      e1.printStackTrace();
                    }
                  }
                });

              } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
              }
            }
          });

        } catch (SQLException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
      }
    });
    b3.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          // f.dispose();
          Frame ren = new Frame("Most Rented");
          ren.setSize(350, 200);
          ren.setLayout(new FlowLayout());
          ren.setVisible(true);
          ren.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent we) {
              ren.dispose();
            }
          });

          Label l1 = new Label("5 Most Rented films: ");
          ren.add(l1);
          Choice most_rented_films_dropdown = new Choice();
          ren.add(most_rented_films_dropdown);
          Label l2 = new Label("5 Most Rented series: ");
          ren.add(l2);
          Choice most_rented_series_dropdown = new Choice();
          ren.add(most_rented_series_dropdown);

          // create a string and assign it the current date
          String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
          // create a string and assign it the date 30 days ago
          String date30 = new SimpleDateFormat("yyyy-MM-dd").format(new Date().getTime() - TimeUnit.DAYS.toMillis(30));
          Statement sqlSt;
          String SQL = "call MostRented('m',5,'" + date30 + "', '" + date + "')";
          sqlSt = conn.createStatement();
          ResultSet rs = sqlSt.executeQuery(SQL);
          while (rs.next()) {
            most_rented_films_dropdown.add(rs.getString("title"));
          }
          String SQL1 = "call MostRented('s',5,'" + date30 + "', '" + date + "')";
          Statement sqlSt1 = conn.createStatement();
          ResultSet rs1 = sqlSt1.executeQuery(SQL1);
          while (rs1.next()) {
            most_rented_series_dropdown.add(rs1.getString("title"));
          }
        } catch (SQLException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        } catch (Exception e2) {
          // TODO Auto-generated catch block
          e2.printStackTrace();
        }
      }
    });
    b4.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        f.dispose();
      }
    });
  }

  static void admin_menu(ResultSet result, ResultSet result1, String email) throws SQLException {
    Frame f = new Frame("Admin Menu");
    f.setSize(300, 200);
    f.setLayout(new FlowLayout());
    f.setVisible(true);
    while (result1.next()) {
      if (result1.getString("email").equals(email)) {
        break;
      }
    }
    f.addWindowListener(new WindowAdapter() {

      public void windowClosing(WindowEvent we) {
        f.dispose();
      }

    });

    String dbURL = "jdbc:mysql://localhost/tvondemand";
    Connection conn = DriverManager.getConnection(dbURL, "root", "");

    Label label = new Label("Welcome: " + result1.getString("first_name") + " " + result1.getString("last_name"));
    f.add(label);
    Button b1 = new Button("Create New Account");
    f.add(b1);
    Button b2 = new Button("Delete Account");
    f.add(b2);
    Button b3 = new Button("Admin Change");
    f.add(b3);
    Button b5 = new Button("Show Revenue");
    f.add(b5);

    Button b4 = new Button("Logout");

    b1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // f.dispose();
        Frame f = new Frame("Create New Account");
        f.setSize(400, 300);
        f.setLayout(new FlowLayout());
        f.setVisible(true);
        f.addWindowListener(new WindowAdapter() {

          public void windowClosing(WindowEvent we) {
            f.dispose();
          }

        });
        int[] addresses = new int[100];
        int cnt = 0;
        Label empty = new Label("                      ");
        Label empty2 = new Label("                          ");
        Label empty3 = new Label("                               ");
        Label empty4 = new Label("                                 ");
        Label empty5 = new Label("             ");
        Label empty6 = new Label("                  ");

        Label l1 = new Label("First Name: ");
        f.add(l1);
        TextField first_name_text = new TextField(20);
        f.add(first_name_text);
        f.add(empty);
        Label l2 = new Label("Last Name: ");
        f.add(l2);
        TextField last_name_text = new TextField(20);
        f.add(last_name_text);
        f.add(empty2);
        Label l3 = new Label("Email: ");
        f.add(l3);
        TextField email_text = new TextField(20);
        f.add(email_text);
        f.add(empty3);
        Label l6 = new Label("Address: ");
        f.add(l6);
        Choice address_dropdown = new Choice();
        f.add(address_dropdown);

        Label l7 = new Label("Active: ");
        f.add(l7);
        Choice active_dropdown = new Choice();
        f.add(active_dropdown);
        active_dropdown.add("1 - Active");
        active_dropdown.add("0 - Inactive");
        f.add(empty4);
        Label l8 = new Label("Create Date: ");
        f.add(l8);
        TextField create_date_text = new TextField(20);
        f.add(create_date_text);
        f.add(empty5);
        Label l11 = new Label("Account Type: ");
        f.add(l11);
        Choice account_type_dropdown = new Choice();
        f.add(account_type_dropdown);
        account_type_dropdown.add("Customer");
        account_type_dropdown.add("Employee");
        Button b1 = new Button("Create Account");
        f.add(empty6);
        f.add(b1);

        // select * from customer
        try {
          String SQL = "select * from customer";
          Statement sqlSt = conn.createStatement();
          ResultSet rs = sqlSt.executeQuery(SQL);
          while (rs.next()) {
            addresses[cnt] = rs.getInt("address_id");
            cnt++;
          }
        } catch (SQLException ex) {
          ex.printStackTrace();
        }

        // select * from addresses where address_id = addresses[i]
        for (int i = 0; i < cnt; i++) {
          try {
            String SQL = "select * from address where address_id = " + addresses[i];
            Statement sqlSt = conn.createStatement();
            ResultSet rs = sqlSt.executeQuery(SQL);
            while (rs.next()) {
              address_dropdown.add(addresses[i] + " -" + rs.getString("address"));
            }
          } catch (SQLException ex) {
            ex.printStackTrace();
          }
        }

        b1.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            try {
              if (account_type_dropdown.getSelectedItem().equals("Customer")) {
                String SQL = "insert into `customer` (`customer_id`, `first_name`, `last_name`, `email`, `address_id`, `active`, `create_date`) VALUES (0,'"
                    + first_name_text.getText() + "','" + last_name_text.getText() + "','" + email_text.getText()
                    + "','" + address_dropdown.getSelectedItem().split("-")[0] + "',"
                    + active_dropdown.getSelectedItem().split("-")[0] + ",'" + create_date_text.getText() + "')";
                Statement sqlSt = conn.createStatement();
                sqlSt.executeUpdate(SQL);
              } else {
                String SQL = "INSERT INTO `employees` (`employee_id`, `first_name`, `last_name`, `email`, `address_id`, `active`, `create_date`) VALUES (0,'"
                    + first_name_text.getText() + "','" + last_name_text.getText() + "','" + email_text.getText()
                    + "','" + address_dropdown.getSelectedItem().split("-")[0] + "',"
                    + active_dropdown.getSelectedItem().split("-")[0] + ",'" + create_date_text.getText() + "')";
                Statement sqlSt = conn.createStatement();
                sqlSt.executeUpdate(SQL);
              }
            } catch (SQLException e1) {
              // TODO Auto-generated catch block
              e1.printStackTrace();
            }
          }
        });
      }
    });
    b2.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Frame f = new Frame("Delete an Account");
        f.setSize(300, 300);
        f.setLayout(new FlowLayout());
        f.setVisible(true);
        f.addWindowListener(new WindowAdapter() {

          public void windowClosing(WindowEvent we) {
            f.dispose();
          }

        });
        Label spaceLabel = new Label("              ");
        Label l2 = new Label("Type: ");
        f.add(l2);
        Choice account_type_dropdown = new Choice();
        f.add(account_type_dropdown);
        f.add(spaceLabel);
        account_type_dropdown.add("Customer");
        account_type_dropdown.add("Employee");
        Label l1 = new Label("Account ID: ");
        f.add(l1);
        Choice account_dropdown = new Choice();
        f.add(account_dropdown);
        if (account_type_dropdown.getSelectedItem().equals("Customer")) {
          try {
            String SQL = "select * from customer";
            Statement sqlSt = conn.createStatement();
            ResultSet rs = sqlSt.executeQuery(SQL);
            while (rs.next()) {
              account_dropdown.add(rs.getInt("customer_id") + " - " + rs.getString("first_name") + " "
                  + rs.getString("last_name"));
            }
          } catch (SQLException ex) {
            ex.printStackTrace();
          }
        } else {
          try {
            String SQL = "select * from employees";
            Statement sqlSt = conn.createStatement();
            ResultSet rs = sqlSt.executeQuery(SQL);
            while (rs.next()) {
              account_dropdown.add(rs.getInt("employee_id") + " - " + rs.getString("first_name") + " "
                  + rs.getString("last_name"));
            }
          } catch (SQLException ex) {
            ex.printStackTrace();
          }
        }
        Button b1 = new Button("Delete Account");
        f.add(b1);

        account_type_dropdown.addItemListener(new ItemListener() {
          public void itemStateChanged(ItemEvent e) {
            account_dropdown.removeAll();
            if (account_type_dropdown.getSelectedItem().equals("Customer")) {
              try {
                String SQL = "select * from customer";
                Statement sqlSt = conn.createStatement();
                ResultSet rs = sqlSt.executeQuery(SQL);
                while (rs.next()) {
                  account_dropdown.add(rs.getInt("customer_id") + " - " + rs.getString("first_name") + " "
                      + rs.getString("last_name"));
                }
              } catch (SQLException ex) {
                ex.printStackTrace();
              }
            } else {
              try {
                String SQL = "select * from employees";
                Statement sqlSt = conn.createStatement();
                ResultSet rs = sqlSt.executeQuery(SQL);
                while (rs.next()) {
                  account_dropdown.add(rs.getInt("employee_id") + " - " + rs.getString("first_name") + " "
                      + rs.getString("last_name"));
                }
              } catch (SQLException ex) {
                ex.printStackTrace();
              }
            }
          }

        });
        b1.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            try {
              if (account_type_dropdown.getSelectedItem().equals("Customer")) {
                String SQL = "delete from customer where customer_id = "
                    + account_dropdown.getSelectedItem().split("-")[0];
                Statement sqlSt = conn.createStatement();
                sqlSt.executeUpdate(SQL);
              } else {
                String SQL = "delete from employees where employee_id = "
                    + account_dropdown.getSelectedItem().split("-")[0];
                Statement sqlSt = conn.createStatement();
                sqlSt.executeUpdate(SQL);
              }
            } catch (SQLException e1) {
              // TODO Auto-generated catch block
              e1.printStackTrace();
            }
          }
        });

      }
    });
    b3.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Frame f = new Frame("Admin Change");
        f.setSize(450, 120);
        f.setLayout(new FlowLayout());
        f.setVisible(true);
        f.addWindowListener(new WindowAdapter() {

          public void windowClosing(WindowEvent we) {
            f.dispose();
          }

        });
        Label employeesLabel = new Label("Employees: ");
        f.add(employeesLabel);
        Choice employees_dropdown = new Choice();
        f.add(employees_dropdown);
        Button b1 = new Button("Make Admin");
        f.add(b1);
        Label adminLabel = new Label("Admins: ");
        f.add(adminLabel);
        Choice admin_dropdown = new Choice();
        f.add(admin_dropdown);
        Button b2 = new Button("Remove Admin");
        f.add(b2);
        try {
          String SQL = "select * from employees";
          Statement sqlSt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
          ResultSet rs = sqlSt.executeQuery(SQL);
          while (rs.next()) {
            employees_dropdown.add(rs.getInt("employee_id") + " - " + rs.getString("first_name") + " "
                + rs.getString("last_name"));
          }

          String SQL1 = "select * from administrator";
          Statement sqlSt1 = conn.createStatement();
          ResultSet rs1 = sqlSt1.executeQuery(SQL1);
          // find the admin first name and last name from rs where employee_id =
          // rs1.employee_id
          rs.beforeFirst();
          while (rs1.next()) {
            while (rs.next()) {
              if (rs1.getInt("employee_id") == rs.getInt("employee_id")) {
                admin_dropdown.add(rs.getInt("employee_id") + " - " + rs.getString("first_name") + " "
                    + rs.getString("last_name"));
                break;
              }
              break;
            }
          }
        } catch (SQLException ex) {
          ex.printStackTrace();
        }
        b1.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            try {
              String SQL = "insert into administrator values (0,'" + employees_dropdown.getSelectedItem().split("-")[0]
                  + "')";
              Statement sqlSt = conn.createStatement();
              sqlSt.executeUpdate(SQL);
              // check if the same entry is already in the admin table if not add it
              if (admin_dropdown.getItemCount() == 0) {
                admin_dropdown.add(employees_dropdown.getSelectedItem());
              } else {
                for (int i = 0; i < admin_dropdown.getItemCount(); i++) {
                  if (admin_dropdown.getItem(i).equals(employees_dropdown.getSelectedItem())) {
                    break;
                  } else if (i == admin_dropdown.getItemCount() - 1) {
                    admin_dropdown.add(employees_dropdown.getSelectedItem());
                  }
                }
              }
            } catch (SQLException e1) {
              // TODO Auto-generated catch block
              e1.printStackTrace();
            }
          }
        });
        b2.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            try {
              String SQL = "delete from administrator where employee_id = "
                  + admin_dropdown.getSelectedItem().split("-")[0];
              Statement sqlSt = conn.createStatement();
              sqlSt.executeUpdate(SQL);
              admin_dropdown.remove(admin_dropdown.getSelectedItem());
            } catch (SQLException e1) {
              // TODO Auto-generated catch block
              e1.printStackTrace();
            }
          }
        });

      }
    });
    b5.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Frame f = new Frame("Revenue");
        f.setSize(300, 300);
        f.setLayout(new FlowLayout());
        f.setVisible(true);
        f.addWindowListener(new WindowAdapter() {

          public void windowClosing(WindowEvent we) {
            f.dispose();
          }

        });
        Label monthLabel = new Label("Select Month and Year: ");
        f.add(monthLabel);
        Choice month_dropdown = new Choice();
        f.add(month_dropdown);
        Label revenueLabel = new Label("Film Revenue: ");
        f.add(revenueLabel);
        TextField revenue_field = new TextField(10);
        f.add(revenue_field);
        Label revenueLabel1 = new Label("Series Revenue: ");
        f.add(revenueLabel1);
        TextField revenue_field1 = new TextField(10);
        f.add(revenue_field1);

        // add every month and every year from where there is an entry in payment from
        // payment date
        try {
          String SQL = "select distinct month(payment_date) as month, year(payment_date) as year from payment";
          Statement sqlSt = conn.createStatement();
          ResultSet rs = sqlSt.executeQuery(SQL);
          while (rs.next()) {
            month_dropdown.add(rs.getInt("month") + "-" + rs.getInt("year"));
          }

          if (month_dropdown.getSelectedItem().split("-")[0].length() > 1) {
            String SQL1 = "call Revenue('" + month_dropdown.getSelectedItem().split("-")[1] + "-0"
                + month_dropdown.getSelectedItem().split("-")[0] + "-01')";
            Statement sqlSt1 = conn.createStatement();
            ResultSet rs1 = sqlSt1.executeQuery(SQL1);
            while (rs1.next()) {
              revenue_field.setText(rs1.getString("films_revenue"));
              revenue_field1.setText(rs1.getString("series_revenue"));
            }
          } else {
            String SQL2 = "call Revenue('" + month_dropdown.getSelectedItem().split("-")[1] + "-"
                + month_dropdown.getSelectedItem().split("-")[0] + "-01')";
            Statement sqlSt2 = conn.createStatement();
            ResultSet rs2 = sqlSt2.executeQuery(SQL2);
            while (rs2.next()) {
              revenue_field.setText(rs2.getString("films_revenue"));
              revenue_field1.setText(rs2.getString("series_revenue"));
            }

          }
        } catch (SQLException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }

        month_dropdown.addItemListener(new ItemListener() {
          public void itemStateChanged(ItemEvent e) {
            try {
              if (month_dropdown.getSelectedItem().split("-")[0].length() > 1) {
                String SQL = "call Revenue('" + month_dropdown.getSelectedItem().split("-")[1] + "-0"
                    + month_dropdown.getSelectedItem().split("-")[0] + "-01')";
                Statement sqlSt = conn.createStatement();
                ResultSet rs = sqlSt.executeQuery(SQL);
                while (rs.next()) {
                  revenue_field.setText(rs.getString("films_revenue"));
                  revenue_field1.setText(rs.getString("series_revenue"));
                }
              } else {
                String SQL = "call Revenue('" + month_dropdown.getSelectedItem().split("-")[1] + "-"
                    + month_dropdown.getSelectedItem().split("-")[0] + "-01')";
                Statement sqlSt = conn.createStatement();
                ResultSet rs = sqlSt.executeQuery(SQL);
                while (rs.next()) {
                  revenue_field.setText(rs.getString("films_revenue"));
                  revenue_field1.setText(rs.getString("series_revenue"));
                }

              }

            } catch (SQLException e1) {
              // TODO Auto-generated catch block
              e1.printStackTrace();
            }
          }
        });

      }
    });

    f.add(b4);
    b4.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        f.dispose();

      }
    });
  }

  static void AwtExample(ResultSet result, ResultSet result1) {

    Frame f = new Frame("Login");
    Button btn = new Button("Login");
    TextField textField = new TextField(30);
    TextField welcome = new TextField();
    f.add(textField);
    btn.setBounds(50, 50, 50, 50);
    f.add(btn);
    f.setSize(450, 70);
    f.setTitle("Login");
    f.setLayout(new FlowLayout());
    f.setVisible(true);
    try {
      result1.beforeFirst();
    } catch (SQLException e2) {
      // TODO Auto-generated catch block
      e2.printStackTrace();
    }
    try {
      result.beforeFirst();
    } catch (SQLException e2) {
      // TODO Auto-generated catch block
      e2.printStackTrace();
    }

    f.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent we) {
        f.dispose();
      }
    });

    btn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

        try {
          if (textField.getText().equals("LIZ.SUTTON@sakilaemployees.org")) {
            String email = textField.getText();
            f.dispose();
            f.setVisible(false);
            admin_menu(result, result1, email);
          } else {
            while (result.next()) {
              if (textField.getText().equals(result.getString("email"))) {
                String email = textField.getText();
                f.dispose();
                result.beforeFirst();
                customer_menu(result, email);
                break;
              }
            }
            while (result1.next()) {
              if (textField.getText().equals(result1.getString("email"))
                  && !result1.getString("email").equals("LIZ.SUTTON@sakilaemployees.org")) {
                String email = textField.getText();
                f.dispose();
                result1.beforeFirst();
                employee_menu(result1, email);
                break;
              }
            }
            if (result.next() == false && result1.next() == false) {
              welcome.setText("Invalid email");
              f.add(welcome);
              result.beforeFirst();
              result1.beforeFirst();
              f.setVisible(true);

            }
          }
        } catch (SQLException e1) {
          Logger.getLogger(GUI_Interface.class.getName()).log(Level.SEVERE, null, e1);
          System.out.println("SQL is bad " + e1.getMessage());
        } catch (Exception e1) {
          Logger.getLogger(GUI_Interface.class.getName()).log(Level.SEVERE, null, e1);
          System.out.println("Exception");
        }

      }
    });

  }

  public static void main(String args[]) {
    Statement sqlSt;
    Statement sqlSt1;
    Statement sqlSt2;
    Statement sqlSt3;
    ResultSet result;
    ResultSet result1;

    String SQL = "select * from tvondemand.customer";
    String SQL1 = "SELECT * FROM tvondemand.employees";

    try {
      Class.forName("com.mysql.jdbc.Driver");
      String dbURL = "jdbc:mysql://localhost/tvondemand";
      Connection conn = DriverManager.getConnection(dbURL, "root", "");
      sqlSt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); // allows SQL
                                                                                                   // statements to be
                                                                                                   // executed
      result = sqlSt.executeQuery(SQL); // result holds the output of the SQL statement

      sqlSt1 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); // allows SQL
                                                                                                    // statements to be
                                                                                                    // executed
      result1 = sqlSt1.executeQuery(SQL1);

      sqlSt2 = conn.createStatement(); // allows SQL
      sqlSt3 = conn.createStatement(); // allows SQL

      sqlSt3.execute("DROP TRIGGER IF EXISTS customer_update");
      ////
      sqlSt2.execute(
          "CREATE TRIGGER customer_update BEFORE UPDATE ON customer "//
              + " FOR EACH ROW "//
              + " BEGIN "//
              + "if old.customer_id != new.customer_id then "//
              + "SIGNAL SQLSTATE '45000' set message_text = 'Cannot change customer_id'; "//
              + "end if; "//
              + "if old.email != new.email THEN "//
              + "SIGNAL SQLSTATE '45000' set message_text = 'Cannot change email'; "//
              + "end if; "//
              + "if old.active != new.active THEN "//
              + "SIGNAL SQLSTATE '45000' set message_text = 'Cannot change active'; "//
              + "end if; "//
              + "if old.create_date != new.create_date THEN "//
              + "SIGNAL SQLSTATE '45000' set message_text = 'Cannot change create_date'; "//
              + "end if; "//
              + "END; ");

      AwtExample(result, result1);
      // sqlSt.close();

    } catch (ClassNotFoundException e) {
      Logger.getLogger(GUI_Interface.class.getName()).log(Level.SEVERE, null, e);
      System.out.println("ClassNotFoundException");
    } catch (SQLException e) {
      Logger.getLogger(GUI_Interface.class.getName()).log(Level.SEVERE, null, e);
      System.out.println("SQL is bad " + e.getMessage());
    } catch (Exception e) {
      Logger.getLogger(GUI_Interface.class.getName()).log(Level.SEVERE, null, e);
      System.out.println("Exception");
    }

  }
}
