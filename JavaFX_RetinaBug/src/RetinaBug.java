import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
  
public class RetinaBug extends JFrame {
 
    private JFXPanel[] jfxPanels = { new JFXPanel(), new JFXPanel()};    
    private WebEngine[] engines  = { null, null};
 
    private final JPanel panel = new JPanel(new BorderLayout());
    private final JButton step1 = new JButton("Step 1");
    private final JButton step2 = new JButton("Step 2");
    private final JTextField txt = new JTextField();
 
    public RetinaBug() {
        super();
        initComponents();
    }

    
    private void initComponents() {
        createScene(0);
        
        JPanel topBar = new JPanel(new BorderLayout(5, 0));
        topBar.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
        topBar.add(step1, BorderLayout.WEST);
        topBar.add(txt,   BorderLayout.CENTER);
        topBar.add(step2, BorderLayout.EAST);
  
        panel.add(topBar, BorderLayout.NORTH);
        panel.add(jfxPanels[0], BorderLayout.CENTER);
        
        getContentPane().add(panel);
        
        setPreferredSize(new Dimension(1024, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();

        step1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				step1.setEnabled(false);
				step2.setEnabled(true);
				panel.remove(jfxPanels[0]);
				if (engines[1]==null) createScene(1);
				panel.add(jfxPanels[1], BorderLayout.CENTER);
			}
		});
        
        step2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				step1.setEnabled(true);
				step2.setEnabled(false);
				panel.remove(jfxPanels[1]);
				panel.add(jfxPanels[0], BorderLayout.CENTER);
			}
		});
        step2.setEnabled(false);
    }
 
    private void createScene(final int sceneId) {
 
        Platform.runLater(new Runnable() {
            @Override 
            public void run() {
                WebView view = new WebView();
                engines[sceneId] = view.getEngine(); 
                jfxPanels[sceneId].setScene(new Scene(view));
                engines[sceneId].load( (sceneId==0)?"http://www.sap.com":"http://www.google.com");
            }
        });
    }
    public static void main(String[] args) {
    	Platform.setImplicitExit(false);
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
            	RetinaBug browser = new RetinaBug();
                browser.setVisible(true);
           }     
       });
    }
}