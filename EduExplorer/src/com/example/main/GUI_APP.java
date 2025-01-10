import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileReader;
import java.io.BufferedReader;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.example.webscraping.Trie;
import com.example.webscraping.Task_Method;
import java.util.concurrent.atomic.AtomicBoolean;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;



import java.util.List;
import java.util.Map;
import java.util.Set;

public class GUI_APP extends JFrame {

    // Declare components
    private JButton linkedInButton;
    private JButton w3SchoolButton;
    private JButton kaggleButton;
    private JButton roadMapButton;
    private JButton javaTpointButton;
    private JTextField textField1;
    private JButton searchButton;
    private JComboBox<String> comboBox1;
    private JComboBox comboBox2;

    //private JButton enterButton;
    private JButton clearButton;
    private JButton homeButtonButton;
    private JButton infoButtonButton;
    private JPanel TopPanel;
    private JLabel welcomeLabel;
    private JPanel buttonPanel;
    private JPanel MainbuttonPanel;
    private JPanel OptionPanel;
    private JPanel CouresFreqPanel;
    private JPanel searchPanel;
    private JPanel topfreqPanel;
    private JLabel comboBoxLabel1;
    private JLabel comboBoxLabel2;
    private JLabel suggestionLabel; // To display suggestions
    private JLabel Toplabel; // To display suggestions
    public static Trie trie = new Trie();
    JComboBox<String> suggestionsDropdown = new JComboBox<>();
    String buttonActivited = null;
    private JTable table;

    String filePath = "/Users/apurvarajput/IdeaProjects/EduExplorer/segregated_courses_data_large.csv"; // Replace with your actual file path
    List<String[]> csvData = com.example.webscraping.Task_Method.loadCSVData(filePath);

    // Create a main panel with vertical BoxLayout
    JPanel mainPanel = new JPanel();


    public GUI_APP() {
        // Set frame properties
        setTitle("EduExplorer - Learn. Grow. Lead.");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Set<String> vocabulary = com.example.webscraping.Task_Method.buildVocabulary(csvData);

        for (String[] row : csvData)
        {
            if(row.length > 1) { //add a check to handle potential exceptions if a row is malformed.
                trie.insert(row[1].toLowerCase()); // Insert course names into the Trie
            }
        }

        JSpinner spinner1 = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
        spinner1.setPreferredSize(new Dimension(100, 40));
        // Initialize components
        textField1 = new JTextField(100);

        suggestionLabel = new JLabel("Suggestions will appear here");
        suggestionLabel.setVerticalAlignment(SwingConstants.TOP);

        // Load and blur the background image
        BufferedImage backgroundImage = blurImage(loadBackgroundImage());

        // Create background panel
        BackgroundPanel bgPanel = new BackgroundPanel(backgroundImage);
        bgPanel.setLayout(new BorderLayout());
        setContentPane(bgPanel);  // Set as content pane

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);  // Make the main panel transparent

// Initialize components with a sleek and modern design
        welcomeLabel = new JLabel("<html>" +
                "<h1 style='color: #1E3A8A; font-family: \"Roboto\", sans-serif; font-size: 40px; text-align: center; font-weight: 600;'>" +
                "Welcome to EduExplorer: Learn. Grow. Lead.</h1>" +
                "<p style='color: #333333; font-size: 22px; font-family: \"Roboto\", sans-serif; text-align: center; font-weight: 300;'>" +
                "Unlock a world of knowledge at your fingertips!<br>Your journey to success starts here." +
                "</p>" +
                "</html>");

// Center the label text
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

// Transparent background for a clean, professional look
        welcomeLabel.setOpaque(false);  // Transparent background
        welcomeLabel.setBackground(new Color(0, 0, 0, 0));  // Fully transparent background

// Apply elegant font style, making the text sleek but readable
        welcomeLabel.setFont(new Font("Roboto", Font.PLAIN, 36));  // Sleek modern font
        welcomeLabel.setForeground(new Color(30, 58, 138));  // Dark blue color for trust and professionalism

// Adding padding for a balanced look
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));  // Added padding for symmetry

