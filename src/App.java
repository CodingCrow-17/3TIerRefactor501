import java.awt.EventQueue;

import controller.DatabaseUtilities;
import controller.ReadLogic;
import controller.WriteLogic;

public class App {
    public static void main(String[] args) throws Exception {
        connectToDatabase();
        EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					DatabaseGUI frame = new DatabaseGUI();
					frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
    }

    	private static void connectToDatabase(){
		DatabaseUtilities utilities = DatabaseUtilities.createDatabaseUtilities();

        ReadLogic.getInstance(utilities);
		WriteLogic.getInstance(utilities).linkReadLogic();
	}
}
