package befunge.Interpreter;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.DefaultCaret;

import java.io.*;
import java.util.*;

import befunge.Components.Pair;
import befunge.Components.OutOfInput;

class BefungeModel {
	ProgramView prg_view;
	OutputView out_view;
	BefungeMachine machine;
	StackView stack_view;
	InputView input_view;
	
	Thread thread;
	
	BefungeModel(PrintWriter pw) {
		machine = new BefungeMachine(20, 40, pw);
	}
	
	public void load(String s) {
		machine.input_port.setScanner(null);
		machine.load(s);
		out_view.reset();
		prg_view.load(machine.instr_mem.getMatrix(), 0, 0);
		stack_view.reset();
	
	}
	
	public void step() {
		try {
			if(machine.input_port.getScanner() == null) 
				machine.input_port.setScanner(input_view.make_scanner());
			
			machine.step();
			Pair P = machine.instr_ptr.value();
			prg_view.load(machine.instr_mem.getMatrix(), P.x, P.y);
			out_view.update();
			stack_view.display(machine.stack.toString());
		} catch (OutOfInput e) {
			machine.running = false;
			machine.input_port.getScanner().close();
			machine.input_port.setScanner(null);
			input_view.clear();
		}
	}
	
	public void run() {
		 machine.running = true;
		 if(thread != null) 
			 return;
		 
		 Thread th=new Thread() {
		      public void run() {
		    	  	while(machine.running == true) {
		    	  		step();
		    	  		prg_view.repaint();           
		    	  		try {
		    	  			Thread.sleep(50);
		    	  		} catch (InterruptedException e) {
		    	  			
		    	  			e.printStackTrace();
		    	  		}
		    	  		run();
		    	  	}
		    	  	thread = null;
		      }
		    };
		    thread = th;
		    th.start();
	}
	
	public void stop() {
		machine.running = false;
	}
}



class HelpData {
	public static String data() {
		return "The list of instructions is given below\n" +
				"Stack Commands\n" + 
				"	0-9  Push the digit on the stack\n" +
				"	+    Pop a and b from stack. Push a + b\n" +
				"	*    Pop a and b from stack. Push a * b\n" +
				"	-    Pop a and b from stack. Push b - a\n" +
				"	/    Pop a and b from stack. Push b / a\n" +
				"	%    Pop a and b from stack. Push b % a\n" +
				"	:    Duplicate the top value on stack\n" +
				"	\\    Swap the top 2 values on stack\n" +
				"	$    Discard a value from stack\n" +
				"	!    Pop a from the stack. Push 1 if 'a' is 0 and 0 otherwise\n" +
				"	`    Pop a and b. If b > a then push 1 otherwise 0 \n" +
				"Direction Commands\n" + 
				"	>    Instruction pointer moves right\n" +
				"	v    Instruction pointer moves down\n" +
				"	<    Instruction pointer moves left\n" +
				"	^    Instruction pointer moves up\n" +
				"	?    Instruction pointer moves in a random direction \n" +
				"Conditional Commands\n" +
				"	_    Pop a from stack. If a = 0, IP moves right otherwise left\n" +
				"	|    Pop a from stack. If a = 0, IP moves down otherwise up\n" +
				"	#    Skip the next command\n" +
				"Input/Output\n" + 
				"	.    Pop from stack and print as integer\n" +
				"	,    Pop from stack and print as ASCII character\n" +
				"	&    Read integer as input\n" +
				"	~    Read character as input\n" +
				"	g    Pop y and x. Push ASCII value at location (x,y) of program\n" +
				"	p    Pop y ,x and v. Store v at location (x,y) of program\n" +
				"Other commands\n" + 
				"	\"   Toggle String mode (Push each commands ASCII value)\n" +
				"	@    Terminate the program\n";	
	}
	
	public static String sample(String name) {
		String file = null;
		if(name.equals("Add 2 Numbers"))
			file = "sample/add.txt";
		else if(name.equals("Simple Loop")) 
			file = "sample/simple_loop.txt";
		else if(name.equals("Factorial"))
			file = "sample/factorial.txt";
		else if(name.equals("Hello World")) 
			file = "sample/hello_world.txt";
		else if (name.equals("Guess The Number"))
			file = "sample/guess_num.txt";
	
		try {
			Scanner sc = new Scanner(new File(file));
			sc.useDelimiter("\\Z");
			String s = sc.next();
			return s;
		} catch (FileNotFoundException e) {
			return "file not found";
		}
	
	}
}

class CellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;
	public int i;
	public int j;

	public Component getTableCellRendererComponent(JTable table, Object obj,
				boolean isSelected, boolean hasFocus, int row, int column) {

		Component cell = super.getTableCellRendererComponent(table, obj, isSelected, hasFocus, row, column);
		cell.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		
		if(row == i && column == j) {
			cell.setBackground(Color.YELLOW);
		}else
			cell.setBackground(Color.white);

		return cell;
	}
}

class InputView extends JTextArea {
	private static final long serialVersionUID = 1L;
	
