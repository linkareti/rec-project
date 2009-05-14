/*
 * im_analizer.java
 *
 * Created on 24 de Agosto de 2004, 2:16
 */

package pt.utl.ist.elab.driver.thomson;


import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import java.util.Vector;

import javax.media.Buffer;
import javax.media.CannotRealizeException;
import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.media.control.FormatControl;
import javax.media.control.FrameGrabbingControl;
import javax.media.format.VideoFormat;
import javax.media.protocol.CaptureDevice;
import javax.media.protocol.DataSource;
import javax.media.util.BufferToImage;


// import com.sun.media.vfw.VFWCapture;         // JMF 2.1.1c version
//import com.sun.media.protocol.v4l.VCapture;   // JMF 2.1.1e version

/**
 *
 * @author  ivo
 */
public class GravadorVideo
{
    
    protected java.awt.Image[] im_temp = null;
    protected java.awt.Image[] video = new java.awt.Image[9];
    protected int n_frames = 0;
    protected boolean init =false;
    protected boolean video_done = false;
    
    protected Component visualComponent = null;
    
    protected Player player = null;
    protected CaptureDeviceInfo webCamDeviceInfo = null;
    protected MediaLocator ml = null;
    protected Dimension imageSize = null;
    protected FormatControl formatControl = null;
    
    protected VideoFormat currentFormat = null;
    public Format[] videoFormats = null;
    private javax.swing.Timer timer2; //timer para tirar fotos ao sitema
    
    private final int WIDTH = 640;
    private final int HEIGHT = 480;
    
