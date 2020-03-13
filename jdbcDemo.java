
import static java.lang.System.out;

import java.sql.*;
import java.awt.*;        // Using AWT container and component classes
import java.awt.event.*;  // Using AWT event classes and listener interfaces

public class jdbcDemo extends Frame implements ActionListener {
	private Label lblInput;     // Declare input Label
	private Label lblOutput;    // Declare output Label
	private TextField tfInput;  // Declare input TextField
	private TextField tfOutput; // Declare output TextField
	private Label lblInput1;     // Declare input Label
	private Label lblOutput1;    // Declare output Label
	private TextField tfInput1;  // Declare input TextField
	private TextField tfOutput1; // Declare output TextField
	private Label lblInput2;     // Declare input Label
	private Label lblOutput2;    // Declare output Label
	private TextField tfInput2;  // Declare input TextField
	private TextField tfOutput2; // Declare output TextField

	public jdbcDemo() {
		setLayout(new FlowLayout());
		// "super" Frame (container) sets layout to FlowLayout, which arranges
		// the components from left-to-right, and flow to next row from top-to-bottom.

		lblInput = new Label("Enter first movie: "); // Construct Label
		add(lblInput);               // "super" Frame container adds Label component

		tfInput = new TextField(50); // Construct TextField
		add(tfInput);                // "super" Frame adds TextField

		tfInput.addActionListener(this);
		// "tfInput" is the source object that fires an ActionEvent upon entered.
		// The source add "this" instance as an ActionEvent listener, which provides
		//  an ActionEvent handler called actionPerformed().
		// Hitting "enter" on tfInput invokes actionPerformed().
		lblInput1 = new Label("Enter second movie: "); // Construct Label
		add(lblInput1);               // "super" Frame container adds Label component

		tfInput1 = new TextField(50); // Construct TextField
		add(tfInput1);                // "super" Frame adds TextField

		tfInput1.addActionListener(this);
		// "tfInput" is the source object that fires an ActionEvent upon entered.
		// The source add "this" instance as an ActionEvent listener, which provides
		//  an ActionEvent handler called actionPerformed().
		// Hitting "enter" on tfInput invokes actionPerformed().
		lblInput2 = new Label("Enter third movie: "); // Construct Label
		add(lblInput2);               // "super" Frame container adds Label component

		tfInput2 = new TextField(50); // Construct TextField
		add(tfInput2);                // "super" Frame adds TextField

		tfInput2.addActionListener(this);
		// "tfInput" is the source object that fires an ActionEvent upon entered.
		// The source add "this" instance as an ActionEvent listener, which provides
		//  an ActionEvent handler called actionPerformed().
		// Hitting "enter" on tfInput invokes actionPerformed().

		lblOutput = new Label("The Recommended movie 1 is: ");  // allocate Label
		add(lblOutput);               // "super" Frame adds Label

		tfOutput = new TextField(40); // allocate TextField
		tfOutput.setEditable(false);  // read-only
		add(tfOutput);                // "super" Frame adds TextField
		lblOutput1 = new Label("The Recommended movie 2 is: ");  // allocate Label
		add(lblOutput1);               // "super" Frame adds Label

		tfOutput1 = new TextField(40); // allocate TextField
		tfOutput1.setEditable(false);  // read-only
		add(tfOutput1);                // "super" Frame adds TextField
		lblOutput2 = new Label("The Recommended movie 3 is: ");  // allocate Label
		add(lblOutput2);               // "super" Frame adds Label

		tfOutput2 = new TextField(40); // allocate TextField
		tfOutput2.setEditable(false);  // read-only
		add(tfOutput2);                // "super" Frame adds TextField

		setTitle("Movie Recommender");  // "super" Frame sets title
		setSize(600, 400);  // "super" Frame sets initial window size
		setVisible(true);   // "super" Frame shows
	}

