/*
 * PoissonDataProducer.java
 *
 * Created on June 28, 2005, 2:18 PM
 */

package pt.utl.ist.elab.virtual.driver.poisson;

/**
 *
 * @author  andre
 */

import mpi.*;
import org.opensourcephysics.numerics.*;
import java.util.Date;
import java.net.*;
import java.io.*;

public class PoissonCalculator
{
    
    // utilitary variables
    private int startx , endx;
    private int myRank , numprocs;
    
    // scientific variables
    private double[][][] rho;
    private double[][][] u;
    private double[][][] v;
    private double[][][] initialV;
    private double hx , hy , hz;
    private double diff[] = new double[1];
    private double firstDiff;
    private double diffTmp[] = new double[1];
    private int  Nx;
    private int  Ny;
    private int  Nz;
    private double Epsilon0 = 8.85E-12;
    
    // mpi utilitary variables
    private double[] msgSend;
    private double[] msgRecv;
    private int topNeighbour;
    private int bottomNeighbour;
    
    // calculation time count related variables
    private long startTime;
    private long endTime;
    
    // number of sweeps before exchanging frontiers between processes
    private int MEDIUM_STEPS = 3;
    
    // command comunication codes
    private final int DO_CALCULATION = 0;
    private final int FINISH = -1;
    
    // gui <-> scientific variables
    private String face1FnStr;
    private String face2FnStr;
    private String face3FnStr;
    private String face4FnStr;
    private String face5FnStr;
    private String face6FnStr;
    private String rhoFnStr;
    private String initFnStr;
    private String NxStr;
    private String NyStr;
    private String NzStr;
    
    // Creates a new instance of PoissonDataProducer
    public PoissonCalculator(String[] args) throws MPIException
    {
        MPI.Init( args );
        
        numprocs = MPI.COMM_WORLD.Size();
        myRank = MPI.COMM_WORLD.Rank();
        
        topNeighbour = ( myRank + 1 < numprocs ) ? myRank + 1 : MPI.PROC_NULL;
        bottomNeighbour = ( myRank > 0 ) ? myRank - 1 : MPI.PROC_NULL;
        
        waitCmd();
    }
    
    
    // this functions waits for a request from the root process
    private void waitCmd()
    {
        if ( myRank == 0 )
        {
            try
            {
                ServerSocket serverSocket = new ServerSocket(4444);
                
                while(true)
                {
                    Socket clientSocket = serverSocket.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    
                    face1FnStr = in.readLine().trim();
                    face2FnStr = in.readLine().trim();
                    face3FnStr = in.readLine().trim();
                    face4FnStr = in.readLine().trim();
                    face5FnStr = in.readLine().trim();
                    face6FnStr = in.readLine().trim();
                    rhoFnStr = in.readLine().trim();
                    NxStr = in.readLine().trim();
                    NyStr = in.readLine().trim();
                    NzStr = in.readLine().trim();
                    
                    int[] cmd = new int[1];
                    cmd[0] = DO_CALCULATION;
                    for ( int j = 1 ; j < numprocs ; j++ )
                    {
                        MPI.COMM_WORLD.Send( cmd , 0 , 1 , MPI.INT , j , 0 );
                    }
                    
                    
                    
                    double[][][] result = doCalculation();                                        
                    
                    ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                    oos.writeObject(result);
                    in.close();
                    oos.close();
                }
            }
            catch(MPIException e)
            {
                e.printStackTrace();
            }
            catch(IOException ioe)
            {
                ioe.printStackTrace();
            }
        }
        
        while ( myRank != 0 )
        {
            int[] cmd = new int[1];
            
            try
            {
                MPI.COMM_WORLD.Recv( cmd , 0 , 1 , MPI.INT , 0 , MPI.ANY_TAG );
            }
            catch (MPIException e)
            {
                e.printStackTrace();
            }
            
            switch(cmd[0])
            {
                case FINISH:
                {
                    finishProgram();
                    break;
                }
                
                case DO_CALCULATION:
                {
                    doCalculation();
                    break;
                }
            }
        }
    }
        
