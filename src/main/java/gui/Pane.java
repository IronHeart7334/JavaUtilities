package gui;

import java.awt.Color;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Matt
 */
public class Pane extends JPanel{
    public Pane(){
        super();
        setBackground(Color.black);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        String ip = "unknown";
        try{
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch(UnknownHostException ex){
            ex.printStackTrace();
        }
        JLabel yourIp = new JLabel("Your IP address is " + ip);
        yourIp.setBackground(Color.green);
        add(yourIp);
        
        JPanel connect = new JPanel();
        connect.setLayout(new BoxLayout(connect, BoxLayout.Y_AXIS));
        connect.setBackground(Color.yellow);
        
        JLabel label = new JLabel("Enter the IP address to chat with");
        connect.add(label);
        
        JTextField ipInput = new JTextField("000.000.000.000");
        connect.add(ipInput);
        
        JButton connButton = new JButton("connect");
        connect.add(connButton);
        
        add(connect);
    }
}