	public static int Rating(double mv1_rate, double mv2_rate, double mv3_rate) {
		int[] itemsCount = new int[10];
		int rating[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		for (int i = 0; i < 10; i++) {
			if (mv1_rate >= rating[i] && mv1_rate <= rating[i + 1])
				itemsCount[i]++;
			if (mv2_rate >= rating[i] && mv2_rate <= rating[i + 1])
				itemsCount[i]++;
			if (mv3_rate >= rating[i] && mv3_rate <= rating[i + 1])
				itemsCount[i]++;
		}
		for (int i = 0; i < 10; i++)
			if (itemsCount[i] > 1)
				return rating[i];
		return -1;
	}

	public static int Year(int mv1_year, double mv2_year, double mv3_year) {
		int[] itemsCount = new int[4];
		int year[] = { 2000, 2005, 2010, 2015, 2020 };
		for (int i = 0; i < 4; i++) {
			if (mv1_year >= year[i] && mv1_year <= year[i + 1])
				itemsCount[i]++;
			if (mv2_year >= year[i] && mv2_year <= year[i + 1])
				itemsCount[i]++;
			if (mv3_year >= year[i] && mv3_year <= year[i + 1])
				itemsCount[i]++;
		}
		for (int i = 0; i < 4; i++)
			if (itemsCount[i] > 1)
				return year[i];
		return -1;
	}

	public static String[] findSimilarities0(String[] Items1, String[] Items2, String[] Items3) {
		String[] AllItems = new String[Items1.length + Items2.length + Items3.length];
		int index = -1;
		for (int i = 0; i < Items1.length; i++) {
			int count = 0;
			for (int j = 0; j < AllItems.length; j++)
				if (Items1[i].equals(AllItems[j]))
					count++;
			if (count == 0) {
				index++;
				AllItems[index] = Items1[i];
			}
		}
		for (int i = 0; i < Items2.length; i++) {
			int count = 0;
			for (int j = 0; j < AllItems.length; j++)
				if (Items2[i].equals(AllItems[j]))
					count++;
			if (count == 0) {
				index++;
				AllItems[index] = Items2[i];
			}
		}
		for (int i = 0; i < Items3.length; i++) {
			int count = 0;
			for (int j = 0; j < AllItems.length; j++)
				if (Items3[i].equals(AllItems[j]))
					count++;
			if (count == 0) {
				index++;
				AllItems[index] = Items3[i];
			}
		}
		String[] unique_items = new String[index + 1];
		int[] counting = new int[index + 1];
		for (int i = 0; i < unique_items.length; i++)
			unique_items[i] = AllItems[i];
		for (int i = 0; i < Items1.length; i++)
			for (int k = 0; k < unique_items.length; k++)
				if (Items1[i].equals(unique_items[k]))
					counting[k]++;
		for (int i = 0; i < Items2.length; i++)
			for (int k = 0; k < unique_items.length; k++)
				if (Items2[i].equals(unique_items[k]))
					counting[k]++;
		for (int i = 0; i < Items3.length; i++)
			for (int k = 0; k < unique_items.length; k++)
				if (Items3[i].equals(unique_items[k]))
					counting[k]++;
		int count = 0;
		for (int k = 0; k < counting.length; k++)
			if (counting[k] == 3)
				count++;
		if (count > 0) {
			String[] topOption = new String[count];
			index = 0;
			for (int k = 0; k < counting.length; k++) {
				if (counting[k] == 3) {
					topOption[index] = unique_items[k];
					index++;
				}
			}
			return topOption;
		}
		count = 0;
		for (int k = 0; k < counting.length; k++)
			if (counting[k] == 2)
				count++;
		if (count > 0) {
			String[] topOption = new String[count];
			index = 0;
			for (int k = 0; k < counting.length; k++) {
				if (counting[k] == 2) {
					topOption[index] = unique_items[k];
					index++;
				}
			}
			return topOption;
		}
		String[] topOption = {};
		return topOption;
	}

	public void print(ResultSet rs, int s) throws SQLException {
		// Retrieve the properties of the columns ResultSet
		ResultSetMetaData rs_md = rs.getMetaData();
		// get number of columns
		int columnsNumber = rs_md.getColumnCount();
		// get number of rows
		rs.last();
		int size = rs.getRow();
		rs.beforeFirst();
		for (int i = 0; i < columnsNumber; i++)
			out.print("-------------------");
		out.println(" ");
		for (int j = 1; j <= columnsNumber; j++)
			// print column names
			System.out.format("%18s", rs_md.getColumnName(j));
		out.println(" ");
		for (int i = 0; i < columnsNumber; i++)
			out.print("-------------------");
		out.println(" ");
		// System.out.println(rsmd.getColumnName(j)+" ");
		// for each row
		for (int i = 0; i < size || i < 100; i++) {
			if (rs.next()) {
				// print every column
				for (int j = 1; j <= columnsNumber; j++) {
					if(i==0)
						tfOutput.setText(rs.getString(j));
					if(i==1)
						tfOutput1.setText(rs.getString(j));
					if(i==2)
						tfOutput2.setText(rs.getString(j));
					//System.out.format("%20s ", rs.getString(j));
				}
				if (i == 3 && s == 1)
					break;
			} else if (i == 0) {
				System.out.println(" Not exsiting satisfying result!");
			} else
				break;
			System.out.println(" ");
		}
		System.out.println("\n\n____________________( Query Finished )____________________\n\n");

	}
/*
	public static boolean connectionMySqlDemo() {
		Connection conn = null;
		try {
			// 1 Import Drivers
			Class.forName("com.mysql.jdbc.Driver");
			// 2 Connect to DB
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testv3?"
					+ "user=root&password=1993526ats&useUnicode=true&characterEncoding=UTF8&useSSL=false");

			// fill in the following values for alg. ***************
			String[] mv1_actors = null; // no split
			String[] mv1_genre = null; // split
			String[] mv1_director = null; // split
			int mv1_year = -1; // split
			double mv1_rate = -1; // split

			String[] mv2_actors = null;
			String[] mv2_genre = null;
			String[] mv2_director = null;
			int mv2_year = -1;
			double mv2_rate = -1;

			String[] mv3_actors = null;
			String[] mv3_genre = null;
			String[] mv3_director = null;
			int mv3_year = -1;
			double mv3_rate = -1;

			// GET MOVIES NAME FROM CONNER MV1, MV2, MV3
			String MV1 = "The Naked Monster";
			String MV2 = "Just Go With It";
			String MV3 = "I Now Pronounce You Chuck & Larry";
			/*
String MV1 = "The Naked Monster";
String MV2 = "Just Go With It";
String MV3 = "I Now Pronounce You Chuck & Larry";

String MV1 = "Horrible Bosses 2";
String MV2 = "Just Go with It";
String MV3 = "The Break-Up";

String MV1 = "The Mummy";
String MV2 = "Mission: Impossible - Ghost Protocol";
String MV3 = "Tropic Thunder";

String MV1 = "Open Range";
String MV2 = "3 Days to Kill";
String MV3 = "3000 Miles to Graceland";

String MV1 = "Killer Bud";
String MV2 = "The Beatnicks";
String MV3 = "Hollywood Palms";
			 */

			// check the movie exist
/*
			String queryCheck1 ="select distinct(primaryTitle) from moviedata where primaryTitle=?";
			PreparedStatement st1 =conn.prepareStatement(queryCheck1);
			st1.setString(1, MV1);
			ResultSet qc1= st1.executeQuery();

			if(qc1.next()) {
				System.out.println("loading");
			}
			else {
				System.out.println("please reenter the first movie");
			}
			//check for the second movie
			PreparedStatement st2 =conn.prepareStatement(queryCheck1);
			st2.setString(1, MV2);
			ResultSet qc2= st2.executeQuery();
			if(qc2.next()) {
				System.out.println("loading");
			}
			else {
				System.out.println("please reenter the second movie");
			}
			//check for the Third movie

			PreparedStatement st3 =conn.prepareStatement(queryCheck1);
			st3.setString(1, MV3);
			ResultSet qc3= st3.executeQuery();
			if(qc3.next()) {
				System.out.println("loading");
			}
			else {
				System.out.println("please reenter the Third movie");
			}
			if(qc1.next()&&qc2.next()&&qc3.next());
			{



				Statement stmt = conn.createStatement();
				ResultSet rs1;

				try {
					rs1 = stmt.executeQuery(
							"select distinct(nconst), genres, directors, startYear, averageRating from moviedata where primaryTitle='"
									+ MV1 + "' and (category = 'actor' or category = 'actress')");
					// fill

					rs1.last();
					int size = rs1.getRow();
					rs1.beforeFirst();
					mv1_actors = new String[size];
					String tempDir = ""; // "A,B,C" -> mv1_director = tempDir.split(",");
					String tempGen = "";
					String tempYear = "";
					String tempRate = "";
					// distinct(nconst), genres, directors, startYear, averageRating
					for (int i = 0; i < size; i++) {
						if (rs1.next()) {
							mv1_actors[i] = rs1.getString(1);
							tempGen = rs1.getString(2);
							tempDir = rs1.getString(3);
							tempYear = rs1.getString(4);
							tempRate = rs1.getString(5);
							// print every column
						}
					}

					mv1_year = Integer.parseInt(tempYear);
					mv1_rate = Double.parseDouble(tempRate);
					mv1_director = tempDir.split(",", -1);
					mv1_genre = tempGen.split(",", -1);

					print(rs1, 0);
				} catch (SQLException e) {
					e.printStackTrace();
				}

				ResultSet rs2;
				try {
					// Printing the name of the employee without duplications made by inner join.
					rs2 = stmt.executeQuery(
							"select distinct(nconst), genres, directors, startYear, averageRating from moviedata where primaryTitle='"
									+ MV2 + "' and (category = 'actor' or category = 'actress')");
					rs2.last();
					int size = rs2.getRow();
					rs2.beforeFirst();
					mv2_actors = new String[size];
					String tempDir = ""; // "A,B,C" -> mv1_director = tempDir.split(",");
					String tempGen = "";
					String tempYear = "";
					String tempRate = "";
					// distinct(nconst), genres, directors, startYear, averageRating
					for (int i = 0; i < size; i++) {
						if (rs2.next()) {
							mv2_actors[i] = rs2.getString(1);
							tempGen = rs2.getString(2);
							tempDir = rs2.getString(3);
							tempYear = rs2.getString(4);
							tempRate = rs2.getString(5);
							// print every column
						}
					}

					mv2_year = Integer.parseInt(tempYear);
					mv2_rate = Double.parseDouble(tempRate);
					mv2_director = tempDir.split(",", -1);
					mv2_genre = tempGen.split(",", -1);

					print(rs2, 0);
				} catch (SQLException e) {
					e.printStackTrace();
				}

				ResultSet rs3;
				try {
					// Printing the name of the employee without duplications made by inner join.
					rs3 = stmt.executeQuery(
							"select distinct(nconst), genres, directors, startYear, averageRating from moviedata where primaryTitle='"
									+ MV3 + "' and (category = 'actor' or category = 'actress')");
					rs3.last();
					int size = rs3.getRow();
					rs3.beforeFirst();
					System.out.println(size);
					mv3_actors = new String[size];
					String tempDir = ""; // "A,B,C" -> mv1_director = tempDir.split(",");
					String tempGen = "";
					String tempYear = "";
					String tempRate = "";
					// distinct(nconst), genres, directors, startYear, averageRating
					for (int i = 0; i < size; i++) {
						if (rs3.next()) {
							mv3_actors[i] = rs3.getString(1);
							tempGen = rs3.getString(2);
							tempDir = rs3.getString(3);
							tempYear = rs3.getString(4);
							tempRate = rs3.getString(5);
							// print every column
						}
					}

					mv3_year = Integer.parseInt(tempYear);
					mv3_rate = Double.parseDouble(tempRate);
					mv3_director = tempDir.split(",", -1);
					mv3_genre = tempGen.split(",", -1);

					print(rs3, 0);
				} catch (SQLException e) {
					e.printStackTrace();
				}

				// String str = rs1.getString(3);

				// These are the result of the Algorithm Downbelow
				String[] Actor;
				String[] Genre;
				String[] Director;
				int Year;
				double Rating;

				// do nothing
				// uncomment when done
				System.out.println("Similarities Found ");
				System.out.println("------------------- ");
				Actor = findSimilarities0(mv1_actors, mv2_actors, mv3_actors);
				System.out.print("Actor/s: ");
				for (int i = 0; i < Actor.length; i++)
					System.out.print(Actor[i] + ", ");
				System.out.println("");
				Director = findSimilarities0(mv1_director, mv2_director, mv3_director);
				System.out.print("Director/s: " + Director.length);
				for (int i = 0; i < Director.length; i++)
					System.out.print(Director[i] + ", ");
				System.out.println("");
				Genre = findSimilarities0(mv1_genre, mv2_genre, mv3_genre);
				System.out.print("Genre: ");
				for (int i = 0; i < Genre.length; i++)
					System.out.print(Genre[i] + ", ");
				System.out.println("");
				Rating = Rating(mv1_rate, mv2_rate, mv3_rate);
				System.out.println("Rate: [" + Rating + " - " + (Rating + 1) + "]");
				Year = Year(mv1_year, mv2_year, mv3_year);
				System.out.println("Year: [" + Year + " - " + (Year + 5) + "]");
				int counter = 0;
				if (Actor.length > 0)
					counter++;
				if (Director.length > 0)
					counter++;
				if (Genre.length > 0)
					counter++;
				System.out.println("\n\nRecommendation:" + counter);
				ResultSet rs;
				if (counter == 3) {
					try {
						for(int i=0; i<Actor.length ;i++) {
							rs = stmt.executeQuery("select distinct(primaryTitle)from moviedata where nconst='" + Actor[i]
									+ "' and  directors like '%" + Director[0] + "%' and genres like '%" + Genre[0] + "%'"
									+ " and primaryTitle <> '" + MV1 + "' and primaryTitle <> '" + MV2
									+ "' and primaryTitle <> '" + MV3 + "' ");
							print(rs, 1);}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else if (counter == 2) {
					if (Actor.length > 0 && Director.length > 0) {
						for(int i=0; i<Actor.length ;i++) {
							rs = stmt.executeQuery("select distinct(primaryTitle)from moviedata where nconst='" + Actor[i]
									+ "' and  directors like '%" + Director[0] + "%'" + " and primaryTitle <> '" + MV1
									+ "' and primaryTitle <> '" + MV2 + "' and primaryTitle <> '" + MV3 + "' ");
							print(rs, 1);}
					} else if (Actor.length > 0 && Genre.length > 0) {
						for(int i=0; i<Actor.length ;i++) {
							rs = stmt.executeQuery("select distinct(primaryTitle)from moviedata where nconst='" + Actor[i]
									+ "' and genres like '%" + Genre[0] + "%' " + "and primaryTitle <> '" + MV1
									+ "' and primaryTitle <> '" + MV2 + "' and primaryTitle <> '" + MV3 + "' ");
							print(rs, 1);}
					} else if (Director.length > 0 && Genre.length > 0) {
						rs = stmt.executeQuery("select distinct(primaryTitle)from moviedata where directors like '%"
								+ Director[0] + "%' and genres like '%" + Genre[0] + "%' " + "and primaryTitle <> '" + MV1
								+ "' and primaryTitle <> '" + MV2 + "' and primaryTitle <> '" + MV3 + "' ");
						print(rs, 1);
					}
				} else if (counter == 1) {
					if (Actor.length > 0) {
						for(int i=0; i<Actor.length ;i++) {
							rs = stmt.executeQuery("select distinct(primaryTitle)from moviedata where nconst='" + Actor[i]
									+ "' and ((startYear between '"+Year+"' and '"+Year+"'+5) or (averageRating between '"+Rating+"' and '"+Rating+"'+1))and primaryTitle <> '" + MV1 + "' and primaryTitle <> '" + MV2
									+ "' and primaryTitle <> '" + MV3 + "' ");
							print(rs, 1);}
					} else if (Director.length > 0) {
						rs = stmt.executeQuery("select distinct(primaryTitle)from moviedata where directors like '%"+ Director[0] 
								+ "%' " + " and ((startYear between '"+Year+"' and '"+Year+"'+5) or (averageRating between '"+Rating+"' and '"+Rating+"'+1))and primaryTitle <> '" + MV1 + "' and primaryTitle <> '" + MV2
								+ "' and primaryTitle <> '" + MV3 + "' ");
						print(rs, 1);
					} else if (Genre.length > 0) {
						rs = stmt.executeQuery("select distinct(primaryTitle)from moviedata where genres like '%" + Genre[0]
								+ "%' " + " and ((startYear between '"+Year+"' and '"+Year+"'+5) or (averageRating between '"+Rating+"' and '"+Rating+"'+1))and primaryTitle <> '" + MV1 + "' and primaryTitle <> '" + MV2
								+ "' and primaryTitle <> '" + MV3 + "' ");
						print(rs, 1);	
					}
					else {
						rs = stmt.executeQuery("select distinct(primaryTitle)from moviedata where directors like '%"+ Director[0] 
								+ "%' " + " or genres like '%" + Genre[0] + "%'"+" or nconst='"+Actor[0]+"' and primaryTitle <> '" + MV1 + "' and primaryTitle <> '" + MV2
								+ "' and primaryTitle <> '" + MV3 + "' ");
					}
				}
				// use these values in quries -> String[] Actor, String[] Genre, String[]
				// Director, int Year, double Rating;
				// Do quries here

				// -----------------------------
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		}

		finally {

			// 5 Exit and close
			if (conn != null) {
				try {
					conn.close();
					return true;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	*/
	public void actionPerformed(ActionEvent evt) {
		// Get the String entered into the TextField tfInput, convert to int
		//int numberIn = Integer.parseInt(tfInput.getText());
		//int numberIn1 = Integer.parseInt(tfInput1.getText());
		//int numberIn2 = Integer.parseInt(tfInput2.getText());
		//		       += numberIn;      // Accumulate numbers entered into sum
		//tfInput.setText("");  // Clear input TextField
		Connection conn = null;
		try {
			// 1 Import Drivers
			Class.forName("com.mysql.jdbc.Driver");
			// 2 Connect to DB
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testv3?"
					+ "user=root&password=1993526ats&useUnicode=true&characterEncoding=UTF8&useSSL=false");

			// fill in the following values for alg. ***************
			String[] mv1_actors = null; // no split
			String[] mv1_genre = null; // split
			String[] mv1_director = null; // split
			int mv1_year = -1; // split
			double mv1_rate = -1; // split

			String[] mv2_actors = null;
			String[] mv2_genre = null;
			String[] mv2_director = null;
			int mv2_year = -1;
			double mv2_rate = -1;

			String[] mv3_actors = null;
			String[] mv3_genre = null;
			String[] mv3_director = null;
			int mv3_year = -1;
			double mv3_rate = -1;

			// GET MOVIES NAME FROM CONNER MV1, MV2, MV3
			String MV1 = tfInput.getText();
			String MV2 = tfInput1.getText();
			String MV3 = tfInput2.getText();
			/*
		String MV1 = "The Naked Monster";
		String MV2 = "Just Go With It";
		String MV3 = "I Now Pronounce You Chuck & Larry";

		String MV1 = "Horrible Bosses 2";
		String MV2 = "Just Go with It";
		String MV3 = "The Break-Up";

		String MV1 = "The Mummy";
		String MV2 = "Mission: Impossible - Ghost Protocol";
		String MV3 = "Tropic Thunder";

		String MV1 = "Open Range";
		String MV2 = "3 Days to Kill";
		String MV3 = "3000 Miles to Graceland";

		String MV1 = "Killer Bud";
		String MV2 = "The Beatnicks";
		String MV3 = "Hollywood Palms";
			 */

			// check the movie exist

			String queryCheck1 ="select distinct(primaryTitle) from moviedata where primaryTitle=?";
			PreparedStatement st1 =conn.prepareStatement(queryCheck1);
			st1.setString(1, MV1);
			ResultSet qc1= st1.executeQuery();

			if(qc1.next()) {
				tfOutput.setText("loading");
			}
			else {
				tfOutput.setText("please reenter");
			}
			//check for the second movie
			PreparedStatement st2 =conn.prepareStatement(queryCheck1);
			st2.setString(1, MV2);
			ResultSet qc2= st2.executeQuery();
			if(qc2.next()) {
				tfOutput1.setText("loading");
			}
			else {
				tfOutput1.setText("please reenter");
			}
			//check for the Third movie

			PreparedStatement st3 =conn.prepareStatement(queryCheck1);
			st3.setString(1, MV3);
			ResultSet qc3= st3.executeQuery();
			if(qc3.next()) {
				tfOutput2.setText("loading");
			}
			else {
				tfOutput2.setText("please reenter");
			}
			if(qc1.next()&&qc2.next()&&qc3.next());
			{



				Statement stmt = conn.createStatement();
				ResultSet rs1;

				try {
					rs1 = stmt.executeQuery(
							"select distinct(nconst), genres, directors, startYear, averageRating from moviedata where primaryTitle='"
									+ MV1 + "' and (category = 'actor' or category = 'actress')");
					// fill

					rs1.last();
					int size = rs1.getRow();
					rs1.beforeFirst();
					mv1_actors = new String[size];
					String tempDir = ""; // "A,B,C" -> mv1_director = tempDir.split(",");
					String tempGen = "";
					String tempYear = "";
					String tempRate = "";
					// distinct(nconst), genres, directors, startYear, averageRating
					for (int i = 0; i < size; i++) {
						if (rs1.next()) {
							mv1_actors[i] = rs1.getString(1);
							tempGen = rs1.getString(2);
							tempDir = rs1.getString(3);
							tempYear = rs1.getString(4);
							tempRate = rs1.getString(5);
							// print every column
						}
					}

					mv1_year = Integer.parseInt(tempYear);
					mv1_rate = Double.parseDouble(tempRate);
					mv1_director = tempDir.split(",", -1);
					mv1_genre = tempGen.split(",", -1);

					print(rs1, 0);
				} catch (SQLException e) {
					e.printStackTrace();
				}

				ResultSet rs2;
				try {
					// Printing the name of the employee without duplications made by inner join.
					rs2 = stmt.executeQuery(
							"select distinct(nconst), genres, directors, startYear, averageRating from moviedata where primaryTitle='"
									+ MV2 + "' and (category = 'actor' or category = 'actress')");
					rs2.last();
					int size = rs2.getRow();
					rs2.beforeFirst();
					mv2_actors = new String[size];
					String tempDir = ""; // "A,B,C" -> mv1_director = tempDir.split(",");
					String tempGen = "";
					String tempYear = "";
					String tempRate = "";
					// distinct(nconst), genres, directors, startYear, averageRating
					for (int i = 0; i < size; i++) {
						if (rs2.next()) {
							mv2_actors[i] = rs2.getString(1);
							tempGen = rs2.getString(2);
							tempDir = rs2.getString(3);
							tempYear = rs2.getString(4);
							tempRate = rs2.getString(5);
							// print every column
						}
					}

					mv2_year = Integer.parseInt(tempYear);
					mv2_rate = Double.parseDouble(tempRate);
					mv2_director = tempDir.split(",", -1);
					mv2_genre = tempGen.split(",", -1);

					print(rs2, 0);
				} catch (SQLException e) {
					e.printStackTrace();
				}

				ResultSet rs3;
				try {
					// Printing the name of the employee without duplications made by inner join.
					rs3 = stmt.executeQuery(
							"select distinct(nconst), genres, directors, startYear, averageRating from moviedata where primaryTitle='"
									+ MV3 + "' and (category = 'actor' or category = 'actress')");
					rs3.last();
					int size = rs3.getRow();
					rs3.beforeFirst();
					System.out.println(size);
					mv3_actors = new String[size];
					String tempDir = ""; // "A,B,C" -> mv1_director = tempDir.split(",");
					String tempGen = "";
					String tempYear = "";
					String tempRate = "";
					// distinct(nconst), genres, directors, startYear, averageRating
					for (int i = 0; i < size; i++) {
						if (rs3.next()) {
							mv3_actors[i] = rs3.getString(1);
							tempGen = rs3.getString(2);
							tempDir = rs3.getString(3);
							tempYear = rs3.getString(4);
							tempRate = rs3.getString(5);
							// print every column
						}
					}

					mv3_year = Integer.parseInt(tempYear);
					mv3_rate = Double.parseDouble(tempRate);
					mv3_director = tempDir.split(",", -1);
					mv3_genre = tempGen.split(",", -1);

					print(rs3, 0);
				} catch (SQLException e) {
					e.printStackTrace();
				}

				// String str = rs1.getString(3);

				// These are the result of the Algorithm Downbelow
				String[] Actor;
				String[] Genre;
				String[] Director;
				int Year;
				double Rating;

				// do nothing
				// uncomment when done
				System.out.println("Similarities Found ");
				System.out.println("------------------- ");
				Actor = findSimilarities0(mv1_actors, mv2_actors, mv3_actors);
				System.out.print("Actor/s: ");
				for (int i = 0; i < Actor.length; i++)
					System.out.print(Actor[i] + ", ");
				System.out.println("");
				Director = findSimilarities0(mv1_director, mv2_director, mv3_director);
				System.out.print("Director/s: " + Director.length);
				for (int i = 0; i < Director.length; i++)
					System.out.print(Director[i] + ", ");
				System.out.println("");
				Genre = findSimilarities0(mv1_genre, mv2_genre, mv3_genre);
				System.out.print("Genre: ");
				for (int i = 0; i < Genre.length; i++)
					System.out.print(Genre[i] + ", ");
				System.out.println("");
				Rating = Rating(mv1_rate, mv2_rate, mv3_rate);
				System.out.println("Rate: [" + Rating + " - " + (Rating + 1) + "]");
				Year = Year(mv1_year, mv2_year, mv3_year);
				System.out.println("Year: [" + Year + " - " + (Year + 5) + "]");
				int counter = 0;
				if (Actor.length > 0)
					counter++;
				if (Director.length > 0)
					counter++;
				if (Genre.length > 0)
					counter++;
				System.out.println("\n\nRecommendation:" + counter);
				ResultSet rs;
				if (counter == 3) {
					try {
						for(int i=0; i<Actor.length ;i++) {
							rs = stmt.executeQuery("select distinct(primaryTitle)from moviedata where nconst='" + Actor[i]
									+ "' and  directors like '%" + Director[0] + "%' and genres like '%" + Genre[0] + "%'"
									+ " and primaryTitle <> '" + MV1 + "' and primaryTitle <> '" + MV2
									+ "' and primaryTitle <> '" + MV3 + "' ");
							print(rs, 1);}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else if (counter == 2) {
					if (Actor.length > 0 && Director.length > 0) {
						for(int i=0; i<Actor.length ;i++) {
							rs = stmt.executeQuery("select distinct(primaryTitle)from moviedata where nconst='" + Actor[i]
									+ "' and  directors like '%" + Director[0] + "%'" + " and primaryTitle <> '" + MV1
									+ "' and primaryTitle <> '" + MV2 + "' and primaryTitle <> '" + MV3 + "' ");
							print(rs, 1);}
					} else if (Actor.length > 0 && Genre.length > 0) {
						for(int i=0; i<Actor.length ;i++) {
							rs = stmt.executeQuery("select distinct(primaryTitle)from moviedata where nconst='" + Actor[i]
									+ "' and genres like '%" + Genre[0] + "%' " + "and primaryTitle <> '" + MV1
									+ "' and primaryTitle <> '" + MV2 + "' and primaryTitle <> '" + MV3 + "' ");
							print(rs, 1);}
					} else if (Director.length > 0 && Genre.length > 0) {
						rs = stmt.executeQuery("select distinct(primaryTitle)from moviedata where directors like '%"
								+ Director[0] + "%' and genres like '%" + Genre[0] + "%' " + "and primaryTitle <> '" + MV1
								+ "' and primaryTitle <> '" + MV2 + "' and primaryTitle <> '" + MV3 + "' ");
						print(rs, 1);
					}
				} else if (counter == 1) {
					if (Actor.length > 0) {
						for(int i=0; i<Actor.length ;i++) {
							rs = stmt.executeQuery("select distinct(primaryTitle)from moviedata where nconst='" + Actor[i]
									+ "' and ((startYear between '"+Year+"' and '"+Year+"'+5) or (averageRating between '"+Rating+"' and '"+Rating+"'+1))and primaryTitle <> '" + MV1 + "' and primaryTitle <> '" + MV2
									+ "' and primaryTitle <> '" + MV3 + "' ");
							print(rs, 1);}
					} else if (Director.length > 0) {
						rs = stmt.executeQuery("select distinct(primaryTitle)from moviedata where directors like '%"+ Director[0] 
								+ "%' " + " and ((startYear between '"+Year+"' and '"+Year+"'+5) or (averageRating between '"+Rating+"' and '"+Rating+"'+1))and primaryTitle <> '" + MV1 + "' and primaryTitle <> '" + MV2
								+ "' and primaryTitle <> '" + MV3 + "' ");
						print(rs, 1);
					} else if (Genre.length > 0) {
						rs = stmt.executeQuery("select distinct(primaryTitle)from moviedata where genres like '%" + Genre[0]
								+ "%' " + " and ((startYear between '"+Year+"' and '"+Year+"'+5) or (averageRating between '"+Rating+"' and '"+Rating+"'+1))and primaryTitle <> '" + MV1 + "' and primaryTitle <> '" + MV2
								+ "' and primaryTitle <> '" + MV3 + "' ");
						print(rs, 1);	
					}
					else {
						rs = stmt.executeQuery("select distinct(primaryTitle)from moviedata where directors like '%"+ Director[0] 
								+ "%' " + " or genres like '%" + Genre[0] + "%'"+" or nconst='"+Actor[0]+"' and primaryTitle <> '" + MV1 + "' and primaryTitle <> '" + MV2
								+ "' and primaryTitle <> '" + MV3 + "' ");
					}
				}
				// use these values in quries -> String[] Actor, String[] Genre, String[]
				// Director, int Year, double Rating;
				// Do quries here

				// -----------------------------
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		}

		finally {

			// 5 Exit and close
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		 // Display sum on the output TextField
		// convert int to String
	}
	public static void main(String[] args) {
		//connectionMySqlDemo();
		new jdbcDemo();
	}
}
