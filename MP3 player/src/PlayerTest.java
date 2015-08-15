import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
//import koko.SimpleList2.PrintListener;
 
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingConstants;
import javax.swing.UIDefaults;
import javax.swing.JTextPane;
import javax.swing.Painter;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.SliderUI;
import javax.swing.plaf.basic.BasicSliderUI;
 
import com.sun.prism.Graphics;
 
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;
 
import javax.swing.JSlider;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.border.CompoundBorder;
import java.awt.List;
import javax.swing.ScrollPaneConstants;
 
public class PlayerTest extends JFrame {
 
	private JFXPanel fxPanel;
	private Media media;
	private MediaPlayer mediaPlayer;
	private JPanel contentPane;
	private JButton btnNewButton_1;
	private JButton btnPlay;
	private JButton btnNewButton;
	private JButton btnNewButton_2;
	private JButton btnStop;
	private JFileChooser ChosenFile;
	private File Song;
	private JSlider slider;
	private boolean flag;
	private boolean isplaying;
	private JProgressBar progressBar;
	private Vector<File> List;
	private JList PlayList;
	private JScrollPane pane;
	private DefaultListModel Model;
	private String temp;
	private String Song_name;
 
	public static void main(String args[]) {
		for (UIManager.LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
			if ("Nimbus".equals(laf.getName())) {
				try {
					UIManager.setLookAndFeel(laf.getClassName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		new PlayerTest();
 
	}
 
	PlayerTest() {
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		progressBar = new JProgressBar();
		Player();
		Add();
		playButton();
		PauseButton();
		PlayList();
		StopButton();
		Volume();
		ProgAndSeek();
	}
 
	void Player() {
		contentPane = new JPanel();
		setBounds(100, 100, 452, 254);
		this.setVisible(true);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(0, 0, 128));
	}
 
	void Add() {
		btnNewButton_1 = new JButton("Add");
 
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ChosenFile = new JFileChooser();
				ChosenFile.showOpenDialog(null);
				List.add(ChosenFile.getSelectedFile());
				Model.addElement(List.lastElement().getName().substring(0,(int)List.lastElement().getName().length()-4));
			}
		});
		btnNewButton_1.setBounds(327, 168, 96, 23);
		contentPane.add(btnNewButton_1);
 
	}
 
	void PlayList() {
		List = new Vector<File>();
		Model = new DefaultListModel();
		PlayList = new JList(Model);
		PlayList.setVisibleRowCount(10);
		PlayList.setSize(71, 83);
		PlayList.setLocation(338, 25);
 
		JScrollPane pane = new JScrollPane(PlayList);
		pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
 
		pane.setSize(104, 92);
		pane.setLocation(320, 29);
 
		DefaultListSelectionModel m = new DefaultListSelectionModel();
		m.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
 
		PlayList.setSelectionModel(m);
		PlayList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				Song = List.get(PlayList.getSelectedIndex());
 
			}
		});
 
		contentPane.add(pane);
 
	}
 
	void playButton() {
		btnPlay = new JButton("Play");
		btnPlay.setToolTipText("Play it U Dumb Bitch");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
 
				try {
					if (mediaPlayer != null) {
						if (flag)
							mediaPlayer.play();
						else {
							mediaPlayer.dispose();
							play(Song.toURL());
						}
						flag = false;
					} else {
						for (int i = 0; i < List.size(); i++)
							if (progressBar.getValue() == 0)
								play(Song.toURL());
					}
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnPlay.setBounds(25, 168, 89, 23);
		contentPane.add(btnPlay);
	}
 
	void play(URL url) {
 
		fxPanel = new JFXPanel();
		String path = url.toString().replace(" ", "%20");
		media = new Media(path);
 
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();
	}
 
	void PauseButton() {
		btnNewButton = new JButton("Pause");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mediaPlayer.pause();
				flag = true;
			}
		});
		btnNewButton.setBounds(125, 169, 89, 23);
		contentPane.add(btnNewButton);
	}
 
	void StopButton() {
		btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mediaPlayer.stop();
				flag = true;
			}
		});
		btnStop.setBounds(226, 169, 89, 23);
		contentPane.add(btnStop);
	}
 
	void Volume() {
 
		UIDefaults sliderDefaults = new UIDefaults();
 
		sliderDefaults.put("Slider.thumbWidth", 20);
		sliderDefaults.put("Slider.thumbHeight", 20);
		sliderDefaults.put("Slider:SliderThumb.backgroundPainter", new Painter<JComponent>() {
			public void paint(Graphics2D g, JComponent c, int w, int h) {
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g.setStroke(new BasicStroke(2f));
				g.setColor(Color.WHITE);
				g.fillOval(1, 1, w - 3, h - 3);
				g.setColor(Color.darkGray);
				g.drawOval(1, 1, w - 3, h - 3);
			}
		});
		sliderDefaults.put("Slider:SliderTrack.backgroundPainter", new Painter<JComponent>() {
			public void paint(Graphics2D g, JComponent c, int w, int h) {
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g.setStroke(new BasicStroke(2f));
				g.setColor(Color.GRAY);
				g.fillRoundRect(0, 6, w - 1, 8, 8, 8);
				g.setColor(Color.WHITE);
				g.drawRoundRect(0, 6, w - 1, 8, 8, 8);
			}
		});
 
		slider = new JSlider(0, 100, 50);
		slider.setToolTipText("Do not touch mother fucker");
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.putClientProperty("Nimbus.Overrides", sliderDefaults);
		slider.putClientProperty("Nimbus.Overrides.InheritDefaults", false);
		slider.putClientProperty("Slider.paintPath", Boolean.TRUE);
		slider.setBounds(325, 134, 96, 23);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				mediaPlayer.setVolume((double) (slider.getValue() / 100.0));
 
			}
		});
		contentPane.add(slider);
	}
 
	void ProgAndSeek() {
		progressBar.setBounds(28, 134, 287, 23);
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);
		progressBar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int v = progressBar.getValue();
				int mouseX = e.getX();
				int progressBarVal = (int) Math
						.round(((double) mouseX / (double) progressBar.getWidth()) * progressBar.getMaximum());
				progressBar.setValue(progressBarVal);
				Duration t;
				t = media.getDuration();
				mediaPlayer.seek(t.multiply(progressBarVal / 100.0));
			}
		});
		progressBar.setVisible(true);
		contentPane.add(progressBar);
 
	}
}