	Scanner make_scanner() {
		return new Scanner(getText() + "\n");
	}
	
	void clear() {
		setText("");
	}
}

class StackView extends JTextArea {
	private static final long serialVersionUID = 1L;
	
	void display(String s) {
		this.setText(s);
	}
	
	void reset() {
		display("");
	}
}

class OutputView extends JTextArea {
	private static final long serialVersionUID = 1L;
	StringWriter str_writer = new StringWriter();
	PrintWriter print_writer = new PrintWriter(str_writer);
	
	OutputView() {
		this.setEditable(false);
	}
	
	void update() {
		String text = str_writer.toString();
		this.setText(text);
	}
	
	void reset() {
		str_writer.getBuffer().setLength(0);
		this.setText("");
	}
}

class ProgramView extends JTable {
	private static final long serialVersionUID = 1L;
	JTable table = this;
	CellRenderer renderer = new CellRenderer();
	
	public ProgramView() {
		super(20, 40);
		this.setDefaultRenderer(Object.class, renderer);
	}
	
	void load(char[][] matrix, int x, int y) {	
		renderer.i = x;
		renderer.j = y;
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[i].length; j++) {
				char c = matrix[i][j];
				setValueAt(Character.valueOf(c), i, j);
			}
		}
		table.repaint();
	}
	
}

class ProgramEditor extends JTextArea
{
	private static final long serialVersionUID = 1L;
	File file = null;
	JTextArea area = this;
	
	ProgramEditor() {
		this.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
	}
	
	void setFile(File f) {
		file = f;
	}
	
	void save() {
		String s = area.getText();
		try {
			FileWriter fw = new FileWriter(file);
			fw.write(s);
			fw.close();
		} catch (IOException e) {
			
		}
	}
	
	void open() {
		try {
			Scanner sc = new Scanner(file);
			sc.useDelimiter("\\Z");
			String s = sc.next();
			area.setText(s);
			sc.close();
		} catch (IOException e) {
			
		}
	}
	
	String getProgram() {
		return area.getText();
	}
	

}

class Header implements ActionListener
{
	JFrame frame = new JFrame("Befunge Home Page");
	ProgramEditor editor = new ProgramEditor();
	BefungeModel bmodel;
//	UIFactory ui = new UIFactory();
	
	Header(BefungeModel bmodel) {
		Menu();
		TextArea();
		
		this.bmodel = bmodel;
		
		JTable table = bmodel.prg_view;
		table.setBounds(30, 50, 800, 500);
		table.setRowHeight(25);
		//table.
		frame.add(table);
		
		JTextArea output = bmodel.out_view;
		output.setBounds(470, 600, 300, 150);
		frame.add(output);
		JScrollPane sp = new JScrollPane(output);
		sp.setBounds(450,600,300,150);
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		sp.setViewportView(output);
		frame.getContentPane().add(sp);

		DefaultCaret caret = (DefaultCaret)output.getCaret();
		output.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		StackView stack_view = bmodel.stack_view;
		stack_view.setBounds(870, 50, 100, 580);
		stack_view.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
		frame.add(stack_view);
		
		InputView input_view = bmodel.input_view;
		input_view.setBounds(30, 600, 300, 150);
		input_view.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
		frame.add(input_view);
		
		JLabel tableLabel = new JLabel("TABLE");
		tableLabel.setBounds(30, 5, 150, 50);
		tableLabel.setForeground(Color.WHITE);
		frame.add(tableLabel);
		
		JLabel stackLabel = new JLabel("STACK");
		stackLabel.setBounds(870, 5, 100, 50);
		stackLabel.setForeground(Color.WHITE);
		frame.add(stackLabel);
		
		JLabel ProgramLabel = new JLabel("PROGRAM");
		ProgramLabel.setBounds(1000, 5, 100, 50);
		ProgramLabel.setForeground(Color.WHITE);
		frame.add(ProgramLabel);
		
		JLabel inputLabel = new JLabel("INPUT");
		inputLabel.setBounds(30, 550, 130, 50);
		inputLabel.setForeground(Color.WHITE);
		frame.add(inputLabel);
		
		JLabel outputLabel = new JLabel("OUTPUT");
		outputLabel.setBounds(450, 550, 130, 50);
		outputLabel.setForeground(Color.WHITE);
		frame.add(outputLabel);
	}
	
