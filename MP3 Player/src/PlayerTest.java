import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.UIDefaults;
import javax.swing.Painter;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Vector;

import javax.swing.JSlider;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.SwingConstants;
import java.awt.CardLayout;
import java.awt.Toolkit;

class PlayerTest extends JFrame {

	private JFXPanel fxPanel;
	private Media media;
	private MediaPlayer mediaPlayer;
	private ImagePanel contentPane;
	private JButton btnNewButton_1;
	private JButton btnPlay;
	private JButton btnStop;
	private File Song;
	private JSlider slider;
	private JSlider Play_Slider;
	private Vector<File> List;
	private JList PlayList;
	private JScrollPane pane;
	private DefaultListModel Model;
	private boolean newFile;
	private boolean newChoice;
	private boolean repeat;
	private boolean songIsPlaying;
	private JPanel panel;
	private BufferedImage Player_Image;
	private JPanel panel_1;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_2;
	private URI uri;
	private JButton btnGoogleIt_1;
	private JLabel Time_Left;
	private JLabel Current_duration;
	private int index_of_the_song;
	private JButton btnNewButton_2;
	private JPanel p;
	private JSlider[] bands;
	private JPanelsSliding control_panel;
	private JLabel[] labels;
	private boolean Deleted;
	private Vector<Integer> Shuffled_Index;
	private RoundButton Shuffle;
	private RoundButton Mute;
	private int Previous_Volume_Value;
	private int Current_Volume = 50;
	boolean Is_Shuffled;
	private JLabel lblNewLabel_1;
    private int Mode_index;
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
		setTitle("ECHO");
		setIconImage(Toolkit.getDefaultToolkit().getImage(PlayerTest.class.getResource("clio-emt-speaker.png")));
		setResizable(false);
		setBackground(Color.BLACK);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);

		Player();
		Add();
		playButton();
		PlayList();

		Volume();
		ProgAndSeek();
		Forward_And_Rewind();
		StopButton();
		Repeat();
		eqWindow();
		PlayList_Other();
		Shuffle();
		Mute_volume();
		contentPane.repaint();

	}

	void Player() {
		contentPane = new ImagePanel();
		setBounds(100, 100, 538, 354);

		this.setVisible(true);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		pane = new JScrollPane(PlayList);
		pane.setVisible(false);

		contentPane.setBackground(new Color(0, 0, 128));
		panel = new JPanel();
		panel.setBounds(0, 214, 511, 110);

		control_panel = new JPanelsSliding();
		control_panel.setLocation(0, 217);
		control_panel.setSize(535, 107);
		control_panel.setLayout(new CardLayout(0, 0));
		control_panel.add(panel, "name_37092408351471");

		contentPane.add(control_panel);
	}

	void Add() {
		btnNewButton_1 = new RoundButton("PlayList");
		btnNewButton_1.setBounds(428, 45, 80, 23);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pane.setVisible(!pane.isVisible());
			}
		});
		panel.setLayout(null);
	
		panel.add(btnNewButton_1);

	}

	void PlayList() {
		List = new Vector<File>();
		Model = new DefaultListModel();
		PlayList = new JList(Model);
		PlayList.setSize(71, 83);
		PlayList.setLocation(338, 25);
		PlayList.setDragEnabled(false);
		PlayList.setFocusable(true);

		PlayList.setTransferHandler(new FileListTransferHandler(PlayList, Model, List));

		pane.setViewportView(PlayList);
		pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		pane.setOpaque(true);
		pane.setSize(130, 217);
		pane.setLocation(405, 0);

		DefaultListSelectionModel m = new DefaultListSelectionModel();
		m.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		PlayList.setSelectionModel(m);
		PlayList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				Song = List.get(PlayList.getSelectedIndex());
				index_of_the_song = PlayList.getSelectedIndex();
				newChoice = true;
				if (Song != null) {
					Deleted = false;
					Play();
				} else {
					mediaPlayer.stop();
					btnPlay.setText("►");
					songIsPlaying = false;
				}
			}
		});

		contentPane.add(pane);

		panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 535, 50);
		panel_1.setBackground(new Color(0, 0, 0, 100));
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		{
			lblNewLabel = new JLabel("Echo Player");
			lblNewLabel.setForeground(Color.WHITE);
			lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
			lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
			lblNewLabel.setBounds(10, 0, 480, 23);

			panel_1.add(lblNewLabel);
		}
		{
			lblNewLabel_2 = new JLabel("");
			lblNewLabel_2.setForeground(Color.WHITE);
			lblNewLabel_2.setVerticalAlignment(SwingConstants.TOP);
			lblNewLabel_2.setFont(new Font("Times New Roman", Font.ITALIC, 16));
			lblNewLabel_2.setBounds(10, 23, 480, 27);
			panel_1.add(lblNewLabel_2);
		}
	}

	void PlayList_Other()

	{
		PlayList.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					if (PlayList.getSelectedIndex() == 0 && List.size() == 0)
						return;

					if (PlayList.getSelectedIndex() >= 0 && mediaPlayer != null) {
						if (List.size() > 1)
							Play_Next();
						else {
							contentPane.setImage(null);
							Deleted = true;
							mediaPlayer.stop();
							btnPlay.setText("►");
							songIsPlaying = false;

							lblNewLabel.setText("Echo Player");
							lblNewLabel_2.setText("");
							contentPane.repaint();

						}
						List.removeElementAt(PlayList.getSelectedIndex());
						Model.removeElementAt(PlayList.getSelectedIndex());
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
	}

	void playButton() {
		btnPlay = new OvalButton("►");
		btnPlay.setFont(new Font("Dialog", Font.BOLD, 26));

		btnPlay.setBounds(240, 30, 54, 54);
		btnPlay.setPreferredSize(new Dimension(130, 28));

		btnPlay.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Play();
			}
		});
		panel.add(btnPlay);
	}

	void Play() {
		
		if (Deleted)
			return;
		if (songIsPlaying && !newChoice) {
			btnPlay.setText("►");
			mediaPlayer.pause();
			songIsPlaying = false;
			return;
		}
		if (newChoice) {
			newFile = true;
			newChoice = false;
			// ==============================================================
			Mp3File toto = null;
			ID3v2 tag = null;
			ID3v1 tag2 = null;
			try {
				toto = new Mp3File(Song.getAbsolutePath());
				if (toto.hasId3v2Tag()) {
					tag = toto.getId3v2Tag();
					// Image
					byte[] imageData = tag.getAlbumImage();
					if (imageData != null)
						Player_Image = ImageIO.read(new ByteArrayInputStream(imageData));
					else
						Player_Image = null;
					// ===========================================
					// Title and Name of the Artist
					lblNewLabel.setText(tag.getTitle());
					lblNewLabel_2.setText(tag.getArtist());
					// ===========================================
				} else if (toto.hasId3v1Tag()) {
					tag2 = toto.getId3v2Tag();
					// It has no Image
					Player_Image = null;
					// Title and Name of the Artist
					lblNewLabel.setText(tag.getTitle());
					lblNewLabel_2.setText(tag.getArtist());
					// ===========================================
				}

			} catch (UnsupportedTagException | InvalidDataException | IOException e1) {
				e1.printStackTrace();
			}

		}

		if (mediaPlayer != null && !newFile) {
			mediaPlayer.play();
			btnPlay.setText("ll");
			songIsPlaying = true;
			return;
		}
		if (mediaPlayer != null)
			mediaPlayer.stop();
		if (List.size() == 1)
			Song = List.get(0);
		fxPanel = new JFXPanel();
		uri = Song.toURI();
		uri.toASCIIString();
		String path = uri.toString();
		media = new Media(path);

		contentPane.setImage(Player_Image);
		mediaPlayer = new MediaPlayer(media);
		contentPane.repaint();

		mediaPlayer.setOnReady(new Runnable() {
			public void run() {

				btnPlay.setText("ll");
				mediaPlayer.play();
				songIsPlaying = true;
				newFile = false;
				Play_Slider.setMaximum((int) media.getDuration().toSeconds());
				Play_Slider.setValue(0);
				Duration t;

				while (!newFile && mediaPlayer.getCurrentTime().toSeconds() < media.getDuration().toSeconds()) {
					// ===================================
					Play_Slider.setValue((int) mediaPlayer.getCurrentTime().toSeconds());
					// ===================================
					t = mediaPlayer.getTotalDuration().subtract(mediaPlayer.getCurrentTime());
					Time_Left.setText(
							Integer.toString((int) t.toMinutes()) + ":" + Integer.toString((int) t.toSeconds() % 60));
					Current_duration.setText(Integer.toString((int) mediaPlayer.getCurrentTime().toMinutes()) + ":"
							+ Integer.toString((int) mediaPlayer.getCurrentTime().toSeconds() % 60));
					// ===================================

				}
				if (!newFile) {
					try {
						Thread.sleep(4000); // 1000 milliseconds is one second.
					} catch (InterruptedException ex) {
						Thread.currentThread().interrupt();
					}
					if (repeat) {
						newChoice = true;
						Play();
					} else {
						index_of_the_song++;
						index_of_the_song = index_of_the_song % (List.size());
						if(!Is_Shuffled)
						Song = List.get(index_of_the_song);
						else 
							Song = List.get(Shuffled_Index.get(index_of_the_song));
						newChoice = true;
						Play();
					}
				} else {
					newFile = true;
					songIsPlaying = false;
					btnPlay.setText("Play");
				}

			}
		});
		eq();
		UpdateVolume();
	}

	void StopButton() {
		btnStop = new RoundButton("▊");
		btnStop.setHorizontalAlignment(SwingConstants.RIGHT);
		btnStop.setHorizontalTextPosition(SwingConstants.CENTER);
		btnStop.setBounds(304, 46, 80, 23);
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mediaPlayer.stop();
				btnPlay.setText("►");
				songIsPlaying = false;
			}
		});
		panel.add(btnStop);
	}

	void Shuffle() {
		Shuffle = new RoundButton("🔀       ");
		Shuffle.setFont(new Font("Segoe UI Symbol", Font.BOLD, 12));
		Shuffle.setSize(75, 23);
		Shuffle.setLocation(114, 46);
		Is_Shuffled=false;
		Shuffle.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				if (!Is_Shuffled) {
					Is_Shuffled=true;
					Shuffled_Index= new Vector<Integer>();
					Shuffled_Index.setSize(List.size());
					Random rnd = new Random();
					int index, a;
					for (int i = 0; i < Shuffled_Index.size(); i++)
						Shuffled_Index.set(i, i);
					
					for (int i = List.size() - 1; i > 0; i--) {
						index = rnd.nextInt(i + 1);
						// Simple swap
						a = Shuffled_Index.elementAt(index);
						Shuffled_Index.set(index, Shuffled_Index.get(i));
						Shuffled_Index.set(i, a);
					}
					Shuffle.Swap_colors();
				}
				else {
					Is_Shuffled=false;
					Shuffle.Swap_colors();
					
				}
			}
		});
		panel.add(Shuffle);

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

		slider = new JSlider();
		slider.setBounds(428, 11, 80, 31);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		lblNewLabel_1 = new JLabel("Volume: " + slider.getValue() + "%");
		lblNewLabel_1.setBounds(428, 3, 80, 14);
		panel.add(lblNewLabel_1);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (mediaPlayer == null)
				{
					Current_Volume= slider.getValue();
					slider.setValue(slider.getValue());
				}
				else
				{
					Current_Volume= slider.getValue();
					mediaPlayer.setVolume((double) (slider.getValue() / 100.0));
				}
				lblNewLabel_1.setText("Volume: " + slider.getValue() + "%");
			}
		});
		panel.add(slider);

	}
	
	void UpdateVolume() {
		slider.setValue(Current_Volume);
		mediaPlayer.setVolume((double) (slider.getValue() / 100.0));
	}
	
	void Mute_volume() {
		
		Mute = new RoundButton("    🔊");
		Mute.setFont(new Font("Segoe UI Symbol", Font.BOLD, 12));
		Mute.setSize(64, 23);
		Mute.setLocation(355, 46);
		Mute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(slider.getValue()>0)
				{
					Mute.setText("    🔇");
					Previous_Volume_Value= slider.getValue();
					slider.setValue(0);
					lblNewLabel_1.setText("Volume: " + slider.getValue() + "%");
					mediaPlayer.setVolume(0);
					Mute.Swap_colors();
				}
				else
				{
					Mute.setText("    🔊");				
					slider.setValue(Previous_Volume_Value);
					lblNewLabel_1.setText("Volume: " + slider.getValue() + "%");
					mediaPlayer.setVolume((double) (slider.getValue() / 100.0));
					Mute.Swap_colors();
				}
			}
		});
		panel.add(Mute);
	}


	void ProgAndSeek() {
		// =============================================================================
		// Play Slider Design
		UIDefaults sliderDefaults = new UIDefaults();

		sliderDefaults.put("Slider.thumbWidth", 20);
		sliderDefaults.put("Slider.thumbHeight", 20);
		sliderDefaults.put("Slider:SliderThumb.backgroundPainter", new Painter<JComponent>() {
			public void paint(Graphics2D g, JComponent c, int w, int h) {
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g.setStroke(new BasicStroke(2f));
				// ========================================
				System.setProperty("myColor1", "0XFF0000");
				g.setColor(Color.getColor("myColor1"));
				// ========================================
				g.fillOval(1, 1, w - 3, h - 3);
				System.setProperty("myColor2", "0XCCCCCC");
				g.setColor(Color.getColor("myColor2"));
				g.drawOval(1, 1, w - 3, h - 3);
			}
		});
		sliderDefaults.put("Slider:SliderTrack.backgroundPainter", new Painter<JComponent>() {
			public void paint(Graphics2D g, JComponent c, int w, int h) {
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g.setStroke(new BasicStroke(2f));
				g.setColor(Color.darkGray);
				g.fillRoundRect(0, 6, w - 1, 8, 8, 8);
				g.setColor(Color.WHITE);
				g.drawRoundRect(0, 6, w - 1, 8, 8, 8);
			}
		});
		Play_Slider = new JSlider();
		Play_Slider.setBounds(58, 7, 300, 23);

		Play_Slider.putClientProperty("Nimbus.Overrides", sliderDefaults);
		Play_Slider.putClientProperty("Nimbus.Overrides.InheritDefaults", false);
		// =====================================================================================
		// Seeking for Play_Slider When clicking on the Slider Bar
		Play_Slider.setValue(0);

		Play_Slider.setVisible(true);
		// ======================================================================================
		// Seek with the media player when dragging the Play_Slider
		Play_Slider.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int mouseX = e.getX();
				int value = (int) Math
						.round(((double) mouseX / (double) Play_Slider.getWidth()) * Play_Slider.getMaximum());
				Play_Slider.setValue(value);

				Duration t;
				t = mediaPlayer.getTotalDuration();
				mediaPlayer.seek(t.multiply(value / (double) Play_Slider.getMaximum()));
			}
		});
		// ======================================================================================
		panel.add(Play_Slider);
		google();
	}

	void google() {
		btnGoogleIt_1 = new RoundButton("Google it!");
		btnGoogleIt_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String f1 = "", f2 = "", f3 = "";
					if (mediaPlayer == null) {
						JOptionPane.showMessageDialog(null, "Please play a song :)", "Info message",
								JOptionPane.PLAIN_MESSAGE);
						return;
					}
					for (Entry<String, Object> entry : media.getMetadata().entrySet()) {
						if (entry.getKey() == "album") {
							f1 = entry.getValue().toString();
							f1 = f1.replaceAll(" ", "+");
							f1 = f1.replaceAll("[^A-Za-z0-9()\\[\\]+]", "");
						} else if (entry.getKey() == "artist") {
							f2 = entry.getValue().toString();
							f2 = f2.replaceAll(" ", "+");
							f2 = f2.replaceAll("[^A-Za-z0-9()\\[\\]+]", "");
						} else if (entry.getKey() == "title") {
							f3 = entry.getValue().toString();
							f3 = f3.replaceAll(" ", "+");
							f3 = f3.replaceAll("[^A-Za-z0-9()\\[\\]+]", "");
						}
					}
					if (f1 != "")
						Desktop.getDesktop().browse(new URI("http://www.google.com/search?q=" + f1));
					else if (f2 != "")
						Desktop.getDesktop().browse(new URI("http://www.google.com/search?q=" + f2));
					else if (f3 != "")
						Desktop.getDesktop().browse(new URI("http://www.google.com/search?q=" + f3));
					else
						JOptionPane.showMessageDialog(null, "Unfortunately...no data for this song", "Info message",
								JOptionPane.PLAIN_MESSAGE);

				} catch (IOException | URISyntaxException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnGoogleIt_1.setBounds(428, 76, 80, 23);
		panel.add(btnGoogleIt_1);

		Time_Left = new JLabel("");
		Time_Left.setBounds(367, 13, 53, 14);
		panel.add(Time_Left);

		Current_duration = new JLabel("");
		Current_duration.setBounds(10, 13, 46, 14);
		panel.add(Current_duration);

	}

	// =================[[Next And Previous Songs]]=======================
	void Play_Next() {
		index_of_the_song++;
		index_of_the_song = index_of_the_song % (List.size());
		if(!Is_Shuffled)
			Song = List.get(index_of_the_song);
			else 
				Song = List.get(Shuffled_Index.get(index_of_the_song));
		newChoice = true;
		Play();

	}

	void Play_Previous() {
		index_of_the_song--;
		if (index_of_the_song < 0)
			index_of_the_song = List.size() - 1;
		if(!Is_Shuffled)
			Song = List.get(index_of_the_song);
			else 
				Song = List.get(Shuffled_Index.get(index_of_the_song));
		newChoice = true;
		Play();
	}

	void Forward_And_Rewind() {
		JButton Forward = new RoundButton("      ►►");
		Forward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Play_Next();
			}
		});
		JButton Rewind = new RoundButton("◄◄   ");
		Rewind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Play_Previous();
			}
		});
		Forward.setBounds(265, 46, 80, 23);
		Rewind.setBounds(187, 46, 75, 23);
		panel.add(Forward);
		panel.add(Rewind);

	}

	// ====================================================================================
	void Repeat() {
		RoundButton Repeat = new RoundButton("🔁         ");
		Repeat.setFont(new Font("Segoe UI Symbol", Font.BOLD, 12));
		Repeat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (repeat) {
					repeat = false;
					Repeat.setFont(new Font("Segoe UI Symbol", Font.BOLD, 12));
					Repeat.setText("🔁         ");
					Repeat.Swap_colors();
				} else {
					repeat = true;
					Repeat.setFont(new Font("Segoe UI Symbol", Font.BOLD, 16));
					Repeat.setText("1        ");
					Repeat.Swap_colors();
				}
			}
		});
		Repeat.setBounds(150, 46, 80, 23);
		panel.add(Repeat);
	}

	void eqWindow() {
		p = new JPanel();
		p.setSize(50, 50);
		bands = new JSlider[10];
		labels = new JLabel[10];
		JLabel labels2[] = new JLabel[10];
		int space = 0;

		for (int i = 0; i < 10; i++) {

			bands[i] = new JSlider(JSlider.VERTICAL);
			labels[i] = new JLabel("100");
			labels2[i] = new JLabel("50");
			// ========================================
			labels[i].setBounds(120 + space, -40, 30, 90);
			labels2[i].setBounds(120 + space, 3, 30, 90);
			bands[i].setBounds(120 + space, 11, 30, 90);

			bands[i].setMaximum(12);
			bands[i].setMinimum(-12);
			bands[i].setValue(0);
			space += 30;
			// =========================================
			p.add(bands[i]);
			p.add(labels[i]);
			p.add(labels2[i]);
		}
		control_panel.add(p);
		p.setLayout(null);
		JButton btnNewButton_3 = new RoundButton("Play Buttons");
		btnNewButton_3.setBounds(10, 11, 111, 23);
		p.add(btnNewButton_3);
		
		JLabel Mode_Name = new JLabel("Normal");
		Mode_Name.setFont(new Font("Tekton Pro Cond", Font.BOLD, 15));
		Mode_Name.setBounds(455, 11, 66, 19);
		p.add(Mode_Name);
		
		JButton Next_Mode = new RoundButton("⇒");
		Next_Mode.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
		Next_Mode.setBounds(475, 34, 46, 23);
		p.add(Next_Mode);
		
		JButton Previous_Mode = new RoundButton("⇐");
		Previous_Mode.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
		Previous_Mode.setBounds(424, 34, 46, 23);
		p.add(Previous_Mode);
		
		Next_Mode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EqualizerPresets temp =new EqualizerPresets();	
				Mode_index++;
				temp.next_Mode(Mode_index, bands, mediaPlayer, Mode_Name);
				Mode_index%=18;
			}
		});
        Previous_Mode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EqualizerPresets temp =new EqualizerPresets();	
				Mode_index--;
				temp.previous_Mode(Mode_index, bands, mediaPlayer, Mode_Name);
			}
		});
		btnNewButton_2 = new RoundButton("Equalizer");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				control_panel.next(JPanelsSliding.direct.up);
			}
		});
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				control_panel.next(JPanelsSliding.direct.Dowun);
			}
		});
		btnNewButton_2.setBounds(10, 45, 89, 23);
		panel.add(btnNewButton_2);

	}

	void eq() {
		for (int i = 0; i < 10; i++) {
			double p = bands[i].getValue();
			mediaPlayer.getAudioEqualizer().getBands().get(i).setGain(p);
		}
		for (int i = 0; i < 10; i++) {
			final int index = i;
			bands[i].addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent arg0) {
					double p = bands[index].getValue();
					
					mediaPlayer.getAudioEqualizer().getBands().get(index).setGain(p);
				}
			});
		}
	}
}