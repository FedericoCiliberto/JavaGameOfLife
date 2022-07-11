package gol;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;

public class MyFrame extends JFrame implements ActionListener{
	
	int dim=10;
	List<MyButton> buttonsList=new LinkedList<>();
	Tabella tabella;
	JPanel campoDiGioco;
	JPanel controls;
	JButton pause;
	JButton start;
	boolean running=false;
	Timer timer;
	int time;
	ImageIcon playIcon=new ImageIcon("play.png");
	ImageIcon pauseIcon=new ImageIcon("pause.png");
	ImageIcon icon=new ImageIcon("f.jfif");
	JMenuItem setDimMenuItem;
	JMenuItem setTimerMenuItem;
	
	
	MyFrame(){
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//this.setSize(new Dimension(600,700));
		this.setTitle("Game Of Life");
		this.setResizable(false);
		this.setIconImage(icon.getImage());
		campoDiGioco=new JPanel();
		campoDiGioco.setLayout(new GridLayout(dim,dim));
		campoDiGioco.setPreferredSize(new Dimension(570,570));
		for(int i=0;i<dim;i++) {
			for(int j=0;j<dim;j++) {
				buttonsList.add(new MyButton(j,i));
			}
		}
		buttonsList.stream().forEach(b->campoDiGioco.add(b));
		buttonsList.stream().forEach(b->b.setFocusable(false));
		buttonsList.stream().forEach(b->b.addActionListener(this));
		this.add(campoDiGioco,BorderLayout.SOUTH);
		tabella=new Tabella(dim);
		time=300;
		controls=new JPanel();
		controls.setPreferredSize(new Dimension(600,100));	
		pause=new JButton();
		start=new JButton();
		pause.setIcon(pauseIcon);
		start.setIcon(playIcon);
		pause.setFocusable(false);
		start.setFocusable(false);
		pause.addActionListener(e->{
			if(e.getSource().equals(pause)) {
				running=false;
				timer.stop();
			}
		});
		start.addActionListener(e->{
			if(e.getSource().equals(start)) {
				running=true;
				timer.start();
			}
		});
		pause.setPreferredSize(new Dimension(60,60));
		start.setPreferredSize(new Dimension(60,60));
		controls.add(pause);
		controls.add(start);
		this.add(controls);
		
		timer=new Timer(time,e->{
			if(running) {
				tabella.evolve();
				buttonsList.stream().forEach(b->b.refresh());
			}
		});
		JMenuBar menuBar=new JMenuBar();
		JMenu optionsMenu=new JMenu("Options");
		JMenu configurationsMenu=new JMenu("Cool configurations (soon..)");
		menuBar.add(optionsMenu);
		menuBar.add(configurationsMenu);
		setDimMenuItem= new JMenuItem("Set dimensions");
		setTimerMenuItem=new JMenuItem("Set timer speed");
		setDimMenuItem.addActionListener(this);
		setTimerMenuItem.addActionListener(this);
		optionsMenu.add(setDimMenuItem);
		optionsMenu.add(setTimerMenuItem);
		this.setJMenuBar(menuBar);
		this.pack();
		this.setVisible(true);
		repaint();
	}
	