// Optional: Soft shadow effect for the title
        welcomeLabel.setText("<html>" +
                "<h1 style='color: #1E3A8A; font-family: \"Roboto\", sans-serif; font-size: 40px; text-align: center; font-weight: 700; text-shadow: 1px 1px 3px rgba(0, 0, 0, 0.2);'>" +
                "Welcome to EduExplorer: Learn. Grow. Lead.</h1>" +
                "<p style='color: #000000; font-size: 22px; font-family: \"Roboto\", sans-serif; text-align: center; font-weight: 700;'>" +
                "Unlock a world of knowledge at your fingertips!<br>Your journey to success starts here." +
                "</p>" +
                "</html>");

        comboBox1 = new JComboBox<>(new String[]{"Search from the database", "Course Frequency Analysis","Search for courses that match a specific price","Search for courses that match a specific hours"});
        comboBox2 = new JComboBox<>(new String[]{"Display the most frequent course names", "Search for a specific course name's frequency"});

        linkedInButton = new JButton("LinkedIn");
        w3SchoolButton = new JButton("W3School");
        kaggleButton = new JButton("Kaggle");
        roadMapButton = new JButton("Roadmap");
        javaTpointButton = new JButton("JavaTpoint");

        textField1 = new JTextField(40);
        textField1.setPreferredSize(new Dimension(300, 40));
        searchButton = new JButton("Search");

        comboBoxLabel1 = new JLabel("Please choose an option:");
        comboBoxLabel2 = new JLabel("Please choose an option:");

        // Create a panel for buttons and add all components
        OptionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));  // Buttons in line horizontally
        OptionPanel.setOpaque(false);  // Make panel transparent
        OptionPanel.add(comboBoxLabel1);
        OptionPanel.add(comboBox1);

        // Set font for JLabel
        comboBoxLabel1.setFont(new Font("Arial", Font.BOLD, 14));

// Set font for JButton
        linkedInButton.setPreferredSize(new Dimension(200, 60));
        w3SchoolButton.setPreferredSize(new Dimension(200, 60));
        kaggleButton.setPreferredSize(new Dimension(200, 60));
        roadMapButton.setPreferredSize(new Dimension(200, 60));
        javaTpointButton.setPreferredSize(new Dimension(200, 60));


        // Set the initial background color of the button
        linkedInButton.setBackground(new Color(30, 58, 138));  // Dark blue background
        linkedInButton.setForeground(Color.WHITE);  // White text for contrast

        linkedInButton.setOpaque(true);
        linkedInButton.setBorderPainted(false);
        // Enable rollover effect (color change when mouse hovers)
        linkedInButton.setRolloverEnabled(true);


        searchButton.setBackground(new Color(30, 58, 138));
        searchButton.setForeground(Color.WHITE);
        searchButton.setOpaque(true);
        searchButton.setBorderPainted(false);

        clearButton.setBackground(new Color(30, 58, 138));
        clearButton.setForeground(Color.WHITE);
        clearButton.setOpaque(true);
        clearButton.setBorderPainted(false);

        w3SchoolButton.setBackground(new Color(30, 58, 138));  // Dark blue background
        w3SchoolButton.setForeground(Color.WHITE);  // White text for contrast

        w3SchoolButton.setOpaque(true);
        w3SchoolButton.setBorderPainted(false);
        // Enable rollover effect (color change when mouse hovers)
        w3SchoolButton.setRolloverEnabled(true);

        kaggleButton.setBackground(new Color(30, 58, 138));  // Dark blue background
        kaggleButton.setForeground(Color.WHITE);  // White text for contrast

        kaggleButton.setOpaque(true);
        kaggleButton.setBorderPainted(false);
        // Enable rollover effect (color change when mouse hovers)
        kaggleButton.setRolloverEnabled(true);

        roadMapButton.setBackground(new Color(30, 58, 138));  // Dark blue background
        roadMapButton.setForeground(Color.WHITE);  // White text for contrast

        roadMapButton.setOpaque(true);
        roadMapButton.setBorderPainted(false);
        // Enable rollover effect (color change when mouse hovers)
        roadMapButton.setRolloverEnabled(true);

        javaTpointButton.setBackground(new Color(30, 58, 138));  // Dark blue background
        javaTpointButton.setForeground(Color.WHITE);  // White text for contrast

        javaTpointButton.setOpaque(true);
        javaTpointButton.setBorderPainted(false);
        // Enable rollover effect (color change when mouse hovers)
        javaTpointButton.setRolloverEnabled(true);


        linkedInButton.setFocusPainted(false); // Remove focus border
        w3SchoolButton.setFocusPainted(false); // Remove focus border
        kaggleButton.setFocusPainted(false); // Remove focus border
        roadMapButton.setFocusPainted(false); // Remove focus border
        javaTpointButton.setFocusPainted(false); // Remove focus border

        // Create a flag to track the clicked state
        final boolean[] isClicked = {false};

        // Shared state to keep track of the currently clicked button
        JButton[] clickedButton = {null};

