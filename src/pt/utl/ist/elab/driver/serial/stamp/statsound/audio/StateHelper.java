/*
 * StateHelper.java
 *
 * Created on 26 de Marco de 2003, 17:13
 */

package pt.utl.ist.elab.driver.serial.stamp.statsound.audio;
import javax.media.ConfigureCompleteEvent;
import javax.media.ControllerClosedEvent;
import javax.media.ControllerErrorEvent;
import javax.media.ControllerEvent;
import javax.media.EndOfMediaEvent;
import javax.media.Player;
import javax.media.PrefetchCompleteEvent;
import javax.media.Processor;
import javax.media.RealizeCompleteEvent;
/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
 
 public class StateHelper implements javax.media.ControllerListener {
 
     Player player = null;
     boolean configured = false;
     boolean realized = false;
     boolean prefetched = false;
     boolean eom = false;
     boolean failed = false;
     boolean closed = false;
     
     public StateHelper(Player p) {
 	 player = p;
 	 p.addControllerListener(this);
     }
 
     public boolean configure(int timeOutMillis) {
 	 long startTime = System.currentTimeMillis();
 	 synchronized (this) {
 	     if (player instanceof Processor)
 	 	 ((Processor)player).configure();
 	     else
 	 	 return false;

 
 	     while (!configured && !failed) {
 	 	 try {
 	 	     wait(timeOutMillis);
 	 	 } catch (InterruptedException ie) {
 	 	 }
 	 	 if (System.currentTimeMillis() - startTime > timeOutMillis)
 	 	     break;
 	     }
 	 }
 	 return configured;
     }
     
     public boolean realize(int timeOutMillis) {
 	 long startTime = System.currentTimeMillis();
 	 synchronized (this) {
 	     player.realize();
 	     while (!realized && !failed) {
 	 	 try {
 	 	     wait(timeOutMillis);
 	 	 } catch (InterruptedException ie) {
 	 	 }
 	 	 if (System.currentTimeMillis() - startTime > timeOutMillis)
 	 	     break;
 	     }
 	 }
 	 return realized;
     }
 
     public boolean prefetch(int timeOutMillis) {
 	 long startTime = System.currentTimeMillis();
 	 synchronized (this) {
 	     player.prefetch();
 	     while (!prefetched && !failed) {
 	 	 try {
 	 	     wait(timeOutMillis);
 	 	 } catch (InterruptedException ie) {
 	 	 }
 	 	 if (System.currentTimeMillis() - startTime > timeOutMillis)
 	 	     break;
 	     }
 	 }
 	 return prefetched && !failed;
     }
     public boolean playToEndOfMedia(int timeOutMillis) {
 	 long startTime = System.currentTimeMillis();
 	 eom = false;
 	 synchronized (this) {
 	     player.start();

 
 	     while (!eom && !failed) {
 	 	 try {
 	 	     wait(timeOutMillis);
 	 	 } catch (InterruptedException ie) {
 	 	 }
 	 	 if (System.currentTimeMillis() - startTime > timeOutMillis)
 	 	     break;
 	     }
 	 }
 	 return eom && !failed;
     }
 
     public void close() {
 	 synchronized (this) {
 	     player.close();
 	     while (!closed) {
 	 	 try {
 	 	     wait(100);
 	 	 } catch (InterruptedException ie) {
 	 	 }
 	     }
 	 }
 	 player.removeControllerListener(this);
     }
 
     public synchronized void controllerUpdate(ControllerEvent ce) {
 	 if (ce instanceof RealizeCompleteEvent) {
 	     realized = true;
 	 } else if (ce instanceof ConfigureCompleteEvent) {
 	     configured = true;
 	 } else if (ce instanceof PrefetchCompleteEvent) {
 	     prefetched = true;
 	 } else if (ce instanceof EndOfMediaEvent) {
 	     eom = true;
 	 } else if (ce instanceof ControllerErrorEvent) {
 	     failed = true;
 	 } else if (ce instanceof ControllerClosedEvent) {
 	     closed = true;
 	 } else {
 	     return;
 	 }
 	 notifyAll();
     }
 }

 
