package view;

import controller.DungeonMouse;
import controller.Icontroller;
import dungeon.ReadonlyDungeon;
import dungeon.Turn;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * This class represents a dungeon GUI view.
 * @author Fan Wu
 * @field model      The input model
 * @field controller The controller
 */
public class DungeonGuiView extends JFrame implements DungeonView, ActionListener {
  private ReadonlyDungeon model;
  private Icontroller controller;
  
  private JMenuBar menuBar;
  
  private JMenu fileMenu;
  private JMenuItem setGame;
  private JMenuItem setGameTwo;
  private JMenuItem newGame;
  private JMenuItem newSameGame;
  private JMenuItem close;
  private JMenuItem pickupMenu;
  private JMenuItem shootMenu;
  
  private DungeonPanel gamePanel;
  
  private JPanel buttonPanel;
  
  private JButton exit;
  
  private JTextField input;
  private JButton set;
  private JButton move;
  private JButton shoot;
  private JButton pickup;
  
  private JLabel playeraHint;
  private JLabel playerbHint;
  
  private JPanel buttonPanel2;
  
  private boolean gameMode;
  
  /**
   * Constructor.
   * @param model The input read only dungeon model
   */
  public DungeonGuiView(ReadonlyDungeon model) {
    super("Dungeon Game");
    
    this.model = model;
    this.setSize(500, 550);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    
    this.menuBar = new JMenuBar();
    this.menuBar.setName("MenuBar");
    this.setJMenuBar(this.menuBar);
    
    // the following add the menu bar as a normal component
    //this.add(this.menuBar);
    this.fileMenu = new JMenu("File");
    this.menuBar.setName("File");
    this.menuBar.add(this.fileMenu);
        
    this.setGame = new JMenuItem("Set Game");
    this.setGame.setName("Set");
    this.setGame.addActionListener(this);
    this.fileMenu.add(this.setGame);
    
    this.setGameTwo = new JMenuItem("Set 2 player");
    this.setGameTwo.setName("Set 2 player");
    this.setGameTwo.addActionListener(this);
    this.fileMenu.add(this.setGameTwo);
    
    this.newGame = new JMenuItem("New Game");
    this.newGame.setName("New Game");
    this.newGame.addActionListener(this);
    this.fileMenu.add(this.newGame);
    
    this.newSameGame = new JMenuItem("New Same Game");
    this.newSameGame.setName("New Same Game");
    this.newSameGame.addActionListener(this);
    this.fileMenu.add(this.newSameGame);
    
    this.close = new JMenuItem("Close");
    this.close.setName("Close");
    // this code enables the button to work, it calls actionPerformed
    this.close.addActionListener(this);
    this.fileMenu.add(this.close);
    
    this.pickupMenu = new JMenuItem("Pick up");
    this.pickupMenu.setName("Pick up");
    this.pickupMenu.addActionListener(this);
    this.menuBar.add(this.pickupMenu);
    
    this.shootMenu = new JMenuItem("Shoot");
    this.shootMenu.setName("Shoot");
    this.shootMenu.addActionListener(this);
    this.menuBar.add(this.shootMenu);
    
    this.buttonPanel = new JPanel();
    this.buttonPanel.setSize(600, 30);
    
    this.playeraHint = new JLabel("The");
    this.buttonPanel.add(this.playeraHint);
    
    this.exit = new JButton("Exit");
    //this.buttonPanel.add(this.exit);
    this.exit.addActionListener(this); 
    this.exit.setName("Close");
    
    this.input = new JTextField(10);
    this.buttonPanel.add(this.input);
    
    this.shoot = new JButton("Shoot");
    this.shoot.setName("Shoot");
    this.shoot.addActionListener(this);
    //this.buttonPanel.add(this.shoot);
    
    this.pickup = new JButton("Pick up");
    this.pickup.setName("Pick up");
    this.pickup.addActionListener(this);
    //this.buttonPanel.add(this.pickup);
    
        
    this.add(this.buttonPanel);
    this.buttonPanel.setBounds(0, 0, 600, 30);
    this.buttonPanel.setVisible(true);

    this.buttonPanel2 = new JPanel();
    this.buttonPanel2.setSize(800, 30);
    
    this.playerbHint = new JLabel("Player B");
    this.buttonPanel2.add(this.playerbHint);
    
    this.add(this.buttonPanel2);
    this.buttonPanel2.setBounds(0, 30, 600, 30);
    this.buttonPanel2.setVisible(true);
    
    this.gamePanel = new DungeonPanel();
    this.gamePanel.setBounds(0, 50, 500, 500);
    this.add(this.gamePanel);
    

    
    makeVisible();
  }

  @Override
  public void addClickListener(Icontroller listener) {
    this.controller = listener;
    this.gamePanel.addMouseListener(new DungeonMouse(listener));
    
  }