	public void actionPerformed(ActionEvent act) {
		String s = act.getActionCommand();
		
		if (s.equals("Open")) {
			JFileChooser j = new JFileChooser("f:");
 
            // Invoke the showsSaveDialog function to show the save dialog
            int r = j.showOpenDialog(null);
 
            if (r == JFileChooser.APPROVE_OPTION) {
 
                // Set the label to the path of the selected directory
                File fi = new File(j.getSelectedFile().getAbsolutePath());
 
                try {
                  editor.setFile(fi);
                  editor.open();
                }
                catch (Exception evt) {
                    JOptionPane.showMessageDialog(frame, evt.getMessage());
                }
            }
		} 
		
		if (s.equals("Save")) {
			if(editor.file == null) 
				s = "Save as";
			else
				editor.save();
		}
		
		if (s.equals("Save as")) {
			JFileChooser j = new JFileChooser("f:");
			 
            // Invoke the showsSaveDialog function to show the save dialog
            int r = j.showSaveDialog(null);
 
            if (r == JFileChooser.APPROVE_OPTION) {
 
                // Set the label to the path of the selected directory
                File fi = new File(j.getSelectedFile().getAbsolutePath());
 
                try {
                  editor.setFile(fi);
                  editor.save();
                }
                catch (Exception evt) {
                    JOptionPane.showMessageDialog(frame, evt.getMessage());
                }
            }
		}
		
		if(s.equals("Load")) {
			String prg = editor.getProgram();
			bmodel.load(prg);
		}
		
		if(s.equals("Step")) {
			bmodel.step();
		}
		
		if(s.equals("Run")) {
			bmodel.run();
		}
		
		if(s.equals("Stop")) {
			bmodel.stop();
		}
		
		if(s.equals("Help")) {
			JFrame help = new JFrame("Help");
			help.setBounds(0, 0, 500, 500);
			help.setVisible(true);
			JTextArea instruct = new JTextArea();
			instruct.setEditable(false);
			instruct.setBounds(0, 0, 500, 500);
			help.add(instruct);
			
			instruct.setText(HelpData.data());
			instruct.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
			instruct.setBackground(Color.getHSBColor((float)46.0/360, (float)0.41, (float)0.84));
		}
		
		if(s.equals("Add 2 Numbers"))
			editor.setText(HelpData.sample(s));
		else if(s.equals("Simple Loop")) 
			editor.setText(HelpData.sample(s));
		else if(s.equals("Factorial"))
			editor.setText(HelpData.sample(s));
		else if(s.equals("Hello World")) 
			editor.setText(HelpData.sample(s));
		else if (s.equals("Guess The Number"))
			editor.setText(HelpData.sample(s));
	
		
	
	}
	
	void Menu()
	{
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 frame.setLocationRelativeTo(null);
		 frame.getContentPane().setBackground(Color.getHSBColor((float)263.0/360, (float)0.19,(float)0.27));
		 frame.pack();
		 frame.setVisible(true);
		 MenuBar mb=new MenuBar();  
         Menu menu=new Menu("File"); 
        
         
         MenuItem i1 = new MenuItem("Open");
         i1.addActionListener(this);
        
         MenuItem i2=new MenuItem("Save"); 
         i2.addActionListener(this);
         
         MenuItem i3 =new MenuItem("Save as"); 
         i3.addActionListener(this);
         
         MenuItem i10=new MenuItem("Help");
         i10.addActionListener(this);
        
         menu.add(i1);  
         menu.add(i2); 
         menu.add(i3);
         menu.add(i10);
         mb.add(menu);
      
         
         JButton b = new JButton("Step");
         
         b.setBounds(1120, 670, 100, 40);
         b.addActionListener(this);
         
         JButton load = new JButton("Load");
         load.setBounds(1240, 670, 100, 40);
         load.addActionListener(this);
         
         JButton stop = new JButton("Stop");
         stop.setBounds(1360, 670, 100, 40);
         stop.addActionListener(this);
         
         
         JButton run = new JButton("Run");
         run.setBounds(1000, 670, 100, 40);
         run.addActionListener(this);
         
         
         frame.add(run);
         frame.add(load);
         frame.add(stop);
         
         frame.add(b);
         frame.setMenuBar(mb);  
         frame.setExtendedState(frame.MAXIMIZED_BOTH);  
         frame.setLayout(null);  
         frame.setVisible(true); 
         
         Menu sample=new Menu("Sample");
         
         MenuItem i4 = new MenuItem("Hello World");
         i4.addActionListener(this);
        
         MenuItem i5=new MenuItem("Add 2 Numbers"); 
         i5.addActionListener(this);
         
         MenuItem i6=new MenuItem("Simple Loop");
         i6.addActionListener(this);
         
         MenuItem i7=new MenuItem("Factorial");
         i7.addActionListener(this);
         
         MenuItem i8=new MenuItem("Guess The Number");
         i8.addActionListener(this);
         
         sample.add(i4);
         sample.add(i5);
         sample.add(i6);
         sample.add(i7);
         sample.add(i8);
         
         sample.addActionListener(this);
         mb.add(sample);
	}
	
	void TextArea() 
	{  	
		editor.setBackground(Color.lightGray);
		editor.setBounds(1000,50,480,580);
	
		frame.add(editor);
	}
}	
	


public class HomeGUI 
{

	public static void main(String[] args) 
	{	
		OutputView output = new OutputView();
		BefungeModel befunge = new BefungeModel(output.print_writer);
		StackView stack = new StackView();
		InputView input = new InputView();
		
		befunge.prg_view = new ProgramView();
		befunge.out_view = output;
		befunge.stack_view = stack;
		befunge.input_view = input;
		
		Header M = new Header(befunge);
	}

}



