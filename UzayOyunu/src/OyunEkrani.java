import java.awt.Component;
import java.awt.HeadlessException;

import javax.swing.JFrame;

public class OyunEkrani extends JFrame{

	public OyunEkrani(String title) throws HeadlessException {
		super(title);
		
	}

	public static void main(String[] args) {
		
		OyunEkrani ekran = new OyunEkrani("Uzay Oyunu"); //JFrame'i başlıklı yaptığımız için constructor bu şekilde oluşuyor.
		ekran.setResizable(false); //Ekranın ölçülerini dışardan değiştirmemek için
		ekran.setFocusable(false); //Bütün işlemlerimiz JFrame'de değil de JPanel'de olmasını istiyoruz. Dolayısıyla
		                           //JFrame focusunu false yaptık!
		ekran.setSize(800,600);
		
		ekran.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Oyun oyun = new Oyun();
		
		oyun.requestFocus(); //Klavye işlemlerinin odağını JPanel'e veriyoruz.
		
		oyun.addKeyListener(oyun); //KeyListener interfaceni çağırıyoruz. Bu interface klavye işlemlerimizi anlamamızı
		                           //sağlar!
		
		oyun.setFocusable(true); //Focusu JPanel'e verdik.
		
		oyun.setFocusTraversalKeysEnabled(false); //Klavye işlemlerinin JPanel'in anlaması için gerekli olan bir metodumuz.
		
		ekran.add(oyun);//JPanel'imizi JFrame'imize eklemiş olduk.
		
		ekran.setVisible(true);
		
		//BU FONKSIYONLARIN SIRASI ÇOK ÖNEMLİ
		
		
		
		
		
		
		
	}
	
	
	
	
}