  @Override
  public void refresh() {
    this.gamePanel.setDungeon(this.model);
    if (this.model.getMode()) {
      this.playeraHint
          .setText("Player A (Red), Hint: " + this.model.detectMonster(
                   this.model.getLastNodeFromPath())
                   + this.model.checkCurrentNode(this.model.getLastNodeFromPath()));
    } else {
      if (this.model.getTurn() == Turn.A) {
        this.playeraHint
            .setText("Player A (Red), Hint: " + this.model.detectMonster(
                     this.model.getLastNodeFromPath())
                     + this.model.checkCurrentNode(this.model.getLastNodeFromPath()));
        this.playerbHint.setText("Current turn: " + this.model.getTurn().toString());
        
            
      } else {
        this.playerbHint
            .setText("Player B (Blue), Hint:" + this.model.detectMonster(
                     this.model.getLastNodeFromPath())
                     + this.model.checkCurrentNode(this.model.getLastNodeFromPath()) 
                     + "Current turn: "
                     + this.model.getTurn().toString());
      }
    }

    this.repaint();
  }

  @Override
  public void makeVisible() {
    this.setVisible(true); 
  }

  @Override
  public void setModel(ReadonlyDungeon model) {
    this.model = model;
    this.gamePanel.setDungeon(model);
    this.gamePanel.repaint();
    
  }

  @Override
  public void showPopupMessage(String message) {
    JOptionPane.showMessageDialog(this, message);
    
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() instanceof Component) {
      Component c = (Component) e.getSource();
      if ("Close".equals(c.getName())) {
        System.exit(0);
      }
      if ("Set 2 player".equals(c.getName())) {
        // if we choose 2 player mode, then the gameMode is false
        this.gameMode = false;
        String[] inputs = this.input.getText().split(" ");
        checkModelInputs(inputs);
        
        // get the parameters of dungeon model
        int row = Integer.parseInt(inputs[0]);
        int col = Integer.parseInt(inputs[1]);
        int inter = Integer.parseInt(inputs[2]);
        boolean wrap = Boolean.parseBoolean(inputs[3]);
        int percent = Integer.parseInt(inputs[4]);
        int monNum = Integer.parseInt(inputs[5]);
        this.controller.buildNewGame(row, col, inter, wrap, percent, monNum, this.gameMode);
        this.input.setText("");
      }
      if ("Set".equals(c.getName())) {
        this.gameMode = true;
        String[] inputs = this.input.getText().split(" ");
        
        checkModelInputs(inputs);
        
        // get the parameters of dungeon model
        int row = Integer.parseInt(inputs[0]);
        int col = Integer.parseInt(inputs[1]);
        int inter = Integer.parseInt(inputs[2]);
        boolean wrap = Boolean.parseBoolean(inputs[3]);
        int percent = Integer.parseInt(inputs[4]);
        int monNum = Integer.parseInt(inputs[5]);
        
        this.controller.buildNewGame(row, col, inter, wrap, percent, monNum, this.gameMode);
        
        this.input.setText("");
        
      }
      if ("New Game".equals(c.getName())) {
        this.controller.restartGame();
        this.refresh();
      }
      if ("New Same Game".equals(c.getName())) {
        this.controller.restartSameGame();
        this.refresh();
      }
      if ("Shoot".equals(c.getName())) {
        String[] inputs = this.input.getText().split(" ");
        int distance = 0;
        
        try {
          // check if the input is valid
          if (inputs.length != 2) {
            throw new IllegalArgumentException("The input string is invalid. Should give "
                                               + "the shooting distance and direction.");
          }
          Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
          for (int i = 0; i < inputs.length; i++) {
            // the first input should be an int, the second should be n, w, e, s
            if (!pattern.matcher(inputs[0]).matches()) {
              throw new IllegalArgumentException("The first input should be an integer.");
            }
            if (pattern.matcher(inputs[1]).matches()) {
              throw new IllegalArgumentException("The second input should be a character.");
            }
          }
          // take the input and turn it into an int and string
          distance = Integer.parseInt(inputs[0]);
          if (!("s".equals(inputs[1].toLowerCase()) || "n".equals(inputs[1].toLowerCase()) 
              || "e".equals(inputs[1].toLowerCase()) || "w".equals(inputs[1].toLowerCase()))) {
            throw new IllegalArgumentException("The second input indicates the shooting direction,"
                                               + " it should be n, s, w or e.");
          }
        } catch (IllegalArgumentException iae) {
          this.showPopupMessage(iae.getMessage());
        }
        
        this.controller.shoot(distance, inputs[1]);
      
        this.input.setText("");
      
      }
      if ("Pick up".equals(c.getName())) {
        this.controller.pickup();
      }
      
    }
    
    
  }
  
  /**
   * Check whether the input text is valid. 
   * @param inputs The input text
   */
  private void checkModelInputs(String[] inputs) {
    try {
      // check if the input is valid
      if (inputs.length != 6) {
        throw new IllegalArgumentException("The input string is invalid. Enter the row, "
                                           + "column, "
                                           + "interconnectivity, wrapping or not, percentage"
                                           + " of "
                                           + "treasure and number of monsters.");
      }
      Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
      for (int i = 0; i < inputs.length; i++) {
        if (i != 3) {  
          // the fourth input is true or false, others should be int
          if (!pattern.matcher(inputs[i]).matches()) {
            throw new IllegalArgumentException("The row, column, interconnectivity, percentage "
                                               + "of treasure and number of monsters should be "
                                               + "integers. The fourth input should be true"
                                               + " or false");
          }
        }
      }
    } catch (IllegalArgumentException iae) {
      this.showPopupMessage(iae.getMessage());
      this.input.setText("");
      return;
    }
  }
  
}