	MyFrame(int dim){
		this.dim=dim;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(new Dimension(600,700));
		this.setTitle("Game Of Life");
		this.setResizable(false);
		campoDiGioco=new JPanel();
		campoDiGioco.setLayout(new GridLayout(dim,dim));
		campoDiGioco.setPreferredSize(new Dimension(570,570));
		for(int i=0;i<dim;i++) {
			for(int j=0;j<dim;j++) {
				buttonsList.add(new MyButton(j,i));
			}
		}
		buttonsList.stream().forEach(b->campoDiGioco.add(b));
		buttonsList.stream().forEach(b->b.setFocusable(false));
		buttonsList.stream().forEach(b->b.addActionListener(this));
		this.add(campoDiGioco,BorderLayout.SOUTH);
		tabella=new Tabella(dim);
		time=1000;
		controls=new JPanel();
		controls.setPreferredSize(new Dimension(600,100));	
		pause=new JButton();
		start=new JButton();
		pause.setIcon(pauseIcon);
		start.setIcon(playIcon);
		pause.setFocusable(false);
		start.setFocusable(false);
		pause.addActionListener(e->{
			if(e.getSource().equals(pause)) {
				running=false;
				timer.stop();
			}
		});
		start.addActionListener(e->{
			if(e.getSource().equals(start)) {
				running=true;
				timer.start();
			}
		});
		pause.setPreferredSize(new Dimension(60,60));
		start.setPreferredSize(new Dimension(60,60));
		controls.add(pause);
		controls.add(start);
		this.add(controls);
		
		timer=new Timer(time,e->{
			if(running) {
				tabella.evolve();
				buttonsList.stream().forEach(b->b.refresh());
			}
		});
		JMenuBar menuBar=new JMenuBar();
		JMenu optionsMenu=new JMenu("Options");
		JMenu configurationsMenu=new JMenu("Cool configurations");
		menuBar.add(optionsMenu);
		menuBar.add(configurationsMenu);
		setDimMenuItem= new JMenuItem("Set dimensions");
		setTimerMenuItem=new JMenuItem("Set timer speed");
		setDimMenuItem.addActionListener(this);
		setTimerMenuItem.addActionListener(this);
		optionsMenu.add(setDimMenuItem);
		optionsMenu.add(setTimerMenuItem);
		this.setJMenuBar(menuBar);
		this.setVisible(true);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		MyButton buttonClicked=buttonsList.stream().filter(b->e.getSource().equals(b))
		.findFirst().orElseGet(()->{
			return null;
		});
		//System.out.println(buttonClicked.getY()+" "+buttonClicked.getX());
		if(buttonClicked!=null) {
			tabella.changeState(buttonClicked.getY(),buttonClicked.getX());
			buttonClicked.refresh();
		}else{ 
			if(e.getSource().equals(setDimMenuItem)) {
				try {
					dim=Integer.parseInt(JOptionPane.showInputDialog("Inserisci dimensione mondo"));
					new MyFrame(dim);
					this.setVisible(false);
					this.dispose();
				}catch(NumberFormatException exc) {
			
				}
			
			}else if(e.getSource().equals(setTimerMenuItem)) {
				TimerSetter timerSetter=new TimerSetter();
				
			}
		}
		
	}
	
	
	class MyButton extends JButton{
		int x;
		int y;
		
		MyButton(int x,int y){
			this.x=x;
			this.y=y;
			this.setBackground(Color.white);
		}
		
		public void refresh() {
			if(tabella.getStateOfOneCell(x, y)) {
				this.setBackground(Color.green);
			}else {
				this.setBackground(Color.white);
			}
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}
		
	}
	
	class TimerSetter extends JFrame{
		
		TimerSetter(){
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setSize(800,300);
			this.setTitle("Set timer");
			this.setResizable(false);
			this.setIconImage(icon.getImage());
			JSlider slider=new JSlider(150,1000);
			slider.setMinorTickSpacing(10);
			slider.setMajorTickSpacing(100);
			slider.setPaintTicks(true);
			slider.setPaintLabels(true);
			slider.setValue(time);
			JLabel label=new JLabel();
			label.setText(time+" ms");
			label.setFont(new Font("Times New Roman",Font.PLAIN,50));
			label.setHorizontalAlignment(JLabel.CENTER);
			label.setVerticalAlignment(JLabel.CENTER);
			slider.addChangeListener(e->{
				label.setText(slider.getValue()+" ms");
				time=slider.getValue();
			});
			JButton confirm=new JButton("Ok");
			confirm.addActionListener(e->{
				if(e.getSource().equals(confirm)) {
					time=slider.getValue();
					timer.setDelay(time);
					this.setVisible(false);
					this.dispose();
				}
			});
			confirm.setPreferredSize(new Dimension(500,50));
			confirm.setFocusable(false);
			
			this.add(label,BorderLayout.CENTER);
			this.add(slider,BorderLayout.NORTH);
			this.add(confirm,BorderLayout.SOUTH);
			this.setVisible(true);
		}
	}


}
