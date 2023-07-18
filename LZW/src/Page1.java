
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;
public class LZW {
	
	int exists(String s, Vector<String> dictionary) {
		int n = dictionary.size() , num = s.length();

		if (num == 1)
		{
			char c = s.toCharArray()[0]; 
			return ((int)c) ;
		}
		
		for (int i = 0; i < n ; i++)
		{
			if (s.equals(dictionary.get(i)))
				return i+128;
		}

		return -1;
	}
	
	public String readFile(String path) throws IOException {
		String content = null;
		File file = new File(path);
		
		FileReader reader = null;
		try {
			reader = new FileReader(file);
			char[] chars = new char[(int) file.length()];
			reader.read(chars);
			content = new String(chars);
			reader.close();
		} catch (IOException e) {
			Sys "File Not Found");
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return content;
	}

	public void writeFile(String text , String name) {
		BufferedWriter writer = null;
		try {
			// create a temporary file
			
			String fileName = name+".txt" ;
			File logFile = new File(fileName);
			
			writer = new BufferedWriter(new FileWriter(logFile));
			writer.write(text);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// Close the writer regardless of what happens...
				writer.close();
			} catch (Exception e) {
			}
		}

	}
	
	public void compress(String path , String name)
	{
		String text = new String();
		String compressed = new String ("") ;
		Vector<String> dictionary = new Vector<String>() ;
		
		try {
			text = readFile(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}

		int n = text.length();
		String done = "";
		
		for (int i = 0; i<n; i++)
		{
			String curr =new String("");
			curr += (text.toCharArray()[i]) ;

			int ptr = 0 ;
//			int start = i ;

			while (exists(curr, dictionary)!=-1) 
			{					
					i++;
					ptr = exists(curr , dictionary) ;
					
					if (i==n)
						break ;
					curr += text.toCharArray()[i];

			}
			dictionary.addElement(curr);
			done += curr ;
			
			compressed+=ptr+" "; 
			i--;
		}
		
		writeFile(compressed , name);
		
	}
	
	public void decompress(String path , String name)
	{
		String text = new String();
		String decompressed = new String ("") ;
		Vector<String> dictionary = new Vector<String>() ;

		try {
			text = readFile(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}

		int n = text.length();
		Scanner stream = new Scanner (text) ;
		String temp = "" ;
		
		while (stream.hasNextInt())
		{	String newDecomp = "" ;

			int curr = stream.nextInt();
			//if (curr>=0 && curr<dictionary.size()+128)
			//{
				boolean exist = false , unknown = true ;
				if (curr<=127)
				{
					exist = true ;
					String str = "" ;
					str += (char)curr ;
					newDecomp=str ;
				}
				else 
				{
					if (curr-128 < dictionary.size())
						newDecomp=dictionary.get(curr-128) ;
					
					else	// number not in the dictionary -> xxx
					{
						newDecomp = (temp + temp.toCharArray()[0]);
						/*
						 * 	The new element in the dictionary will have size of the previous (temp) +1
						 * 	because when we add a new element in the dictionary it is the previous
						 *  compressed (temp) + the 1st character of the new compressed.
						 *  so when we decompress, we reform the new element of the dictionary
						 *  by taking the previous decompressed + a new character.
						 *  This new character is the 1st character of temp 
						 * 
						 * */
					}
				}
				
				decompressed+=newDecomp ;

				if ((temp+newDecomp.toCharArray()[0]).length() > 1)
					dictionary.add(temp+newDecomp.toCharArray()[0]); 
				
				temp = newDecomp;			
				
		}
		writeFile(decompressed , name);
	}
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Page1 window = new Page1();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Page1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		JLabel enterLabel = new JLabel();
		enterLabel.setText("Enter the path of the file you want to compress replacing each \"\\\" by \"\\\\\" ");
		enterLabel.setBounds(15, 10, 425, 50);
		frame.getContentPane().add(enterLabel);

		final JTextField textField = new JTextField();
		textField.setBounds(15, 50, 350, 25);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JButton btn = new JButton();
		btn.setText("Compress");
		btn.setBounds(100, 196, 125, 25);
		frame.getContentPane().add(btn);
		
		JLabel enterLabelde = new JLabel();
		enterLabelde.setText("Enter the name of the compressed file ");
		enterLabelde.setBounds(15, 86, 425, 50);
		frame.getContentPane().add(enterLabelde);

		final JTextField textFieldde = new JTextField();
		textFieldde.setBounds(15, 130, 350, 25);
		frame.getContentPane().add(textFieldde);
		textFieldde.setColumns(10);

		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				compress(textField.getText() , textFieldde.getText() );
			}
		});
		btnde.setText("Decompress");
		btnde.setBounds(100, 232, 125, 25);
		frame.getContentPane().add(btnde);
		
		btnde.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				decompress(textField.getText() , textFieldde.getText() );
			}
		});
		

	}

}
