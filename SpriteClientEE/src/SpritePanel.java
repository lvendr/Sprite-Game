
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.JPanel;

import cst8218.nguy0770.entity.Sprite;
import cst8218.nguy0770.game.SpriteSessionRemote;

public class SpritePanel extends JPanel implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<Sprite> sprites;
	SpriteSessionRemote session;

	public SpritePanel(SpriteSessionRemote session) {
		this.session = session;
		this.addMouseListener(new Mouse());
	}

	@Override
	public void run() {
		System.out.println("now animating...");
		try {
			while (true) {
				try {
					sprites = session.getSpriteList();
				} catch (javax.ejb.NoSuchEJBException e) {
					System.out.println("Lost contact with server, exiting...");
					System.exit(1);
				}
				repaint();
				// sleep while waiting to display the next frame of the animation
				try {
					Thread.sleep(200); // wake up roughly 25 frames per second
				} catch (InterruptedException exception) {
					exception.printStackTrace();
				}
			}
		} catch (RemoteException e) {
			System.out.println("game over? exiting...");
		}
	}

	private class Mouse extends MouseAdapter {
		@Override
		public void mousePressed(final MouseEvent event) {
			try {
				System.out.println("Creating a new sprite");
				session.newSprite(event);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (sprites != null) {
			for (Sprite s : sprites) {
				s.draw(g);
			}
		}
	}
}