    // if root, send the FINISH command to all processes
    // then finalize MPI
    public void finishProgram()
    {
        try
        {
            if ( myRank == 0 )
            {
                int[] cmd = new int[1];
                cmd[0] = FINISH;
                for ( int j = 1 ; j < numprocs ; j++ )
                {
                    MPI.COMM_WORLD.Send( cmd , 0 , 1 , MPI.INT , j , 0 );
                }
            }
            MPI.Finalize();
            System.exit(0);
        }
        
        catch ( mpi.MPIException e )
        {
            if ( myRank == 0 )
            {
                e.printStackTrace();
                /*dialog.updateResultField( "Error in MPI (finalizing)." );
                dialog.updateResultField( e.getMessage() );*/
            }
        }
    }
    
    
    // broadcasts the string that define the boundary and rho functions
    // and does the iteration cicle
    private double[][][] doCalculation()
    {
        int[] cmd = new int[1];
        
        try
        {
            if ( myRank == 0 ) cmd[0] = face1FnStr.length();
            MPI.COMM_WORLD.Bcast( cmd , 0 , 1 , MPI.INT , 0 );
            char[] face1FnChar = new char[cmd[0]];
            
            if ( myRank == 0 ) cmd[0] = face2FnStr.length();
            MPI.COMM_WORLD.Bcast( cmd , 0 , 1 , MPI.INT , 0 );
            char[] face2FnChar = new char[cmd[0]];
            
            if ( myRank == 0 ) cmd[0] = face3FnStr.length();
            MPI.COMM_WORLD.Bcast( cmd , 0 , 1 , MPI.INT , 0 );
            char[] face3FnChar = new char[cmd[0]];
            
            if ( myRank == 0 ) cmd[0] = face4FnStr.length();
            MPI.COMM_WORLD.Bcast( cmd , 0 , 1 , MPI.INT , 0 );
            char[] face4FnChar = new char[cmd[0]];
            
            if ( myRank == 0 ) cmd[0] = face5FnStr.length();
            MPI.COMM_WORLD.Bcast( cmd , 0 , 1 , MPI.INT , 0 );
            char[] face5FnChar = new char[cmd[0]];
            
            if ( myRank == 0 ) cmd[0] = face6FnStr.length();
            MPI.COMM_WORLD.Bcast( cmd , 0 , 1 , MPI.INT , 0 );
            char[] face6FnChar = new char[cmd[0]];
            
            if ( myRank == 0 ) cmd[0] = rhoFnStr.length();
            MPI.COMM_WORLD.Bcast( cmd , 0 , 1 , MPI.INT , 0 );
            char[] rhoFnChar = new char[cmd[0]];
            
            if ( myRank == 0 )
            {
                face1FnStr.getChars( 0 , face1FnStr.length() , face1FnChar , 0 );
                face2FnStr.getChars( 0 , face2FnStr.length() , face2FnChar , 0 );
                face3FnStr.getChars( 0 , face3FnStr.length() , face3FnChar , 0 );
                face4FnStr.getChars( 0 , face4FnStr.length() , face4FnChar , 0 );
                face5FnStr.getChars( 0 , face5FnStr.length() , face5FnChar , 0 );
                face6FnStr.getChars( 0 , face6FnStr.length() , face6FnChar , 0 );
                rhoFnStr.getChars( 0 , rhoFnStr.length() , rhoFnChar , 0 );
            }
            
            MPI.COMM_WORLD.Bcast( face1FnChar , 0 , face1FnChar.length , MPI.CHAR , 0 );
            MPI.COMM_WORLD.Bcast( face2FnChar , 0 , face2FnChar.length , MPI.CHAR , 0 );
            MPI.COMM_WORLD.Bcast( face3FnChar , 0 , face3FnChar.length , MPI.CHAR , 0 );
            MPI.COMM_WORLD.Bcast( face4FnChar , 0 , face4FnChar.length , MPI.CHAR , 0 );
            MPI.COMM_WORLD.Bcast( face5FnChar , 0 , face5FnChar.length , MPI.CHAR , 0 );
            MPI.COMM_WORLD.Bcast( face6FnChar , 0 , face6FnChar.length , MPI.CHAR , 0 );
            MPI.COMM_WORLD.Bcast( rhoFnChar , 0 , rhoFnChar.length , MPI.CHAR , 0 );
            
            face1FnStr = String.copyValueOf( face1FnChar );
            face2FnStr = String.copyValueOf( face2FnChar );
            face3FnStr = String.copyValueOf( face3FnChar );
            face4FnStr = String.copyValueOf( face4FnChar );
            face5FnStr = String.copyValueOf( face5FnChar );
            face6FnStr = String.copyValueOf( face6FnChar );
            rhoFnStr = String.copyValueOf( rhoFnChar );
        }
        
        catch ( mpi.MPIException e )
        {
            if ( myRank == 0 )
            {
                e.printStackTrace();
            }
        }
        
        if (myRank == 0)
        {
            try
            {
                Nx = Integer.parseInt(NxStr);
                Ny = Integer.parseInt(NyStr);
                Nz = Integer.parseInt(NzStr);
            }
            
            catch(NumberFormatException e)
            {
                if ( myRank == 0 )
                {
                    e.printStackTrace();
                }
            }
        }
        
        try
        {
            int[] i = new int[1];
            
            if ( myRank == 0 ) i[0] = Nx;
            MPI.COMM_WORLD.Bcast( i , 0 , 1 , MPI.INT , 0 );
            Nx = i[0];
            
            if ( myRank == 0 ) i[0] = Ny;
            MPI.COMM_WORLD.Bcast( i , 0 , 1 , MPI.INT , 0 );
            Ny = i[0];
            
            if ( myRank == 0 ) i[0] = Nz;
            MPI.COMM_WORLD.Bcast( i , 0 , 1 , MPI.INT , 0 );
            Nz = i[0];
            
        }
        
        catch ( mpi.MPIException e )
        {
            if ( myRank == 0 )
            {
                e.printStackTrace();
            }
        }
        
        gridInit();
        
        
        if ( myRank == 0 )
        {
            //dialog.updateResultField( "Calculation " + CalculationNumber );
            //dialog.updateResultField( "Initialization done:" );
            //dialog.updateResultField( " Nx: " + Nx );
            //dialog.updateResultField( " Ny: " + Ny );
            //dialog.updateResultField( " Nz: " + Nz );
            //dialog.updateResultField( " hx: " + hx );
            //dialog.updateResultField( " hy: " + hy );
            //dialog.updateResultField( " hz: " + hz );
            //dialog.updateResultField( "" );
        }
        
        for ( int i = 0 ; i < numprocs ; i++ )
        {
            int sx = get1DDecompositionStart(i);
            int ex = get1DDecompositionEnd(i);
        }
        
        try
        {
            int i = 0;
            do
            {
                exchange( v );
                sweep( u , v , rho );
                for ( int j = 0 ; j < MEDIUM_STEPS ; j++ )
                {
                    sweep( v , u , rho );
                    sweep( u , v , rho );
                }
                
                exchange( u );
                sweep( v , u , rho );
                for ( int j = 0 ; j < MEDIUM_STEPS ; j++ )
                {
                    sweep( u , v , rho );
                    sweep( v , u , rho );
                }
                
                diffTmp[0] = diff( u , v );
                MPI.COMM_WORLD.Allreduce( diffTmp , 0 , diff , 0 , 1 , MPI.DOUBLE , MPI.SUM );
                if ( i==0 ) firstDiff = diff[0];
                //if ( myRank == 0) dialog.updateResultField( "(iteration " + i + "): delta = " + diff[0] + " (aim = " + Error + ")" );
                i++;
            }
            while ( diff[0] > 0.01 * firstDiff );
            
            System.out.println("myRank= " + myRank);
            
            if ( myRank == 0 )
            {
                //dialog.updateResultField( "" );
                //dialog.updateResultField( "Done!" );
                //dialog.updateResultField( "" );
                //dialog.updateResultField( "After " + i + " iterations: delta = " + diff[0] + "\n\n" );
                //endTime = (new Date()).getTime();
                //dialog.updateResultField( "" );
                //dialog.updateResultField( "Time elapsed: " + (endTime-startTime) / 1000 );
            }
        }
        catch(mpi.MPIException e)
        {
            if ( myRank == 0 )
            {
                e.printStackTrace();
            }
        }
        
        
        // gather the whole solution in rank 0
        
        double solution[][][] = new double[Nx+2][Ny+2][Nz+2];
        
        if ( myRank == 0 )
        {
            for ( int x = startx ; x <= endx ; x++ )
            {
                for ( int j = 0 ; j < Ny+2 ; j++ ) System.arraycopy( u[x][j] , 0 , solution[x][j] , 0 , Nz+2 );
            }
        }
        
        try
        {
            for ( int proc = 1 ; proc < numprocs ; proc++ )
            {
                int sx = get1DDecompositionStart( proc );
                int ex = get1DDecompositionEnd( proc );
                
                for ( int x = sx ; x <= ex ; x++ )
                {
                    if ( myRank == proc )
                    {
                        for ( int j = 0 ; j < Ny+2 ; j++ ) System.arraycopy( u[x-sx+1][j] , 0 , msgSend , j*(Nz+2) , Nz+2 );
                        MPI.COMM_WORLD.Send( msgSend , 0 , (Ny+2)*(Nz+2) , MPI.DOUBLE , 0 , 0 );
                    }
                    
                    if ( myRank == 0 )
                    {
                        MPI.COMM_WORLD.Recv( msgRecv , 0 , (Ny+2)*(Nz+2) , MPI.DOUBLE , proc , 0 );
                        for ( int j = 0 ; j < Ny+2 ; j++ ) System.arraycopy( msgRecv , j*(Nz+2) , solution[x][j] , 0 , Nz+2 );
                    }
                }
            }
        }
        
        catch ( MPIException e )
        {
            if ( myRank == 0 )
            {
                e.printStackTrace();
            }
        }
        
        if ( myRank != 0 )
            waitCmd();
        
        return solution;
    }
    
    
    // 1D decomposition along the processes over the x axis of the cube
    // This function yields the first x of the process rank
    private int get1DDecompositionStart( int rank )
    {
        int R = (int)( Math.floor( (double)Nx / (double)numprocs ) );
        int deficit = Nx % numprocs;
        int sx = rank * R + 1;
        
        if ( rank < deficit ) sx = sx + rank;
        
        if ( rank >= deficit ) sx = sx + deficit;
        
        return sx;
    }
    
    
    // 1D decomposition along the processes over the x axis of the cube
    // This function yields the last x of the process rank
    private int get1DDecompositionEnd( int rank )
    {
        int R = (int)( Math.floor( (double)Nx / (double)numprocs ) );
        int deficit = Nx % numprocs;
        int ex = ( rank + 1 ) * R;
        
        if ( rank < deficit ) ex = ex + rank + 1;
        
        if ( rank >= deficit ) ex = ex + deficit;
        
        return ex;
    }
    
