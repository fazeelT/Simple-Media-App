
import javax.swing.*;
import java.awt.*;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.MalformedURLException;

public class SimpleMediaApp extends JFrame {

    LinkList list = new LinkList();

    class ImageFilter extends javax.swing.filechooser.FileFilter {

        public boolean accept(File f) {
            return f.isDirectory() || f.getName().endsWith(".jpg") || f.getName().endsWith(".png") || f.getName().endsWith(".gif");
        }

        public String getDescription() {
            return "Jpg files (*.jpg),PNG File (*.png),GIF Files (*.gif)";
        }
    }

    class AudioFilter extends javax.swing.filechooser.FileFilter {

        public boolean accept(File f) {
            return f.isDirectory() || f.getName().endsWith(".wav") || f.getName().endsWith(".au");
        }

        public String getDescription() {
            return "wav files (*.wav),AU file(*.au)";
        }
    }

    class SoundPanel extends JPanel {

        java.applet.AudioClip snd;

        public SoundPanel() {
            Font fnt = new Font("Monotype Corsiva", Font.PLAIN, 60);
            JButton jb;
            add(jb = new JButton("Play")).setFont(fnt);
            jb.addActionListener((ActionEvent e) -> snd.play());
            add(jb = new JButton("Loop")).setFont(fnt);
            jb.addActionListener((ActionEvent e) -> snd.loop());
            add(jb = new JButton("Stop")).setFont(fnt);
            jb.addActionListener((ActionEvent e) -> snd.stop());
        }

        public void setSound(File f) {
            try {
                snd = java.applet.Applet.newAudioClip(
                        new java.net.URL("file:" + f.getAbsolutePath()));
            } catch (MalformedURLException exc) {

            }
        }

    }

    class ImagePanel extends JPanel {

        Image image;
        int imageScale; //0 Fit to Window         1 100%     2 200%

        public ImagePanel() {

            image = Toolkit.getDefaultToolkit().getImage("E:\\Simple Media App\\SimpleMediaApp\\BrookHeaven.jpg");

        }

        public void setImage(File f) {
            image = Toolkit.getDefaultToolkit().getImage(f.getAbsolutePath());
        }

        public void setImageScale(int n) {
            if (n != imageScale) {
                imageScale = n;
                if (n > 0) {
                    setPreferredSize(
                            new Dimension(image.getWidth(this) * n,
                                    image.getWidth(this) * n));
                } else {
                    this.setPreferredSize(null);
                }
                revalidate(); // tells java to check for position and sizing

            }
        }

        public void paintComponent(Graphics g) {

            super.paintComponent(g);
            if (imageScale == 0) {
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            } else if (imageScale == 1) {
                g.drawImage(image, 0, 0, image.getWidth(this), image.getWidth(this), this);
            } else {
                g.drawImage(image, 0, 0, image.getWidth(this) * 2, image.getHeight(this) * 2, this);
            }
        }
    }

    public SimpleMediaApp() {
        super("Super duper simple media app");
        final JTabbedPane jtb = new JTabbedPane();
        JMenuBar jmb = new JMenuBar();
        setJMenuBar(jmb);
        final ImagePanel ip = new ImagePanel();
        final SoundPanel sp = new SoundPanel();

        //File Menu
        JMenu jm = jmb.add(new JMenu("File"));
        jm.setMnemonic('F');

        final JFileChooser jfc = new JFileChooser();
        final ImageFilter imageFilter = new ImageFilter();
        final AudioFilter audioFilter = new AudioFilter();

        jfc.setFileFilter(imageFilter);
        jfc.setFileFilter(audioFilter);

        JMenuItem jmi = jm.add(new JMenuItem("Open...", 'O'));
        jmi.addActionListener((ActionEvent e) -> {
            if (jfc.showOpenDialog(SimpleMediaApp.this) == JFileChooser.APPROVE_OPTION) {
                File f = jfc.getSelectedFile();
                list.insert(f);
                if (imageFilter.accept(f)) {

                    ip.setImage(f);
                    jtb.setSelectedIndex(0);
                    repaint();
                } else if (audioFilter.accept(f)) {
                    sp.setSound(f);
                    jtb.setSelectedIndex(1);
                }

            }
        });

        jmi = jm.add(new JMenuItem("List", 'L'));
        jm.addSeparator();
        jmi.addActionListener((ActionEvent e)
                -> JOptionPane.showMessageDialog(SimpleMediaApp.this, list.toString(),
                        "File List",
                        JOptionPane.PLAIN_MESSAGE));
        jm.addSeparator();
        jmi = jm.add(new JMenuItem("Exit", 'E'));
        jmi.addActionListener((ActionEvent e) -> System.exit(0));

        //Zoom Menu
        jm = jmb.add(new JMenu("Zoom"));
        jm.setMnemonic('Z');
        ButtonGroup bg = new ButtonGroup();
        jmi = jm.add(new JRadioButtonMenuItem("Fit to Window"));
        jmi.setMnemonic('F');
        jmi.addActionListener((ActionEvent e) -> {
            ip.setImageScale(0);

            repaint();
        });
        bg.add(jmi);
        jmi = jm.add(new JRadioButtonMenuItem("100%"));
        jmi.setMnemonic('1');
        jmi.addActionListener((ActionEvent e) -> {
            ip.setImageScale(1);
            repaint();
        });
        bg.add(jmi);
        jmi = jm.add(new JRadioButtonMenuItem("200%"));
        jmi.setMnemonic('2');
        jmi.addActionListener((ActionEvent e) -> {
            ip.setImageScale(2);
            repaint();
        });
        bg.add(jmi);

        //Help Menu
        jm = jmb.add(new JMenu("Help"));
        jm.setMnemonic('H');
        jmi = jm.add(new JMenuItem("About", 'A'));
        jmi.addActionListener((ActionEvent e)
                -> JOptionPane.showMessageDialog(SimpleMediaApp.this,
                        new JLabel("<html><big><center>"
                                + "Simple Media App<br>"
                                + "Copyright \u00a9 2014 Fazeel<br>"
                                + "All Rights Reserved."),
                        "About", JOptionPane.PLAIN_MESSAGE));

        add(jtb);
        JScrollPane jsp = new JScrollPane(ip);
        jtb.addTab("Image", jsp);
        jtb.addTab("Audio", sp);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 400, 300);
        setVisible(true);

    }

    public static void main(String[] args) {
        new SimpleMediaApp();
    }

}