    /** Creates a new instance of im_analizer */
    public GravadorVideo()
    {
        
        timer2 = new javax.swing.Timer(3000,  new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                timer2OnTime(evt);
            }
        });
        
        timer2.setRepeats(true);
    }
    
    public int addFrame(java.awt.Image img)
    {
        if(video_done || video.length <= n_frames)
            return n_frames;
               
        if (img!=null)
        {
            video[n_frames] = img;
            n_frames++;
        }
        if (n_frames  > 9)
        {
            n_frames--;
            timer2.stop();
            video_done = true;
        }
        
        return (n_frames);
    }
    
    public java.awt.Image[] getVideo()
    {
        java.awt.Image[] toRet = video;
        video = new java.awt.Image[9];
        return toRet;
    }
    
    public java.awt.Image getFrame(int numero_do_frame)
    {
        
        if(numero_do_frame < n_frames)
        {
            return (video[numero_do_frame - 1]);
        }
        
        return (null);
    }
    
    public int getNumeroFrames()
    {
        
        return(n_frames);
    }
    
    public void comecarVideo()
    {
        
        n_frames = 0;
        video_done = false;
        timer2.stop();
        timer2.start();
    }
    
    public java.awt.Image tirarFoto()
    {
        
        java.awt.Image img = grabFrameImage( );
        if ( img == null )
        {
            System.err.println("Error : Could not grab frame");
        }
        
        return (img);
    }
    
    public boolean iniciarWebcam()
    {
        
        try
        {
            if ( !initialise() )
            {
                System.out.println("Web Cam not detected / initialised");
            }
            else init = true;
            System.out.println("Web Cam initialised!!!");
        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
        }
        
        return (init);
    }
    
    public void fecharWebcam()
    {
        
        playerClose();
        init = false;
    }
    
    private void timer2OnTime(java.awt.event.ActionEvent evt)
    {
        addFrame(tirarFoto());
    }
    
    public boolean initialise( )
    throws Exception
    {
        return ( initialise( autoDetect() ) );
    }
    
    private DataSource ds = null;
    public boolean initialise( CaptureDeviceInfo _deviceInfo )
    throws Exception
    {
        webCamDeviceInfo = _deviceInfo;
        
        if ( webCamDeviceInfo != null )
        {
            try
            {
                videoFormats = webCamDeviceInfo.getFormats();
                
                VideoFormat wantedFormat = null;
                int w = 0;
                int h = 0;
                for ( int i=0; i<videoFormats.length; i++ )
                {
                    wantedFormat = (VideoFormat)videoFormats[i];
                    w = (int)wantedFormat.getSize().getWidth();
                    h = (int)wantedFormat.getSize().getHeight();
                    
                    if(w == WIDTH && h == HEIGHT)
                        break;                    
                }
                                
                
                ml = webCamDeviceInfo.getLocator();
                if ( ml != null )
                {
                    ds = Manager.createDataSource(ml);
                    
                    FormatControl[] formatControls = ((CaptureDevice)ds).getFormatControls();
                    
                    Format finalFormat = null;
                    for (int i = 0; i <formatControls.length; i++)
                    {
                        if (formatControls == null)
                            continue;
                        if ((finalFormat = formatControls[i].setFormat(wantedFormat)) != null)
                            break;
                    }

                    if(finalFormat == null)
                        System.out.println("Couldn't set the desired format...using the default!");
                    
                    ds.connect();
                    player = Manager.createRealizedPlayer(ds);
                    player.start();
                    return true;
                }
                else
                {
                    System.err.println("Error : No MediaLocator for " + webCamDeviceInfo.getName() );
                    return ( false );
                }
            }
            catch ( IOException ioEx )
            {
                ioEx.printStackTrace();
                return ( false );
            }
            catch ( NoPlayerException npex )
            {
                npex.printStackTrace();
                return ( false );
            }
            catch ( CannotRealizeException nre )
            {
                nre.printStackTrace();
                return ( false );
            }
        }
        else
        {
            return ( false );
        }
    }
    
    public CaptureDeviceInfo autoDetect( )
    {
        Vector list = CaptureDeviceManager.getDeviceList( null );
        CaptureDeviceInfo devInfo = null;
        
        if ( list != null )
        {
            String name;
            
            for ( int i=0; i<list.size(); i++ )
            {
                devInfo = (CaptureDeviceInfo)list.elementAt( i );
                name = devInfo.getName();
                
                if ( name.startsWith("v4l:") )
                {
                    break;
                }
            }
            
            if ( devInfo != null && devInfo.getName().startsWith("v4l:") )
            {
                return ( devInfo );
            }
        }
        
        return ( null );
        
    }
    
    public void deviceInfo( )
    {
        if ( webCamDeviceInfo != null )
        {
            Format[] formats = webCamDeviceInfo.getFormats();
            
            if ( ( formats != null ) && ( formats.length > 0 ) )
            {
            }
            
            for ( int i=0; i<formats.length; i++ )
            {
                Format aFormat = formats[i];
                if ( aFormat instanceof VideoFormat )
                {
                    Dimension dim = ((VideoFormat)aFormat).getSize();
                }
            }
        }
        else
        {
            System.out.println("Error : No web cam detected");
        }
    }
    
    
    protected void finalize( ) throws Throwable
    {
        playerClose();
        super.finalize();
    }
    
    public void playerClose( )
    {
        if ( player != null )
        {
            player.close();
            player.deallocate();
            player = null;
        }
    }
    
    
    public Buffer grabFrameBuffer( )
    {
        if ( player != null )
        {
            
            FrameGrabbingControl fgc = (FrameGrabbingControl)player.getControl( "javax.media.control.FrameGrabbingControl" );
            
            if ( fgc != null )
            {
                return ( fgc.grabFrame() );
            }
            else
            {
                System.err.println("Error : FrameGrabbingControl is null");
                return ( null );
            }
        }
        else
        {
            System.err.println("Error : Player is null");
            return ( null );
        }
    }
    
    public Image grabFrameImage( )
    {
        Buffer buffer = grabFrameBuffer();
        if ( buffer != null )
        {
            // Convert it to an image
            BufferToImage btoi = new BufferToImage( (VideoFormat)buffer.getFormat() );
            if ( btoi != null )
            {
                Image image = btoi.createImage( buffer );
                if ( image != null )
                {
                    return ( image );
                }
                else
                {
                    System.err.println("Error : BufferToImage cannot convert buffer");
                    return ( null );
                }
            }
            else
            {
                System.err.println("Error : cannot create BufferToImage instance");
                return ( null );
            }
        }
        else
        {
            System.out.println("Error : Buffer grabbed is null");
            return ( null );
        }
    }    
}