    // parses the boundary and rho functions
    // evaluates these functions in the portion of the grid belonging to each process
    // creates an initial solution (iteration 0) (all points evaluated to zero)
    // and evaluates the start and end x values of each process
    private void gridInit()
    {
        String varFaces[] = new String[2];
        String varRho[] = new String[3];
        double d3[] = new double[3];
        double d2[] = new double[2];
        int i , j , k;
        
        // These objects represent the mathematical funtions that define the boundary conditions
        // and the electric charge distribution inside the cube
        ParsedMultiVarFunction face1Function;
        ParsedMultiVarFunction face2Function;
        ParsedMultiVarFunction face3Function;
        ParsedMultiVarFunction face4Function;
        ParsedMultiVarFunction face5Function;
        ParsedMultiVarFunction face6Function;
        ParsedMultiVarFunction rhoFunction;
        ParsedMultiVarFunction initFunction;
        
        // These variables represent the spacing between samples
        hx = 1.0 / ( (double)Nx + 1.0 );
        hy = 1.0 / ( (double)Ny + 1.0 );
        hz = 1.0 / ( (double)Nz + 1.0 );
        
        startx = get1DDecompositionStart( myRank );
        endx   = get1DDecompositionEnd( myRank );
        
        // alocation of memory for the rho and V functions inside the section
        // of the cube belonging to each process
        rho        = new double [ endx - startx + 3 ] [ Ny+2 ] [ Nz+2 ];
        u          = new double [ endx - startx + 3 ] [ Ny+2 ] [ Nz+2 ];
        v          = new double [ endx - startx + 3 ] [ Ny+2 ] [ Nz+2 ];
        initialV   = new double [ endx - startx + 3 ] [ Ny+2 ] [ Nz+2 ];
        
        // alocation of memory for passing the frontier values between processes
        msgSend = new double [ (Ny+2) * (Nz+2) ];
        msgRecv = new double [ (Ny+2) * (Nz+2) ];
        
        try
        {
            varFaces[0] = "x";
            varFaces[1] = "y";
            face1Function = new ParsedMultiVarFunction( face1FnStr , varFaces );
            
            varFaces[0] = "x";
            varFaces[1] = "z";
            face2Function = new ParsedMultiVarFunction( face2FnStr , varFaces );
            
            varFaces[0] = "x";
            varFaces[1] = "y";
            face3Function = new ParsedMultiVarFunction( face3FnStr , varFaces );
            
            varFaces[0] = "x";
            varFaces[1] = "z";
            face4Function = new ParsedMultiVarFunction( face4FnStr , varFaces );
            
            varFaces[0] = "y";
            varFaces[1] = "z";
            face5Function = new ParsedMultiVarFunction( face5FnStr , varFaces );
            
            varFaces[0] = "y";
            varFaces[1] = "z";
            face6Function = new ParsedMultiVarFunction( face6FnStr , varFaces );
            
            varRho[0] = "x";
            varRho[1] = "y";
            varRho[2] = "z";
            rhoFunction = new ParsedMultiVarFunction( rhoFnStr , varRho );
            initFunction = new ParsedMultiVarFunction( "0" , varRho );
            
            
            // initialization of rho and initial solutions
            for ( i = 0 ; i < ( endx - startx + 3 ) ; i++ ) for ( j = 0 ; j < Ny + 2; j++ ) for ( k = 0 ; k < Nz + 2 ; k++ )
            {
                d3[0] = hx * ( startx + i - 1 );
                d3[1] = hy * j;
                d3[2] = hz * k;
                rho[i][j][k] = rhoFunction.evaluate( d3 );
                u[i][j][k] = initFunction.evaluate( d3 );
                v[i][j][k] = u[i][j][k];
                initialV[i][j][k] = u[i][j][k];
            }
            
            // initialization of boundary at z and -z surfaces (face1Function and face3Function)
            for ( i = 0 ; i < ( endx - startx + 3 ) ; i++ ) for ( j = 0 ; j < Ny + 2; j++ )
            {
                d2[0] = hx * ( startx + i - 1 );
                d2[1] = hy * j;
                
                u[i][j][0] = face1Function.evaluate( d2 );
                v[i][j][0] = u[i][j][0];
                
                u[i][j][Nz+1] = face3Function.evaluate( d2 );
                v[i][j][Nz+1] = u[i][j][Nz+1];
            }
            
            
            // initialization of boundary at y and -y surfaces (face2Function face4Function)
            for ( i = 0 ; i < ( endx - startx + 3 ) ; i++ )
                for ( k = 0 ; k < Nz + 2; k++ )
                {
                    d2[0] = hx * ( startx + i - 1 );
                    d2[1] = hz * k;
                    
                    u[i][Ny+1][k] = face2Function.evaluate( d2 );
                    v[i][Ny+1][k] = u[i][Ny+1][k];
                    
                    u[i][0][k] = face4Function.evaluate( d2 );
                    v[i][0][k] = u[i][0][k];
                }
            
            
            // initialization of boundary at surface x (face5Function)
            if ( myRank + 1 == numprocs )
            {
                for ( j = 0 ; j < Ny + 2 ; j++ )
                    for ( k = 0 ; k < Nz + 2; k++ )
                    {
                        d2[0] = hy * j;
                        d2[1] = hz * k;
                        
                        u[endx - startx + 2][j][k] = face5Function.evaluate( d2 );
                        v[endx - startx + 2][j][k] = u[endx - startx + 2][j][k];
                    }
            }
            
            
            // initialization of boundary at surface -x (face6Function)
            if ( myRank == 0 )
            {
                for ( j = 0 ; j < Ny + 2 ; j++ )
                    for ( k = 0 ; k < Nz + 2; k++ )
                    {
                        d2[0] = hy * j;
                        d2[1] = hz * k;
                        
                        u[0][j][k] = face6Function.evaluate( d2 );
                        v[0][j][k] = u[0][j][k];
                    }
            }
            
        }
        
        catch ( org.opensourcephysics.numerics.ParserException e )
        {
            if ( myRank == 0 )
            {
                e.printStackTrace();
            }
        }
        
    }
    
    
    // exchange ghost bottom (startx-1) rows with bottom neighbour and ghost top (endx+1) rows with top neighbour.
    private void exchange( double[][][] a ) throws MPIException
    {
        try
        {
            if ( topNeighbour != MPI.PROC_NULL )
            {
                for ( int j = 0 ; j < Ny+2 ; j++ ) System.arraycopy( a[endx-startx+1][j] , 0 , msgSend , j*(Nz+2) , Nz+2 );
                MPI.COMM_WORLD.Send( msgSend , 0 , (Ny+2)*(Nz+2) , MPI.DOUBLE , topNeighbour , 0 );
            }
            
            if ( bottomNeighbour != MPI.PROC_NULL )
            {
                MPI.COMM_WORLD.Recv( msgRecv , 0 , (Ny+2)*(Nz+2) , MPI.DOUBLE , bottomNeighbour , 0 );
                for ( int j = 0 ; j < Ny+2 ; j++ ) System.arraycopy( msgRecv , j*(Nz+2) , a[0][j] , 0 , Nz+2 );
            }
            
            if ( bottomNeighbour != MPI.PROC_NULL )
            {
                for ( int j = 0 ; j < Ny+2 ; j++ ) System.arraycopy( a[1][j] , 0 , msgSend , j*(Nz+2) , Nz+2 );
                MPI.COMM_WORLD.Send( msgSend , 0 , (Ny+2)*(Nz+2) , MPI.DOUBLE , bottomNeighbour , 0 );
            }
            
            if ( topNeighbour != MPI.PROC_NULL )
            {
                MPI.COMM_WORLD.Recv( msgRecv , 0 ,(Ny+2)*(Nz+2) , MPI.DOUBLE , topNeighbour , 0 );
                for ( int j = 0 ; j < Ny+2 ; j++ ) System.arraycopy( msgRecv , j*(Nz+2) , a[endx-startx+2][j] , 0 , Nz+2 );
            }
            
        }
        catch ( MPIException e )
        {
            if ( myRank == 0 )
            {
                e.printStackTrace();
            }
        }
    }
    
    
    // perform one iteration of the Jacobi method. the result is put in out.
    private void sweep( double[][][] out , double[][][] in  , double[][][] rho )
    {
        double r = 0.5 * ( hx*hx * hy*hy * hz*hz ) / ( hy*hy*hz*hz + hx*hx*hz*hz + hx*hx*hy*hy );
        double hx2 = hx*hx;
        double hy2 = hy*hy;
        double hz2 = hz*hz;
        
        for ( int i = 1 ; i < endx - startx + 2 ; i++ )
            for ( int j = 1 ; j < Ny+1 ; j++ )
                for ( int k = 1 ; k < Nz+1 ; k++ )
                    out[i][j][k] = r * ( ( in[i+1][j][k] + in[i-1][j][k] ) / hx2 + ( in[i][j+1][k] + in[i][j-1][k] ) / hy2 + ( in[i][j][k+1] + in[i][j][k-1] ) / hz2 + rho[i][j][k] / Epsilon0 );
    }
    
    
    // calculate the difference between two iterations
    private double diff( double[][][] a , double[][][] b )
    {
        double diff = 0;
        double d;
        
        for ( int i = 1 ; i < endx - startx + 2 ; i++ ) for ( int j = 1 ; j < Ny+1 ; j++ ) for ( int k = 1 ; k < Nz+1 ; k++ )
        {
            d = a[i][j][k] - b[i][j][k];
            diff = diff + ( d * d );
        }
        
        return diff;
    }
        
    
    public static void main( String[] args ) throws Exception
    {
        new PoissonCalculator( args );
    }
}