// Add mouse listener for LinkedIn button
        linkedInButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (clickedButton[0] != linkedInButton) {
                    linkedInButton.setBackground(new Color(70, 130, 190)); // Light blue on hover
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (clickedButton[0] != linkedInButton) {
                    linkedInButton.setBackground(new Color(30, 58, 138)); // Dark blue
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                handleButtonClick(linkedInButton, clickedButton);
            }
        });

// Add mouse listener for W3School button
        w3SchoolButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (clickedButton[0] != w3SchoolButton) {
                    w3SchoolButton.setBackground(new Color(70, 130, 190)); // Light blue on hover
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (clickedButton[0] != w3SchoolButton) {
                    w3SchoolButton.setBackground(new Color(30, 58, 138)); // Dark blue
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                handleButtonClick(w3SchoolButton, clickedButton);
            }
        });

// Add mouse listener for Kaggle button
        kaggleButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (clickedButton[0] != kaggleButton) {
                    kaggleButton.setBackground(new Color(70, 130, 190)); // Light blue on hover
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (clickedButton[0] != kaggleButton) {
                    kaggleButton.setBackground(new Color(30, 58, 138)); // Dark blue
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                handleButtonClick(kaggleButton, clickedButton);
            }
        });

// Add mouse listener for Roadmap button
        roadMapButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (clickedButton[0] != roadMapButton) {
                    roadMapButton.setBackground(new Color(70, 130, 190)); // Light blue on hover
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (clickedButton[0] != roadMapButton) {
                    roadMapButton.setBackground(new Color(30, 58, 138)); // Dark blue
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                handleButtonClick(roadMapButton, clickedButton);
            }
        });

// Add mouse listener for JavaTPoint button
        javaTpointButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (clickedButton[0] != javaTpointButton) {
                    javaTpointButton.setBackground(new Color(70, 130, 190)); // Light blue on hover
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (clickedButton[0] != javaTpointButton) {
                    javaTpointButton.setBackground(new Color(30, 58, 138)); // Dark blue
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                handleButtonClick(javaTpointButton, clickedButton);
            }
        });



        linkedInButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        w3SchoolButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        kaggleButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        roadMapButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        javaTpointButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));


