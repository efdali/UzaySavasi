import javax.swing.JFrame;

public class SavasEkrani extends JFrame{

	public static void main(String[] args) {
		
		SavasEkrani ekran=new SavasEkrani();
		ekran.setTitle("Uzay Savaþlarý");
		ekran.setResizable(false);
		ekran.setFocusable(false);
		ekran.setSize(800,600);
		ekran.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		SavasOyunu oyun = new SavasOyunu();
		oyun.requestFocus();
		oyun.setFocusable(true);
		oyun.addKeyListener(oyun);
		oyun.setFocusTraversalKeysEnabled(false);
		ekran.add(oyun);
		ekran.setVisible(true);

	}

}
