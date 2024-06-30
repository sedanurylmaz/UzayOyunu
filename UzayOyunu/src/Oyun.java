import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

class Ates{
	
	private int x;
	private int y;
	
	public Ates(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	
	
	
}

public class Oyun extends JPanel implements KeyListener, ActionListener{
	//Klavye işlemlerimizi anlaması için KeyListener interface'ini implemente ediyoruz!
	//Toplarımızı falan hareket ettirmemiz içinde ActionListener interface'ini implemente etmemiz gerekiyordu!

javax.swing.Timer timer = new javax.swing.Timer(5, this); //5 milisaniye'de çalışacağını gösterdik ve ActionListener'den actionperf. metodunu 
                                                          //kullanmak için this diyor ve böyle yapıyoruz...
	
private int gecen_sure = 0;
private int harcanan_ates = 0;

private BufferedImage image;

private ArrayList<Ates> atesler = new ArrayList<Ates>();//Birden fazla yaptığımız ateşleri ArrayList'imizde
                                                        //tutuyoruz!
private int atesdirY = 3;

private int topX = 0;

private int topdirX = 2;

private int uzayGemisiX = 0;

private int dirUzayX = 20;

public boolean kontrolEt() {
	
	for(Ates ates : atesler)
	{
		
		//Topla ateşlerimizin çarpışıp çarpışmadığını kontrol ediyoruz...
		if(new Rectangle(ates.getX(), ates.getY(), 10, 20).intersects(new Rectangle(topX, 0, 20, 20))) {
			
			return true;
		}
	}
	
	return false;
	
}

public Oyun() {
	
	try {
		image = ImageIO.read(new FileImageInputStream(new File("uzaygemisi.png.png")));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	setBackground(Color.black);
	
	timer.start();
	
}

@Override
public void repaint() {
	
	super.repaint();
	
}

@Override
public void paint(Graphics g) {
	
	gecen_sure += 5;
	
	super.paint(g);
	
	g.setColor(Color.red);
	
	g.fillOval(topX, 0, 20, 20); //Daire şeklinde top oluşturduk. (X EKSENI(topun konumu), Y EKSENI, WIDHT, HEIGHT) top x ekseninde hareket
	                             //edeceği için y'sini 0 aldık.
	
	g.drawImage(image, uzayGemisiX, 490, image.getWidth() / 10, image.getHeight() / 10, this);
	//Uzay gemimizi oluşturduk. (Uzay gemimizi belirttik, X EKSENI, Y EKSENI, WIDHT, HEIGHT, JPanel'in üzerinde oluşacağını belirttik)
	//Uzay gemisi x ekseninde hareket edeceği için y'sini sabit aldık.
    //Uzay gemimizin gerçek genişliği ve yüksekliği çok büyük olduğundan 10'a böldük.
	
	for(Ates ates : atesler) {
		
		if(ates.getY() < 0) {//Sınırları kontrol ediyoruz.
			
			atesler.remove(ates);
		}
	 }
	
     g.setColor(Color.BLUE);
     
     for(Ates ates : atesler)
     {
    	 
    	 g.fillRect(ates.getX(), ates.getY(), 10, 20); //Ateslerimizi olusturuyoruz.(x koordinatı, y koordinatı, width, height)
     }
	
	if(kontrolEt()) { //Eğer çarpışma olmuşsa;
		
		
		timer.stop();//zamanı durduyoruz.
		String message = "Kazandınız...\n"+
		                 "Harcanan Ateş : " + harcanan_ates+
		                 "\nGeçen Süre : " + gecen_sure / 1000.0 + " saniye";
		
		JOptionPane.showMessageDialog(this, message);
		System.exit(0);
		
	}
	
	
	
	
}



	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		//UZAY GEMISINI HAREKET ETTİRME
		
				int c = e.getKeyCode(); //Sağ tuşa bastığımızda uzay gemimiz sağa gidecek, sol tuşa bastığımızda uzay gemimiz sola hareket edecektir.
				
				if(c == KeyEvent.VK_LEFT) {
					
					//Sola basılmışsa
					if(uzayGemisiX <= 0) {
						
						//Yani uzay gemisi sınırın dışına çıkmak istiyorsa;
						uzayGemisiX = 0;//Uzay gemimizin sınırı geçemeyeceğini en sol olarak 0'a gideceğini belirtiyoruz.
					}
					else {
						
						uzayGemisiX -= dirUzayX;
					}
				}
				else if(c == KeyEvent.VK_RIGHT) {
					
					//Sağa basılmışsa
		            if(uzayGemisiX >= 700) {
						
						//Yani uzay gemisi sınırın dışına çıkmak istiyorsa;
						uzayGemisiX = 700;//Uzay gemimizin sınırı geçemeyeceğini en sol olarak 700'e gideceğini belirtiyoruz.
					}
					else {
						
						uzayGemisiX += dirUzayX;
					}
					
				}
				else if(c == KeyEvent.VK_CONTROL) {
					
					atesler.add(new Ates(uzayGemisiX + 56,470)); //Ateş oluşturuyoruz... Ateşimizin çıkacağı yer, uzay gemisinden olacağı için
					                                             //uzay gemisinin bulunduğu yerin koordinatlarına göre belirliyoruz.
					harcanan_ates++;
					
				}
				
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

//ATEŞLERİ HAREKET ETTİRİYORUZ...
		

		
		
		for(Ates ates : atesler)
		{
			
			ates.setY(ates.getY() - atesdirY); //ateşler y koordinat düzleminde hareket edeceği için y'yi ayarlıyoruz.
			//Burada peki neden çıkardık çünkü y'ler(y koordinatı) yukarıdan aşağıya artarken, x'ler soldan sağa doğru artar.
		}
		
		topX += topdirX;//TOPA HAREKET VERİYORUZ...
		
		if(topX >= 750) {

			topdirX = -topdirX; // Sınırları aşmaması için uyguluyoruz. Sağa doğru giderken sınıra yaklaştığı için tersine çeviriyoruz.
		}
		
		if(topX <= 0) {

			topdirX = -topdirX; // Sınırları aşmaması için uyguluyoruz. Sola doğru giderken sınıra yaklaştığı için tersine çeviriyoruz.
		}
		
		repaint(); //repaint'i çağırdığımızda paint metodunu da çağırmış olacağız(bunlar birbirine bağlıymış) ve top hareket edecek. 
		
		
		
		
		
		
		
		
	}

	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