// Set font for ComboBox
        comboBox1.setFont(new Font("Verdana", Font.PLAIN, 13));
        comboBox2.setFont(new Font("Verdana", Font.PLAIN, 13));

        // Create a panel for buttons and add all components
        CouresFreqPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));  // Buttons in line horizontally
        CouresFreqPanel.setOpaque(false);  // Make panel transparent
        CouresFreqPanel.add(comboBoxLabel2);
        CouresFreqPanel.add(comboBox2);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));  // Buttons in line horizontally
        buttonPanel.setOpaque(false);  // Make panel transparent
        buttonPanel.add(linkedInButton);
        buttonPanel.add(w3SchoolButton);
        buttonPanel.add(kaggleButton);
        buttonPanel.add(roadMapButton);
        buttonPanel.add(javaTpointButton);

        // Create a panel for the text field and search button
        searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));  // Search panel in center
        searchPanel.setOpaque(false);
        searchPanel.add(textField1);
        searchPanel.add(searchButton);
        searchPanel.add(clearButton);
        searchPanel.setVisible(false);  // Initially hide the search panel


        topfreqPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));  // Search panel in center
        topfreqPanel.setOpaque(false);
        topfreqPanel.add(spinner1);
        //topfreqPanel.add(enterButton);
        topfreqPanel.setVisible(false);  // Initially hide the search panel

        // Initially set buttons' visibility to false (hidden)
        linkedInButton.setVisible(false);
        w3SchoolButton.setVisible(false);
        kaggleButton.setVisible(false);
        roadMapButton.setVisible(false);
        javaTpointButton.setVisible(false);
        comboBoxLabel2.setVisible(false);
        comboBox2.setVisible(false);


        mainPanel.add(OptionPanel);  // Keep button panel visible
        mainPanel.add(CouresFreqPanel);  // Keep button panel visible
        mainPanel.add(buttonPanel);  // Keep button panel visible
        mainPanel.add(searchPanel);  // Initially hidden search panel
        mainPanel.add(topfreqPanel);  // Initially hidden search panel

        bgPanel.add(welcomeLabel, BorderLayout.NORTH);
        bgPanel.add(mainPanel, BorderLayout.CENTER);

        // Set the action listener for comboBox1
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get selected item
                String selectedOption = (String) comboBox1.getSelectedItem();
                System.out.println("Selected Option: " + selectedOption);  // Debugging output

                // Reset visibility for all buttons
                linkedInButton.setVisible(false);
                w3SchoolButton.setVisible(false);
                kaggleButton.setVisible(false);
                roadMapButton.setVisible(false);
                javaTpointButton.setVisible(false);

                // Show buttons based on selected option
                if ("Search from the database".equals(selectedOption)) {

                    linkedInButton.setVisible(true);
                    w3SchoolButton.setVisible(true);
                    kaggleButton.setVisible(true);
                    roadMapButton.setVisible(true);
                    javaTpointButton.setVisible(true);
                    topfreqPanel.setVisible(false);
                    comboBoxLabel2.setVisible(false);
                    comboBox2.setVisible(false);

                } else if ("Course Frequency Analysis".equals(selectedOption)) {

                    comboBoxLabel2.setVisible(true);
                    comboBox2.setVisible(true);
                    comboBox2.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Get selected item


                            List<String> previouslyDisplayedCourses = new ArrayList<>();
                            String selected_Frequence = (String) comboBox2.getSelectedItem();
                            System.out.println("selected_Frequence Option: " + selected_Frequence);
                            Map<String, Integer> courseNameFrequencies = Task_Method.calculateCourseNameFrequencies(csvData);
                            if ("Display the most frequent course names".equals(selected_Frequence)) {
                                topfreqPanel.setVisible(true);
                                searchPanel.setVisible(false);
                                JLabel valueLabel = new JLabel("Spinner Value: 0");
                                spinner1.addChangeListener(new ChangeListener() {
                                    @Override
                                    public void stateChanged(ChangeEvent e) {

                                        JPanel contentPanel = new JPanel();
                                        contentPanel.setLayout(new GridLayout(0, 1)); // One column, multiple rows
                                        contentPanel.setBackground(new Color(240, 240, 240)); // Light background

                                        mainPanel.add(contentPanel);
                                        mainPanel.setVisible(true);
                                        int currentValue = (Integer) spinner1.getValue();
                                        valueLabel.setText("Spinner Value: " + currentValue);
                                        String labelText = valueLabel.getText();

                                        int numberValue = Integer.parseInt(labelText.replaceAll("[^0-9]", ""));
                                        System.out.println("Integer Value: " + numberValue);
                                        if (numberValue != 0) {
                                            List<String> topCourses = Task_Method.displayTopCourses(courseNameFrequencies, numberValue);
                                            // Filter out previously displayed courses
                                            for (String course : topCourses) {
                                                if (!previouslyDisplayedCourses.contains(course)) {
                                                    System.out.println(course);
                                                    contentPanel.add(createTutorialLabel("📘 " + course, new Color(70, 130, 180))); // Blue
                                                    previouslyDisplayedCourses.add(course); // Add course to the list of displayed courses
                                                }
                                            }
                                        }
                                            mainPanel.revalidate();
                                    }
                                });
                            }
                            else if("Search for a specific course name's frequency".equals(selected_Frequence))
                            {
                                //boolean isExecuted = false;
                                AtomicBoolean isExecuted = new AtomicBoolean(false);
                                searchPanel.setVisible(true);
                                topfreqPanel.setVisible(false);
                                comboBox2.setVisible(false);
                                comboBoxLabel2.setVisible(false);
                                textField1.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                                    @Override
                                    public void insertUpdate(javax.swing.event.DocumentEvent e) {
                                        updateSuggestions();
                                    }

                                    @Override
                                    public void removeUpdate(javax.swing.event.DocumentEvent e) {
                                        updateSuggestions();
                                    }

                                    @Override
                                    public void changedUpdate(javax.swing.event.DocumentEvent e) {
                                        updateSuggestions();
                                    }
                                    public void updateSuggestions() {
                                        String text = textField1.getText().trim();
                                        if (!text.isEmpty()) {
                                            List<String> suggestions = trie.getWordsWithPrefix(text.toLowerCase());
                                            System.out.println("Suggestions: " + suggestions);
                                            // Create a panel for the content
                                            JPanel contentPanel = new JPanel();
                                            contentPanel.setLayout(new GridLayout(0, 1)); // One column, multiple rows
                                            contentPanel.setBackground(new Color(240, 240, 240)); // Light background

                                            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
                                            JScrollPane scrollPane = new JScrollPane(contentPanel);
                                            scrollPane.setPreferredSize(new Dimension(100, 100)); // Set the preferred size of the scrollable area
                                            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Always show vertical scrollbar
                                            // Create a JScrollPane with the content panel as the viewport view

                                            System.out.println("display one loop");
                                            // Add an ActionListener to the search button
                                            searchButton.addActionListener(new ActionListener() {
                                                @Override

                                                public void actionPerformed(ActionEvent e) {
                                                    // Display the main window

                                                    System.out.println("inside search button");
                                                    mainPanel.setVisible(true);
                                                    if (isExecuted.compareAndSet(false, true)) {
                                                        System.out.println("inside if");
                                                        for (String course : suggestions) {
                                                            contentPanel.add(createTutorialLabel(course, new Color(70, 130, 180))); // Blue
                                                        }
                                                        contentPanel.add(Box.createVerticalStrut(10));  // Space between sets
                                                        // Refresh the display
                                                        contentPanel.revalidate();
                                                        mainPanel.add(scrollPane);
                                                        mainPanel.revalidate();

                                                    }
                                                }
                                            });
                                            clearButton.addActionListener(new ActionListener() {
                                                @Override

                                                public void actionPerformed(ActionEvent e) {
                                                    // Display the main window
                                                    textField1.setText("");
                                                    mainPanel.remove(scrollPane);
                                                    //contentPanel.setVisible(false);
                                                    isExecuted.set(false);
                                                    mainPanel.revalidate();
                                                    //mainPanel.repaint();
                                                    mainPanel.setVisible(true);

                                                }

                                            });
                                        }

                                    }
                                });
                            }
                        }
                    });
                }else if("Search for courses that match a specific price".equals(selectedOption)){

                    //boolean isExecuted = false;
                    AtomicBoolean isExecuted = new AtomicBoolean(false);
                    searchPanel.setVisible(true);
                    topfreqPanel.setVisible(false);
                    comboBoxLabel2.setVisible(false);
                    comboBox2.setVisible(false);
                    textField1.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                        @Override
                        public void insertUpdate(javax.swing.event.DocumentEvent e) {
                            updateSuggestions();
                        }

                        @Override
                        public void removeUpdate(javax.swing.event.DocumentEvent e) {
                            updateSuggestions();
                        }
                        @Override
                        public void changedUpdate(javax.swing.event.DocumentEvent e) {
                            updateSuggestions();
                        }
                        public void updateSuggestions() {
                            JPanel cardPanel = new JPanel(new GridLayout(2, 2, 10, 10));
                            String details = "";
                            Boolean NoResult = false;

                                searchButton.addActionListener(new ActionListener() {
                                    @Override

                                    public void actionPerformed(ActionEvent e) {
                                        // Display the main window

                                        //System.out.println("inside search button");
                                        mainPanel.setVisible(true);
                                            String priceRange = textField1.getText().trim();
                                            System.out.println("priceRange before:" + priceRange);
                                            if (!priceRange.isEmpty()) {
                                                if (!priceRange.startsWith("$")) {

                                                    priceRange = "$" + priceRange;
                                                    System.out.println("priceRange $:" + priceRange);
                                                } else if (priceRange.contentEquals("free")) {

                                                    System.out.println("priceRange free:" + priceRange);
                                                }

                                            }
                                            String price = priceRange;
                                            Boolean datafound = false;

                                         searchByPrice(priceRange, filePath);
                                    }
                                });
                                clearButton.addActionListener(new ActionListener() {
                                    @Override

                                    public void actionPerformed(ActionEvent e) {
                                        // Display the main window
                                        textField1.setText("");
                                        isExecuted.set(false);
                                        mainPanel.revalidate();
                                        //mainPanel.repaint();
                                        mainPanel.setVisible(true);
                                    }
                                });
                        }
               });
            } else if ("Search for courses that match a specific hours".equals(selectedOption)) {
                    //boolean isExecuted = false;
                    AtomicBoolean isExecuted = new AtomicBoolean(false);
                    searchPanel.setVisible(true);
                    topfreqPanel.setVisible(false);
                    //comboBoxLabel2.setVisible(false);
                    comboBox2.setVisible(false);
                    textField1.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                        @Override
                        public void insertUpdate(javax.swing.event.DocumentEvent e) {
                            updateSuggestions();
                        }

                        @Override
                        public void removeUpdate(javax.swing.event.DocumentEvent e) {
                            updateSuggestions();
                        }
                        @Override
                        public void changedUpdate(javax.swing.event.DocumentEvent e) {
                            updateSuggestions();
                        }
                        public void updateSuggestions() {
                            JPanel cardPanel = new JPanel(new GridLayout(2, 2, 10, 10));

                            Boolean NoResult = false;

                            searchButton.addActionListener(new ActionListener() {
                                @Override

                                public void actionPerformed(ActionEvent e) {
                                    // Display the main window

                                    //System.out.println("inside search button");
                                    mainPanel.setVisible(true);
                                    String hoursRange = textField1.getText().trim();
                                    System.out.println("hoursRange before:" + hoursRange);
                                    if (!hoursRange.isEmpty()) {
                                        if (!hoursRange.endsWith("hours")) {

                                            hoursRange = hoursRange + " hours";
                                            System.out.println("hours :" + hoursRange);
                                        }
                                    }
                                    String hours = hoursRange;
                                    Boolean datafound = false;
                                    String details = "";
                                    searchByHours(hoursRange, filePath);
                                }
                            });
                            clearButton.addActionListener(new ActionListener() {
                                @Override

                                public void actionPerformed(ActionEvent e) {
                                    // Display the main window
                                    textField1.setText("");
                                    isExecuted.set(false);
                                    mainPanel.revalidate();
                                    //mainPanel.repaint();
                                    mainPanel.setVisible(true);

                                }
                            });
                        }
                    });
            }

                linkedInButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Check which button triggered the event
                        if (e.getSource() == linkedInButton) {
                            System.out.println("LinkedIn Button Clicked");
                            buttonActivited = "LinkedIn";
                        }
                    }
                });

                w3SchoolButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Check which button triggered the event
                        if (e.getSource() == w3SchoolButton) {
                            System.out.println("w3SchoolButton Button Clicked");
                            buttonActivited = "W3Schools";
                        }
                    }
                });

                kaggleButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Check which button triggered the event
                        if (e.getSource() == kaggleButton) {
                            System.out.println("kaggleButton Button Clicked");
                            buttonActivited = "Kaggle";
                        }
                    }
                });

                roadMapButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Check which button triggered the event
                        if (e.getSource() == roadMapButton) {
                            System.out.println("roadMapButton Button Clicked");
                            buttonActivited = "roadMapButton";
                        }
                    }
                });

                javaTpointButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Check which button triggered the event
                        if (e.getSource() == javaTpointButton) {
                            System.out.println("javaTpointButton Button Clicked");
                            buttonActivited = "JavaTpoint";
                        }
                    }
                });

                // Force the panel to update
                buttonPanel.revalidate();
                buttonPanel.repaint();
            }
        });

        // Add action listeners for buttons
        linkedInButton.addActionListener(e -> showSearchPanel());
        w3SchoolButton.addActionListener(e -> showSearchPanel());
        kaggleButton.addActionListener(e -> showSearchPanel());
        roadMapButton.addActionListener(e -> showSearchPanel());
        javaTpointButton.addActionListener(e -> showSearchPanel());
    }

    // Load background image from file
    private BufferedImage loadBackgroundImage() {
        try {
            File imgFile = new File("/Users/apurvarajput/IdeaProjects/EduExplorer/src/com/example/main/education.jpg");
            return ImageIO.read(imgFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void displayResults(ArrayList<String[]> results) {
        // Column Names
        String[] columnNames = {"Source", "Course Title", "Course URL", "Duration", "Views", "Creator Info", "Email", "Date", "Price"};

        // Convert results to Object array for JTable
        Object[][] data = new Object[results.size()][9];
        for (int i = 0; i < results.size(); i++) {
            String[] row = results.get(i);
            for (int j = 0; j < row.length; j++) {
                data[i][j] = row[j].trim();
            }
        }

        // Create JTable
        JTable table = new JTable(data, columnNames);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);

        // Add JTable to JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);

        // Create JFrame to display results
        JFrame frame = new JFrame("Course Search Results");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLayout(new BorderLayout());

        // Add components
        frame.add(new JLabel("Search Results for the  Range: "), BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Set frame visible
        frame.setVisible(true);
    }

    public static void searchByPrice(String priceRange, String filePath) {
        ArrayList<String[]> results = new ArrayList<>();
        boolean noResult = true;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");  // Split the line by commas

                if (columns.length >= 8) {
                    String priceInCsv = columns[8].trim();

                    if (priceInCsv.equals(priceRange)) {
                        results.add(columns);
                        noResult = false;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (noResult) {
            JOptionPane.showMessageDialog(null, "No results found for the given price range!", "No Results", JOptionPane.INFORMATION_MESSAGE);
        } else {
            displayResults(results);
        }
    }

    public static void searchByHours(String hoursRange, String filePath) {
        ArrayList<String[]> results = new ArrayList<>();
        boolean noResult = true;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");  // Split the line by commas

                if (columns.length >= 8) {
                    String hoursInCsv = columns[3].trim(); // Duration column

                    if (hoursInCsv.equals(hoursRange)) {
                        results.add(columns);
                        noResult = false;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (noResult) {
            JOptionPane.showMessageDialog(null, "No results found for the given hours range!", "No Results", JOptionPane.INFORMATION_MESSAGE);
        } else {
            displayResults(results);
        }
    }



    // Method to handle button click
    private void handleButtonClick(JButton clicked, JButton[] clickedButton) {
        if (clickedButton[0] != null) {
            // Reset previously clicked button
            clickedButton[0].setBackground(new Color(30, 58, 138)); // Dark blue
        }

        if (clickedButton[0] == clicked) {
            // If the same button is clicked, unclick it
            clickedButton[0] = null;
        } else {
            // Set the clicked button to bright blue
            clicked.setBackground(new Color(70, 130, 190)); // Bright blue
            clickedButton[0] = clicked;
        }
    }

    // Apply blur effect to an image
    private BufferedImage blurImage(BufferedImage image) {
        if (image == null) {
            return null;
        }

        // Define a balanced Gaussian blur kernel (5x5)
        float[] blurKernel = {
                1 / 256f, 4 / 256f, 6 / 256f, 4 / 256f, 1 / 256f,
                4 / 256f, 16 / 256f, 24 / 256f, 16 / 256f, 4 / 256f,
                6 / 256f, 24 / 256f, 36 / 256f, 24 / 256f, 6 / 256f,
                4 / 256f, 16 / 256f, 24 / 256f, 16 / 256f, 4 / 256f,
                1 / 256f, 4 / 256f, 6 / 256f, 4 / 256f, 1 / 256f
        };

        Kernel kernel = new Kernel(3, 3, blurKernel);
        ConvolveOp convolveOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);

        // Create a new buffered image to hold the blurred result
        BufferedImage blurredImage = new BufferedImage(
                image.getWidth(),
                image.getHeight(),
                BufferedImage.TYPE_INT_ARGB
        );

        // Apply the blur operation
        convolveOp.filter(image, blurredImage);

        return blurredImage;
    }

    // Helper method to create a styled label for each tutorial
    private static JLabel createTutorialLabel(String tutorialText, Color highlightColor) {
        JLabel tutorialLabel = new JLabel(tutorialText, JLabel.LEFT);
        tutorialLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        tutorialLabel.setForeground(highlightColor);
        tutorialLabel.setOpaque(true);
        tutorialLabel.setBackground(Color.WHITE);
        tutorialLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return tutorialLabel;
    }

    // Custom JPanel for background
    static class BackgroundPanel extends JPanel {
        private BufferedImage backgroundImage;

        public BackgroundPanel(BufferedImage image) {
            this.backgroundImage = image;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    // Method to show the search panel and keep the button panel visible
    private void showSearchPanel() {
        AtomicBoolean isExecuted = new AtomicBoolean(false);
        searchPanel.setVisible(true);   // Show the search panel
        if (isExecuted.compareAndSet(false, true)) {
            textField1.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override
                public void insertUpdate(javax.swing.event.DocumentEvent e) {
                    updateSuggestions();
                }

                @Override
                public void removeUpdate(javax.swing.event.DocumentEvent e) {
                    updateSuggestions();
                }

                @Override
                public void changedUpdate(javax.swing.event.DocumentEvent e) {
                    updateSuggestions();
                }

                public void updateSuggestions() {
                    String text = textField1.getText().trim();
                    if (!text.isEmpty()) {
                        List<String> suggestions = trie.getWordsWithPrefix(text.toLowerCase());
                        System.out.println("Suggestions: " + suggestions);
                        updateDropdown(suggestions);
                        // Optionally, update a UI component with these suggestions
                    } else {
                        suggestionsDropdown.removeAllItems();
                    }
                }

                // Update the JComboBox with suggestions
                private void updateDropdown(List<String> suggestions) {
                    // Clear the current items
                    suggestionsDropdown.removeAllItems();

                    // Add the new suggestions to the dropdown
                    for (String suggestion : suggestions) {
                        suggestionsDropdown.addItem(suggestion);
                    }

                    // Optionally, show the dropdown immediately after updating
                    suggestionsDropdown.showPopup();

                    // Add an ActionListener to handle selection from the dropdown
                    suggestionsDropdown.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Get the selected value from the JComboBox
                            String selectedValue = (String) suggestionsDropdown.getSelectedItem();

                            String previousSelectedValue = (String) suggestionsDropdown.getSelectedItem();

                            System.out.println("previousSelectedValue Value Set: " + previousSelectedValue);
                            // Check if the selected value is not the same as the previous value
                            if (selectedValue != null) {
                                //previousSelectedValue = selectedValue;
                                // Set the selected value to the JTextField
                                textField1.setText(selectedValue);
                                System.out.println("TextField Value Set: " + selectedValue); // Debugging line

                                // Optionally, hide the dropdown after a selection
                                suggestionsDropdown.setPopupVisible(false);
                            }
                        }
                    });
                }
            });
            textField1.add(suggestionsDropdown);
        }

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    String selectedWord = textField1.getText();
                    if (!selectedWord.isEmpty()) {
                        JPanel cardPanel = new JPanel(new GridLayout(2, 2, 10, 10));
                        System.out.println("selectedWord : " + selectedWord);
                        System.out.println("buttonActivited : " + buttonActivited);
                        Boolean datafound = false;

                        for (String[] row : csvData) {
                            //System.out.println("Row data: " + Arrays.toString(row));
                            System.out.println("Row[0]: " + row[0]);
                            System.out.println("Row[1]: " + row[1]);
                            if (row.length > 1 && row[0].equalsIgnoreCase(buttonActivited) && row[1].toLowerCase().equals(selectedWord)) {
                                System.out.println("Match found in " + buttonActivited + " for course: " + row[1]);

                                datafound = true;

                                cardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                                String[] descriptions = {
                                        "Course URL: " + row[2],
                                        "Duration: " + row[3],
                                        "Views: " + row[4],
                                        "Creator Info: " + row[5],
                                        "Email: " + row[6],
                                        "Date: " + row[7],
                                        "Price: " + row[8]
                                };

                                cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
                                for (String desc : descriptions) {
                                    JPanel card = new JPanel();
                                    card.setLayout(new BorderLayout());
                                    card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
                                    card.setBackground(new Color(240, 240, 255));

                                    JLabel descLabel = new JLabel("<html><center>" + desc + "</center></html>");
                                    descLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                                    descLabel.setHorizontalAlignment(SwingConstants.CENTER);

                                    card.add(descLabel, BorderLayout.CENTER);
                                    cardPanel.add(card);
                                }
                                break;
                            }
                        }

                        if (datafound) {
                            mainPanel.add(cardPanel);
                            mainPanel.revalidate();
                            mainPanel.repaint();
                        }
                        clearButton.addActionListener(new ActionListener() {
                            @Override

                            public void actionPerformed(ActionEvent e) {
                                // Display the main window
                                textField1.setText("");
                                cardPanel.setVisible(false);
                                isExecuted.set(false);
                                mainPanel.revalidate();
                                //mainPanel.repaint();
                                mainPanel.setVisible(true);

                            }

                        });
                    }
                });
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUI_APP().setVisible(true));
    }
}
